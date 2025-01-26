package dam.pmdm.pokepoke;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonApiService {

    /**
     * Obtiene la lista de Pokémon con paginación.
     *
     * @param offset Índice inicial para la paginación.
     * @param limit  Número de elementos a recuperar.
     * @return Una llamada a la API que devuelve un objeto PokemonResponse.
     */
    @GET("pokemon")
    Call<PokemonResponse> getPokemonList(
            @Query("offset") int offset,
            @Query("limit") int limit
    );
    // Obtener detalles de un Pokémon por ID
    @GET("pokemon/{id}")
    Call<PokemonDetailsResponse> getPokemonDetails(@Path("id") int id);
}
