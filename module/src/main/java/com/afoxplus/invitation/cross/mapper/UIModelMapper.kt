package com.afoxplus.invitation.cross.mapper

import com.afoxplus.invitation.delivery.models.InvitationItemModel
import com.afoxplus.invitation.domain.entities.Invitation

internal fun Invitation.toUIModel(): InvitationItemModel {
    return InvitationItemModel(
        id = this.id,
        title = this.title,
        address = this.address,
        date = this.date,
        participants = this.participants
    )
}

internal fun List<Invitation>.toUIModel(): List<InvitationItemModel> {
    return this.map { item -> item.toUIModel() }
}