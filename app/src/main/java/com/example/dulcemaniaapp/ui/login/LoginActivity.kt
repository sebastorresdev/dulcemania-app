package com.example.dulcemaniaapp.ui.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dulcemaniaapp.MenuActivity
import com.example.dulcemaniaapp.R
import com.example.dulcemaniaapp.databinding.ActivityLoginBinding
import com.example.dulcemaniaapp.databinding.FragmentPedidoBinding
import com.example.dulcemaniaapp.models.Usuario
import com.example.dulcemaniaapp.services.UsuarioService
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        private const val RC_SIGN_IN = 9001
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar ViewBinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Obtén esto de google-services.json
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Inicializar Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Configurar el botón de Google Sign-In
        binding.btnGoogleSignIn.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = firebaseAuth.currentUser
                    Toast.makeText(this, "Bienvenido ${user?.displayName}", Toast.LENGTH_SHORT).show()

                    val usuarioService = UsuarioService()
                    val usuario = Usuario(user?.displayName.toString(), user?.email.toString(), user?.uid.toString())

                    usuarioService.registrarUsuario(usuario) { usuarioCreado, errorMessage ->
                        if (usuarioCreado != null) {
                            // Si el usuario fue creado exitosamente
                            Toast.makeText(this, "Usuario creado con éxito: ${usuarioCreado.nombreCompleto}", Toast.LENGTH_LONG).show()
                        } else {
                            // Si ocurrió un error
                            Toast.makeText(this, "Error: Usuario ya existe", Toast.LENGTH_LONG).show()
                        }
                    }

                    // Redirige al usuario al MainActivity después de iniciar sesión
                    val intent = Intent(this, MenuActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Error en la autenticación", Toast.LENGTH_SHORT).show()
                }
            }
    }
}