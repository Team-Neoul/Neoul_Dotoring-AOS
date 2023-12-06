package com.example.dotoring.ui.util.common

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dotoring.R

@Composable
fun BottomButtonLong(
    onClick: () -> Unit = {},
    enabled: Boolean = false,
    isMentor: Boolean = true,
    innerText: String = stringResource(id = R.string.register1_next),
) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(width = 320.dp, height = 45.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = colorResource(id = R.color.white),
            backgroundColor = if (isMentor) {
                colorResource(R.color.green)
            } else {
                colorResource(id = R.color.navy)
            },
            disabledBackgroundColor = colorResource(id = R.color.grey_200),
            disabledContentColor = colorResource(id = R.color.grey_500)
        ),
        shape = RoundedCornerShape(30.dp),
        enabled = enabled
    ) {
        Text(
            text = innerText,
            fontSize = 15.sp
        )
    }
}

