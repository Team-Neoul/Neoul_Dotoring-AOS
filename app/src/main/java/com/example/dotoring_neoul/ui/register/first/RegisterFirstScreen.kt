package com.example.dotoring_neoul.ui.register.first

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.dotoring_neoul.navigation.AuthScreen
import com.example.dotoring_neoul.ui.theme.DotoringTheme
import com.example.dotoring_neoul.ui.theme.Green
import com.example.dotoring_neoul.ui.util.RegisterScreenTop
import com.example.dotoring_neoul.ui.util.bottomsheet.BottomSheetOption
import com.example.dotoring_neoul.ui.util.bottomsheet.SelectedData
import com.example.dotoring_neoul.ui.util.register.MentoInformation
import com.example.dotoring_neoul.ui.util.register.RegisterScreenNextButton

/**
 * 분기 화면 Composable
 */
// content - Introduce
@Composable
private fun Introduce(
    registerFirstViewModel: RegisterFirstViewModel = viewModel(),
    onMajorClick: () -> Unit,
    onJobClick: () -> Unit
) {

    val registerFirstUiState by registerFirstViewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row() {

            Text(
                text = stringResource(id = R.string.register1_im),
                modifier = Modifier.padding(top = 10.dp),
                fontSize = 18.sp)

            Spacer(modifier = Modifier.size(25.dp))

            Column() {
                Column() {
                    TextFieldIntroduceContent(
                        value = registerFirstUiState.company,
                        onValueChange = {
                            registerFirstViewModel.updateUserCompany(it)
                            if(registerFirstUiState.company == "") {
                                registerFirstViewModel.updateCompanyFieldState(true)
                            } else {
                                registerFirstViewModel.updateCompanyFieldState(false)
                            }
                            registerFirstViewModel.enableNextButton()
                        },
                        placeholder = stringResource(id = R.string.register1_company),
                        text = stringResource(R.string.register1_belong_to),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(
                            FocusDirection.Next)}),
                        readOnly = false,
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    TextFieldIntroduceContent(
                        value = registerFirstUiState.careerLevel,
                        onValueChange = {
                            registerFirstViewModel.updateUserCareer(it)
                            if(registerFirstUiState.careerLevel == "") {
                                registerFirstViewModel.updateCareerFieldState(true)
                            } else {
                                registerFirstViewModel.updateCareerFieldState(false)
                            }
                            registerFirstViewModel.enableNextButton()
                        },
                        placeholder = stringResource(id = R.string.register1_years),
                        text = stringResource(R.string.register1_years_of_experience),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(
                            FocusDirection.Next) }),
                        readOnly = false,
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    TextFieldIntroduceContent(
                        value = registerFirstViewModel.toString(registerFirstViewModel.selectedJobList),
                        onValueChange = {
                            if(registerFirstUiState.chosenJobList.toString() == "") {
                                registerFirstViewModel.updateJobFieldState(true)
                            } else {
                                registerFirstViewModel.updateJobFieldState(false)
                            }
                            registerFirstViewModel.enableNextButton()
                        },
                        placeholder = stringResource(id = R.string.register1_work),
                        text = stringResource(R.string.register1_),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(
                            FocusDirection.Next) }),
                        readOnly = true,
                        onClick = onJobClick
                    )
                }
                Log.d("리스트", "selectedJobList: ${registerFirstViewModel.selectedJobList.toString()}")

                Spacer(modifier = Modifier.size(18.dp))

                Column(
                ) {
                    Text(
                        text = stringResource(id = R.string.register1_majored),
                        fontSize = 18.sp
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    TextFieldIntroduceContent(
                        value = registerFirstViewModel.toString(registerFirstViewModel.selectedMajorList),
                        onValueChange = {
                            if(registerFirstUiState.chosenMajorList.toString() == "") {
                                registerFirstViewModel.updateMajorFieldState(true)
                            } else {
                                registerFirstViewModel.updateMajorFieldState(false)
                            }
                            registerFirstViewModel.enableNextButton()
                        },
                        placeholder = stringResource(id = R.string.register1_major),
                        text = stringResource(id = R.string.register1_go),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { registerFirstViewModel.enableNextButton()
                                focusManager.clearFocus()}),
                        readOnly = true,
                        onClick = onMajorClick
                    )
                }
            }
        }
    }
}


