package com.example.repokotlinmvvm

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.repokotlinmvvm.model.User
import com.example.repokotlinmvvm.model.viewmodel.NetworkViewModel
import com.example.repokotlinmvvm.model.viewmodel.RepoViewModel
import kotlinx.android.synthetic.main.activity_main.*


class LoginActivity : AppCompatActivity() {
    private lateinit var networkViewModel: NetworkViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        networkViewModel = ViewModelProviders.of(this).get(NetworkViewModel::class.java)
        val repoViewModel = ViewModelProviders.of(this).get(RepoViewModel::class.java)
//        repoViewModel.getAllRepo().observe(this, )
        txtLogin.setOnClickListener{
            val userName = edUserName.text.toString().trim()
            val pass = edPass.text.toString().trim()
            if (networkViewModel.checkPass(pass)) {
                networkViewModel.getUser(userName, pass)
                    .observe(this, object : Observer<User> {
                        override fun onChanged(t: User?) {
                            Log.e("TAG", "user name " + t?.userName)
                        }
                    })
            }else{
                Toast.makeText(this@LoginActivity, "Password fail", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
