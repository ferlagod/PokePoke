package dam.pmdm.pokepoke;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import dam.pmdm.pokepoke.databinding.ActivityMainBinding;

/**
 * Actividad principal que gestiona la navegación y la interfaz de usuario de la aplicación.
 */
public class MainActivity extends AppCompatActivity {


    private NavController navController; // Controlador de navegación

    /**
     * Metodo llamado al crear la actividad. Configura la navegación y los elementos de la interfaz.
     *
     * @param savedInstanceState Estado previamente guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar el diseño con ViewBinding,configurar el toolbar y el controlador de navegación
        // Enlace a las vistas del layout
        dam.pmdm.pokepoke.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();

            // Configurar el AppBar con el NavController
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.fragmentCaptured,
                    R.id.fragmentPokedex,
                    R.id.fragmentSettings
            ).build();
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

            // Configurar el BottomNavigationView con el NavController
            NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
        }
    }

    /**
     * Metodo que maneja la acción de navegación hacia arriba (en la barra de acción).
     *
     * @return true si la navegación hacia arriba es exitosa, false de lo contrario.
     */
    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
