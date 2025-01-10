package com.luvsoft.profitsales.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.luvsoft.profitsales.databinding.ActivityAddProfitBinding
import com.luvsoft.profitsales.network.FortnightsModel
import com.luvsoft.profitsales.viewmodels.AddProfitViewModel
import com.luvsoft.profitsales.viewmodels.ProfitField
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddProfitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProfitBinding
    private lateinit var viewModel: AddProfitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViewModel()
        initView()
        initListener()
        setObserver()
    }

    private fun setObserver() {
        viewModel.onErro().observe(this) {
            if (it) {
                clearForm()
            } else Snackbar.make(binding.root, com.luvsoft.base.R.string.food_save_error, Snackbar.LENGTH_LONG)
                .show()
        }

        viewModel.onEnabled().observe(this){
            binding.button.isEnabled = it
        }
    }

    private fun clearForm() {
        with(binding){
            val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    private fun initView() {
        binding = ActivityAddProfitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        bundle?.let {
            val dep = it.getParcelable<FortnightsModel>(PROFIT_ID)
            dep?.let {
                viewModel.init(dep.id)
            }
        }
    }

    private fun initListener() {
        with(binding){
            imageView.setOnClickListener {
                finish()
            }
            binding.etPrice.doAfterTextChanged { profitName ->
                viewModel.updateField(profitName.toString(), ProfitField.NAME_PROFIT)
            }

            binding.ettotal.doAfterTextChanged {  profitTotal ->
                viewModel.updateField(profitTotal.toString(), ProfitField.PROFIT_TOTAL)
            }

            binding.button.setOnClickListener {
                saveFood()
            }
        }
    }

    private fun saveFood(){
        lifecycleScope.launch {
           viewModel.saveProfit()
        }
    }

    private fun setUpViewModel() {
        val vm: AddProfitViewModel by viewModels()
        viewModel = vm
    }

}