package com.example.geographicatlas.di

import com.example.geographicatlas.repository.CountryRepository
import com.example.geographicatlas.repository.CountryRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun providesProductRepository(imp: CountryRepositoryImp): CountryRepository = imp

}