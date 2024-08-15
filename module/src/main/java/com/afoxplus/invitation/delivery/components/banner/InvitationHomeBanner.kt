package com.afoxplus.invitation.delivery.components.banner

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.afoxplus.invitation.R
import com.afoxplus.invitation.delivery.activities.MyInvitationsActivity
import com.afoxplus.uikit.designsystem.atoms.UIKitText
import com.afoxplus.uikit.designsystem.foundations.UIKitColorTheme
import com.afoxplus.uikit.designsystem.foundations.UIKitTheme

@Composable
fun InvitationHomeBanner(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        //Nothing
    }

    Surface(modifier = modifier, color = UIKitColorTheme.yellow100) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = UIKitTheme.spacing.spacing24,
                    horizontal = UIKitTheme.spacing.spacing16
                )
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(UIKitTheme.spacing.spacing16)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(UIKitTheme.spacing.spacing08)) {
                    UIKitText(
                        text = stringResource(id = R.string.invitation_banner_title),
                        style = UIKitTheme.typography.header02
                    )
                    UIKitText(
                        text = stringResource(id = R.string.invitation_banner_description),
                        style = UIKitTheme.typography.paragraph01
                    )
                }

                OutlinedButton(
                    shape = RoundedCornerShape(UIKitTheme.spacing.spacing08),
                    contentPadding = PaddingValues(
                        vertical = UIKitTheme.spacing.spacing04,
                        horizontal = UIKitTheme.spacing.spacing22
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = UIKitColorTheme.secondaryColor
                    ),
                    onClick = {
                        val intent = Intent(context, MyInvitationsActivity::class.java)
                        launcher.launch(intent)
                    }) {

                    UIKitText(
                        text = stringResource(id = R.string.invitation_banner_button_title),
                        style = UIKitTheme.typography.paragraph01
                    )
                }
            }
            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(140.dp),
                painter = painterResource(id = R.drawable.banner_minion),
                contentDescription = "Banner Minion"
            )
        }
    }
}