package com.afoxplus.invitation.delivery.activities

import androidx.activity.compose.setContent
import com.afoxplus.invitation.delivery.screens.MyInvitationScreen
import com.afoxplus.uikit.activities.UIKitBaseActivity
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MyInvitationsActivity : UIKitBaseActivity() {

    override fun setMainView() {
        setContent {
            UIKitTheme {
                MyInvitationScreen(
                    onBackPressed = { onBackPressedDispatcher.onBackPressed() }
                )
            }
        }
    }

    override fun setUpView() {

    }

}