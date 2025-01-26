package dam.pmdm.pokepoke;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento que muestra los Pokémon capturados.
 * Permite visualizar los detalles de cada Pokémon y eliminarlos.
 */
public class FragmentCaptured extends Fragment {

    private RecyclerView recyclerView; // RecyclerView para mostrar los Pokémon
    private PokeAdapter pokeAdapter; // Adaptador para el RecyclerView
    private List<PokeData> capturedPokemons; // Lista de Pokémon
    private SharedPreferences sharedPreferences; // SharedPreferences para almacenar los datos
    private FirebaseFirestore firestore; // Instancia de FirebaseFirestore
    private SharedViewModel sharedViewModel; // ViewModel para la gestión del estado

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_captured, container, false);

        // Inicializar Firestore, ShredPreferences, RecyclerView y Adaptador
        firestore = FirebaseFirestore.getInstance();

        sharedPreferences = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);

        initializeRecyclerView(rootView);

        // Cargar datos de Pokémon capturados
        loadCapturedPokemons();

        // Eliminar todos los Pokémon capturados desde el ViewModel
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getShouldDeletePokemons().observe(getViewLifecycleOwner(), shouldDelete -> {
            if (shouldDelete != null && shouldDelete) {
                deleteAllCapturedPokemons();
                sharedViewModel.setShouldDeletePokemons(false); // Resetear el flag
            }
        });

        return rootView;
    }

    /**
     * Inicializa el RecyclerView para mostrar la lista de Pokémon capturados.
     * Configura el adaptador y los gestos de deslizamiento para eliminar elementos.
     *
     * @param rootView Vista raíz del fragmento.
     */
    private void initializeRecyclerView(View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        capturedPokemons = new ArrayList<>();
        pokeAdapter = new PokeAdapter(capturedPokemons, pokemon -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("selectedPokemon", (Serializable) pokemon);

            // Navegar al fragmento de detalles
            Navigation.findNavController(requireView()).navigate(R.id.action_fragmentCaptured_to_fragmentPokemonDetails, bundle);
        });

        recyclerView.setAdapter(pokeAdapter);

        // Deslizamiento para eliminar
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                PokeData pokemon = capturedPokemons.get(position);

                // Eliminar de Firestore
                deletePokemonFromFirestore(pokemon);

                // Eliminar de la lista local
                capturedPokemons.remove(position);
                pokeAdapter.notifyItemRemoved(position);
            }
        }).attachToRecyclerView(recyclerView);
    }

    /**
     * Carga los Pokémon capturados desde Firestore y actualiza la lista local.
     */
    private void loadCapturedPokemons() {
        CollectionReference collection = firestore.collection("capturedPokemons");

        collection.get().addOnSuccessListener(querySnapshot -> {
            capturedPokemons.clear();
            for (var document : querySnapshot) {
                PokeData pokemon = document.toObject(PokeData.class);
                capturedPokemons.add(pokemon);
            }
            pokeAdapter.notifyDataSetChanged();
        });
    }

    /**
     * Elimina un Pokémon de Firestore.
     *
     * @param pokemon El Pokémon a eliminar.
     */
    private void deletePokemonFromFirestore(PokeData pokemon) {
        firestore.collection("capturedPokemons")
                .document(String.valueOf(pokemon.getId()))
                .delete();
    }

    /**
     * Elimina todos los Pokémon capturados.
     * Este método es llamado desde el fragmento de configuraciones.
     */
    public void deleteAllCapturedPokemons() {
        firestore.collection("capturedPokemons")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        document.getReference().delete();
                    }

                    // Actualizar la lista
                    capturedPokemons.clear();
                    pokeAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Manejar el error
                    showErrorDialog();
                });
    }

    /**
     * Muestra un diálogo de error si ocurre un fallo al eliminar los Pokémon.
     */
    private void showErrorDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.error)
                .setMessage(R.string.fragment_not_loaded)
                .setPositiveButton(R.string.ok, null)
                .show();
    }
}
