package com.luvsoft.profitsales.viewmodels

import android.content.Context
import androidx.annotation.CheckResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.luvsoft.profitsales.network.FortnightsModel
import com.luvsoft.profitsales.repositories.FortnightsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
internal class DashboardViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: FortnightsRepository
): ViewModel(){

    private val subModuleList = MutableLiveData<List<FortnightsModel>>()

    fun start(){
        val list = repository.getAllMenu()
        subModuleList.value = list
    }

    fun setType(type: Long){
        repository.setTypeNow(type)
    }

    @CheckResult
    fun onSubModuleListChange(): LiveData<List<FortnightsModel>> = subModuleList
}