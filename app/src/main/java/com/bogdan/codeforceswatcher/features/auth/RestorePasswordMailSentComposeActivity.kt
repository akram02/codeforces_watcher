package com.bogdan.codeforceswatcher.features.auth

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.buttons.BigButton
import com.bogdan.codeforceswatcher.components.compose.NavigationBar
import com.bogdan.codeforceswatcher.components.compose.Title
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme

class RestorePasswordMailSentComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlgoismeTheme {
                RestorePasswordMailSentScreen()
            }
        }
    }

    @Composable
    private fun RestorePasswordMailSentScreen() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBar() },
            bottomBar = { BottomBar() },
            backgroundColor = AlgoismeTheme.colors.background
        ) {
            Content()
        }
    }

    @Composable
    private fun TopBar() { NavigationBar { finish() } }

    @Composable
    private fun BottomBar() {
        Text(
            text = getString(R.string.check_your_spam_folder),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 45.dp),
            style = AlgoismeTheme.typography.hintRegular,
            fontSize = 14.sp,
            color = AlgoismeTheme.colors.secondaryVariant,
            textAlign = TextAlign.Center
        )
    }

    @Composable
    private fun Content() {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(56.dp))

            Title(
                title = getString(R.string.check_your_box),
                style = AlgoismeTheme.typography.headerBigMedium.copy(
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(Modifier.height(12.dp))

            Title(
                title = getString(R.string.open_mail_hint),
                style = AlgoismeTheme.typography.headerSmallMedium.copy(textAlign = TextAlign.Center)
            )

            Spacer(Modifier.height(72.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_email),
                contentDescription = "Email",
                tint = AlgoismeTheme.colors.onBackground
            )

            Spacer(Modifier.height(72.dp))

            BigButton(getString(R.string.open_mail)) {
                startMailApp()
            }

            Spacer(Modifier.height(20.dp))

            BigButton(
                label = getString(R.string.back_to_sign_in),
                modifier = Modifier.border(
                    width = 2.dp,
                    color = AlgoismeTheme.colors.secondary,
                    shape = RoundedCornerShape(100)
                ),
                isInverted = true
            ) {
                startSignInActivity()
            }
        }
    }


    private fun startMailApp() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_APP_EMAIL)
        try {
            startActivity(Intent.createChooser(intent, "Email"))
        } catch (e: ActivityNotFoundException) {
            val toast = Toast.makeText(
                applicationContext,
                getString(R.string.mail_app_not_found),
                Toast.LENGTH_SHORT
            )
            toast.show()
        }
    }

    private fun startSignInActivity() {
        val intent = Intent(this, SignInComposeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent)
    }
}