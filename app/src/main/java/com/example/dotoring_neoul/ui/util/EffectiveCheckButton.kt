package com.example.dotoring_neoul.ui.util

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dotoring.R
import com.example.dotoring_neoul.ui.theme.DotoringTheme

@Composable
fun EffectiveCheckButton(onClick: () -> Unit = {}, text: String = "Sample Text") {

    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .size(width = 60.dp, height = 28.dp)
            .padding(4.8.dp),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp,
        ),
        shape = RoundedCornerShape(30.dp),
        border = BorderStroke(width = 0.5.dp, color = colorResource(id = R.color.black)),
        colors = ButtonDefaults.buttonColors(
            contentColor = colorResource(id = R.color.black),
            backgroundColor = colorResource(id = R.color.white),
            disabledBackgroundColor = colorResource(id = R.color.white),
            disabledContentColor = colorResource(id = R.color.black)
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
            fontSize = 8.sp,
            letterSpacing = (-1).sp)
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    DotoringTheme {
        EffectiveCheckButton()
    }
}