package com.example.dotoring.ui.mypage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.dotoring.R
import com.example.dotoring.ui.theme.DotoringTheme

@Composable
fun InfoUpdateAlertDialog() {
    Dialog(onDismissRequest = {}) {
        Card(
            modifier = Modifier
                .size(
                    width = 304.dp,
                    height = 219.dp
                ),
            shape = RoundedCornerShape(20.dp),
            backgroundColor = colorResource(id = R.color.error),
            contentColor = colorResource(id = R.color.black_500)
        ) {
            Box(
                modifier = Modifier
                    .size(
                        width = 304.dp,
                        height = 32.dp
                    ),
                contentAlignment = Alignment.BottomCenter
            ) {
                Card(
                    modifier = Modifier
                        .size(
                            width = 304.dp,
                            height = 187.dp
                        )
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = stringResource(R.string.alert_dialog_message),
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                        )
                        Spacer(modifier = Modifier.height(25.dp))
                        Text(
                            text = stringResource(R.string.alert_dialog_subtext),
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = colorResource(R.color.grey_700)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            Button(
                                onClick = { },
                                modifier = Modifier.size(
                                    width = 141.dp,
                                    height = 36.dp
                                ),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(id = R.color.dotoring_gray),
                                    contentColor = colorResource(id = R.color.white),
                                    disabledContentColor = colorResource(id = R.color.dotoring_gray),
                                    disabledBackgroundColor = colorResource(id = R.color.white)
                                ),
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.alert_dialog_no),
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(5.dp))
                            Button(
                                onClick = { /*TODO - 다음 화면으로 전환*/ },
                                modifier = Modifier.size(
                                    width = 141.dp,
                                    height = 36.dp
                                ),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(id = R.color.error),
                                    contentColor = colorResource(id = R.color.white),
                                    disabledContentColor = colorResource(id = R.color.error),
                                    disabledBackgroundColor = colorResource(id = R.color.white)
                                ),
                                shape = RoundedCornerShape(20.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.alert_dialog_yes),
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 16.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DialogPreview() {
    DotoringTheme {
        InfoUpdateAlertDialog()
    }
}