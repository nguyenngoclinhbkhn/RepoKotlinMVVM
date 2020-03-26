package com.example.repokotlinmvvm.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.repokotlinmvvm.R
import com.example.repokotlinmvvm.Utils
import com.example.repokotlinmvvm.adapter.AdapterRepoFavo
import com.example.repokotlinmvvm.adapter.AdapterRepoSearch
import com.example.repokotlinmvvm.model.local.enity.Repo
import com.example.repokotlinmvvm.model.viewmodel.NetworkViewModel
import com.example.repokotlinmvvm.model.viewmodel.RepoViewModel
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterRepoFavo.OnRepoListener,
    AdapterRepoSearch.OnRepoSearchListener, Observer<List<Repo>> {
    private lateinit var networkViewModel: NetworkViewModel
    private lateinit var adapterRepoSearch: AdapterRepoSearch
    private lateinit var adapterRepoFavo: AdapterRepoFavo
    private lateinit var repoViewModel: RepoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        networkViewModel = ViewModelProviders.of(this).get(NetworkViewModel::class.java)
        repoViewModel = ViewModelProviders.of(this).get(RepoViewModel::class.java)
        setupRecyclerViewFavo()
        setupRecyclerViewSearch()
        repoViewModel.getAllRepo().observe(this, Observer {
            adapterRepoFavo.setList(it)
        })
        edSearch.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!TextUtils.isEmpty(edSearch.text)){
                    txtMostRecent.visibility = View.VISIBLE
                    txtMostPopular.visibility = View.VISIBLE
                    progressBar.visibility = View.VISIBLE
                    recyclerViewFavo.visibility = View.GONE
                    txtLogout.visibility = View.GONE
//                    recyclerView.visibility = View.GONE
                    networkViewModel.getRepoSearch(edSearch.text.toString().trim(), "", "")
                        .observe(this@MainActivity, Observer {it ->
                            it.let {
                                Log.e("TAG", "size ${it.size}")
                                recyclerView.visibility = View.VISIBLE
                                progressBar.visibility = View.GONE
                                adapterRepoSearch.setList(it)
                            }

                        })
                }
            }

        })


        txtLogout.setOnClickListener(this)
        txtCancel.setOnClickListener(this)
        txtMostPopular.setOnClickListener(this)
        txtMostRecent.setOnClickListener(this)
    }

    private fun setupRecyclerViewSearch() {
        adapterRepoFavo = AdapterRepoFavo(this)
        recyclerViewFavo.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewFavo.adapter = adapterRepoFavo
    }

    private fun setupRecyclerViewFavo() {
        adapterRepoSearch = AdapterRepoSearch(this)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapterRepoSearch
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.txtCancel -> {
                txtMostRecent.visibility = View.GONE
                txtMostPopular.visibility = View.GONE
                recyclerView.visibility = View.GONE
                txtLogout.visibility = View.VISIBLE
                recyclerViewFavo.visibility = View.VISIBLE
                repoViewModel.getAllRepo().observe(this, Observer {
                    adapterRepoFavo.setList(it)
                })
            }
            R.id.txtLogout -> {
                repoViewModel.deleteAll()
                getSharedPreferences(Utils.NAME, Context.MODE_PRIVATE).edit().putInt(Utils.KEY, 0).commit()
                finish()
            }
            R.id.txtMostPopular -> {
                txtMostRecent.visibility = View.VISIBLE
                txtMostPopular.visibility = View.VISIBLE
                progressBar.visibility = View.VISIBLE
                recyclerViewFavo.visibility = View.GONE
                txtLogout.visibility = View.GONE
                recyclerView.visibility = View.GONE
                networkViewModel.getRepoSearch(edSearch.text.toString().trim(), "star", "desc")
                    .observe(this, this)
            }
            R.id.txtMostRecent -> {
                txtMostRecent.visibility = View.VISIBLE
                txtMostPopular.visibility = View.VISIBLE
                progressBar.visibility = View.VISIBLE
                recyclerViewFavo.visibility = View.GONE
                txtLogout.visibility = View.GONE
                recyclerView.visibility = View.GONE
                networkViewModel.getRepoSearch(edSearch.text.toString().trim(), "updated", "desc")
                    .observe(this, this)
            }
        }
    }

    override fun OnRepoClicked(repo: Repo?) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(Utils.KEY, repo!!.id.toString())
        startActivity(intent)
        finish()
    }

    override fun onRepoSearchClicked(repo: Repo) {
        Log.e("TAG", "repo id ${repo.id} : name ${repo.fullName}" )
        repoViewModel.insertRepo(repo)
    }

    override fun onChanged(t: List<Repo>?) {
        if (t?.size == 0){
            Toast.makeText(this, "Please try again after 10 second", Toast.LENGTH_SHORT).show()
        }else {
            recyclerView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            adapterRepoSearch.setList(t!!)
        }
    }
}
