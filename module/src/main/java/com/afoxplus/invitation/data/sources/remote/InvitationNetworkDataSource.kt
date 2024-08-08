package com.afoxplus.invitation.data.sources.remote

import com.afoxplus.invitation.cross.mapper.toDomain
import com.afoxplus.invitation.data.sources.remote.api.InvitationApiNetwork
import com.afoxplus.invitation.domain.entities.Invitation
import com.afoxplus.network.extensions.map
import com.afoxplus.uikit.views.status.ListEmptyData
import com.afoxplus.uikit.views.status.ListError
import com.afoxplus.uikit.views.status.ListLoading
import com.afoxplus.uikit.views.status.ListState
import com.afoxplus.uikit.views.status.ListSuccess
import javax.inject.Inject

internal class InvitationNetworkDataSource @Inject constructor(private val invitationApiNetwork: InvitationApiNetwork) {

    suspend fun fetch(): ListState<Invitation> {
        var invitationsResult: ListState<Invitation> = ListLoading()
        try {
            val response = invitationApiNetwork.fetch()
            if (response.isSuccessful.not()) invitationsResult = ListEmptyData()
            response.map {
                val result = it.payload.toDomain()
                invitationsResult = if (result.isEmpty()) ListEmptyData()
                else ListSuccess(data = result)
            }
        } catch (ex: Exception) {
            invitationsResult = ListError(ex)
        }
        return invitationsResult
    }

    suspend fun findByCode(code: String): Invitation? {
        var invitationResult: Invitation? = null
        try {
            val response = invitationApiNetwork.find(code)
            response.map {
                invitationResult = it.payload.toDomain()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return invitationResult
    }
}