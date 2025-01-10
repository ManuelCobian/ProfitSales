package com.luvsoft.profitsales.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.core.extensions.toMexican
import com.example.core.ui.activities.BaseActivity
import com.example.core.ui.activities.REQUEST_CODE
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import com.luvsoft.profitsales.databinding.ActivityProfitBinding
import com.luvsoft.profitsales.network.FortnightsModel
import com.luvsoft.profitsales.ui.adapters.ProfitAdapter
import com.luvsoft.profitsales.ui.adapters.swap.SwipeToDeleteProfitCallback
import com.luvsoft.profitsales.viewmodels.ProfitViewModel
import com.luvsoft.room.network.models.ProfitEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfitActivity : BaseActivity(), ProfitAdapter.CallbackClick {

    private lateinit var binding: ActivityProfitBinding

    private lateinit var viewModel: ProfitViewModel

    @Inject
    lateinit var laM: RecyclerView.LayoutManager

    @Inject
    lateinit var adapters: ProfitAdapter

    private var tyeGet: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}
        initView()
        setupBanner()
        setupRecyclerView()
        setUpViewModel()
        initListerner()
        initObservers()
        swapItem()
    }

    private fun initObservers() {
        viewModel.onSubModuleListChange().observe(this){
            adapters.submitList(it)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.onTotalProfit().observe(this){
            binding.tvSave.text = it.toMexican()
        }
    }

    private fun setUpViewModel() {
        val vm: ProfitViewModel by viewModels()
        viewModel = vm
        loadProfit()
    }

    private fun loadProfit() {
        lifecycleScope.launch {
            viewModel.getProfit()
            viewModel.getTotal()
        }
    }

    private fun initListerner() {
        binding.button.setOnClickListener {
            val bundle = intent.extras
            bundle?.let {
                val dep = it.getParcelable<FortnightsModel>(PROFIT_ID)
                dep?.let {
                    val bundle = Bundle().apply {
                        putParcelable(PROFIT_ID, dep)
                    }
                    val intent = Intent(this@ProfitActivity, AddProfitActivity::class.java).apply {
                        putExtras(bundle)
                    }
                    startActivityForResult(intent, REQUEST_CODE)
                }
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            loadProfit()
        }
    }

    private fun initView(){
        binding = ActivityProfitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        bundle?.let {
            val dep = it.getParcelable<FortnightsModel>(TYPE_ID)
            dep?.let {
               tyeGet = dep.id.toLong()
                setupActionBar(
                    binding.layoutToolbar.toolbar,
                    true,
                    dep.name
                )
            }
        }
    }

    private fun setupRecyclerView() {
       binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = laM
            adapter = this@ProfitActivity.adapters
        }
    }

    private fun swapItem(){
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteProfitCallback(adapters))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }


    private fun setupBanner() {
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            loadProfit()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onItemClicked(dep: ProfitEntity) {
        lifecycleScope.launch {
            viewModel.deleteProfit(dep)
            Snackbar.make(binding.root, com.luvsoft.base.R.string.message_succes, Snackbar.LENGTH_LONG)
                .show()
        }
    }
}