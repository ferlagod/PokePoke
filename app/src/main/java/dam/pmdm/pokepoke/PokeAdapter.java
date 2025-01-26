package dam.pmdm.pokepoke;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dam.pmdm.pokepoke.databinding.PokeCardviewBinding;

/**
 * Adaptador para mostrar la lista de Pokémon en un RecyclerView.
 * Permite capturar Pokémon y actualizar su estado en la interfaz.
 */
public class PokeAdapter extends RecyclerView.Adapter<PokeViewHolder> {

    private final List<PokeData> pokeList;
    private final OnPokemonCaptureListener captureListener;

    /**
     * Constructor para el adaptador sin lista inicial de Pokémon.
     *
     * @param captureListener El listener para capturar Pokémon.
     */
    public PokeAdapter(OnPokemonCaptureListener captureListener) {
        this.pokeList = new ArrayList<>();
        this.captureListener = captureListener;
    }

    /**
     * Constructor para el adaptador con lista inicial de Pokémon.
     *
     * @param pokeList Lista de Pokémon.
     * @param captureListener El listener para capturar Pokémon.
     */
    public PokeAdapter(List<PokeData> pokeList, OnPokemonCaptureListener captureListener) {
        this.pokeList = pokeList;
        this.captureListener = captureListener;
    }

    /**
     * Actualiza la lista de Pokémon.
     *
     * @param pokeList La nueva lista de Pokémon.
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setPokeList(List<PokeData> pokeList) {
        this.pokeList.clear();
        this.pokeList.addAll(pokeList);
        notifyDataSetChanged();
    }

    /**
     * Crea y devuelve un nuevo ViewHolder para un Pokémon.
     *
     * @param parent El contenedor de vista en el que se inflará el item.
     * @param viewType El tipo de vista.
     * @return El ViewHolder para el Pokémon.
     */
    @NonNull
    @Override
    public PokeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PokeCardviewBinding binding = PokeCardviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PokeViewHolder(binding);
    }

    /**
     * Asocia los datos del Pokémon con el ViewHolder.
     *
     * @param holder El ViewHolder al que se le asignarán los datos.
     * @param position La posición del Pokémon en la lista.
     */
    @Override
    public void onBindViewHolder(@NonNull PokeViewHolder holder, int position) {
        PokeData pokemon = pokeList.get(position);
        holder.bind(pokemon); // Pasa el objeto pokemon a bind()

        holder.itemView.setOnClickListener(v -> {
            // Cuando un Pokémon es capturado
            pokemon.setCaptured(true); // Actualiza el estado a capturado
            captureListener.onPokemonCaptured(pokemon); // Llama al listener para hacer cualquier acción adicional
            notifyItemChanged(position); // Notifica que el item ha cambiado para actualizar su color
        });
    }

    /**
     * Obtiene la cantidad de elementos en la lista de Pokémon.
     *
     * @return La cantidad de Pokémon en la lista.
     */
    @Override
    public int getItemCount() {
        return pokeList.size();
    }

    /**
     * Interface para manejar la captura de Pokémon.
     */
    public interface OnPokemonCaptureListener {
        /**
         * Llamado cuando un Pokémon es capturado.
         *
         * @param pokemon El Pokémon que ha sido capturado.
         */
        void onPokemonCaptured(PokeData pokemon);
    }
}

