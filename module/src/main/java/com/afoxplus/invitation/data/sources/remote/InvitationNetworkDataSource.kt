package com.afoxplus.invitation.data.sources.remote

import com.afoxplus.invitation.cross.mapper.toDomain
import com.afoxplus.invitation.data.sources.remote.api.InvitationApiNetwork
import com.afoxplus.invitation.data.sources.remote.model.request.InvitationRequest
import com.afoxplus.invitation.domain.entities.Invitation
import com.afoxplus.network.extensions.body
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
            invitationApiNetwork.fetch().body(
                onSuccess = {
                    val result = it.payload.toDomain()
                    invitationsResult = if (result.isEmpty()) ListEmptyData()
                    else ListSuccess(data = result)
                },
                onFailed = {
                    invitationsResult = ListEmptyData()
                })
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

    suspend fun setGuestUUID(invitationID: String): Boolean {
        var result = false
        try {
            invitationApiNetwork.setGuestUUID(InvitationRequest(invitationID)).body(
                onSuccess = { result = it.payload },
                onFailed = { result = false })
        } catch (ex: Exception) {
            result = false
        }
        return result
    }
}