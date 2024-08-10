package com.afoxplus.invitation.delivery.activities

import android.content.Context
import android.content.Intent
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.afoxplus.invitation.delivery.screens.InvitationDetailScreen
import com.afoxplus.invitation.delivery.viewmodels.InvitationDetailViewModel
import com.afoxplus.uikit.activities.UIKitBaseActivity
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class InvitationDetailActivity : UIKitBaseActivity() {
    private val viewModel: InvitationDetailViewModel by viewModels()

    override fun setMainView() {
        setContent {
            UIKitTheme {
                intent.getStringExtra(INTENT_CODE_EXTRA)?.let { code ->
                    InvitationDetailScreen(
                        viewModel = viewModel,
                        code = code,
                        useLocal = intent.getBooleanExtra(INTENT_USE_LOCAL_EXTRA, false),
                        onBackPressed = { onBackPressedDispatcher.onBackPressed() },
                        onConfirmSuccess = { finishSuccess() }
                    )
                }
            }
        }
    }

    private fun finishSuccess() {
        setResult(RESULT_OK)
        finish()
    }

    override fun setUpView() {

    }

    companion object {
        private const val INTENT_CODE_EXTRA = "intent_code_extra"
        private const val INTENT_USE_LOCAL_EXTRA = "intent_use_local_extra"
        fun getIntentInvitationDetail(context: Context, code: String, useLocal: Boolean): Intent {
            return Intent(
                context,
                InvitationDetailActivity::class.java
            ).apply {
                putExtra(INTENT_CODE_EXTRA, code)
                putExtra(INTENT_USE_LOCAL_EXTRA, useLocal)
            }
        }
    }


}