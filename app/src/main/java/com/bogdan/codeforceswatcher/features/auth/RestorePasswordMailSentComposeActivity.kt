package com.bogdan.codeforceswatcher.features.auth

import android.app.usage.UsageEvents
import android.content.ActivityNotFoundException
import android.content.ClipDescription
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.components.compose.*
import com.bogdan.codeforceswatcher.components.compose.theme.AlgoismeTheme
import io.xorum.codeforceswatcher.features.auth.redux.AuthRequests
import io.xorum.codeforceswatcher.features.auth.redux.AuthState
import io.xorum.codeforceswatcher.redux.ToastAction
import io.xorum.codeforceswatcher.redux.store
import tw.geothings.rekotlin.StoreSubscriber

class RestorePasswordMailSentComposeActivity : ComponentActivity() {

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlgoismeTheme {
                RestorePasswordMailSentScreen()
            }
        }
    }

    @ExperimentalComposeUiApi
    @Composable
    private fun RestorePasswordMailSentScreen() {

        Box {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    NavigationBar { finish() }
                },
                bottomBar = {
                    LinkText(
                        linkTextData = listOf(
                            LinkTextData(getString(R.string.check_your_spam_folder))
                        ),
                        modifier = Modifier
                            .height(80.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        textStyle = MaterialTheme.typography.body1.copy(
                            fontSize = 14.sp,
                            color = MaterialTheme.colors.secondaryVariant
                        ),
                        paragraphStyle = ParagraphStyle(textAlign = TextAlign.Center)
                    )
                },
                backgroundColor = MaterialTheme.colors.background
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(Modifier.height(56.dp))

                    Title(
                        title = getString(R.string.check_your_box),
                        style = MaterialTheme.typography.h3.copy(
                            fontSize = 36.sp,
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(Modifier.height(12.dp))

                    Title(
                        title = getString(R.string.open_mail_hint),
                        style = MaterialTheme.typography.h6.copy(textAlign = TextAlign.Center)
                    )

                    Spacer(Modifier.height(72.dp))

                    Icon(
                        painter = painterResource(id = R.drawable.ic_email),
                        contentDescription = "Email",
                        tint = MaterialTheme.colors.onBackground
                    )

                    Spacer(Modifier.height(72.dp))

                    AuthButton(getString(R.string.open_mail)) {
                        startMailApp()
                    }

                    Spacer(Modifier.height(20.dp))

                    AuthButton(
                        label = getString(R.string.back_to_sign_in),
                        modifier = Modifier.border(
                            width = 2.dp,
                            color = MaterialTheme.colors.secondary,
                            shape = RoundedCornerShape(100)
                        ),
                        isInvert = true
                    ) {
                        startSignInActivity()
                    }
                }
            }
        }
    }

    private fun startSignInActivity() {
        startActivity(
            Intent(this, SignInComposeActivity::class.java)
        )
    }

    private fun startMailApp() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_APP_EMAIL)
        try {
            startActivity(Intent.createChooser(intent, "Email"))
        } catch (e: ActivityNotFoundException) {
            val toast = Toast.makeText(applicationContext, "Mail app not found", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}