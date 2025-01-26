package dam.pmdm.pokepoke;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import android.widget.Toast;

/**
 * Clase LoginActivity que gestiona la autenticación del usuario
 * mediante correo electrónico/contraseña o inicio de sesión con Google.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnGoogleLogin;
    private TextView tvRegister;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    /**
     * Metodo llamado al crear la actividad. Configura la vista y
     * los elementos necesarios para la autenticación.
     *
     * @param savedInstanceState Estado previamente guardado de la actividad.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Inicializar FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Verificar si ya existe un usuario autenticado y redirigir a MainActivity
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Vincular vistas de la interfaz con variables
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // Configurar acción para el botón de inicio de sesión con email y contraseña
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            loginWithEmail(username, password);
        });

        // Configurar acción para el botón de inicio de sesión con Google
        btnGoogleLogin.setOnClickListener(v -> signInWithGoogle());

        // Configurar acción para el enlace de registro
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Configurar opciones de inicio de sesión con Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    /**
     * Metodo que gestiona el inicio de sesión con email y contraseña.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     */
    private void loginWithEmail(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            // Mostrar error si los campos están vacíos
            etUsername.setError(getString(R.string.no_email_empty));
            etPassword.setError("La contraseña no puede estar vacía");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Si la autenticación es exitosa, redirigir a MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Manejo de errores
                        String errorMessage = task.getException() != null
                                ? task.getException().getLocalizedMessage()
                                : getString(R.string.error_session);
                        etUsername.setError(errorMessage);
                    }
                });
    }

    /**
     * Metodo que gestiona el inicio de sesión con Google.
     */
    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }

    /**
     * Metodo que gestiona el resultado de actividades iniciadas desde LoginActivity.
     *
     * @param requestCode Código de solicitud que identifica la actividad.
     * @param resultCode Código de resultado devuelto por la actividad.
     * @param data Datos adicionales proporcionados por la actividad.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            // Manejar el resultado del inicio de sesión con Google
            if (resultCode == RESULT_OK) {
                // La autenticación fue exitosa
                GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult();
                if (account != null) {
                    String username = account.getDisplayName();
                    String email = account.getEmail();

                    // Realizar acciones con la cuenta, como almacenar los datos o iniciar sesión en Firebase
                    AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    // Inicio de sesión exitoso en Firebase
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    // Aquí puedes redirigir al usuario a la pantalla principal
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    // Error en la autenticación de Google
                                    Toast.makeText(this, R.string.authentication_failed, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            } else {
                // La autenticación no fue exitosa
                Toast.makeText(this, R.string.google_sign_in_failed, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
