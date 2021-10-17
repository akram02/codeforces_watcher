package com.bogdan.codeforceswatcher.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bogdan.codeforceswatcher.components.compose.*
import com.bogdan.codeforceswatcher.features.auth.SignUpActivity

@Composable
fun SignInScreen(activity: Activity) {
    val localFocusManager = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { NavigationBar() },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                AnnotatedClickableText(
                    text = "Don't have an account yet?",
                    textStyle = MaterialTheme.typography.body1.copy(fontSize = 14.sp),
                    clickableText = "Sign Up",
                    clickableTextStyle = MaterialTheme.typography.body2.copy(fontSize = 14.sp)
                ) {
                    activity.startActivity(Intent(activity, SignUpActivity::class.java))
                }
            }
        },
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(56.dp))
            Title("Sign In")
            Spacer(Modifier.height(44.dp))
            AuthTextField(
                label = "Email",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { localFocusManager.moveFocus(FocusDirection.Down) }
                )
            )
            Spacer(Modifier.height(24.dp))
            AuthTextField(
                label = "Password",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { localFocusManager.clearFocus() }
                ),

                visualTransformation = PasswordVisualTransformation(mask = '*')
            )
            Spacer(Modifier.height(72.dp))
            AuthButton("SIGN IN")
            Spacer(Modifier.height(72.dp))
            AnnotatedClickableText(clickableText = "Forgot password?") { /*TODO*/ }
        }
    }
}