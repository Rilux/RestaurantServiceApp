package com.example.restaurantserviceapp.singup.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.util.PatternsCompat

@Composable
fun SignUpComposable(
    onSignUp: (String, String, String, String) -> Unit,
) {

    var fullNameValue by remember {
        mutableStateOf("")
    }

    var emailValue by remember {
        mutableStateOf("")
    }

    var passwordValue by remember {
        mutableStateOf("")
    }

    var secondPasswordValue by remember {
        mutableStateOf("")
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xffE6E6E6))
                .padding(16.dp)
        ) {

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = fullNameValue,
                onValueChange = {
                    fullNameValue = it
                },
                label = {
                    Text(text = "Full name")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    autoCorrect = true
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

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

            Spacer(Modifier.height(16.dp))

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

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = secondPasswordValue,
                onValueChange = {
                    secondPasswordValue = it
                },
                label = {
                    Text(text = "Re enter password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    autoCorrect = false
                ),
                modifier = Modifier.fillMaxWidth()

            )

            Spacer(
                Modifier
                    .heightIn(16.dp)
                    .weight(1f)
            )

            Button(
                onClick = {
                    onSignUp(
                        fullNameValue,
                        emailValue,
                        passwordValue,
                        secondPasswordValue
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors()
                    .copy(containerColor = Color(0xff0d99ff)),
                enabled = emailValue.isNotBlank()
                        && PatternsCompat.EMAIL_ADDRESS.matcher(emailValue).matches()
                        && passwordValue.isNotBlank()
                        && passwordValue == secondPasswordValue
                        && fullNameValue.isNotBlank()
                        && passwordValue.length > 6
                        && passwordValue.any { it.isDigit() }
                        && passwordValue.any { it.isLetter() }
            ) {
                Text(text = "Sign up")
            }

            Spacer(Modifier.height(16.dp))

        }
    }
}