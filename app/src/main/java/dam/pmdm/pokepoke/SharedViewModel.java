package dam.pmdm.pokepoke;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel compartido que mantiene el estado relacionado con la eliminación de Pokémon.
 * Esta clase es responsable de almacenar y gestionar los datos de forma que sobrevivan
 * a los cambios de configuración.
 */
public class SharedViewModel extends ViewModel {

    // MutableLiveData que contiene el valor de si se deben eliminar los Pokémon
    private final MutableLiveData<Boolean> shouldDeletePokemons = new MutableLiveData<>();

    /**
     * Obtiene un LiveData que indica si se deben eliminar los Pokémon.
     *
     * @return LiveData<Boolean> que contiene el estado de eliminación de los Pokémon.
     */
    public LiveData<Boolean> getShouldDeletePokemons() {
        return shouldDeletePokemons;
    }

    /**
     * Establece el valor que indica si se deben eliminar los Pokémon.
     *
     * @param value Valor booleano que indica si se deben eliminar los Pokémon.
     */
    public void setShouldDeletePokemons(Boolean value) {
        shouldDeletePokemons.setValue(value);
    }
}


