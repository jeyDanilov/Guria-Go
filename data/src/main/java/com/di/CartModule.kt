package com.di

import com.repository.CartRepository
import com.repository.InMemoryCartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CartModule {
    @Binds
    abstract fun bindCartRepository(impl: InMemoryCartRepository): CartRepository
}