package com.luvsoft.profitsales.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.core.ui.activities.BaseActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.luvsoft.profitsales.databinding.ActivityDashBoardBinding
import com.luvsoft.profitsales.network.FortnightsModel
import com.luvsoft.profitsales.ui.adapters.ProfitTypeAdapter
import com.luvsoft.profitsales.viewmodels.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

const val PROFIT_LIST = 1
const val SALES_LIST = 2

@AndroidEntryPoint
class DashBoardActivity : BaseActivity() , ProfitTypeAdapter.CallbackClick {

    private lateinit var binding: ActivityDashBoardBinding

    private lateinit var viewModel: DashboardViewModel

    @Inject
    lateinit var adapters: ProfitTypeAdapter

    @Inject
    lateinit var laM: RecyclerView.LayoutManager

    private var tyeGet: Long = 0L

    lateinit var menuModel: FortnightsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}
        initView()
        setupBanner()
        setupRecyclerView()
        setUpViewModel()
        setObservable()
    }

    private fun initView(){
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras

        bundle?.let {
            val dep = it.getParcelable<FortnightsModel>(PROFIT_ID)
            dep?.let {
                tyeGet = dep.id.toLong()
                menuModel = dep
                setupActionBar(
                    binding.layoutToolbar.toolbar,
                    true,
                    dep.name
                )
            }
        }
    }

    private fun setupBanner() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = laM
            adapter = this@DashBoardActivity.adapters
        }
    }

    private fun setUpViewModel() {
        val vm: DashboardViewModel by viewModels()
        viewModel = vm
        loadZones()
        viewModel.setType(type = tyeGet)
    }

    private fun setObservable() {
        viewModel.onSubModuleListChange().observe(this) {
            adapters.submitList(it)
        }
    }

    private fun loadZones() {
        lifecycleScope.launch {
            binding.swipeRefreshLayout.isRefreshing = false
            loadingSearch()
        }
    }
    private fun loadingSearch() {
        viewModel.start()
    }

    override fun onResume() {
        super.onResume()
        loadZones()
    }

    override fun onItemClicked(dep: FortnightsModel) {
        when(dep.id){
            PROFIT_LIST -> {
                val bundle = Bundle().apply {
                    putParcelable(TYPE_ID, dep)
                    putParcelable(PROFIT_ID, menuModel)
                }
                val intent = Intent(this@DashBoardActivity, ProfitActivity::class.java).apply {
                    putExtras(bundle)
                }
                startActivity(intent)
            }

            SALES_LIST -> {
                val bundle = Bundle().apply {
                    putParcelable(TYPE_ID, dep)
                    putParcelable(PROFIT_ID, menuModel)
                }
                val intent = Intent(this@DashBoardActivity, SalesActivity::class.java).apply {
                    putExtras(bundle)
                }
                startActivity(intent)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Ir hacia atrás cuando se presiona el botón de retroceso
        return true
    }
}