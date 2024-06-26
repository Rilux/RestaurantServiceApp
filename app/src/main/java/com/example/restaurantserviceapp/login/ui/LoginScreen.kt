package com.example.restaurantserviceapp.login.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.util.PatternsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.restaurantserviceapp.NavGraphs
import com.example.restaurantserviceapp.destinations.AdminScreenDestination
import com.example.restaurantserviceapp.destinations.ForgotPasswordScreenDestination
import com.example.restaurantserviceapp.destinations.SignUpScreenDestination
import com.example.restaurantserviceapp.destinations.WaiterScreenDestination
import com.example.restaurantserviceapp.destinations.WaitingScreenDestination
import com.example.restaurantserviceapp.login.ui.model.LoginIntent
import com.example.restaurantserviceapp.login.ui.model.LoginSideEffect
import com.example.restaurantserviceapp.ui.theme.interFontFamily
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
@Destination
@RootNavGraph
fun LoginScreen(
    navigator: DestinationsNavigator,
) {
    val loginViewModel = hiltViewModel<LoginViewModel>()
    val context = LocalContext.current

    loginViewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            LoginSideEffect.NavigateToAdminPage -> navigator.navigate(AdminScreenDestination){
                popUpTo(NavGraphs.root)
            }

            LoginSideEffect.ShowErrorMessage -> {
                Toast.makeText(context, "Unexpected error happened, try again", Toast.LENGTH_LONG)
                    .show()
            }

            LoginSideEffect.NavigateToWaitingPage -> navigator.navigate(WaitingScreenDestination){
                popUpTo(NavGraphs.root)
            }

            LoginSideEffect.NavigateToWaiterPage -> {
                navigator.navigate(WaiterScreenDestination) {
                    popUpTo(NavGraphs.root)
                }

            }
        }
    }


    LoginComposable(
        onSignIn = { email, password ->
            loginViewModel.handleIntent(
                LoginIntent.OnCredentialsEntered(
                    email, password
                )
            )
        },
        onSignUp = {
            navigator.navigate(SignUpScreenDestination)
        },
        onForgotPassword = {
            navigator.navigate(ForgotPasswordScreenDestination)
        }

    )

}

@Composable
fun LoginComposable(
    onSignIn: (String, String) -> Unit,
    onSignUp: () -> Unit,
    onForgotPassword: () -> Unit,
) {
    Scaffold(
        Modifier.fillMaxSize()
    ) { paddings ->
        var emailValue by remember {
            mutableStateOf("")
        }

        var passwordValue by remember {
            mutableStateOf("")
        }

        val isEmailValid = remember(emailValue) { PatternsCompat.EMAIL_ADDRESS.matcher(emailValue).matches() }
        val isPasswordValid =
            remember(passwordValue) { passwordValue.length > 6 && passwordValue.any { it.isDigit() } && passwordValue.any { it.isLetter() } }

        Column(
            modifier = Modifier
                .padding(paddings)
                .padding(horizontal = 40.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Sign In",
                style = TextStyle(
                    fontFamily = interFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp
                )
            )

            OutlinedTextField(
                value = emailValue,
                onValueChange = {
                    emailValue = it
                },
                label = {
                    Text(text = "Email")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = passwordValue,
                onValueChange = {
                    passwordValue = it
                },
                label = {
                    Text(text = "Password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    autoCorrect = false
                ),
                modifier = Modifier.fillMaxWidth()

            )


            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onSignIn(emailValue, passwordValue) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors()
                    .copy(containerColor = Color(0xff0d99ff)),
                enabled = isEmailValid && isPasswordValid
            ) {
                Text(text = "Sign In")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { onForgotPassword() }) {

                    Text(
                        text = "Forgot password",
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                TextButton(onClick = { onSignUp() }) {
                    Text(
                        text = "Sign up",
                        color = Color(0xff0d99ff)
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun PreviewLoginComposable() {
    LoginComposable(
        { _, _ -> },
        {},
        {}
    )
}