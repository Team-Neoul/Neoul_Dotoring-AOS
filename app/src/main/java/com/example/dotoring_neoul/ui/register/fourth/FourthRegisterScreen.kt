package com.example.dotoring_neoul.ui.register.fourth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dotoring.R
import com.example.dotoring_neoul.navigation.AuthScreen
import com.example.dotoring_neoul.ui.theme.DotoringTheme
import com.example.dotoring_neoul.ui.util.TopRegisterScreen
import com.example.dotoring_neoul.ui.util.register.MentorInformation
import com.example.dotoring_neoul.ui.util.register.RegisterScreenNextButton

/**
 * 네번째 회원 가입 화면 composable
 */
@Composable
fun FourthRegisterScreen(
    registerFourthViewModel: RegisterFourthViewModel = viewModel(),
    navController: NavHostController,
    mentorInformation: MentorInformation
) {

    val registerFourthUiState by registerFourthViewModel.uiState.collectAsState()

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopRegisterScreen(4, R.string.register4_q4, stringResource(id = R.string.register4_guide))

        Spacer(modifier = Modifier.size(30.dp))

        Column(
            modifier = Modifier.padding(start = 30.dp, end = 30.dp)
        ) {
            Text(text = stringResource(id = R.string.register_A))

            Spacer(modifier = Modifier.size(10.dp))

            RoundedCornerTextField(
                value = registerFourthUiState.mentorIntroduction,
                onValueChange = {
                    registerFourthViewModel.updateIntroductionInput(it)

                    if(it.length in 10..100) {
                        registerFourthViewModel.updateNextButtonState(true)
                    } else {
                        registerFourthViewModel.updateNextButtonState(false)
                    }
                },
                onDone = {
                    focusManager.clearFocus()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(colorResource(R.color.grey_200))
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(1.dp))

            Text(
                text = if(!registerFourthUiState.nextButtonState) {
                    stringResource(id = R.string.register4_error_condition_not_satisfied)

                } else {
                    ""
                },
                modifier = Modifier
                    .padding(start = 2.dp, top = 3.dp),
                color = Color(0xffff7B7B),
                fontSize = 10.sp
            )
        }

        Spacer(modifier = Modifier.weight(5f))

        RegisterScreenNextButton(
            onClick = {
                val mentoInfo = MentorInformation(
                    company = mentorInformation.company,
                    careerLevel = mentorInformation.careerLevel,
                    field = mentorInformation.field,
                    major = mentorInformation.major,
                    employmentCertification = mentorInformation.employmentCertification,
                    graduateCertification = mentorInformation.graduateCertification,
                    nickname = mentorInformation.nickname,
                    introduction = registerFourthUiState.mentorIntroduction
                )
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "mentoInfo",
                    value = mentoInfo
                )
                navController.navigate(AuthScreen.Register5.route)
            },
            enabled = registerFourthUiState.nextButtonState)

        Spacer(modifier = Modifier.weight(4f))
    }
}

/**
 * 회원 가입 네번째 화면 텍스트필드 composable
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoundedCornerTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hintText: String = "",
    textStyle: TextStyle = MaterialTheme.typography.body1,
    maxLines: Int = 5,
    onDone: (KeyboardActionScope.() -> Unit)?
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        maxLines = maxLines,
        decorationBox = {innerTextField ->
            Box(modifier = modifier) {
                if(value.isEmpty()) {
                    Text(
                        text = hintText,
                        color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                    )
                }
                innerTextField()
            }
        }
    )
}
/*@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoundedCornerTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onDone: (KeyboardActionScope.() -> Unit)?
) {
    val interactionSource = remember { MutableInteractionSource() }
    // parameters below will be passed to BasicTextField for correct behavior of the text field,
    // and to the decoration box for proper styling and sizing
    val enabled = true
    val singleLine = false

    val colors = TextFieldDefaults.textFieldColors(
        textColor = colorResource(id = R.color.black),
        focusedIndicatorColor = Color(0xffffffff),
        unfocusedIndicatorColor = Color(0xffffffff),
        backgroundColor = colorResource(id = R.color.grey_200),
        placeholderColor = colorResource(id = R.color.grey_500)
    )
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .background(
                color = colorResource(id = R.color.grey_200),
                shape = RoundedCornerShape(15.dp)
            )
            .indicatorLine(
                enabled = enabled,
                isError = false,
                interactionSource = interactionSource,
                colors = colors
            )
            .size(width = 300.dp, height = 38.dp),
        visualTransformation = VisualTransformation.None,
        // internal implementation of the BasicTextField will dispatch focus events
        interactionSource = interactionSource,
        enabled = enabled,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = onDone),
        maxLines = 5
    ) {
        TextFieldDefaults.TextFieldDecorationBox(
            value = value,
            visualTransformation = VisualTransformation.None,
            innerTextField = it,
            singleLine = singleLine,
            enabled = enabled,
            // same interaction source as the one passed to BasicTextField to read focus state
            // for text field styling
            placeholder = {
                Text(
                    text = stringResource(id = R.string.register4_placeholder),
                    fontSize = 13.sp
                )
            },
            colors = colors,
            interactionSource = interactionSource,
            // keep vertical paddings but change the horizontal
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp
            )
        )
    }
}*/

/**
 * 회원 가입 네번째 화면 미리보기
 */
@Preview(showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    DotoringTheme {
        FourthRegisterScreen(navController = rememberNavController(), mentorInformation = MentorInformation())
    }
}