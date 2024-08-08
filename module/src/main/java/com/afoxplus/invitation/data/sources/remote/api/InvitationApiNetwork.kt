package com.afoxplus.invitation.data.sources.remote.api

import com.afoxplus.invitation.data.sources.remote.model.response.InvitationResponse
import com.afoxplus.network.annotations.ServiceClient
import com.afoxplus.network.api.UrlProvider
import com.afoxplus.network.response.BaseResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

@ServiceClient(type = UrlProvider.Type.API_PRODUCTS)
internal interface InvitationApiNetwork {

    companion object {
        const val PATH_INVITATION = "invitation"
    }

    @GET(PATH_INVITATION)
    suspend fun fetch(): Response<BaseResponse<List<InvitationResponse>>>

    @GET("$PATH_INVITATION/{code}")
    suspend fun find(@Path("code") code: String): Response<BaseResponse<InvitationResponse>>
}