package kr.ac.uc.letseat.owner

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kr.ac.uc.letseat.R

class OwnerLoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_owner_login)

        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.etOwnerEmail)
        val password = findViewById<EditText>(R.id.etOwnerPassword)
        val btnLogin = findViewById<Button>(R.id.btnOwnerLogin)

        btnLogin.setOnClickListener {
            val emailStr = email.text.toString()
            val passStr = password.text.toString()

            if (emailStr.isEmpty() || passStr.isEmpty()) {
                Toast.makeText(this, "Enter email & password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            signInOwner(emailStr, passStr)
        }
    }

    private fun signInOwner(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user!!.uid
                Log.e("OWNER_LOGIN", "Owner UID = $uid")

                checkRestaurant(uid)  // move login here
            }
            .addOnFailureListener {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkRestaurant(uid: String) {
        db.collection("restaurants")
            .whereEqualTo("ownerId", uid)
            .get()
            .addOnSuccessListener { snap ->
                if (snap.isEmpty) {
                    Toast.makeText(this, "Restaurant not found for this owner", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                val restaurantId = snap.documents[0].id
                Log.e("OWNER_LOGIN", "Restaurant = $restaurantId")

                val intent = Intent(this, OwnerOrdersActivity::class.java)
                intent.putExtra("restaurantId", restaurantId)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error loading restaurant", Toast.LENGTH_SHORT).show()
            }
    }
}
