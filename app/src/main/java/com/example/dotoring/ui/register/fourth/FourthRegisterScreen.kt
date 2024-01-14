package com.example.dotoring.ui.register.fourth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dotoring.R
import com.example.dotoring.navigation.AuthScreen
import com.example.dotoring.ui.theme.DotoringTheme
import com.example.dotoring.ui.util.TopRegisterScreen
import com.example.dotoring.ui.util.register.MenteeInformation
import com.example.dotoring.ui.util.register.MentorInformation
import com.example.dotoring.ui.util.common.BottomButtonLong

/**
 * 네번째 회원 가입 화면 composable
 */
@Composable
fun FourthRegisterScreen(
    registerFourthViewModel: RegisterFourthViewModel = viewModel(),
    navController: NavHostController,
    mentorInformation: MentorInformation?,
    menteeInformation: MenteeInformation?,
    isMentor: Boolean
) {
    val registerFourthUiState = registerFourthViewModel.uiState.collectAsState()

    val question = if (isMentor) {
        R.string.register4_q4_mentor
    } else {
        R.string.register4_q4_mentee
    }
    Row {
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .requiredWidth(width = 320.dp)
        ) {
            TopRegisterScreen(
                screenNumber = 4,
                question = question,
                guide = stringResource(id = R.string.register4_guide),
                isMentor = isMentor
            )
            Spacer(modifier = Modifier.height(23.dp))


            Row {
                Text(
                    text = stringResource(id = R.string.register_A),
                    modifier = Modifier.padding(top = 15.dp)
                )
                Spacer(modifier = Modifier.size(7.dp))
                TagContent(
                    addTag = { tag -> (registerFourthViewModel::addTag)(tag) },
                    deleteTag = { tag -> (registerFourthViewModel::deleteTag)(tag) },
                    registerFourthUiState = registerFourthUiState
                )
            }
            Spacer(modifier = Modifier.weight(1f))


            BottomButtonLong(
                onClick = {
                    if (isMentor) {
                        if (mentorInformation != null) {
                            val mentorInfo = MentorInformation(
                                company = mentorInformation.company,
                                careerLevel = mentorInformation.careerLevel,
                                field = mentorInformation.field,
                                major = mentorInformation.major,
                                employmentCertification = mentorInformation.employmentCertification,
                                graduateCertification = mentorInformation.graduateCertification,
                                nickname = mentorInformation.nickname,
                                introduction = registerFourthUiState.value.tags.joinToString(separator = " ")
                            )
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "mentorInfo",
                                value = mentorInfo
                            )
                        }
                    } else {
                        if (menteeInformation != null) {
                            val menteeInfo = MenteeInformation(
                                school = menteeInformation.school,
                                grade = menteeInformation.grade,
                                field = menteeInformation.field,
                                major = menteeInformation.major,
                                enrollmentCertification = menteeInformation.enrollmentCertification,
                                nickname = menteeInformation.nickname,
                                introduction = registerFourthUiState.value.tags.joinToString(separator = " ")
                            )
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "menteeInfo",
                                value = menteeInfo
                            )
                        }
                    }
                    navController.navigate(AuthScreen.Register5.passScreenState(isMentor))
                },
                enabled = registerFourthUiState.value.tags.size in 1 .. 3,
                isMentor = isMentor
            )
            Spacer(modifier = Modifier.weight(5f))
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun TagContent(
    addTag: (String) -> Unit,
    deleteTag: (String) -> Unit,
    registerFourthUiState: State<RegisterFourthUiState>
) {
    var tag by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    val keyboardController = LocalSoftwareKeyboardController.current

    if(" " in tag) {
        addTag(tag)
        tag = ""
        keyboardController?.hide()
    }

    Column {
        LazyColumn {
            items(registerFourthUiState.value.tags) { tag ->
                Tag(
                    tag = tag,
                    deleteTag = { deleteTag(tag) }
                )
                Spacer(modifier = Modifier.size(11.dp))
            }
        }
        if(registerFourthUiState.value.tags.size < 3) {
            BasicTextField(
                value = tag,
                onValueChange = { tag = it },
                textStyle = MaterialTheme.typography.body1,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        addTag(tag)
                        tag = ""
                        keyboardController?.hide()

                    },
                ),
                decorationBox = @Composable { innerTextField ->
                    TextFieldDefaults.TextFieldDecorationBox(
                        value = tag,
                        innerTextField = innerTextField,
                        placeholder = { Text(text = "#태그") },
                        singleLine = true,
                        enabled = true,
                        interactionSource = interactionSource,
                        visualTransformation = VisualTransformation.None
                    )
                }
            )
        }
    }
}

@Composable
private fun Tag(tag: String, deleteTag: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(colorResource(R.color.grey_100))
            .padding(horizontal = 14.dp, vertical = 11.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("#$tag")
            Spacer(modifier = Modifier.size(7.dp))
            Icon(
                painter = painterResource(id = R.drawable.tag_erase_button_12dp),
                contentDescription = null,
                modifier = Modifier
                    .clickable { deleteTag() }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun NewFourthScreenPreview() {
    DotoringTheme {
        FourthRegisterScreen(
            navController = rememberNavController(),
            mentorInformation = MentorInformation(),
            menteeInformation = MenteeInformation(),
            isMentor = true
        )
    }
}