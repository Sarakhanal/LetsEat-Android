package kr.ac.uc.letseat.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kr.ac.uc.letseat.R
import kr.ac.uc.letseat.driver.DriverLoginActivity
import kr.ac.uc.letseat.owner.OwnerLoginActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnCustomer = findViewById<Button>(R.id.btnCustomerLogin)
        val btnOwner = findViewById<Button>(R.id.btnOwnerLogin)
        val btnDriver = findViewById<Button>(R.id.btnDriverLogin)

        // CUSTOMER → Google Login
        btnCustomer.setOnClickListener {
            startActivity(Intent(this, GoogleLoginActivity::class.java))
        }

        // OWNER
        btnOwner.setOnClickListener {
            startActivity(Intent(this, OwnerLoginActivity::class.java))
        }

        // DRIVER
        btnDriver.setOnClickListener {
            startActivity(Intent(this, DriverLoginActivity::class.java))
        }
    }
}
