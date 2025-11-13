package kr.ac.uc.letseat.driver

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kr.ac.uc.letseat.R

class DriverLoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_login)

        auth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.etDriverEmail)
        val etPassword = findViewById<EditText>(R.id.etDriverPassword)
        val btnLogin = findViewById<Button>(R.id.btnDriverLogin)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter email & password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val driverId = auth.currentUser?.uid ?: ""
                    if (driverId.isEmpty()) {
                        Toast.makeText(this, "Failed to get driver ID.", Toast.LENGTH_SHORT).show()
                        return@addOnSuccessListener
                    }

                    val intent = Intent(this, DriverHomeActivity::class.java)
                    intent.putExtra("driverId", driverId)

                    Toast.makeText(this, "Driver Login successful!", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Login failed: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
