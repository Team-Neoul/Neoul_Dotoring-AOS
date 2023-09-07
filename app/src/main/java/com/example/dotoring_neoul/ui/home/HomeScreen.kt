package com.example.dotoring_neoul.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dotoring.R
import com.example.dotoring_neoul.navigation.HomeNavGraph
import androidx.compose.runtime.getValue
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dotoring_neoul.navigation.BottomNavScreen
import com.example.dotoring_neoul.ui.theme.DotoringTheme

@Composable
fun MainScreen(navController: NavHostController, homeViewModel: HomeViewModel = viewModel(),
) {
    val homeUiState by homeViewModel.uiState.collectAsState()
    Log.d("홈통신", " 홈통신 실행")
    LaunchedEffect(Unit) {
        /*homeViewModel.loadMentiList()*/
        homeViewModel.loadMentiList()
    }
    Log.d("홈","확인 성공할락말락")

    Box(modifier = Modifier
        .fillMaxSize()){
        Image(
            painter=painterResource(R.drawable.home_screen_background),
            contentDescription=null,
            contentScale = ContentScale.FillWidth)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Row() {
            Spacer(modifier = Modifier.weight(1f))

            Column (
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.size(50.dp))

                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    DotoringLogo()

                    Spacer(modifier = Modifier.size(50.dp))

                    Row(
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column() {
                            Text(
                                text = stringResource(id = R.string.home_for_u),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Light
                            )

                            Spacer(modifier = Modifier.size(5.dp))

                            Text(
                                text = stringResource(id = R.string.home_recommended_mentee),
                                fontSize = 30.sp,
                                fontWeight = FontWeight.ExtraBold

                            )
                        }

                        Spacer(modifier = Modifier.size(width = 150.dp, height = 10.dp))

                        Image(
                            painter = painterResource(id = R.drawable.ic_search_unfocused),
                            contentDescription = null
                        )
                    }
                }

                Spacer(modifier = Modifier.size(30.dp))

                Row() {
                    FilteringButton(
                        onClick = { /*TODO*/ },
                        width = 140.dp,
                        text = stringResource(id = R.string.home_major)
                    )

                    Spacer(modifier = Modifier.size(15.dp))

                    FilteringButton(
                        onClick = { /*TODO*/ },
                        width = 140.dp,
                        text = stringResource(id = R.string.home_job_objectives)
                    )
                }

                Spacer(modifier = Modifier.size(30.dp))

                MenteeList(
                    menteeList = homeUiState.mentiList,
                    navController = navController
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController = rememberNavController()) {
    Scaffold (
        bottomBar = {BottomBar(navController)}
    ) { padding ->
        HomeNavGraph(navController = navController,
            modifier = Modifier.padding(padding))

    }
}

@Composable
private fun FilteringButton(onClick: () -> Unit, width: Dp, text: String) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(width = width, height = 38.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = colorResource(id = R.color.white),
            backgroundColor = colorResource(id = R.color.green),
            disabledBackgroundColor = colorResource(id = R.color.grey_700),
            disabledContentColor = colorResource(id = R.color.white)
        ),
        shape = RoundedCornerShape(30.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
private fun MenteeList(menteeList: List<Mentee>, navController: NavHostController) {
    LazyColumn() {
        items(menteeList) { mentee ->
            MenteeCard(mentee = mentee, navController = navController)
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}

@Composable
private fun SearchField(value: String, onValueChange: (String) -> Unit) {
    Box(
        contentAlignment = Alignment.CenterEnd
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("직접 검색하기")},
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = colorResource(id = R.color.green),
                    shape = RoundedCornerShape(30.dp)
                )
                .size(width = 284.dp, height = 50.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = colorResource(id = R.color.black),
                backgroundColor = Color(0x00ffffff),
                placeholderColor = colorResource(id = R.color.grey_500)
            )
        )

        Row() {
            Icon(
                painter = painterResource(id = R.drawable.ic_search_focused),
                contentDescription = "검색창이 켜있습니다.",
                tint = colorResource(id = R.color.green)
            )

            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    DotoringTheme() {
        MainScreen(navController = rememberNavController())
    }
}


/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoiceBottomSheet() {
    ModalBottomSheet(onDismissRequest = { *//*TODO*//* }) {
        Row {
            Text(
                text = "학과 필터"
            )
            EffectiveCheckButton(text = "초기화")
        }
    }
}*/

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Calendar,
        BottomNavScreen.Message,
        BottomNavScreen.Mypage
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomNavigation(
            backgroundColor = Color.White
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = stringResource(screen.resourceId))
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        selectedContentColor = Color(0xff8BD045),
        unselectedContentColor = Color(0xffC3C3C3),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}