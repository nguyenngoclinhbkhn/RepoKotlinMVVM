package com.example.repokotlinmvvm.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.repokotlinmvvm.R
import com.example.repokotlinmvvm.Utils
import com.example.repokotlinmvvm.model.User
import com.example.repokotlinmvvm.model.viewmodel.NetworkViewModel
import com.example.repokotlinmvvm.model.viewmodel.RepoViewModel
import kotlinx.android.synthetic.main.activity_main.*


class LoginActivity : AppCompatActivity() {
    private lateinit var networkViewModel: NetworkViewModel
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences(Utils.NAME, Context.MODE_PRIVATE)
        if (sharedPreferences.getInt(Utils.KEY_LOGIN, 0) == 1){
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
        networkViewModel = ViewModelProviders.of(this).get(NetworkViewModel::class.java)
        txtLogin.setOnClickListener{
            val userName = edUserName.text.toString().trim()
            val pass = edPass.text.toString().trim()
            if (networkViewModel.checkPass(pass)) {
                progressLogin.visibility = View.VISIBLE
                txtLogin.visibility = View.GONE
                networkViewModel.getUser(userName, pass)
                    .observe(this,
                        Observer<User> {t->
                            if (t!= null){
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                sharedPreferences.edit().putInt(Utils.KEY_LOGIN, 1).commit()
                                finish()
                            }else{
                                progressLogin.visibility = View.GONE
                                txtLogin.visibility = View.VISIBLE
                            }
                        })
            }else{
                Toast.makeText(this@LoginActivity, "Password fail", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
