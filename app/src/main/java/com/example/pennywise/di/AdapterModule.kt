package com.example.pennywise.di

import android.app.Application
import android.content.Context
import com.example.pennywise.Adapter.model.TabLabel
import com.example.pennywise.Adapter.recylerview_adapter.HomeListAdapter
import com.example.pennywise.Adapter.recylerview_adapter.TabLabelAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class) // Sử dụng với ViewModel
object AdapterModule {
    @Provides
    fun provideTabLabelAdapter(
        @ApplicationContext context: Context,
        items: List<TabLabel>
    ): TabLabelAdapter {
        return TabLabelAdapter(context, items)
    }


    @Provides
    fun provideHomeListAdapter(application: Application): HomeListAdapter {
        // Assuming TabLabel data is available; you might want to load it from a repository or database
        val items = listOf<TabLabel>() // Placeholder, provide actual data
        return HomeListAdapter(application.applicationContext, items)
    }
}
