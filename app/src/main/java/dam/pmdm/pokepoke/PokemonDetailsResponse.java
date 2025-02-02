package dam.pmdm.pokepoke;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Representa la respuesta de los detalles de un Pokémon obtenida de la API de Pokémon.
 * Incluye los tipos, peso y altura del Pokémon.
 */
public class PokemonDetailsResponse {

    @SerializedName("types")
    private List<Type> types;

    @SerializedName("weight")
    private final int weight;

    @SerializedName("height")
    private final int height;

    public PokemonDetailsResponse(List<Type> types, int weight, int height) {
        this.types = types;
        this.weight = weight;
        this.height = height;
    }

    /**
     * Obtiene la lista de tipos del Pokémon.
     *
     * @return Lista de tipos del Pokémon.
     */
    public List<Type> getTypes() {
        return types;
    }

    /**
     * Obtiene el peso del Pokémon.
     *
     * @return El peso del Pokémon en hectogramos.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Obtiene la altura del Pokémon.
     *
     * @return La altura del Pokémon en decímetros.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Clase interna que representa un tipo de Pokémon.
     */
    public static class Type {

        @SerializedName("type")
        private final TypeInfo type;

        public Type(TypeInfo type) {
            this.type = type;
        }

        /**
         * Obtiene la información del tipo del Pokémon.
         *
         * @return El objeto que contiene información sobre el tipo.
         */
        public TypeInfo getType() {
            return type;
        }

        /**
         * Clase interna que contiene la información sobre el tipo de Pokémon.
         */
        public static class TypeInfo {

            @SerializedName("name")
            private final String name;

            public TypeInfo(String name) {
                this.name = name;
            }

            /**
             * Obtiene el nombre del tipo del Pokémon.
             *
             * @return El nombre del tipo (por ejemplo, "fire", "water").
             */
            public String getName() {
                return name;
            }
        }
    }
}
