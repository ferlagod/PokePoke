package dam.pmdm.pokepoke;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

/**
 * Fragmento que gestiona la configuración de la aplicación.
 * Permite cambiar el idioma, eliminar los Pokémon capturados, cerrar sesión y ver información sobre el desarrollador.
 */
public class FragmentSettings extends Fragment {

    private Switch switchLanguage; // Switch para cambiar el idioma
    private Button btnDeletePokemons, btnLogout, btnAbout; // Botones para las acciones
    private FirebaseAuth mAuth; // Instancia de FirebaseAuth
    private SharedPreferences sharedPreferences; // Preferencias compartidas para almacenar configuraciones
    private SharedViewModel sharedViewModel; // ViewModel compartido

    public FragmentSettings() {
        // Constructor vacío requerido para fragments
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout y configurar las vistas
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        // Inicializar Firebase Auth y SharedPreferences
        initializeComponents();

        // Referenciar las vistas de la interfaz
        initializeViews(rootView);

        // Cargar las preferencias guardadas
        loadPreferences();

        // Configurar los listeners de los elementos interactivos
        setupListeners();

        // Obtener el ViewModel compartido
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        return rootView;
    }

    /**
     * Inicializa los componentes necesarios como Firebase Auth y SharedPreferences.
     */
    private void initializeComponents() {
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = requireActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
    }

    /**
     * Referencia las vistas de la interfaz de usuario.
     *
     * @param rootView Vista raíz del fragmento.
     */
    private void initializeViews(View rootView) {
        switchLanguage = rootView.findViewById(R.id.switchLanguage);
        btnDeletePokemons = rootView.findViewById(R.id.btnDeletePokemons);
        btnLogout = rootView.findViewById(R.id.btnLogout);
        btnAbout = rootView.findViewById(R.id.btnAbout);
    }

    /**
     * Carga las preferencias guardadas, como el idioma de la aplicación.
     */
    private void loadPreferences() {
        boolean isEnglish = sharedPreferences.getBoolean("languageEnglish", false);
        switchLanguage.setChecked(isEnglish);
    }

    /**
     * Configura los listeners para las acciones de los botones y el switch.
     */
    private void setupListeners() {
        switchLanguage.setOnCheckedChangeListener((buttonView, isChecked) ->
                changeLanguage(isChecked ? "en" : "es"));

        btnDeletePokemons.setOnClickListener(v -> deleteCapturedPokemons());

        btnLogout.setOnClickListener(v -> logout());

        btnAbout.setOnClickListener(v -> {
            showAboutDialog();
        });
    }

    /**
     * Cambia el idioma de la aplicación.
     *
     * @param languageCode El código de idioma.
     */
    private void changeLanguage(String languageCode) {
        // Guardar la preferencia de idioma
        savePreference("languageEnglish", "en".equals(languageCode));

        // Cambiar el idioma de la aplicación
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration config = requireActivity().getResources().getConfiguration();
        config.setLocale(locale);

        requireActivity().getResources().updateConfiguration(config,
                requireActivity().getResources().getDisplayMetrics());
        requireActivity().recreate();
    }

    /**
     * Redirige al fragmento donde se eliminarán los Pokémon capturados y marca que deben eliminarse.
     */
    private void deleteCapturedPokemons() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.action_fragmentSettings_to_fragmentCaptured);
        sharedViewModel.setShouldDeletePokemons(true);
    }

    /**
     * Cierra la sesión del usuario y lo redirige a la pantalla de inicio de sesión.
     */
    private void logout() {
        mAuth.signOut();

        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

    /**
     * Guarda una preferencia en SharedPreferences.
     *
     * @param key La clave de la preferencia.
     * @param value El valor de la preferencia.
     */
    private void savePreference(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Muestra un diálogo con información sobre el desarrollador de la aplicación.
     */
    private void showAboutDialog() {
        new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.about)
                .setMessage(R.string.developer)
                .setPositiveButton(R.string.closed, null)
                .show();
    }
}
