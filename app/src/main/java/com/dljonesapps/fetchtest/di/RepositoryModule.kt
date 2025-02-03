package com.dljonesapps.fetchtest.di

import com.dljonesapps.fetchtest.data.api.FetchApi
import com.dljonesapps.fetchtest.data.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    @Provides
    @Singleton
    fun provideItemRepository(api: FetchApi): ItemRepository = 
        ItemRepository(api)
}
