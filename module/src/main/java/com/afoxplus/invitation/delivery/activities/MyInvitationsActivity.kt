package com.afoxplus.invitation.delivery.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.afoxplus.invitation.delivery.models.StrategyInvitationDetail
import com.afoxplus.invitation.delivery.screens.MyInvitationScreen
import com.afoxplus.invitation.delivery.viewmodels.MyInvitationViewModel
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
internal class MyInvitationsActivity : ComponentActivity() {

    private val myInvitationViewModel: MyInvitationViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UIKitTheme {
                val modalBottomSheetState = rememberModalBottomSheetState()
                val context = LocalContext.current
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartActivityForResult()
                ) { result ->
                    if (result.resultCode == RESULT_OK) {
                        myInvitationViewModel.fetch()
                    }
                }

                MyInvitationScreen(
                    modalBottomSheetState = modalBottomSheetState,
                    viewModel = myInvitationViewModel,
                    onBackPressed = { onBackPressedDispatcher.onBackPressed() },
                    onItemInvitationClicked = {
                        launcher.launch(
                            InvitationDetailActivity.getIntentInvitationDetail(
                                context = context,
                                code = it.code,
                                useLocal = true,
                                StrategyInvitationDetail.CARTA
                            )
                        )
                    }
                )

                LaunchedEffect(key1 = Unit) {
                    myInvitationViewModel.navigation.collectLatest { nav ->
                        when (nav) {
                            is MyInvitationViewModel.Navigation.ToInvitationDetail -> {
                                modalBottomSheetState.hide()
                                launcher.launch(
                                    InvitationDetailActivity.getIntentInvitationDetail(
                                        context = context,
                                        code = nav.invitation.code,
                                        useLocal = true,
                                        StrategyInvitationDetail.CONFIRM
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}