/**
 * TextField Composable
 */
// IntroduceContent - 소속, 연차
@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TextFieldIntroduceContent(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    text: String,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    readOnly: Boolean,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }

    val colors = TextFieldDefaults.textFieldColors(
        textColor = colorResource(id = R.color.black),
        focusedIndicatorColor = colorResource(id = R.color.black),
        unfocusedIndicatorColor = colorResource(id = R.color.black),
        backgroundColor = colorResource(id = R.color.white),
        placeholderColor = colorResource(id = R.color.grey_500)
    )

    Row(verticalAlignment = Alignment.CenterVertically) {

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .size(width = 120.dp, height = 40.dp)
                .background(
                    color = colorResource(R.color.white),
                    shape = TextFieldDefaults.TextFieldShape
                )
                .indicatorLine(
                    enabled = true,
                    isError = false,
                    interactionSource = interactionSource,
                    colors = colors
                )
                .onFocusChanged {
                    if (it.isFocused) {
                        Log.d("test", "test - isFocused")
                        onClick()
                        Log.d("test", "test - onClick")

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
                // same interaction source as the one passed to BasicTextField to read focus state
                // for text field styling
                placeholder = {
                    Text(
                        text = placeholder,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 15.sp,
                        textAlign = TextAlign.Center
                    )
                },
                colors = colors,
                interactionSource = interactionSource,
                // keep vertical paddings but change the horizontal
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    start = 2.dp, top = 1.dp, end = 1.dp, bottom = 3.dp
                ),
            )
        }

        Spacer(modifier = Modifier.size(25.dp))

        Text(
            text = text,
            fontSize = 18.sp)
    }
}

/**
 * ModalBottomSheet Composable
 */
@Composable
fun MyModalBottomSheetLayout(
    text: String,
    selectedDataList: List<String>,
    optionDataList: List<String>,
    updateChosenList: (String) -> Unit,
    registerFirstUiState: RegisterFirstUiState,
    registerFirstViewModel: RegisterFirstViewModel
) {
    // val selectedDataList by remember { mutableStateOf(mutableListOf<String>()) }
    // var selectedDataList by remember { mutableStateOf(registerFirstUiState.chosenMajorList)}

    Row(
        modifier = Modifier.padding(top = 30.dp)

    ) {
        Spacer(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Spacer(modifier = Modifier.size(width = 30.dp, height = 0.dp))

                Text(
                    text = text,
                    color = colorResource(id = R.color.white),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.weight(5f))

                ResetButton(
                    onClick = { registerFirstViewModel.removeAll(selectedDataList) },
                    text = "초기화",
                )
                Spacer(modifier = Modifier.weight(1f))

            }

            Spacer(modifier = Modifier.size(20.dp))


            LazyColumn() {
                items(selectedDataList) {item ->
                    SelectedData(item, onClick = { registerFirstViewModel.remove(selectedDataList, item)})
                }
            }

            Spacer(modifier = Modifier.size(30.dp))

            LazyColumn() {
                items(optionDataList) {option ->
                    BottomSheetOption(option) {
//                        selectedDataList.add(option)
//                        updateChosenList(option)
                        registerFirstViewModel.add(selectedDataList, option)
                        /*{리컴포지션 TODO}*/
                        Log.d("리스트", "selectedDataList: $selectedDataList")
                    }
                    Spacer(modifier = Modifier.size(3.dp))
                }
            }

        }
        Spacer(modifier = Modifier.weight(1f))
    }
}


