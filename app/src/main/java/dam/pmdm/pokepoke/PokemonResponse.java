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
     * Clase interna que representa un resultado individual con el nombre y la URL de un Pokémon.
     */
    public static class Result {

        @SerializedName("name")
        private String name;

        @SerializedName("url")
        private final String url;

        public Result(String url) {
            this.url = url;
        }


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
         * La URL tiene el formato: <a href="https://pokeapi.co/api/v2/pokemon/">https://pokeapi.co/api/v2/pokemon/"</a>{id}/
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

    }
}
