package com.example.pokemonbook.ui.mainframe

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.pokemonbook.R
import com.example.pokemonbook.databinding.ActivityMainBinding
import com.example.pokemonbook.ui.detail.TARGET_POKEMON_ID
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binging: ActivityMainBinding? = null
    private val viewModel: MainViewModel by viewModels()
    private val binding get() = _binging!!
    private var navHostFragment: NavHostFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binging = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        observerViewModel()
    }

    private fun observerViewModel() {
        viewModel.toDetailOnceEventLiveData.observe(this) { event ->
            event.getContentIfNotHandled()?.let { id ->
                navHostFragment?.navController
                val bundle = Bundle()
                bundle.putString(TARGET_POKEMON_ID, id)
                navHostFragment?.navController?.navigate(
                    R.id.action_groupFragment_to_detailFragment,
                    bundle
                )
            }
        }

        viewModel.errorViewLivaData.observe(this) {
            if (it == null) {
                binding.errorView.visibility = View.GONE
            } else {
                binding.errorView.visibility = View.VISIBLE
                binding.errorText.text =
                    String.format(Locale.getDefault(), getString(R.string.error_code_text), it)
            }
        }
    }

}