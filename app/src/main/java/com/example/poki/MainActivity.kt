package com.example.poki

import androidx.appcompat.widget.SearchView
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.example.pokeapp.FilterActivity
import com.example.pokeapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PokemonAdapter
    private lateinit var lottieProgress: LottieAnimationView
    private lateinit var noResultsText: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var searchView: SearchView
    private lateinit var filterButton: ImageFilterView

    private val viewModel: PokemonViewModel by viewModels {
        PokemonViewModelFactory(PokemonRepository(AppDatabase.getDatabase(this).pokemonDao()))
    }

    private var currentOffset = 0
    private val limit = 20
    private var isLoading = false
    private var selectedTypes: List<String> = emptyList()
    private var searchQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        lottieProgress = findViewById(R.id.lottieProgress)
        noResultsText = findViewById(R.id.noResultsText)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        searchView = findViewById(R.id.searchView)
        filterButton = findViewById(R.id.filterButton)

        if(lottieProgress != null) {
            lottieProgress.visibility = View.VISIBLE
        }

        adapter = PokemonAdapter { pokemon ->
            val intent = Intent(this, PokemonDetailActivity::class.java)
            intent.putExtra("POKEMON_NAME", pokemon.name)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                if (!isLoading && layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    loadMorePokemons()
                }
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            refreshData()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchQuery = query ?: ""
                refreshData()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        filterButton.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            intent.putStringArrayListExtra("SELECTED_TYPES", ArrayList(selectedTypes))
            startActivityForResult(intent, 1)
        }

        observeViewModel()
        loadPokemons(true)
    }

    private fun observeViewModel() {
        viewModel.pokemons.observe(this) { pokemons ->
            adapter.submitList(pokemons)
            if (pokemons.isEmpty()) {
                noResultsText.visibility = View.VISIBLE
            } else {
                noResultsText.visibility = View.GONE
            }
            lottieProgress.visibility = View.GONE
            swipeRefreshLayout.isRefreshing = false
            isLoading = false
        }

        viewModel.isNetworkAvailable.observe(this) { isAvailable ->
            // Handle if needed, but caching handles offline
        }
    }

    private fun loadPokemons(initial: Boolean = false) {
        if (initial) {
            currentOffset = 0
            adapter.submitList(emptyList())
        }
        isLoading = true
        lottieProgress.visibility = View.VISIBLE
        viewModel.loadPokemons(limit, currentOffset, searchQuery, selectedTypes)
        currentOffset += limit
    }

    private fun loadMorePokemons() {
        loadPokemons()
    }

    private fun refreshData() {
        currentOffset = 0
        loadPokemons(true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            selectedTypes = data?.getStringArrayListExtra("SELECTED_TYPES") ?: emptyList()
            refreshData()
        }
    }
}