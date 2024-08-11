package com.afoxplus.invitation.delivery.activities

import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.afoxplus.invitation.delivery.flows.InvitationFlow
import com.afoxplus.invitation.delivery.models.StrategyInvitationDetail
import com.afoxplus.uikit.activities.UIKitBaseActivity
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme
import com.afoxplus.uikit.designsystem.molecules.UIKitLoading
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class ScanInvitationTicketActivity : UIKitBaseActivity() {

    @Inject
    lateinit var invitationFlow: InvitationFlow

    override fun setMainView() {
        setContent {
            val context = LocalContext.current
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) {
                invitationFlow.goToMyInvitationsActivity(this)
                finish()
            }
            UIKitTheme {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(
                        UIKitTheme.spacing.spacing08,
                        alignment = Alignment.CenterVertically
                    )
                ) { UIKitLoading() }
            }
            LaunchedEffect(key1 = Unit) {
                launcher.launch(
                    InvitationDetailActivity.getIntentInvitationDetail(
                        context = context,
                        code = intent.getStringExtra(INTENT_CODE_EXTRA).toString(),
                        useLocal = false,
                        StrategyInvitationDetail.CONFIRM
                    )
                )
            }
        }
    }

    override fun setUpView() {

    }


    companion object {
        private const val INTENT_CODE_EXTRA = "intent_code_extra"
        fun getIntentScanInvitationTicket(
            context: Context,
            code: String,
        ): Intent {
            return Intent(
                context,
                ScanInvitationTicketActivity::class.java
            ).apply {
                putExtra(INTENT_CODE_EXTRA, code)
            }
        }
    }
}