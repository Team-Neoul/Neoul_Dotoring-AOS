package com.example.dotoring_neoul.ui.register.first

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dotoring.R
import com.example.dotoring_neoul.ui.theme.DotoringTheme


// content - Introduce
@Composable
private fun MentorIntroduce(
    registerFirstViewModel: RegisterFirstViewModel = viewModel(),
    onMajorClick: () -> Unit,
    onJobClick: () -> Unit
) {
    val registerFirstUiState by registerFirstViewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    Row {
        Text(
            text = stringResource(id = R.string.register1_im),
            modifier = Modifier.padding(top = 10.dp),
            fontSize = 18.sp)


        Column() {
            Column() {
                TextFieldIntroduceContent(
                    value = registerFirstUiState.company,
                    onValueChange = {
                        registerFirstViewModel.updateUserCompany(it)
                        Log.d("onValueChange 테스트", "registerFirstViewModel.updateUserCompany 실행 - it: ${it}")

                        if (it == "") {
                            registerFirstViewModel.updateCompanyFieldState()
                            registerFirstViewModel.enableNextButton()
                            Log.d("onValueChange 테스트 뒤", "onValueChange it == null")
                        }
                    },
                    placeholder = stringResource(id = R.string.register1_company),
                    description = stringResource(R.string.register1_belong_to_mentor),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
                    readOnly = false,
                    onLooseFocus = {
                        registerFirstViewModel.updateCompanyFieldState()
                        registerFirstViewModel.enableNextButton()
                    }
                )
                TextFieldIntroduceContent(
                    value = registerFirstUiState.careerLevel,
                    onValueChange = {
                        registerFirstViewModel.updateUserCareer(it)
                        if (registerFirstUiState.careerLevel == "") {
                            registerFirstViewModel.updateCareerFieldState()
                        }
                    },
                    placeholder = stringResource(id = R.string.register1_years),
                    description = stringResource(R.string.register1_years_of_experience),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
                    readOnly = false,
                    onLooseFocus = {
                        registerFirstViewModel.updateCareerFieldState()
                        registerFirstViewModel.enableNextButton()
                    }
                )
                TextFieldIntroduceContent(
                    value = registerFirstViewModel.toString(registerFirstViewModel.selectedFieldList),
                    onValueChange = { },
                    placeholder = stringResource(id = R.string.register1_work),
                    description = stringResource(R.string.register1_mentor),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
                    readOnly = true,
                    showBottomSheet = onJobClick
                )
            }
            Log.d("리스트", "selectedFieldList: ${registerFirstViewModel.selectedFieldList.toString()}")

            Column {
                Text(
                    text = stringResource(id = R.string.register1_majored),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 10.dp, bottom = 8.dp)
                )
                TextFieldIntroduceContent(
                    value = registerFirstViewModel.toString(registerFirstViewModel.selectedMajorList),
                    onValueChange = { },
                    placeholder = stringResource(id = R.string.register1_major),
                    description = stringResource(id = R.string.register1_go),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { registerFirstViewModel.enableNextButton()
                            focusManager.clearFocus()}),
                    readOnly = true,
                    showBottomSheet = onMajorClick
                )
            }
        }
    }
}

