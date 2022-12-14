package com.example.miprojecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.miprojecto.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)


    }

    fun onClick(view: View) {
        when(view.id){
            R.id.btn_ingresar -> startActivity(Intent(this, MainActivity::class.java))
        }
    }
}