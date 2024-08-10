package com.afoxplus.invitation.di

import com.afoxplus.invitation.data.repository.InvitationDataRepository
import com.afoxplus.invitation.domain.repository.InvitationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface InvitationModule {

    @Binds
    fun bindInvitationRepository(impl: InvitationDataRepository): InvitationRepository
}