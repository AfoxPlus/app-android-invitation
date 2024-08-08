package com.afoxplus.invitation.di

import com.afoxplus.invitation.data.sources.remote.api.InvitationApiNetwork
import com.afoxplus.network.api.RetrofitGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal class InvitationProviderModule {
    @Provides
    fun providerMeasureService(
        retrofitGenerator: RetrofitGenerator
    ): InvitationApiNetwork = retrofitGenerator.createRetrofit(InvitationApiNetwork::class.java)

    
}