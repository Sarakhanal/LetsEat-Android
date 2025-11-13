package kr.ac.uc.letseat.ui

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.driver.DriverLoginActivity
import kr.ac.uc.letseat.owner.OwnerLoginActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleClient: GoogleSignInClient

    private val RC_GOOGLE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        configureGoogleLogin()

        val btnGoogleLogin = findViewById<LinearLayout>(R.id.btnGoogleLogin)
        val tvCreate = findViewById<TextView>(R.id.tvCreateAccount)
        val tvDriver = findViewById<TextView>(R.id.tvDriver)
        val tvOwner = findViewById<TextView>(R.id.tvOwner)

        // Google Login
        btnGoogleLogin.setOnClickListener {
            startGoogleLogin()
        }

        // Signup Page
        tvCreate.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        // Driver login
        tvDriver.setOnClickListener {
            startActivity(Intent(this, DriverLoginActivity::class.java))
        }

        // Owner login
        tvOwner.setOnClickListener {
            startActivity(Intent(this, OwnerLoginActivity::class.java))
        }
    }

    private fun configureGoogleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleClient = GoogleSignIn.getClient(this, gso)
    }

    private fun startGoogleLogin() {
        val intent = googleClient.signInIntent
        startActivityForResult(intent, RC_GOOGLE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_GOOGLE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                auth.signInWithCredential(credential)
                    .addOnSuccessListener {
                        startActivity(Intent(this, HotelListActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Google Login Failed", Toast.LENGTH_SHORT).show()
                    }

            } catch (e: Exception) {
                Toast.makeText(this, "Google sign-in error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
