package com.afoxplus.invitation.delivery.flows

import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.afoxplus.invitation.delivery.activities.InvitationDetailActivity
import com.afoxplus.invitation.delivery.activities.MyInvitationsActivity
import com.afoxplus.invitation.delivery.activities.ScanInvitationTicketActivity
import java.util.Locale
import javax.inject.Inject

class InvitationFlow @Inject constructor() {

    fun goToMyInvitationsActivity(activity: Activity) {
        activity.startActivity(Intent(activity, MyInvitationsActivity::class.java))
    }

    fun goToAddedInvitationActivity(activity: Activity) {
        activity.startActivity(Intent(activity, InvitationDetailActivity::class.java))
    }

    fun goToScanInvitationTicketActivity(activity: Activity, code: String) {
        activity.startActivity(ScanInvitationTicketActivity.getIntentScanInvitationTicket(activity, code))
    }

    fun goToGoogleMaps(activity: Activity) {
        val uri: String =
            java.lang.String.format(Locale.ENGLISH, "geo:%f,%f", -8.139586, -79.039202)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        activity.startActivity(intent)
    }
}