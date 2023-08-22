package com.example.dotoring_neoul.ui.register.sixth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dotoring.R
import com.example.dotoring.navigation.AuthScreen
import com.example.dotoring_neoul.ui.register.sixthpackage.RegisterSixthViewModel
import com.example.dotoring_neoul.ui.theme.DotoringTheme
import com.example.dotoring_neoul.ui.util.EffectiveCheckButton
import com.example.dotoring_neoul.ui.util.RegisterScreenTop
import com.example.dotoring_neoul.ui.util.register.CommonTextField
import com.example.dotoring_neoul.ui.util.register.MentoInformation


@Composable
fun SixthRegisterScreen(
    registerSixthViewModel: RegisterSixthViewModel = viewModel(),
    navController: NavHostController,
    mentoInformation: MentoInformation
) {
    val registerSixthUiState by registerSixthViewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    val width: Dp = 310.dp

    val spaceBetweenTitleAndContent: Dp = 18.dp
    val spaceBetweenSurface: Dp = 15.dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 50.dp)
    ) {
        RegisterScreenTop(screenNumber = 6, question = R.string.register6_q6)

        Spacer(modifier = Modifier.weight(1f))

        Column() {
            Text(
                text = stringResource(id = R.string.register6_set_ID),
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.size(spaceBetweenTitleAndContent))

            Column() {

                TextFieldWithEffectiveCheckButton(
                    value = registerSixthUiState.memberId,
                    onValueChange = { registerSixthViewModel.updateUserId(it) },
                    placeholder = stringResource(id = R.string.register6_ID),
                    btnText = stringResource(R.string.register_nickname_duplication_check),
                    width = width,
                    onClick = {
                        registerSixthViewModel.userIdDuplicationCheck()
                        registerSixthViewModel.toggleErrorTextColor() },
                    onNext = { focusManager.moveFocus(FocusDirection.Next)}
                )

                Text(
                    text = stringResource(id = R.string.register6_ID_guide),
                    modifier = Modifier
                        .padding(start = 2.dp, top = 3.dp),
                    color = colorResource(id = R.color.grey_500),
                    fontSize = 10.sp
                )

                Text(
                    text = stringResource(id = R.string.register6_ID_error),
                    modifier = Modifier
                        .padding(start = 2.dp, top = 3.dp),
                    color = registerSixthUiState.idErrorTextColor,
                    fontSize = 10.sp
                )
            }
        }

        Spacer(modifier = Modifier.size(spaceBetweenSurface))

        Column() {
            Text(
                text = stringResource(id = R.string.register6_set_pass_word),
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.size(spaceBetweenTitleAndContent))

            CommonTextField(
                value = registerSixthUiState.password,
                onValueChange = { registerSixthViewModel.updateUserPassword(it) },
                placeholder = stringResource(id = R.string.register6_pass_word),
                width = width,
                imeAction = ImeAction.Next,
                onNext = { focusManager.moveFocus(FocusDirection.Next)},
                visualTransformation = PasswordVisualTransformation()
            )

            Column() {
                Box(
                    contentAlignment = Alignment.CenterEnd
                ) {
                    CommonTextField(
                        value = registerSixthUiState.passwordCertification,
                        onValueChange = { registerSixthViewModel.updatePasswordCertification(it) },
                        placeholder = stringResource(id = R.string.register6_pass_word_check),
                        width = width,
                        imeAction = ImeAction.Next,
                        onNext = { registerSixthViewModel.passwordErrorCheck()
                            focusManager.moveFocus(FocusDirection.Next)},
                        modifier = Modifier.onFocusChanged {
                            if( it.isCaptured ) {
                                registerSixthViewModel.passwordErrorCheck()
                            } },
                        visualTransformation = PasswordVisualTransformation()
                    )

                    Checkbox(
                        checked = registerSixthUiState.passwordCertified,
                        onCheckedChange = { },
                        colors = CheckboxDefaults.colors(
                            checkedColor = colorResource(id = R.color.green),
                            uncheckedColor = colorResource(id = R.color.grey_500),
                            checkmarkColor = Color(0xffffffff),
                            disabledColor = colorResource(id = R.color.green)
                        ),
                        enabled = false
                    )
                }

                Text(
                    text = stringResource(id = R.string.register6_pass_word_error),
                    modifier = Modifier
                        .padding(start = 2.dp, top = 3.dp),
                    color = registerSixthUiState.passwordErrorTextColor,
                    fontSize = 10.sp
                )
            }

        }

        Spacer(modifier = Modifier.size(spaceBetweenSurface))

        Column() {
            Text(
                text = stringResource(id = R.string.register6_set_email),
                fontSize = 17.sp
            )

            Spacer(modifier = Modifier.size(spaceBetweenTitleAndContent))

            Column(
                horizontalAlignment = Alignment.End
            ) {

                TextFieldWithEffectiveCheckButton(
                    value = registerSixthUiState.email,
                    onValueChange = { registerSixthViewModel.updateEmail(it) },
                    placeholder = stringResource(id = R.string.register6_email),
                    btnText = stringResource(R.string.register6_send_verification_code),
                    width = width,
                    onClick = {
                        registerSixthViewModel.sendAuthenticationCode()
                        registerSixthViewModel.startTimer()}, //countdown & 코드 발송
                    onNext = {focusManager.moveFocus(FocusDirection.Next)}
                )

                Text(
                    text = registerSixthUiState.certificationPeriod,
                    modifier = Modifier
                        .padding(end = 15.dp, top = 3.dp),
                    color = colorResource(id = R.color.error),
                    fontSize = 10.sp
                )

                Column() {
                    TextFieldWithEffectiveCheckButton(
                        value = registerSixthUiState.validationCode,
                        onValueChange = { registerSixthViewModel.updateValidationCode(it) },
                        placeholder = stringResource(id = R.string.register6_verification_code),
                        btnText = stringResource(R.string.register6_verify),
                        width = width,
                        onClick = {
                            registerSixthViewModel.codeCertification()
                        },
                        onDone = { focusManager.clearFocus() },
                        imeAction = ImeAction.Done
                    )

                    Text(
                        text = stringResource(id = R.string.register6_verify_error),
                        modifier = Modifier
                            .padding(start = 2.dp, top = 3.dp),
                        color = registerSixthUiState.emailErrorTextColor,
                        fontSize = 10.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Register6ScreenNextButton(
            onClick = {
                registerSixthViewModel.finalRegistser(mentoInformation)
                navController.navigate(AuthScreen.Login.route)
            },
            enabled = registerSixthUiState.btnState)

        Spacer(modifier = Modifier.weight(1.5f))
    }
}

@Composable
private fun Register6ScreenNextButton(onClick: ()->Unit = {}, enabled: Boolean = false) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(width = 300.dp, height = 45.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = colorResource(id = R.color.white),
            backgroundColor = colorResource(id = R.color.green),
            disabledBackgroundColor = colorResource(id = R.color.grey_200),
            disabledContentColor = colorResource(id = R.color.grey_500)
        ),
        shape = RoundedCornerShape(30.dp),
        enabled = enabled
    ) {
        Text(text = stringResource(id = R.string.register6_to_login_page))
    }
}

@Composable
private fun TextFieldWithEffectiveCheckButton(value: String, onValueChange: (String) -> Unit, placeholder: String, btnText: String, width: Dp, onClick: () -> Unit = {}, onNext: (KeyboardActionScope.() -> Unit)? = {}, onDone: (KeyboardActionScope.() -> Unit)? = {}, imeAction: ImeAction = ImeAction.Next ) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.CenterEnd
    ) {
        CommonTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            width = width,
            imeAction = imeAction,
            onNext = onNext,
            onDone = onDone
        )

        EffectiveCheckButton(onClick = onClick, text = btnText)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    DotoringTheme {
        SixthRegisterScreen(navController = rememberNavController(), mentoInformation = MentoInformation())
    }
}