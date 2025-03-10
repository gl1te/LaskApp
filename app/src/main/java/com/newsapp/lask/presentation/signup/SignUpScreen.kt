package com.newsapp.lask.presentation.signup

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.newsapp.lask.R
import com.newsapp.lask.domain.model.AuthResult

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateHomeScreen: () -> Unit,
) {
    val state by viewModel.state
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.authResults) {
        viewModel.authResults.collect { result ->
            when (result) {
                is AuthResult.Authorized -> navigateHomeScreen()
                is AuthResult.Unauthorized -> println("Unauthorized during signup")
                is AuthResult.UnknownError -> println("Unknown error during signup")
            }
        }
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.mainanim))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = true,
        reverseOnRepeat = true,
        iterations = LottieConstants.IterateForever,
        speed = 0.5f
    )

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { focusManager.clearFocus() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimation(
            modifier = Modifier.size(260.dp).align(Alignment.CenterHorizontally),
            composition = composition,
            progress = progress
        )

        Text(
            text = stringResource(R.string.signup),
            fontSize = 32.sp,
            color = colorResource(id = R.color.text_title),
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.login,
            onValueChange = { viewModel.onEvent(SignUpEvent.LoginChanged(it)) },
            label = {
                Text(
                    text = state.loginError?.let { stringResource(it) } ?: stringResource(R.string.login),
                    color = if (state.loginError != null) Color.Red else Color.Unspecified
                )
            },
            leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).focusRequester(focusRequester),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = state.email,
            onValueChange = { viewModel.onEvent(SignUpEvent.EmailChanged(it)) },
            label = {
                Text(
                    text = state.emailError?.let { stringResource(it) } ?: stringResource(R.string.email),
                    color = if (state.emailError != null) Color.Red else Color.Unspecified
                )
            },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).focusRequester(focusRequester),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = state.password,
            onValueChange = { viewModel.onEvent(SignUpEvent.PasswordChanged(it)) },
            label = {
                Log.d("SignUpScreen", "passwordError: ${state.passwordError}")
                Text(
                    text = state.passwordError?.let { stringResource(it) } ?: stringResource(R.string.password),
                    color = if (state.passwordError != null) Color.Red else Color.Unspecified
                )
            },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24
                Icon(
                    painter = painterResource(image),
                    contentDescription = null,
                    modifier = Modifier.clickable { passwordVisible = !passwordVisible }
                )
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).focusRequester(focusRequester),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = state.passwordConfirm,
            onValueChange = { viewModel.onEvent(SignUpEvent.ConfirmPasswordChanged(it)) },
            label = {
                Log.d("SignUpScreen", "passwordConfirmError: ${state.passwordConfirmError}")
                Text(
                    text = state.passwordConfirmError?.let { stringResource(it) } ?: stringResource(R.string.confirm_password),
                    color = if (state.passwordConfirmError != null) Color.Red else Color.Unspecified
                )
            },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp).focusRequester(focusRequester),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.onEvent(SignUpEvent.Submit) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 70.dp)
        ) {
            Text(
                text = stringResource(R.string.signup_reg),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.clickable { navigateToLogin() },
            text = stringResource(R.string.login_name) + "?",
            color = colorResource(id = R.color.text_title),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}