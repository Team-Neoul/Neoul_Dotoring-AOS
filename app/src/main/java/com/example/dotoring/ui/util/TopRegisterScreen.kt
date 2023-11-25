package com.example.dotoring.ui.util

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dotoring.R
import com.example.dotoring.ui.theme.DotoringTheme
import de.charlex.compose.HtmlText

@Composable
private fun ProgressBar(page: Int, isMentor: Boolean) {

    val spaceBetweenCircle = 7.dp
    val iconModifier = Modifier.size(16.dp)

    val doneColor = if(isMentor) {
        colorResource(id = R.color.green)
    } else {
        colorResource(id = R.color.navy)
    }
    val defaultColor = colorResource(id = R.color.grey_200)


    Row() {
        for(i in 1..page) {
            Icon(
                painter = painterResource(R.drawable.ic_progress_register),
                contentDescription = "회원가입 진행 상황",
                modifier = iconModifier,
                tint = doneColor
            )
            Spacer(modifier = Modifier.size(spaceBetweenCircle))
        }


        for(i in 1..6-page) {
            Icon(
                painter = painterResource(R.drawable.ic_progress_register),
                contentDescription = "",
                modifier = iconModifier,
                tint = defaultColor
            )
            Spacer(modifier = Modifier.size(spaceBetweenCircle))
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun TopRegisterScreen(
    screenNumber: Int,
    question: Int,
    guide: String = "",
    isMentor: Boolean = true
) {
    val progressBarUpperSpace = 80.dp
    val progressBarLowerSpace = 20.dp

    val paddingTop = 50.dp

    val screenDescriptionText = if(isMentor) {
        R.string.register_title_mentor
    } else {
        R.string.register_title_mentee
    }
    val guideFontSize = 12.sp
    val questionFontSize = 24.sp
    val screenDescriptionFontSize = 14.sp

        Column(
            modifier = Modifier.padding(top = paddingTop)
        ) {
            HtmlText(
                textId = screenDescriptionText,
                fontSize = screenDescriptionFontSize
            )
            Spacer(modifier = Modifier.size(progressBarUpperSpace))


            Column() {
                ProgressBar(screenNumber, isMentor)
                Spacer(modifier = Modifier.size(progressBarLowerSpace))


                Row() {
                    Text(
                        text = stringResource(id = R.string.register_Q),
                        fontSize= questionFontSize
                    )
                    Column() {
                        HtmlText(
                            textId = question,
                            fontSize = questionFontSize
                        )
                        Spacer(modifier = Modifier.size(5.dp))


                        Text(
                            text = guide,
                            color = colorResource(id = R.color.grey_500),
                            fontSize = guideFontSize
                        )
                    }
                }
            }
        }
}

@Composable
@Preview(showBackground = true)
private fun RegisterScreenPreview() {
    DotoringTheme {
        TopRegisterScreen(3, R.string.register1_q1_mentor, isMentor = false)
    }
}