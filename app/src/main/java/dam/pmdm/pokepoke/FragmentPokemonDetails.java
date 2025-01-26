package dam.pmdm.pokepoke;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

/**
 * Fragmento que muestra los detalles de un Pokémon.
 * Permite ver la información detallada y eliminar un Pokémon de los capturados.
 */
public class FragmentPokemonDetails extends Fragment {

    private ImageView pokemonImage; // Imagen del Pokémon
    private TextView pokemonName, pokemonIndex, pokemonTypes, pokemonWeight, pokemonHeight; // Detalles del Pokémon
    private Button btnDeletePokemon; // Botón para eliminar el Pokémon

    private FirebaseFirestore firestore; // Instancia de FirebaseFirestore
    private PokeData selectedPokemon; // Pokémon seleccionado

    public FragmentPokemonDetails() {
        // Constructor vacío requerido para fragmentos
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout
        return inflater.inflate(R.layout.fragment_pokemon_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar Firestore
        firestore = FirebaseFirestore.getInstance();

        // Referencias a las vistas
        pokemonImage = view.findViewById(R.id.pokemon_image);
        pokemonName = view.findViewById(R.id.pokemon_name);
        pokemonIndex = view.findViewById(R.id.pokemon_index);
        pokemonTypes = view.findViewById(R.id.pokemon_types);
        pokemonWeight = view.findViewById(R.id.pokemon_weight);
        pokemonHeight = view.findViewById(R.id.pokemon_height);
        btnDeletePokemon = view.findViewById(R.id.btn_delete_pokemon);

        // Obtener datos del Pokémon desde los argumentos
        if (getArguments() != null) {
            selectedPokemon = (PokeData) getArguments().getSerializable("selectedPokemon");
            if (selectedPokemon != null) {
                // Mostrar los detalles del Pokémon
                displayPokemonDetails(selectedPokemon);
            }
        }

        // Configurar el botón de eliminación
        btnDeletePokemon.setOnClickListener(v -> deletePokemon(view));
    }

    /**
     * Muestra los detalles del Pokémon seleccionado.
     *
     * @param pokemon El Pokémon cuyo detalle se va a mostrar.
     */
    private void displayPokemonDetails(PokeData pokemon) {
        // Mostrar los datos en las vistas
        pokemonName.setText(pokemon.getName());
        pokemonIndex.setText(getString(R.string.pokemon_index, pokemon.getId()));
        pokemonTypes.setText(formatTypes(pokemon.getTypes()));
        pokemonWeight.setText(getString(R.string.pokemon_weight, pokemon.getWeight()));
        pokemonHeight.setText(getString(R.string.pokemon_height, pokemon.getHeight()));

        // Cargar la imagen del Pokémon usando Glide
        Glide.with(this)
                .load(pokemon.getImageUrl())
                .placeholder(android.R.drawable.ic_menu_gallery) // Imagen de carga
                .error(R.drawable.pokepoke) // Imagen de error
                .into(pokemonImage);
    }

    /**
     * Formatea la lista de tipos de Pokémon como un string.
     *
     * @param types Lista de tipos del Pokémon.
     * @return Un string con los tipos formateados.
     */
    private String formatTypes(List<String> types) {
        if (types == null || types.isEmpty()) {
            return getString(R.string.unknown_types); // Si no hay tipos, se muestra "Desconocidos"
        }
        return String.join(", ", types); // Une los tipos con comas
    }

    /**
     * Elimina el Pokémon de la base de datos.
     *
     * @param view La vista donde se realizó el clic para eliminar el Pokémon.
     */
    private void deletePokemon(View view) {
        if (selectedPokemon != null) {
            // Eliminar el Pokémon de Firestore
            firestore.collection("capturedPokemons")
                    .document(String.valueOf(selectedPokemon.getId()))
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        // Mostrar mensaje de éxito
                        Toast.makeText(requireContext(), getString(R.string.pokemon_deleted, selectedPokemon.getName()), Toast.LENGTH_SHORT).show();

                        // Navegar hacia atrás al fragmento de Pokémon capturados
                        Navigation.findNavController(view).navigateUp();
                    })
                    .addOnFailureListener(e -> {
                        // Mostrar mensaje de error
                        Toast.makeText(requireContext(), getString(R.string.error_deleting_pokemon), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}

