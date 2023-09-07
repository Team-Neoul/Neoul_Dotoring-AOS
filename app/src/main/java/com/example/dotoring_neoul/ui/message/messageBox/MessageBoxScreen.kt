package com.example.dotoring_neoul.ui.message.messageBox

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dotoring.R
import com.example.dotoring_neoul.navigation.MessageDetailScreen
import com.example.dotoring_neoul.ui.theme.DotoringTheme
import com.example.dotoring_neoul.ui.theme.Gray
import com.example.dotoring_neoul.ui.theme.Navy

/**
 * 쪽지함 
 */
@Composable
fun MessageBoxScreen(messageBoxViewModel: MessageBoxViewModel = viewModel(), navController: NavHostController
) {
    val messageBoxUiState by messageBoxViewModel.uiState.collectAsState()
    val messageList = messageBoxUiState.messageList
    Log.d("쪽지함", " 쪽지함 실행")
    LaunchedEffect(Unit) {
        messageBoxViewModel.renderMessageBoxScreen(navController)
    }
    Log.d("쪽지함", " 쪽지함 ")
    val scrollState = rememberLazyListState()

    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(horizontal = 30.dp, vertical = 50.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text("  쪽지함",  fontWeight = FontWeight.ExtraBold, fontSize = 30.sp,color= colorResource(id= R.color.black), modifier = Modifier )
        Spacer(modifier = Modifier.size(40.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {


            LazyColumn(state = scrollState, ) {
                this.items(messageList) {
                        messageBox -> MessageListItem(messageBox=messageBox, navController = navController)
                }
            }

        }
    }
}

@Composable
fun MessageListItem(messageBoxViewModel: MessageBoxViewModel = viewModel(), messageBox: MessageBox, navController: NavHostController){
    val messageBoxUiState by messageBoxViewModel.uiState.collectAsState()
    val messageList = messageBoxUiState.messageList
//        val messageList = source().loadRoom()
    Column(modifier = Modifier
        .width(300.dp)
        , horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .width(350.dp)
                .height(100.dp)
                .padding(horizontal = 5.dp)
                .clickable {
//                        navController.currentBackStackEntry?.savedStateHandle?.set(
//                            key = "RoomInfo",
//                            value = messageBox
//                        )
                    navController.navigate(MessageDetailScreen.MessageDetailed.route)
                },



            ) {
            Box(
                modifier = Modifier
                    .width(260.dp)
                    .align(Alignment.CenterEnd)

            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape= RoundedCornerShape(15.dp)
                    , color = Gray
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 45.dp, top = 15.dp, bottom = 15.dp, end = 10.dp)

                    ) {
                        Text(
                            text=messageBox.updateAt,
                            fontSize = 8.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            color = Color.Gray, fontWeight = FontWeight.Normal
                        )
                        Text(text=messageBox.nickname, fontSize = 15.sp, color = Navy, fontWeight = FontWeight.ExtraBold)
                        Text(text=messageBox.nickname, fontSize = 12.sp, fontWeight = FontWeight.Normal, color= Color.Black)
                        Spacer(modifier = Modifier.size(12.dp))
                        Text(
                            text=messageBox.lastLetter,
                            fontSize = 12.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            fontWeight = FontWeight.Normal, color= Color.Black
                        )

                    }

                }

            }
            Surface(
                shape= CircleShape,
                modifier = Modifier
                    .size(70.dp)
                    .align(Alignment.CenterStart)
                    .border(
                        width = 6.dp,
                        color = colorResource(id = R.color.white),
                        shape = CircleShape

                    )

            ) {
                Image(
                    painter = painterResource(R.drawable.home_profile_sample),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .clip(CircleShape)
                )

            }

        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun MessageBoxPreview() {
    DotoringTheme {
        MessageBoxScreen(navController = rememberNavController())
    }
}