@Composable
private fun MenteeIntroduce(
    registerFirstViewModel: RegisterFirstViewModel = viewModel(),
    onMajorClick: () -> Unit,
    onJobClick: () -> Unit
) {
    val registerFirstUiState by registerFirstViewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    Row {
        Text(
            text = stringResource(id = R.string.register1_im),
            modifier = Modifier.padding(top = 10.dp),
            fontSize = 18.sp)


        Column() {
            Column() {
                TextFieldIntroduceContent(
                    value = registerFirstUiState.company,
                    onValueChange = {
                        registerFirstViewModel.updateUserCompany(it)
                        Log.d("onValueChange 테스트", "registerFirstViewModel.updateUserCompany 실행 - it: $it")

                        if (it == "") {
                            registerFirstViewModel.updateCompanyFieldState()
                            registerFirstViewModel.enableNextButton()
                            Log.d("onValueChange 테스트 뒤", "onValueChange it == null")
                        }
                    },
                    placeholder = stringResource(id = R.string.register1_colleague),
                    description = stringResource(R.string.register1_belong_to_mentee),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
                    readOnly = false,
                    onLooseFocus = {
                        registerFirstViewModel.updateCompanyFieldState()
                        registerFirstViewModel.enableNextButton()
                    }
                )
                TextFieldIntroduceContent(
                    value = registerFirstUiState.careerLevel,
                    onValueChange = {
                        registerFirstViewModel.updateUserCareer(it)
                        if (registerFirstUiState.careerLevel == "") {
                            registerFirstViewModel.updateCareerFieldState()
                        }
                    },
                    placeholder = stringResource(id = R.string.register1_grade),
                    description = stringResource(R.string.register1_grade),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
                    readOnly = false,
                    onLooseFocus = {
                        registerFirstViewModel.updateCareerFieldState()
                        registerFirstViewModel.enableNextButton()
                    }
                )
                TextFieldIntroduceContent(
                    value = registerFirstViewModel.toString(registerFirstViewModel.selectedMajorList),
                    onValueChange = { },
                    placeholder = stringResource(id = R.string.register1_major),
                    description = stringResource(R.string.register1_mentee),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
                    readOnly = true,
                    showBottomSheet = onMajorClick
                )
            }
            Log.d("리스트", "selectedFieldList: ${registerFirstViewModel.selectedFieldList.toString()}")

            Column {
                Text(
                    text = stringResource(id = R.string.register1_in_the_future),
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 10.dp, bottom = 8.dp)
                )
                TextFieldIntroduceContent(
                    value = registerFirstViewModel.toString(registerFirstViewModel.selectedFieldList),
                    onValueChange = { },
                    placeholder = stringResource(id = R.string.register1_job_field),
                    description = stringResource(id = R.string.register1_want_to),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { registerFirstViewModel.enableNextButton()
                            focusManager.clearFocus()}),
                    readOnly = true,
                    showBottomSheet = onJobClick
                )
            }
        }
    }
}



// IntroduceContent - 소속, 연차
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TextFieldIntroduceContent(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    description: String,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    readOnly: Boolean,
    showBottomSheet: () -> Unit = {},
    onLooseFocus: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    
    val textFieldWidth = 120.dp
    val textFieldHeight = 33.dp
    val contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(bottom = 0.5.dp)
    val placeholderFontSize = 15.sp
    val descriptionFontSize = 18.sp
    val textFieldSidePadding = 25.dp
    val spaceBetweenParagraph = 10.dp
    val colors = TextFieldDefaults.textFieldColors(
        textColor = colorResource(id = R.color.black),
        focusedIndicatorColor = colorResource(id = R.color.black_500),
        unfocusedIndicatorColor = colorResource(id = R.color.black_500),
        backgroundColor = colorResource(id = R.color.white),
        placeholderColor = colorResource(id = R.color.grey_500)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =  Modifier.padding(start = textFieldSidePadding, bottom = spaceBetweenParagraph)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .size(width = textFieldWidth, height = textFieldHeight)
                .indicatorLine(
                    enabled = true,
                    isError = false,
                    interactionSource = interactionSource,
                    colors = colors
                )
                .onFocusChanged {
                    if (it.isFocused) {
                        Log.d("test", "test - isFocused")
                        showBottomSheet()
                        Log.d("test", "test - onClick")
                    } else {
                        onLooseFocus()
                    }
                },
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            enabled = true,
            singleLine = true,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            readOnly = readOnly
        ) {
            TextFieldDefaults.TextFieldDecorationBox(
                value = value,
                visualTransformation = VisualTransformation.None,
                innerTextField = it,
                singleLine = true,
                enabled = true,
                placeholder = {
                    Text(
                        text = placeholder,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = placeholderFontSize,
                        textAlign = TextAlign.Center
                    )
                },
                colors = colors,
                interactionSource = interactionSource,
                contentPadding = contentPadding,
            )
        }
        Text(
            text = description,
            fontSize = descriptionFontSize,
            modifier = Modifier.padding(start = textFieldSidePadding)
        )
    }
}


// ModalBottomSheetLayout - content
@Composable
fun FirstRegisterScreenContent(
    registerFirstViewModel: RegisterFirstViewModel = viewModel(),
    onMajorClick: () -> Unit,
    onMentoringFieldClick: () -> Unit,
    isMentor: Boolean
) {
    Column {
        if(isMentor) {
            MentorIntroduce(
                registerFirstViewModel = registerFirstViewModel,
                onMajorClick = onMajorClick,
                onJobClick = onMentoringFieldClick
            )
        } else {
            MenteeIntroduce(
                registerFirstViewModel = registerFirstViewModel,
                onMajorClick = onMajorClick,
                onJobClick = onMentoringFieldClick
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun RegisterScreenPreview() {
    DotoringTheme {
        FirstRegisterScreenContent(onMajorClick = {}, onMentoringFieldClick = {}, isMentor = false)
    }
}