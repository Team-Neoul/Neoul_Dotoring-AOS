package com.example.dotoring.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dotoring.R
import com.example.dotoring.navigation.AuthScreen

/**
 * 로그인 관련 화면 구성
 */
@Composable
fun LoginScreen(loginViewModel: LoginViewModel= viewModel(), navController: NavHostController) {
    val loginUiState by loginViewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current



    Box(){
        Image(
            painter= painterResource(id = R.drawable.dotoring_background_logo),
            contentDescription=null,
            contentScale = ContentScale.FillWidth)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxSize()
                .padding(horizontal = 40.dp, vertical = 160.dp)
        ) {
            Column {
                Text(text = stringResource(id = R.string.university), fontSize = 23.sp, fontWeight = FontWeight.Bold)
                Text(text = stringResource(id = R.string.mentor_login1),fontSize = 23.sp, fontWeight = FontWeight.Bold)
                Text(text = stringResource(id = R.string.login_text), fontSize = 13.sp)
            }
            Spacer(modifier = Modifier.size(30.dp))

            Column(
                modifier= Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .fillMaxWidth()
            ) {
                LoginField(value = loginUiState.id, onValueChange = {loginViewModel.updateId(it)},
                    textField = stringResource(id = R.string.id),
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Next)}
                    ,
                )
                LoginField(value = loginUiState.pwd, onValueChange = {loginViewModel.updatePwd(it)},
                    textField = stringResource(id = R.string.password),
                    onDone = { focusManager.clearFocus()
                    }
                )
            }
            Spacer(modifier = Modifier.size(30.dp))

            loginViewModel.updateBtnState()

            OutlinedButton(
                onClick = { loginViewModel.sendLogin(navController)
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                border = BorderStroke(width = 2.dp, Color.Black),
                enabled = loginUiState.btnState

            ) { Text(text = "로그인", fontSize = 15.sp) }




            TextButton(
                modifier=Modifier.align(alignment = Alignment.CenterHorizontally),
                onClick = { /*TODO*/ },
            )
            {
                Text("아이디 찾기", fontSize = 12.sp,color= colorResource(id = R.color.grey_500), modifier = Modifier
                    .clickable {
                        // onClick()
                    }
                )
                Text(text = "  |  ", fontSize = 12.sp,color= colorResource(id = R.color.grey_500))
                Text("비밀번호 찾기", fontSize = 12.sp, color= colorResource(id = R.color.grey_500), modifier = Modifier
                    .clickable {
                        // onClick() }
                    }
                )
                Text(text = "  |  ", fontSize = 12.sp)
                Text("회원가입", fontSize = 12.sp, color= colorResource(id = R.color.grey_500), modifier = Modifier
                    .clickable {
                        navController.navigate(AuthScreen.Branch.route)
                    }
                )

            }
        }
    }



}

/**
 * 아이디, 비밀번호 작성하는 textField
 */
@Composable
fun LoginField(value:String, onValueChange:(String)->Unit, textField: String, onDone: (KeyboardActionScope.() -> Unit)? = null,
               onNext: (KeyboardActionScope.() -> Unit)? = null, ) {
    Column() {TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(
            text = textField,
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left) },
        colors = TextFieldDefaults.textFieldColors(
            textColor = colorResource(id = R.color.black),
            focusedIndicatorColor = colorResource(id = R.color.black),
            unfocusedIndicatorColor = colorResource(id = R.color.black),
            backgroundColor = Color.Transparent,
            placeholderColor = colorResource(id = R.color.grey_500)),
        keyboardActions = KeyboardActions(onDone, onNext),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)


    )

    }
}


@Preview(showSystemUi = true)
@Composable
fun LoginPreview() {
        LoginScreen(navController = rememberNavController())
}
