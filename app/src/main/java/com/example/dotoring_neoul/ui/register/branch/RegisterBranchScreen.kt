package com.example.dotoring_neoul.ui.register.branch

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dotoring.R
import com.example.dotoring_neoul.ui.theme.DotoringTheme

/**
 * 분기 화면 Composable
 */
@Composable
fun RegisterBranchScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.branch_logo_our_mentoring),
                fontSize = 20.sp,
                letterSpacing = (-1).sp)

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = stringResource(id = R.string.branch_logo_app_name),
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = (-1).sp
            )

            Spacer(modifier = Modifier.size(20.dp))

            Image(painter = painterResource(id = R.drawable.ic_dotoring_colored), contentDescription = null)
        }

        Spacer(modifier = Modifier.size(50.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.size(width = 219.dp, 103.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.branch_helper),
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = stringResource(id = R.string.branch_mento),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Spacer(modifier = Modifier.size(10.dp))

            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.size(width = 219.dp, 103.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.navy))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.branch_wanna_grow_up),
                        fontSize = 14.sp,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.size(10.dp))

                    Text(
                        text = stringResource(id = R.string.branch_mentee),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    DotoringTheme {
        RegisterBranchScreen()
    }
}