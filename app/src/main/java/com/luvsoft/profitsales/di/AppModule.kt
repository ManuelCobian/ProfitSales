package com.luvsoft.profitsales.di

import android.app.Activity
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luvsoft.profitsales.ui.adapters.ExpensesAdapter
import com.luvsoft.profitsales.ui.adapters.ProfitAdapter
import com.luvsoft.profitsales.ui.adapters.ProfitTypeAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ActivityComponent::class)
@Module
object AdapterModule
{
    @Provides
    fun provideLayoutManager(@ApplicationContext context: Context): RecyclerView.LayoutManager =
        LinearLayoutManager(context)
}

@Module
@InstallIn(ActivityComponent::class)
object YearsModule {
    @Provides
    fun provideCallback(activity: Activity) =
        activity as ProfitTypeAdapter.CallbackClick
}

@Module
@InstallIn(ActivityComponent::class)
object ProfitModule {
    @Provides
    fun provideCallback(activity: Activity) =
        activity as ProfitAdapter.CallbackClick
}


@Module
@InstallIn(ActivityComponent::class)
object ExpensesModule {
    @Provides
    fun provideCallback(activity: Activity) =
        activity as ExpensesAdapter.CallbackClick
}