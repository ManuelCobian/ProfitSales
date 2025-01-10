package com.luvsoft.profitsales.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.core.extensions.toMexican
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.luvsoft.profitsales.viewmodels.MainViewModels
import com.luvsoft.profitsales.databinding.ActivityMainBinding
import com.luvsoft.profitsales.network.FortnightsModel
import com.luvsoft.profitsales.ui.adapters.ProfitTypeAdapter
import com.luvsoft.profitsales.viewmodels.MESSAGE_DANGER
import com.luvsoft.profitsales.viewmodels.MESSAGE_GREEN
import com.luvsoft.profitsales.viewmodels.MESSAGE_ORANGE
import com.luvsoft.profitsales.viewmodels.MESSAGE_YELLOW
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
const val PROFIT_ID = "profit_id"
const val TYPE_ID = "type_id"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , ProfitTypeAdapter.CallbackClick {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainViewModels

    @Inject
    lateinit var adapters: ProfitTypeAdapter

    @Inject
    lateinit var laM: RecyclerView.LayoutManager

    private var profit = 0.0

    private var expenses = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        MobileAds.initialize(this) {}
        setupRecyclerView()
        setUpViewModel()
        setObservable()
        setupBanner()
    }

    private fun setupBanner() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun setObservable() {
        viewModel.onSubModuleListChange().observe(this) {
            adapters.submitList(it)
        }

        viewModel.onTotalProfit().observe(this){
            binding.tvSave.text = it.toMexican()
            profit = it
        }

        viewModel.onTotalExpenses().observe(this){
            binding.tvSale.text = it.toMexican()
            expenses = it
        }

        viewModel.onEvaluate().observe(this){
            when(it){
                MESSAGE_GREEN -> {
                    binding.textViewSuccess.isGone = false
                    binding.textViewYellow.isGone = true
                    binding.textViewOrange.isGone = true
                    binding.textViewDanger.isGone = true
                }
                MESSAGE_YELLOW -> {
                    binding.textViewSuccess.isGone = true
                    binding.textViewYellow.isGone = false
                    binding.textViewOrange.isGone = true
                    binding.textViewDanger.isGone = true
                }
                MESSAGE_ORANGE -> {
                    binding.textViewSuccess.isGone = true
                    binding.textViewYellow.isGone = true
                    binding.textViewOrange.isGone = false
                    binding.textViewDanger.isGone = true
                }
                MESSAGE_DANGER -> {
                    binding.textViewSuccess.isGone = true
                    binding.textViewYellow.isGone = true
                    binding.textViewOrange.isGone = true
                    binding.textViewDanger.isGone = false
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = laM
            adapter = this@MainActivity.adapters
        }
    }

    private fun setUpViewModel() {
        val vm: MainViewModels by viewModels()
        viewModel = vm
        loadZones()
    }

    private fun loadZones() {
        lifecycleScope.launch {
            loadingSearch()
            viewModel.getTotal()
            viewModel.getTotalExpenses()
        }
    }
    private fun loadingSearch() {
        viewModel.start()
    }

    override fun onResume() {
        super.onResume()
        loadZones()
    }

    override fun onItemClicked(profit: FortnightsModel) {
        val bundle = Bundle().apply {
            putParcelable(PROFIT_ID, profit)
        }
        val intent = Intent(this@MainActivity, DashBoardActivity::class.java).apply {
            putExtras(bundle)
        }
        startActivity(intent)
    }
}