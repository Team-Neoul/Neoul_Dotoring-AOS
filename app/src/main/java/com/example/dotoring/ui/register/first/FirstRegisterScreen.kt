package com.example.dotoring.ui.register.first

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dotoring.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import com.example.dotoring.navigation.AuthScreen
import com.example.dotoring.ui.theme.DotoringTheme
import com.example.dotoring.ui.util.TopRegisterScreen
import com.example.dotoring.ui.util.bottomsheet.BottomSheetLayout
import com.example.dotoring.ui.util.register.MenteeInformation
import com.example.dotoring.ui.util.register.MentorInformation
import com.example.dotoring.ui.util.register.RegisterScreenNextButton

// 메인 화면
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FirstRegisterScreen(
    registerFirstViewModel: RegisterFirstViewModel = viewModel(),
    navController: NavHostController,
    isMentor: Boolean = true
) {
    /*    val filterBottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
        )*/
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    var isMajorBottomSheet by remember { mutableStateOf(false) }
    val registerFirstUiState by registerFirstViewModel.uiState.collectAsState()

    val chosenMajorList = registerFirstViewModel.selectedMajorList
    val chosenFieldList = registerFirstViewModel.selectedFieldList

    val titleText = if (isMajorBottomSheet) {
        stringResource(R.string.choose_major)
    } else {
        stringResource(R.string.choose_mentoring_field)
    }
    val selectedDataList = if (isMajorBottomSheet) {
        chosenMajorList
    } else {
        chosenFieldList
    }
    val optionDataList = if (isMajorBottomSheet) {
        registerFirstUiState.optionMajorList
    } else {
        registerFirstUiState.optionFieldList
    }

    val sheetRadius = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
    val backgroundColor = if (isMentor) {
        colorResource(R.color.green)
    } else {
        colorResource(R.color.navy)
    }
    val contentColor = colorResource(R.color.white)

    LaunchedEffect(Unit) {
        // State Change Callback
        snapshotFlow { bottomSheetScaffoldState.bottomSheetState.currentValue == BottomSheetValue.Expanded }.collect { isVisible ->
            if (isVisible) {
                // Sheet is visible
            } else {
                // Not visible
                registerFirstViewModel.updateChosenMajorList(chosenMajorList)
                registerFirstViewModel.updateMajorFieldState()
                registerFirstViewModel.updateChosenFieldList(chosenFieldList)
                registerFirstViewModel.updateFieldFieldState()
                registerFirstViewModel.enableNextButton()
            }
        }
    }
// 여기부터 ----------------------------
    BottomSheetScaffold(
        sheetContent = {
            BottomSheetLayout(
                title = titleText,
                selectedDataList = selectedDataList,
                optionDataList = optionDataList,
                onResetButtonClick = { registerFirstViewModel.removeAll(selectedDataList) },
                onSelectedDataClick = { item ->
                    registerFirstViewModel.remove(selectedDataList, item)
                },
                onOptionDataClick = { option ->
                    registerFirstViewModel.add(selectedDataList, option)
                    Log.d("리스트", "selectedDataList: $selectedDataList")
                },
                isMentor = isMentor
            )
        },
        scaffoldState = bottomSheetScaffoldState,
        snackbarHost = { SnackbarHost(hostState = bottomSheetScaffoldState.snackbarHostState) },
        sheetShape = sheetRadius,
        sheetBackgroundColor = backgroundColor,
        sheetContentColor = contentColor,
        sheetPeekHeight = 0.dp,
    ) {
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    TopRegisterScreen(
                        screenNumber = 1,
                        question = if (isMentor) {
                            R.string.register1_q1_mentor
                        } else {
                            R.string.register1_q1_mentee
                        },
                        isMentor = isMentor
                    )
                    Spacer(modifier = Modifier.size(30.dp))


                    FirstRegisterScreenContent(
                        onMentoringFieldClick = {
                            registerFirstViewModel.loadFieldList()
                            isMajorBottomSheet = false
                            scope.launch {
                                if (bottomSheetScaffoldState.bottomSheetState.currentValue == BottomSheetValue.Collapsed) {
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                } else {
                                    bottomSheetScaffoldState.bottomSheetState.collapse()
                                }

                                if(registerFirstViewModel.isSnackbarShowing.value) {
                                    bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                                        message = registerFirstUiState.snackbarMessage,
                                        actionLabel = "close",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }

                        },
                        onMajorClick = {
                            registerFirstViewModel.loadMajorList()
                            isMajorBottomSheet = true
                            scope.launch {
                                if (bottomSheetScaffoldState.bottomSheetState.currentValue == BottomSheetValue.Collapsed) {
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                } else {
                                    bottomSheetScaffoldState.bottomSheetState.collapse()
                                }

                                if(registerFirstViewModel.isSnackbarShowing.value) {
                                    bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                                        message = registerFirstUiState.snackbarMessage,
                                        actionLabel = "close",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        },
                        registerFirstViewModel = registerFirstViewModel,
                        isMentor = isMentor
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                RegisterScreenNextButton(
                    onClick = {
                        if (isMentor) {
                            val mentorInfo = MentorInformation(
                                company = registerFirstUiState.company,
                                careerLevel = registerFirstUiState.careerLevel.toInt(),
                                field = registerFirstUiState.chosenFieldList,
                                major = registerFirstUiState.chosenMajorList
                            )
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "mentorInfo",
                                value = mentorInfo
                            )
                            navController.navigate(AuthScreen.Register2.passScreenState(isMentor = true))
                        } else {
                            val menteeInfo = MenteeInformation(
                                school = registerFirstUiState.company,
                                grade = registerFirstUiState.careerLevel.toInt(),
                                field = registerFirstUiState.chosenFieldList,
                                major = registerFirstUiState.chosenMajorList
                            )
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "menteeInfo",
                                value = menteeInfo
                            )
                            navController.navigate(AuthScreen.Register2.passScreenState(isMentor = false))
                        }
                    },
                    enabled = registerFirstUiState.firstBtnState,
                    isMentor = isMentor
                )
                Spacer(modifier = Modifier.weight(3f))
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
// 여기까지 ----------------------------
    /*ModalBottomSheetLayout(
        sheetContent = {
            BottomSheetLayout(
                title = titleText,
                selectedDataList = selectedDataList,
                optionDataList = optionDataList,
                onResetButtonClick = { registerFirstViewModel.removeAll(selectedDataList) },
                onSelectedDataClick = { item ->
                    registerFirstViewModel.remove(selectedDataList, item)
                },
                onOptionDataClick = { option ->
                    registerFirstViewModel.add(selectedDataList, option)
                    Log.d("리스트", "selectedDataList: $selectedDataList")
                },
                isMentor = isMentor
            )
        },
        sheetState = filterBottomSheetState,
        sheetShape = sheetRadius,
        sheetBackgroundColor = backgroundColor,
        sheetContentColor = contentColor
    ) {
        Row {
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    TopRegisterScreen(
                        screenNumber = 1,
                        question = if (isMentor) {
                            R.string.register1_q1_mentor
                        } else {
                            R.string.register1_q1_mentee
                        },
                        isMentor = isMentor
                    )
                    Spacer(modifier = Modifier.size(30.dp))


                    FirstRegisterScreenContent(
                        onMentoringFieldClick = {
                            isMajorBottomSheet = false
                            scope.launch {
                                if (!filterBottomSheetState.isVisible) {
                                    filterBottomSheetState.show()
                                } else {
                                    filterBottomSheetState.hide()
                                }
                            }
                        },
                        onMajorClick = {
                            isMajorBottomSheet = true
                            scope.launch {
                                if (!filterBottomSheetState.isVisible) {
                                    filterBottomSheetState.show()
                                } else {
                                    filterBottomSheetState.hide()
                                }
                            }
                        },
                        registerFirstViewModel = registerFirstViewModel,
                        isMentor = isMentor
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                RegisterScreenNextButton(
                    onClick = {
                        if (isMentor) {
                            val mentorInfo = MentorInformation(
                                company = registerFirstUiState.company,
                                careerLevel = registerFirstUiState.careerLevel.toInt(),
                                field = registerFirstUiState.chosenFieldList,
                                major = registerFirstUiState.chosenMajorList
                            )
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "mentorInfo",
                                value = mentorInfo
                            )
                            navController.navigate(AuthScreen.Register2.passScreenState(isMentor = true))
                        } else {
                            val menteeInfo = MenteeInformation(
                                school = registerFirstUiState.company,
                                grade = registerFirstUiState.careerLevel.toInt(),
                                field = registerFirstUiState.chosenFieldList,
                                major = registerFirstUiState.chosenMajorList
                            )
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "menteeInfo",
                                value = menteeInfo
                            )
                            navController.navigate(AuthScreen.Register2.passScreenState(isMentor = false))
                        }
                    },
                    enabled = registerFirstUiState.firstBtnState,
                    isMentor = isMentor
                )
                Spacer(modifier = Modifier.weight(3f))
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }*/
}


@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    DotoringTheme {
        FirstRegisterScreen(
            navController = rememberNavController(),
            isMentor = false
        )
    }
}