package com.example.dotoring_neoul.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dotoring.R
import com.example.dotoring_neoul.ui.theme.DotoringTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dotoring_neoul.ui.util.bottomsheet.BottomSheetLayout
import kotlinx.coroutines.launch

@Composable
fun HomeScreen_(
    isMentor: Boolean,
    navController: NavHostController,
    onMajorClick: () -> Unit,
    onFieldClick: () -> Unit,
    homeUiState: HomeUiState,
    homeViewModel: HomeViewModel
) {
    val spaceBetweenTitleAndTab = 24.dp
    val spaceBetweenTabAndList = 17.dp
    val titlePadding = 43.dp

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(
            painter = painterResource(R.drawable.home_background),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }

    Row {
        Spacer(modifier = Modifier.weight(1f))
        Column {
            Spacer(modifier = Modifier.height(titlePadding))
            HomeTitle(
                isMentor = isMentor,
                forYouText = if(isMentor) {
                    String.format(stringResource(id = R.string.home_for_u_mentor), homeUiState.nickname)
                } else {
                    String.format(stringResource(id = R.string.home_for_u_mentee), homeUiState.nickname)
                }
            )
            Spacer(modifier = Modifier.height(spaceBetweenTitleAndTab))

            Tab(
                onMajorButtonClick = onMajorClick,
                onMentoringButtonClick = onFieldClick,
                isMentor = isMentor,
                hasChosenMajor = homeUiState.hasChosenMajor,
                hasChosenField = homeUiState.hasChosenField
            )
            Spacer(modifier = Modifier.height(spaceBetweenTabAndList))

            MemberList(
                memberList = homeUiState.memberList,
                navController = navController,
                isMentor = isMentor
                //onListBottomReached = homeViewModel.fetchMoreItems()
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}


/**
 * 홈 화면 상단 타이틀을 그리기 위한 Composable 함수
 *
 * @param Boolean isMentor - 회원이 멘토인지 멘티인지 알려주는 parameter
 */
@Composable
private fun HomeTitle(
    isMentor: Boolean,
    forYouText: String
) {
    val mediumTextSize = 24.sp
    val boldTextSize = 34.sp

    Column {
        Text(
            text = forYouText,
            fontSize = mediumTextSize,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.size(5.dp))

        Text(
            text = if(isMentor) {
                stringResource(id = R.string.home_recommended_mentee)
            } else {
                stringResource(id = R.string.home_recommended_mentor)
            },
            fontSize = boldTextSize,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

/**
 * 탭 영역을 그리는 Composable 함수
 */
@Composable
private fun Tab(
    onMajorButtonClick: () -> Unit,
    onMentoringButtonClick: () -> Unit,
    isMentor: Boolean,
    hasChosenMajor: Boolean,
    hasChosenField: Boolean
) {
    Row {
        FilteringButton(
            onClick = onMajorButtonClick,
            modifier = Modifier.size(width = 158.dp, height = 33.dp),
            text = stringResource(R.string.home_major),
            isMentor = isMentor,
            hasChosenData = hasChosenMajor
        )
        Spacer(modifier = Modifier.size(10.dp))

        FilteringButton(
            onClick = onMentoringButtonClick,
            modifier = Modifier.size(width = 158.dp, height = 33.dp),
            text = stringResource(R.string.home_mentoring_objectives),
            isMentor = isMentor,
            hasChosenData = hasChosenField
        )
    }
}


/**
 * 전체 홈화면 Composable
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = viewModel(),
    isMentor: Boolean = true
) {
    val filterBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()
    var isMajorBottomSheet by remember { mutableStateOf(false) }
    val homeUiState by homeViewModel.uiState.collectAsState()

    val chosenMajorList = homeViewModel.selectedMajorList
    val chosenFieldList = homeViewModel.selectedFieldList

    val titleText = if(isMajorBottomSheet) {
        stringResource(R.string.home_modal_major_filter)
    } else {
        stringResource(R.string.home_modal_field_filter)
    }
    val selectedDataList = if(isMajorBottomSheet) {
        chosenMajorList
    } else {
        chosenFieldList
    }
    val optionDataList = if(isMajorBottomSheet) {
        homeUiState.optionMajorList
    } else {
        homeUiState.optionFieldList
    }

    val sheetRadius = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
    val backgroundColor = if(isMentor) {
        colorResource(R.color.green)
    } else {
        colorResource(R.color.navy)
    }

    val contentColor = colorResource(R.color.white)

    LaunchedEffect(Unit) {
        Log.d("홈 통신", "loadMentiList - 홈통신 실행")

        if(isMentor) {
            homeViewModel.loadMenteeList()
        } else {
            homeViewModel.loadMentorList()
        }
        homeViewModel.loadMajorList()
        homeViewModel.loadFieldList()

        // State Change Callback
        snapshotFlow { filterBottomSheetState.isVisible }.collect { isVisible ->
            if (isVisible) {
                // Sheet is visible
            } else {
                // Not visible
                homeViewModel.updateChosenMajorList(chosenMajorList)
                homeViewModel.updateMajorFieldState()
                homeViewModel.updateChosenFieldList(chosenFieldList)
                homeViewModel.updateFieldFieldState()
            }
        }
        Log.d("홈","loadMentiList - 성공 확인")
    }

    ModalBottomSheetLayout(
        sheetContent = {
            BottomSheetLayout(
                title = titleText,
                selectedDataList = selectedDataList,
                optionDataList = optionDataList,
                onResetButtonClick = { homeViewModel.removeAll(selectedDataList) },
                onSelectedDataClick = { item ->
                    homeViewModel.remove(selectedDataList, item)
                },
                onOptionDataClick = { item ->
                    homeViewModel.add(selectedDataList, item)
                    Log.d("리스트", "selectedDataList: $selectedDataList")
                },
                isMentor = isMentor
            ) },
        sheetState = filterBottomSheetState,
        sheetShape = sheetRadius,
        sheetBackgroundColor = backgroundColor,
        sheetContentColor = contentColor
    ) {
        HomeScreen_(
            isMentor = isMentor,
            navController = navController,
            homeUiState = homeUiState,
            onMajorClick = {
                isMajorBottomSheet = true
                scope.launch {
                    if(!filterBottomSheetState.isVisible) {
                        filterBottomSheetState.show()
                    } else {
                        filterBottomSheetState.hide()
                    }
                }
                Log.d("리스트 확인", "HomeScreen - homeViewModel.selectedFieldList: ${homeViewModel.selectedFieldList.toList()}")
                Log.d("리스트 확인", "HomeScreen - homeUiState.selectedFieldList: ${homeUiState.chosenFieldList.toList()}")
            },
            onFieldClick = {
                isMajorBottomSheet = false
                scope.launch {
                    if(!filterBottomSheetState.isVisible) {
                        filterBottomSheetState.show()
                    } else {
                        filterBottomSheetState.hide()
                    }
                }
                Log.d("리스트 확인", "HomeScreen - homeViewModel.selectedMajorList: ${homeViewModel.selectedMajorList.toList()}")
                Log.d("리스트 확인", "HomeScreen - homeUiState.selectedMajorList: ${homeUiState.chosenMajorList.toList()}")
            },
            homeViewModel = homeViewModel
        )
    }
}


/**
 * 홈 화면 필터링 버튼 Composable
 */
@Composable
private fun FilteringButton(
    onClick: () -> Unit,
    modifier: Modifier,
    text: String,
    isMentor: Boolean = true,
    hasChosenData: Boolean
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            contentColor = colorResource(id = R.color.white),
            backgroundColor = if(isMentor) {
                if(hasChosenData) {
                    colorResource(id = R.color.green)
                } else {
                    colorResource(id = R.color.grey_500)
                }

            } else {
                if(hasChosenData) {
                    colorResource(id = R.color.navy)
                } else {
                    colorResource(id = R.color.grey_500)
                }
                   },
            disabledBackgroundColor = colorResource(id = R.color.grey_700),
            disabledContentColor = colorResource(id = R.color.white)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )
    }
}


/**
 * 멘티 카드 리스트로 보여주는 Composable
 */
@Composable
private fun MemberList(
    memberList: List<Member>,
    navController: NavHostController,
    isMentor: Boolean,
//    onListBottomReached: () -> Unit
) {
    //val listState = rememberLazyListState()
    LazyColumn() {
        items(memberList) { member ->
            MemberCard(
                member = member,
                navController = navController,
                isMentor = isMentor
            )
            Spacer(modifier = Modifier.size(3.dp))
        }
    }

    /*listState.OnBottomReached(buffer = 2) {
        onListBottomReached()
    }*/
}

@Composable
fun LazyListState.OnBottomReached(
    buffer: Int = 0,
    loadMore: () -> Unit
) {
    // Buffer must be positive.
    // Or our list will never reach the bottom.
    require(buffer >= 0) { "buffer cannot be negative, but was $buffer" }

    val shouldLoadMore = remember {
        derivedStateOf {

            // get last visible item
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?:
                // list is empty
                // return false here if loadMore should not be invoked if the list is empty
                return@derivedStateOf true

            // Check if last visible item is the last item in the list
            lastVisibleItem.index >=  layoutInfo.totalItemsCount - 1 - buffer
        }
    }

    // Convert the state into a cold flow and collect
    LaunchedEffect(shouldLoadMore){
        snapshotFlow { shouldLoadMore.value }
            .collect {
                // if should load more, then invoke loadMore
                if (it) loadMore()
            }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    DotoringTheme() {
        MainScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview_() {
    DotoringTheme() {
        HomeScreen_(
            homeUiState = HomeUiState(),
            navController = rememberNavController(),
            onMajorClick = {},
            onFieldClick = {},
            homeViewModel = HomeViewModel(),
            isMentor = false
        )
    }
}