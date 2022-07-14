package com.kimdo.animalmvitest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kimdo.animalmvitest.R
import com.kimdo.animalmvitest.api.AnimalService
import com.kimdo.animalmvitest.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private var adapter = AnimalListAdapter(arrayListOf())

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( binding.root )

        setupUI()
        setupObservables()


    }

    private fun setupUI() {
        mainViewModel = ViewModelProvider(this, ViewModelFactory(AnimalService.api)) [MainViewModel::class.java]
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    binding.recyclerView.context,
                    (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        binding.recyclerView.adapter = adapter
        binding.buttonFetchAnimals.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchAnimals)
            }
        }
    }

    private fun setupObservables() {
        lifecycleScope.launch {
            mainViewModel.state.collect { collector ->
                when(collector) {
                    is MainState.Idle -> {

                    }
                    is MainState.Loading -> {
                        binding.buttonFetchAnimals.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is MainState.Animals -> {
                        binding.progressBar.visibility = View.GONE
                        binding.buttonFetchAnimals.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        collector.animals.let {
                            adapter.newAnimals(it)
                        }
                    }
                    is MainState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.buttonFetchAnimals.visibility = View.GONE
                        Toast.makeText(this@MainActivity, collector.error, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }


}