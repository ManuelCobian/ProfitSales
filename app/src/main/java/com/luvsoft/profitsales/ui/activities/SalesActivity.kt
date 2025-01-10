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
import com.luvsoft.profitsales.databinding.ActivitySalesBinding
import com.luvsoft.profitsales.network.FortnightsModel
import com.luvsoft.profitsales.ui.adapters.ExpensesAdapter
import com.luvsoft.profitsales.ui.adapters.swap.SwipeToDeleteExpensesCallback
import com.luvsoft.profitsales.viewmodels.ProfitViewModel
import com.luvsoft.room.network.models.ExpensesEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SalesActivity : BaseActivity(), ExpensesAdapter.CallbackClick {

    private lateinit var binding: ActivitySalesBinding

    private lateinit var viewModel: ProfitViewModel

    @Inject
    lateinit var laM: RecyclerView.LayoutManager

    @Inject
    lateinit var adapters: ExpensesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}
        initView()
        setupBanner()
        setUpViewModel()
        setupRecyclerView()
        initListerner()
        initObservers()
        swapItem()
    }

    private fun initView(){
        binding = ActivitySalesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        bundle?.let {
            val dep = it.getParcelable<FortnightsModel>(TYPE_ID)
            dep?.let {
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

    private fun setUpViewModel() {
        val vm: ProfitViewModel by viewModels()
        viewModel = vm
        loadExpenses()
    }

    private fun loadExpenses() {
        lifecycleScope.launch {
            viewModel.getExpenses()
            viewModel.getTotalExpenses()
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
                    val intent = Intent(this@SalesActivity, AddExpenseActivity::class.java).apply {
                        putExtras(bundle)
                    }
                    startActivityForResult(intent, REQUEST_CODE)
                }
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            loadExpenses()
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = laM
            adapter = this@SalesActivity.adapters
        }
    }

    private fun initObservers() {
        viewModel.onExpensesListChange().observe(this){
            adapters.submitList(it)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.onTotalExpenses().observe(this){
            binding.tvSave.text = it.toMexican()
        }
    }

    private fun swapItem(){
        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteExpensesCallback(adapters))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // Ir hacia atrás cuando se presiona el botón de retroceso
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            loadExpenses()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onItemClicked(dep: ExpensesEntity) {
        lifecycleScope.launch {
            viewModel.deleteExpenses(dep)
            Snackbar.make(binding.root, com.luvsoft.base.R.string.message_succes, Snackbar.LENGTH_LONG)
                .show()
        }
    }
}