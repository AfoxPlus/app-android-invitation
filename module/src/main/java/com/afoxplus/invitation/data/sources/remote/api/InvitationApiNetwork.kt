package com.afoxplus.invitation.data.sources.remote.api

import com.afoxplus.invitation.data.sources.remote.model.request.InvitationRequest
import com.afoxplus.invitation.data.sources.remote.model.response.InvitationResponse
import com.afoxplus.network.annotations.EndpointInfo
import com.afoxplus.network.annotations.ServiceClient
import com.afoxplus.network.api.UrlProvider
import com.afoxplus.network.response.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

@ServiceClient(type = UrlProvider.Type.API_INVITATION)
internal interface InvitationApiNetwork {

    companion object {
        const val PATH_INVITATION = "invitation"
    }

    @GET(PATH_INVITATION)
    @EndpointInfo(type = UrlProvider.Type.API_INVITATION)
    suspend fun fetch(): Response<BaseResponse<List<InvitationResponse>>>

    @GET("$PATH_INVITATION/{code}")
    @EndpointInfo(type = UrlProvider.Type.API_INVITATION)
    suspend fun find(@Path("code") code: String): Response<BaseResponse<InvitationResponse>>

    @PUT(PATH_INVITATION)
    @EndpointInfo(type = UrlProvider.Type.API_INVITATION)
    suspend fun setGuestUUID(@Body request: InvitationRequest): Response<BaseResponse<Boolean>>
}