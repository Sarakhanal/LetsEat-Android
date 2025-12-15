package kr.ac.uc.letseat.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kr.ac.uc.letseat.R

class LocationActivity : AppCompatActivity() {

    private lateinit var edtSearch: EditText
    private lateinit var listView: ListView
    private lateinit var btnConfirm: Button

    // You can add more landmarks later
    private val landmarks = listOf(
        "Nepalgunj Buspark",
        "Eklaini Chowk",
        "Setu BK Chowk",
        "Tribhuvan Chowk",
        "Bhrikutinagar",
        "Banke Hospital",
        "Nepalgunj Airport",
        "Uttar Chowk",
        "Dhamboji",
        "Nepalgunj Stadium"
    )

    private var selectedLocation: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        edtSearch = findViewById(R.id.edtSearch)
        listView = findViewById(R.id.listView)
        btnConfirm = findViewById(R.id.btnConfirm)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, landmarks)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            selectedLocation = adapter.getItem(position)
            Toast.makeText(this, "Selected: $selectedLocation", Toast.LENGTH_SHORT).show()
        }

        // Search filter
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val filtered = landmarks.filter {
                    it.contains(s.toString(), ignoreCase = true)
                }

                val filteredAdapter = ArrayAdapter(
                    this@LocationActivity,
                    android.R.layout.simple_list_item_1,
                    filtered
                )
                listView.adapter = filteredAdapter
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        btnConfirm.setOnClickListener {
            if (selectedLocation == null) {
                Toast.makeText(this, "Please choose a location", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, HotelListActivity::class.java)
            intent.putExtra("location", selectedLocation)
            startActivity(intent)
            finish()
        }
    }
}
