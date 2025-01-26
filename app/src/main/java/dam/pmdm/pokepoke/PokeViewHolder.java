package dam.pmdm.pokepoke;

import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import dam.pmdm.pokepoke.databinding.PokeCardviewBinding;

/**
 * ViewHolder para representar un ítem en la lista de Pokémon en el RecyclerView.
 * Este clase se encarga de vincular los datos del Pokémon a los elementos visuales en la vista.
 */
public class PokeViewHolder extends RecyclerView.ViewHolder {

    private final PokeCardviewBinding binding;

    /**
     * Constructor que inicializa el ViewHolder con el binding correspondiente.
     *
     * @param binding El binding asociado con el diseño de la tarjeta del Pokémon.
     */
    public PokeViewHolder(PokeCardviewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    /**
     * Metodo para vincular los datos de un Pokémon a la vista del ViewHolder.
     *
     * @param pokeData El objeto que contiene la información del Pokémon.
     */
    public void bind(PokeData pokeData) {
        // Asignar nombre, tipo e índice del Pokémon
        binding.name.setText(pokeData.getName());
        binding.type.setText(String.join(", ", pokeData.getTypes()));
        binding.pokemonIndex.setText(String.format("#%d", pokeData.getIndex())); // Mostrar el índice


        // Asignar peso y altura
        binding.pokemonWeight.setText(pokeData.getWeight() + " kg"); // Peso en kg
        binding.pokemonHeight.setText(pokeData.getHeight() + " m"); // Altura en metros

        // Cargar imagen del Pokémon utilizando Glide
        Glide.with(binding.image.getContext())
                .load(pokeData.getImageUrl())
                .into(binding.image);

        // Descripción para accesibilidad (descripción de la imagen del Pokémon)
        binding.image.setContentDescription(pokeData.getName() + " image");

        // Diferenciación visual para Pokémon capturados
        if (pokeData.isCaptured()) {
            // Cambia el color de fondo a un tono distintivo si el Pokémon ha sido capturado
            binding.cardView.setCardBackgroundColor(Color.rgb(251, 221, 215)); // Color de fondo para Pokémon capturados
        } else {
            // Color predeterminado para Pokémon no capturados
            binding.cardView.setCardBackgroundColor(Color.WHITE);
        }
    }
}
