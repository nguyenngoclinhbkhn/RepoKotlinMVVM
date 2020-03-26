package com.example.repokotlinmvvm.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.repokotlinmvvm.R
import com.example.repokotlinmvvm.Utils
import com.example.repokotlinmvvm.model.local.enity.Repo
import com.example.repokotlinmvvm.model.viewmodel.RepoViewModel
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private lateinit var repoViewModel: RepoViewModel
    private lateinit var repo: Repo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        repoViewModel = ViewModelProviders.of(this).get(RepoViewModel::class.java)
        val id = intent.getStringExtra(Utils.KEY).toInt()
        repoViewModel.getRepo(id).observe(this, Observer {t ->
            if (t != null){
                repo = t
                txtTitleToolbar.text = t.fullName
                txtFullName.text = t.fullName
                txtDes.text = t.description
                txtStar.text = t.stars
                txtForks.text = t.fork
                txtLanguage.text = t.language
            }
        })

        txtDelete.setOnClickListener{
            repoViewModel.deleteRepo(repo)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
