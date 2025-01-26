package dam.pmdm.pokepoke;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa un Pokémon con su información básica, como nombre, tipos, imagen, peso, altura y estado de captura.
 * Implementa Serializable para permitir su almacenamiento y transferencia.
 */
public class PokeData implements Serializable {

    private String name; // Nombre del Pokémon
    private List<String> types; // Lista de tipos
    private String imageUrl; // URL de la imagen
    private int id; // Íd del Pokémon
    private float weight; // Peso en kg
    private float height; // Altura en metros
    private boolean isCaptured; // Estado de captura
    private int index; // Índice del Pokémon en la Pokédex

    /**
     * Constructor vacío requerido por Firebase.
     */
    public PokeData() {}

    /**
     * Constructor completo para crear un objeto PokeData con los parámetros proporcionados.
     *
     * @param name El nombre del Pokémon.
     * @param types La lista de tipos del Pokémon.
     * @param imageUrl La URL de la imagen del Pokémon.
     * @param id El índice del Pokémon en la Pokédex.
     * @param weight El peso del Pokémon en kg.
     * @param height La altura del Pokémon en metros.
     * @param isCaptured El estado de captura del Pokémon.
     */
    public PokeData(String name, List<String> types, String imageUrl, int id, float weight, float height, boolean isCaptured) {
        this.name = name;
        this.types = types != null ? types : new ArrayList<>(); // Prevenir valores nulos
        this.imageUrl = imageUrl;
        this.id = id;
        this.weight = weight;
        this.height = height;
        this.isCaptured = isCaptured;
        this.index=index;
    }

    // Getters y Setters

    /**
     * Obtiene el nombre del Pokémon.
     *
     * @return El nombre del Pokémon.
     */
    public String getName() { return name; }

    /**
     * Establece el nombre del Pokémon.
     *
     * @param name El nombre del Pokémon.
     */
    public void setName(String name) { this.name = name; }

    /**
     * Obtiene la lista de tipos del Pokémon.
     *
     * @return La lista de tipos del Pokémon.
     */
    public List<String> getTypes() { return types; }


    /**
     * Obtiene la URL de la imagen del Pokémon.
     *
     * @return La URL de la imagen del Pokémon.
     */
    public String getImageUrl() { return imageUrl; }


    /**
     * Obtiene el índice del Pokémon en la Pokédex.
     *
     * @return El índice del Pokémon.
     */
    public int getId() { return id; }

    /**
     * Establece el índice del Pokémon en la Pokédex.
     *
     * @param id El índice del Pokémon.
     */
    public void setId(int id) { this.id = id; }

    /**
     * Obtiene el peso del Pokémon en kg.
     *
     * @return El peso del Pokémon.
     */
    public float getWeight() { return weight; }

    /**
     * Obtiene la altura del Pokémon en metros.
     *
     * @return La altura del Pokémon.
     */
    public float getHeight() { return height; }

    /**
     * Obtiene el estado de captura del Pokémon.
     *
     * @return true si el Pokémon está capturado, false en caso contrario.
     */
    public boolean isCaptured() { return isCaptured; }

    /**
     * Establece el estado de captura del Pokémon.
     *
     * @param captured true si el Pokémon está capturado, false en caso contrario.
     */
    public void setCaptured(boolean captured) { isCaptured = captured; }

    // Métodos derivados

    /**
     * Obtiene la altura del Pokémon formateada con dos decimales.
     *
     * @return La altura formateada como una cadena.
     */
    @SuppressLint("DefaultLocale")
    public String getFormattedHeight() {
        return String.format("%.2f m", height);
    }

    /**
     * Obtiene el peso del Pokémon formateado con dos decimales.
     *
     * @return El peso formateado como una cadena.
     */
    @SuppressLint("DefaultLocale")
    public String getFormattedWeight() {
        return String.format("%.2f kg", weight);
    }

    /**
     * Obtiene los tipos del Pokémon como una cadena formateada.
     *
     * @return Los tipos del Pokémon como una cadena separada por comas.
     */
    public String getFormattedTypes() {
        return String.join(", ", types);
    }

    /**
     * Devuelve una representación en cadena del objeto PokeData.
     *
     * @return Una cadena que describe el Pokémon.
     */
    @Override
    public String toString() {
        return "PokeData{" +
                "name='" + name + '\'' +
                ", types=" + types +
                ", id=" + id +
                ", weight=" + weight +
                ", height=" + height +
                ", isCaptured=" + isCaptured +
                '}';
    }


    /**
     * Compara este objeto PokeData con otro objeto para ver si son iguales.
     *
     * @param o El objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PokeData pokeData = (PokeData) o;
        return id == pokeData.id &&
                Float.compare(pokeData.weight, weight) == 0 &&
                Float.compare(pokeData.height, height) == 0 &&
                isCaptured == pokeData.isCaptured &&
                Objects.equals(name, pokeData.name) &&
                Objects.equals(types, pokeData.types) &&
                Objects.equals(imageUrl, pokeData.imageUrl);
    }

    /**
     * Devuelve el código hash del objeto PokeData.
     *
     * @return El código hash del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, types, imageUrl, id, weight, height, isCaptured);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
