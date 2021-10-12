package com.bogdan.codeforceswatcher.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.bogdan.codeforceswatcher.components.compose.*

@Composable
fun SignInScreen() {
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
                    clickableText = "Sign Up"
//                    textStyle = TextStyle(
//                        fontFamily = sfMono,
////                        color = MaterialTheme.colors.secondary,
//                        fontSize = 14.sp,
//                        letterSpacing = (-1).sp
//                    ),
//                    clickableTextStyle = TextStyle(
//                        fontFamily = sfMono,
////                        color = MaterialTheme.colors.onBackground,
//                        fontSize = 14.sp,
//                        letterSpacing = (-1).sp,
//                        textDecoration = TextDecoration.Underline
//                    )
                )
            }
        },

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
                    keyboardType = KeyboardType.Email
                )
            )
            Spacer(Modifier.height(24.dp))
            AuthTextField(
                label = "Password",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation(mask = '*')
            )
            Spacer(Modifier.height(72.dp))
            AuthButton("SIGN IN")
            Spacer(Modifier.height(72.dp))
            AnnotatedClickableText(
                clickableText = "Forgot password?"
//                clickableTextStyle = TextStyle(
//                    fontFamily = sfMono,
////                    color = MaterialTheme.colors.onBackground,
//                    fontSize = 14.sp,
//                    fontWeight = FontWeight.SemiBold,
//                    textDecoration = TextDecoration.Underline
//                )
            )
        }
    }
}