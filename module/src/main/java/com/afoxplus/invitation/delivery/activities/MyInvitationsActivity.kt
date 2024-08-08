package com.afoxplus.invitation.delivery.activities

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.afoxplus.invitation.delivery.screens.MyInvitationScreen
import com.afoxplus.invitation.delivery.viewmodels.MyInvitationViewModel
import com.afoxplus.uikit.activities.UIKitBaseActivity
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MyInvitationsActivity : UIKitBaseActivity() {

    private val myInvitationViewModel: MyInvitationViewModel by viewModels()

    override fun setMainView() {
        setContent {
            UIKitTheme {
                MyInvitationScreen(
                    viewModel = myInvitationViewModel,
                    onBackPressed = { onBackPressedDispatcher.onBackPressed() }
                )
            }
        }
    }

    override fun setUpView() {

    }

}