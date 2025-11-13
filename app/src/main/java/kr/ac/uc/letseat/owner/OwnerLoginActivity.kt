package kr.ac.uc.letseat.owner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kr.ac.uc.letseat.R

class OwnerLoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_login)

        auth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.etOwnerEmail)
        val etPassword = findViewById<EditText>(R.id.etOwnerPassword)
        val btnLogin = findViewById<Button>(R.id.btnOwnerLogin)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ✅ Ultra fast login (ZERO heavy work)
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Toast.makeText(this, "Welcome Owner!", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this, OwnerDashboardActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this,
                        "Login Failed: ${it.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}
