package com.example.dotoring_neoul.ui.login.findId

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dotoring.R
import com.example.dotoring_neoul.ui.theme.DotoringTheme
import com.example.dotoring_neoul.ui.util.EffectiveCheckButton
import com.example.dotoring_neoul.ui.util.register.CommonTextField
import de.charlex.compose.HtmlText

@Composable
fun FindIdScreen(

    findIdViewModel: FindIdViewModel = viewModel(),
    navController: NavHostController
) {
    val findIdUiState by findIdViewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(50.dp)
            .fillMaxSize()
    ) {
        HtmlText(
            textId = R.string.find_id,
            fontSize = 15.sp)

        Spacer(modifier = Modifier.size(100.dp))


        HtmlText(
            textId = R.string.find_id_ment,
            fontSize = 20.sp)


        Spacer(modifier = Modifier.size(100.dp))

        Column(modifier = Modifier.height(50.dp)) {


            Box(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.CenterEnd
            ) {
                CommonTextField(
                    value = findIdUiState.email,
                    onValueChange = { findIdViewModel.updateEmail(it) },
                    placeholder = "이메일",
                    width = 280.dp,
                    imeAction = ImeAction.Done,
                    onDone = { focusManager.clearFocus() })

                EffectiveCheckButton(
                    onClick = {
                        findIdViewModel.getVerifyCode(navController)
                    },
                    text = "인증 코드 발송",
                )
            }
            if(findIdUiState.emailState){
                Text( text = "등록되지 않은 이메일이에요.", fontSize = 12.sp, color = Color.Red)
            }
        }
        Spacer(modifier = Modifier.size(20.dp))

        Column(modifier = Modifier.height(50.dp)) {


            Box(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.CenterEnd
            ) {
                CommonTextField(
                    value = findIdUiState.email,
                    onValueChange = { findIdViewModel.updateEmail(it) },
                    placeholder = "인증 코드",
                    width = 280.dp,
                    imeAction = ImeAction.Done,
                    onDone = { focusManager.clearFocus() })

                EffectiveCheckButton(
                    onClick = {
                        findIdViewModel.sendVerifyCode(navController)
                    },
                    text = "인증 하기",
                )
            }
            if(findIdUiState.codeState){
                Text( text = "인증 코드가 달라요.", fontSize = 12.sp, color = Color.Red)
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
private fun FindIdScreenPreview() {
    DotoringTheme {
        FindIdScreen(navController = rememberNavController())
    }
}