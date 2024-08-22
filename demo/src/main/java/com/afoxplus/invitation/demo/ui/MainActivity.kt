package com.afoxplus.invitation.demo.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.afoxplus.invitation.delivery.components.banner.InvitationHomeBanner
import com.afoxplus.invitation.delivery.flows.InvitationFlow
import com.afoxplus.uikit.designsystem.atoms.UIKitButtonOutlineLarge
import com.afoxplus.uikit.designsystem.atoms.UIKitText
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var invitationFlow: InvitationFlow

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UIKitTheme {
                Scaffold(topBar = {
                    TopAppBar(title = { UIKitText(text = "YaListo") })
                }, content = { paddingValues ->
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                    ) {
                        InvitationHomeBanner( modifier = Modifier.align(
                            Alignment.TopCenter
                        ))

                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(UIKitTheme.spacing.spacing16),
                            verticalArrangement = Arrangement.spacedBy(UIKitTheme.spacing.spacing08)
                        ) {
                            UIKitButtonOutlineLarge(
                                text = "My Invitations"
                            ) {
                                invitationFlow.goToMyInvitationsActivity(this@MainActivity)
                            }
                            UIKitButtonOutlineLarge(
                                text = "Added Invitation"
                            ) {
                                invitationFlow.goToAddedInvitationActivity(this@MainActivity)
                            }

                            UIKitButtonOutlineLarge(
                                text = "Scan Invitation"
                            ) {
                                invitationFlow.goToScanInvitationTicketActivity(this@MainActivity, "AF12456")
                            }

                            UIKitButtonOutlineLarge(
                                text = "Go gps"
                            ) {
                                invitationFlow.goToGoogleMaps(this@MainActivity)
                            }

                        }


                    }
                })


            }
        }
    }
}