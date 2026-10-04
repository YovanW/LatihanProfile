package com.kelas.latihanprofile

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.kelas.latihanprofile.RoleActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvRole: TextView

    // menerima hasil role dari RoleActivity
    private val roleLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.getStringExtra(RoleActivity.EXTRA_ROLE)?.let { tvRole.text = it }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val tvPhone = findViewById<TextView>(R.id.tvPhone)
        tvRole = findViewById(R.id.tvRole)

        // menyimpan posisi role jika layar di rotate
        savedInstanceState?.getString(STATE_ROLE)?.let { tvRole.text = it }

        findViewById<TextView>(R.id.btnBack).setOnClickListener { finish() }

        // 1a. klik email -> kirim email
        findViewById<android.view.View>(R.id.itemEmail).setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:${tvEmail.text}")
            }
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Tidak ada aplikasi email", Toast.LENGTH_SHORT).show()
            }
        }

        // 1b. klik phone -> buka dialer (tidak butuh permission)
        findViewById<android.view.View>(R.id.itemPhone).setOnClickListener {
            val number = tvPhone.text.toString().replace(Regex("[^+\\d]"), "")
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$number")
            }
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Tidak ada aplikasi telepon", Toast.LENGTH_SHORT).show()
            }
        }

        // 2. klik role -> halaman pilih role
        findViewById<android.view.View>(R.id.itemRole).setOnClickListener {
            roleLauncher.launch(Intent(this, RoleActivity::class.java))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_ROLE, tvRole.text.toString())
    }

    companion object {
        private const val STATE_ROLE = "state_role"
    }
}