/**
 * 회원 가입 첫번째 화면 내부 Composable
 */
@Composable
fun FirstRegistserScreen(
    registerFirstViewModel: RegisterFirstViewModel = viewModel(),
    navController: NavHostController,
    onMajorClick: () -> Unit,
    onJobClick: () -> Unit
) {
    val registerFirstUiState by registerFirstViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .padding(top = 50.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RegisterScreenTop(1, R.string.register1_q1)

        Spacer(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Introduce(
                registerFirstViewModel = registerFirstViewModel,
                onMajorClick = onMajorClick,
                onJobClick = onJobClick
            )

            Spacer(modifier = Modifier.size(50.dp))

            RegisterScreenNextButton(
                onClick = {
                    val mentoInfo = MentoInformation(
                        company = registerFirstUiState.company,
                        careerLevel = registerFirstUiState.careerLevel.toInt(),
                        job = registerFirstUiState.job,
                        major = registerFirstUiState.major
                    )
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "mentoInfo",
                        value = mentoInfo
                    )
                    navController.navigate(AuthScreen.Register2.route)
                },
                enabled = registerFirstUiState.firstBtnState
            )
        }

        Spacer(modifier = Modifier.weight(3f))
    }
}


/**
 * 회원 가입 첫번째 화면 Composable
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RegisterScreenFirst(
    registerFirstViewModel: RegisterFirstViewModel = viewModel(),
    navController: NavHostController
) {
    val filterBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    var majorBottomSheet by remember { mutableStateOf(false) }
    val registerFirstUiState by registerFirstViewModel.uiState.collectAsState()

    val chosenMajorList = registerFirstViewModel.selectedMajorList
    val chosenJobList = registerFirstViewModel.selectedJobList

    val updateChosenMajorList: (String) -> Unit = { x: String -> registerFirstViewModel.updateChosenMajorList(x) }
    val updateChosenJobList: (String) -> Unit = { x: String -> registerFirstViewModel.updateChosenJobList(x) }

    LaunchedEffect(Unit) {
        registerFirstViewModel.loadJobAndMajorList()
    }

    if (majorBottomSheet) {
        ModalBottomSheetLayout(
            sheetContent = {
                MyModalBottomSheetLayout(
                    text = "학과 선택",
                    selectedDataList = chosenMajorList,
                    optionDataList = registerFirstUiState.optionMajorList,
                    updateChosenList = updateChosenMajorList,
                    registerFirstUiState = registerFirstUiState,
                    registerFirstViewModel = registerFirstViewModel
                )},
            sheetState = filterBottomSheetState,
            sheetShape = RoundedCornerShape(topStart = 43.dp, topEnd = 43.dp),
            sheetBackgroundColor = Green,
            sheetContentColor = Color.White
        ) {
            FirstRegistserScreen(
                navController = navController,
                onJobClick = {
                    majorBottomSheet = false
                    scope.launch {
                        if(!filterBottomSheetState.isVisible) {
                            filterBottomSheetState.show()
                            Log.d("test", "test - filterBottomSheetState")

                        } else {
                            filterBottomSheetState.hide()
                        }
                    }
                },
                onMajorClick = {
                    majorBottomSheet = true
                    scope.launch {
                        if(!filterBottomSheetState.isVisible) {
                            filterBottomSheetState.show()
                            Log.d("test", "test - filterBottomSheetState")

                        } else {
                            filterBottomSheetState.hide()
                        }
                    }
                },
                registerFirstViewModel = registerFirstViewModel
            )
        }
    } else {
        ModalBottomSheetLayout(
            sheetContent = {
                MyModalBottomSheetLayout(
                    text = "직무 분야 선택",
                    selectedDataList = chosenJobList,
                    optionDataList = registerFirstUiState.optionJobList,
                    updateChosenList = updateChosenJobList,
                    registerFirstUiState = registerFirstUiState,
                    registerFirstViewModel = registerFirstViewModel

                )},
            sheetState = filterBottomSheetState,
            sheetShape = RoundedCornerShape(topStart = 43.dp, topEnd = 43.dp),
            sheetBackgroundColor = Green,
            sheetContentColor = Color.White
        ) {
            FirstRegistserScreen(
                navController = navController,
                onJobClick = {
                    majorBottomSheet = false
                    scope.launch {
                        if(!filterBottomSheetState.isVisible) {
                            filterBottomSheetState.show()
                            Log.d("test", "test - filterBottomSheetState")

                        } else {
                            filterBottomSheetState.hide()
                        }
                    }
                },
                onMajorClick = {
                    majorBottomSheet = true
                    scope.launch {
                        if(!filterBottomSheetState.isVisible) {
                            filterBottomSheetState.show()
                            Log.d("test", "test - filterBottomSheetState")

                        } else {
                            filterBottomSheetState.hide()
                        }
                    }
                },
                registerFirstViewModel = registerFirstViewModel
            )
        }
    }


    /*val filterBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var majorBottomSheet by remember { mutableStateOf(false) }

    if ( showBottomSheet ) {
        if ( majorBottomSheet ) {
            ModalBottomSheetLayout(
                sheetContent = { MyModalBottomSheetLayout("직무 분야 선택") }
            ) {
                FirstRegistserScreen(
                    navController = navController,

                )
            }
        } else {
            ModalBottomSheetLayout(
                sheetContent = { MyModalBottomSheetLayout("학과 선택") }
            ) {
                FirstRegistserScreen(
                    navController = navController,
                )
            }
        }
    } else {
        FirstRegistserScreen(
            navController = navController,
            onClick = {
                showBottomSheet = true
                Log.d("test", "showBottomSheet true")
            }
        )
    }*/
}

