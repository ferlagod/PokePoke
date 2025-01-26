package dam.pmdm.pokepoke;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Representa la respuesta de la API de Pokémon que contiene una lista de resultados con los nombres
 * y URLs de los Pokémon.
 */
public class PokemonResponse {

    @SerializedName("results") // Nombre exacto en la respuesta JSON
    private List<Result> results;

    /**
     * Obtiene la lista de resultados de Pokémon.
     *
     * @return Una lista de objetos Result que representan los Pokémon.
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     * Establece la lista de resultados de Pokémon.
     *
     * @param results Una lista de objetos Result que representan los Pokémon.
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }

    /**
     * Clase interna que representa un resultado individual con el nombre y la URL de un Pokémon.
     */
    public static class Result {

        @SerializedName("name")
        private String name;

        @SerializedName("url")
        private String url;

        @SerializedName("weight")
        private int weight;

        @SerializedName("height")
        private int height;

        @SerializedName("index")
        private int index;

        @SerializedName("type")
        private String type;



        /**
         * Obtiene el nombre del Pokémon.
         *
         * @return El nombre del Pokémon.
         */
        public String getName() {
            return name;
        }

        /**
         * Establece el nombre del Pokémon.
         *
         * @param name El nombre del Pokémon.
         */
        public void setName(String name) {
            this.name = name;
        }


        /**
         * Extrae el ID del Pokémon desde la URL.
         * La URL tiene el formato: https://pokeapi.co/api/v2/pokemon/{id}/
         *
         * @return El ID del Pokémon extraído de la URL, o -1 si la URL es inválida o no se puede parsear.
         */
        public int getIdFromUrl() {
            if (url == null || url.isEmpty()) {
                return -1;
            }
            String[] parts = url.split("/");
            try {
                return Integer.parseInt(parts[parts.length - 1]); // Última parte de la URL
            } catch (NumberFormatException e) {
                return -1; // Devuelve -1 si no se puede parsear el ID
            }
        }

        public int getWeight() {
            return weight;
        }

        public int getHeight() {
            return height;
        }

        public int getIndex() {
            return index;
        }

        public String getType() {
            return type;
        }
    }
}
