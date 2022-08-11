package com.kk.m3biserverrequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kk.m3biserverrequest.adapter.UserAdapter
import com.kk.m3biserverrequest.api.RetrofitHelper
import com.kk.m3biserverrequest.api.UserService
import com.kk.m3biserverrequest.models.Data
import com.kk.m3biserverrequest.repository.UserRepository
import com.kk.m3biserverrequest.viewmodels.MainViewModel
import com.kk.m3biserverrequest.viewmodels.MainViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //test adapter
        val recyclerView = findViewById<RecyclerView>(R.id.userList)
        //  lifecycleScope.launch(Dispatchers.IO) {
        val adapter = UserAdapter()
        //test data for adapter
        val p1 = Data("avtar", "email", "Swarupa", 1, "Kirve")
        val p2 = Data("avtar", "email", "Kavya", 2, "Kirve")
        val p3 = Data("avtar", "email", "Kiran", 3, "Kirve")

        adapter.submitList(listOf(p1, p2, p3))
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter


        //improvements :possible to define in application class
        //below object will access main view model
        val userService = RetrofitHelper.getInstance().create(UserService::class.java)
        val repository = UserRepository(userService)

        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        mainViewModel.users.observe(this, Observer {

            Log.d(TAG, TAG + " " + it.data.toString())
            //add to adapter
            adapter.submitList(it.data)

        })

        //for testing quot data
/*        mainViewModel.quotes.observe(this, Observer {
            Log.d(TAG,TAG+" "+it.results.toString())
        })*/

    }
}