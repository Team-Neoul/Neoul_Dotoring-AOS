package com.example.dotoring_neoul.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
 * 홈화면 도토링 Logo 부분 Composable
 */
@Composable
fun DotoringLogo() {
    val dotoringIcon = R.drawable.ic_dotoring_gray

    Row() {
        Image(
            painter = painterResource(dotoringIcon),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.size(5.dp))

        Text(
            text = stringResource(id = R.string.main_dotoring),
            color = colorResource(id = R.color.grey_700),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            letterSpacing = (-1).sp
        )

    }
}

@Preview
@Composable
private fun DotoringLogoPreview() {
    DotoringTheme {
        DotoringLogo()
    }
}