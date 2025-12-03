package kr.ac.uc.letseat.driver

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.R

class DriverLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_login)

        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtPass = findViewById<EditText>(R.id.edtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val pass = edtPass.text.toString()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, pass)
                .addOnSuccessListener { result ->

                    val uid = result.user!!.uid
                    val db = FirebaseFirestore.getInstance()

                    val driverDoc = db.collection("drivers").document(uid)

                    driverDoc.get().addOnSuccessListener { doc ->

                        if (!doc.exists()) {
                            // AUTO-CREATE DRIVER DOCUMENT
                            val data = mapOf(
                                "uid" to uid,
                                "active" to true
                            )
                            driverDoc.set(data)
                        }

                        // NOW OPEN DRIVER ORDERS SCREEN
                        startActivity(Intent(this, DriverOrdersActivity::class.java))
                        finish()

                    }.addOnFailureListener {
                        Toast.makeText(this, "Error checking driver record", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Login failed: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
