package dam.pmdm.pokepoke;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragmento que muestra la lista de Pokémon en un RecyclerView.
 * Permite al usuario capturar Pokémon.
 */
public class FragmentPokedex extends Fragment {

    private RecyclerView recyclerView;
    private PokeAdapter pokeAdapter;
    private ArrayList<PokeData> pokeList;
    private ProgressBar progressBar;
    private FirebaseFirestore firestore;

    /**
     * Infla la vista y configura los elementos principales:
     * RecyclerView, ProgressBar y el adaptador.
     *
     * @param inflater El inflador de la vista.
     * @param container El contenedor del fragmento.
     * @param savedInstanceState El estado guardado.
     * @return La vista inflada del fragmento.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pokedex, container, false);

        // Inicializar Firestore, ProgressBar y RecyclerView
        firestore = FirebaseFirestore.getInstance();

        progressBar = rootView.findViewById(R.id.progressBar);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configurar Adaptador con un listener para capturar Pokémon
        pokeAdapter = new PokeAdapter(poke -> capturePokemon(poke));
        recyclerView.setAdapter(pokeAdapter);

        // Cargar la lista de Pokémon
        fetchPokemonList();

        return rootView;
    }

    /**
     * Solicita la lista de Pokémon a la API de Pokémon.
     */
    private void fetchPokemonList() {
        progressBar.setVisibility(View.VISIBLE); // Mostrar el ProgressBar

        PokemonApiService apiService = RetrofitClient.getClient().create(PokemonApiService.class);

        apiService.getPokemonList(0, 150).enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                progressBar.setVisibility(View.GONE); // Ocultar el ProgressBar

                if (response.isSuccessful() && response.body() != null) {
                    pokeList = new ArrayList<>();

                    // Hacer una solicitud para obtener detalles  de cada Pokémon
                    for (PokemonResponse.Result result : response.body().getResults()) {
                        // Realizar otra llamada para obtener los detalles de cada Pokémon (peso, altura, tipos)
                        apiService.getPokemonDetails(result.getIdFromUrl()).enqueue(new Callback<PokemonDetailsResponse>() {
                            @Override
                            public void onResponse(Call<PokemonDetailsResponse> call, Response<PokemonDetailsResponse> detailsResponse) {
                                if (detailsResponse.isSuccessful() && detailsResponse.body() != null) {
                                    PokemonDetailsResponse details = detailsResponse.body();
                                    pokeList.add(new PokeData(
                                            result.getName(),
                                            extractTypeNames(details.getTypes()), // Lista de tipos
                                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" +
                                                    result.getIdFromUrl() + ".png", // URL de la imagen
                                            result.getIdFromUrl(),
                                            details.getWeight() / 10f,  // Peso (en kilogramos)
                                            details.getHeight() / 10f,  // Altura (en metros)
                                            false
                                    ));

                                    // Pasar los datos al adaptador después de agregar un Pokémon
                                    pokeAdapter.setPokeList(pokeList);
                                }
                            }

                            @Override
                            public void onFailure(Call<PokemonDetailsResponse> call, Throwable t) {
                                // Manejo de fallos en la solicitud de detalles
                                Toast.makeText(getContext(), R.string.error_loading_pokemon, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE); // Ocultar el ProgressBar
                Toast.makeText(getContext(), R.string.error_loading_data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Extrae los tipos de los Pokémon.
     *
     * @param types La lista de tipos del Pokémon.
     * @return Una lista de nombres de tipos.
     */
    private List<String> extractTypeNames(List<PokemonDetailsResponse.Type> types) {
        List<String> typeNames = new ArrayList<>();
        for (PokemonDetailsResponse.Type type : types) {
            typeNames.add(type.getType().getName()); // Obtener el nombre del tipo
        }
        return typeNames;
    }

    /**
     * Captura un Pokémon y lo guarda en Firebase Firestore.
     *
     * @param pokemon El Pokémon que ha sido capturado.
     */
    private void capturePokemon(PokeData pokemon) {
        // Cambiar el estado a capturado
        pokemon.setCaptured(true);

        // Guardar en Firebase Firestore
        firestore.collection("capturedPokemons")
                .document(String.valueOf(pokemon.getId()))
                .set(pokemon)
                .addOnSuccessListener(aVoid -> {
                    // Mostrar un mensaje de éxito
                    Toast.makeText(getContext(), pokemon.getName() + R.string.captured, Toast.LENGTH_SHORT).show();

                    // Actualizar el adaptador para reflejar el cambio de estado
                    pokeAdapter.notifyDataSetChanged(); // Refrescar el RecyclerView
                })
                .addOnFailureListener(e -> {
                    // Mostrar un mensaje de error
                    Toast.makeText(getContext(), R.string.error + pokemon.getName(), Toast.LENGTH_SHORT).show();
                });
    }
}
