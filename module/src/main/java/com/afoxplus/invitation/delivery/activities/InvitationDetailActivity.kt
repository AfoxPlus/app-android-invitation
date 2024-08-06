package com.afoxplus.invitation.delivery.activities

import androidx.activity.compose.setContent
import com.afoxplus.invitation.delivery.screens.InvitationDetailScreen
import com.afoxplus.uikit.activities.UIKitBaseActivity
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class InvitationDetailActivity : UIKitBaseActivity() {

    override fun setMainView() {
        setContent {
            UIKitTheme {
                InvitationDetailScreen(
                    onBackPressed = { onBackPressedDispatcher.onBackPressed() }
                )
            }
        }
    }

    override fun setUpView() {

    }
}