/*@Composable
fun OptionDataList(optionDataList: List<String>, selectedDataList: MutableList<String>, updateChosenList: (String) -> Unit) {
    LazyColumn() {
        items(optionDataList) {option ->
            BottomSheetOption(option) {
                selectedDataList.add(option)
                updateChosenList(option)
            }
            Spacer(modifier = Modifier.size(3.dp))
        }
    }
}*/
/*
@Composable
fun SelectedDataList(
    selectedDataList: MutableList<String>,
) {
    LazyColumn() {
        items(selectedDataList) {item ->
            SelectedData(item, onClick = { registerFirstViewModel.remove(selectedDataList, item)})
        }
    }

}*/

/**
 * ModalBottomSheet 초기화 버튼 Composable
 */
@Composable
fun ResetButton(onClick: () -> Unit, text: String) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .size(width = 53.dp, height = 20.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp,
        ),
        shape = RoundedCornerShape(30.dp),
        border = BorderStroke(width = 0.5.dp, color = Color(0xff42691A)),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color(0xff42691A),
            backgroundColor = Color(0x0042691A),
            disabledBackgroundColor = Color(0x0042691A),
            disabledContentColor = Color(0xff42691A)
        ),
        contentPadding = PaddingValues(
            start = 3.dp,
            top = 3.dp,
            end = 3.dp,
            bottom = 3.dp
        )
    ){
        Text(
            text = text,
            fontSize = 11.sp,
            letterSpacing = 1.sp)
    }
}

/**
 * 회원 가입 첫번째 화면 미리보기
 */
@Preview(showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    DotoringTheme {
        FirstRegistserScreen(
            navController = rememberNavController(), onMajorClick = {}, onJobClick = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HelloScreenPreview() {
    DotoringTheme {
        HelloScreen()
    }
}

@Composable
fun HelloScreen() {
    var name by rememberSaveable { mutableStateOf("") }

    HelloContent(name = name, onNameChange = { name = it })
}

@Composable
fun HelloContent(name: String, onNameChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Hello, $name",
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(value = name, onValueChange = onNameChange, label = { Text("Name") })
    }
}