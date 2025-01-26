package dam.pmdm.pokepoke;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Clase para configurar y proporcionar una instancia de Retrofit.
 * Esta clase se encarga de crear una instancia singleton de Retrofit que se usa para
 * realizar solicitudes HTTP a la API de Pokémon.
 */
public class RetrofitClient {
    // URL base de la API de Pokémon
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";

    // Instancia de Retrofit
    private static Retrofit retrofit;

    /**
     * Método para obtener una instancia única de Retrofit.
     * Si la instancia de Retrofit no existe, se crea una nueva.
     *
     * @return La instancia de Retrofit configurada.
     */
    public static Retrofit getClient() {
        // Verificar si la instancia ya fue creada
        if (retrofit == null) {
            try {
                // Construir una nueva instancia de Retrofit con la URL base y el convertidor de Gson
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)  // Establecer la URL base de la API
                        .addConverterFactory(GsonConverterFactory.create()) // Convertir respuestas JSON en objetos Java
                        .build();
            } catch (IllegalArgumentException e) {
                // Si la URL base es incorrecta, se captura y se muestra un error
                Log.e("RetrofitClient", "Invalid base URL", e);
            }
        }
        return retrofit; // Devolver la instancia de Retrofit
    }
}
