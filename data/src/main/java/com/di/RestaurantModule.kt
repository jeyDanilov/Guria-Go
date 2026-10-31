package com.di

import com.repository.FakeRestaurantRepository
import com.repository.RestaurantRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RestaurantModule {
    @Binds
    abstract fun bindRestaurantRepository(impl: FakeRestaurantRepository): RestaurantRepository
}