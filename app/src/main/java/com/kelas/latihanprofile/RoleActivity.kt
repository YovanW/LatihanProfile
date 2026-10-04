package com.kelas.latihanprofile // GANTI sesuai package project kamu

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RoleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role)

        findViewById<Button>(R.id.btnAdmin).setOnClickListener { selectRole("Admin") }
        findViewById<Button>(R.id.btnUser).setOnClickListener { selectRole("User") }
        findViewById<Button>(R.id.btnGuest).setOnClickListener { selectRole("Guest") }
    }

    // kirim role yang dipilih ke halaman Profile lalu tutup halaman role
    private fun selectRole(role: String) {
        val data = Intent().putExtra(EXTRA_ROLE, role)
        setResult(RESULT_OK, data)
        finish()
    }

    companion object {
        const val EXTRA_ROLE = "extra_role"
    }
}