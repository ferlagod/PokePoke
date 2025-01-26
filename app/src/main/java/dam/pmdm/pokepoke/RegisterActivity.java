package dam.pmdm.pokepoke;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Actividad que gestiona el registro de un usuario mediante Google Sign-In.
 */
public class RegisterActivity extends AppCompatActivity {

    private SignInButton btnGoogleSignIn; // Botón de inicio de sesión con Google
    private FirebaseAuth mAuth; // Instancia de FirebaseAuth
    private GoogleSignInClient mGoogleSignInClient; // Cliente de Google Sign-In

    /**
     * Metodo llamado al crear la actividad. Configura las vistas y la autenticación.
     *
     * @param savedInstanceState Estado previamente guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        // Inicializar Firebase Auth, SignInButton y GoogleSignInClient
        mAuth = FirebaseAuth.getInstance();
        btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn);

        // Configurar acción para el botón de inicio de sesión con Google
        btnGoogleSignIn.setOnClickListener(v -> signInWithGoogle());

        // Configuración de Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Reemplaza con tu ID de cliente
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    /**
     * Metodo que inicia el flujo de inicio de sesión con Google.
     */
    private void signInWithGoogle() {
        // Inicia el flujo de inicio de sesión con Google
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }

    /**
     * Metodo que maneja el resultado del inicio de sesión con Google.
     *
     * @param requestCode Código de solicitud que identifica la actividad.
     * @param resultCode Código de resultado devuelto por la actividad.
     * @param data Datos adicionales proporcionados por la actividad.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            // Maneja el resultado de la autenticación de Google aquí
            GoogleSignIn.getSignedInAccountFromIntent(data)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Si la autenticación con Google es exitosa, obtener el cuenta de Google
                            com.google.android.gms.auth.api.signin.GoogleSignInAccount account = task.getResult();

                            // Usar la cuenta de Google para autenticarse en Firebase
                            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                            mAuth.signInWithCredential(credential)
                                    .addOnCompleteListener(this, firebaseTask -> {
                                        if (firebaseTask.isSuccessful()) {
                                            // Si la autenticación en Firebase es exitosa
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            // Redirigir a la actividad principal
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish(); // Finalizar RegisterActivity
                                        } else {
                                            // Si la autenticación en Firebase falla
                                            String errorMessage = firebaseTask.getException() != null
                                                    ? firebaseTask.getException().getLocalizedMessage()
                                                    : getString(R.string.firebase_authentication_failed);
                                            // Mostrar mensaje de error
                                            Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            // Si la autenticación con Google falla
                            String errorMessage = task.getException() != null
                                    ? task.getException().getLocalizedMessage()
                                    : String.valueOf(R.string.google_sign_in_failed);
                            // Mostrar mensaje de error
                            Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

}


