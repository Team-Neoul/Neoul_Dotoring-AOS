package com.example.dotoring_neoul.ui.util.register

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
import com.example.dotoring.R

@Composable
fun RegisterScreenNextButton(onClick: ()->Unit = {}, enabled: Boolean = false) {
    Button(
        onClick = onClick,
        modifier = Modifier.size(width = 320.dp, height = 45.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = colorResource(id = R.color.white),
            backgroundColor = colorResource(id = R.color.green),
            disabledBackgroundColor = colorResource(id = R.color.grey_200),
            disabledContentColor = colorResource(id = R.color.grey_500)
        ),
        shape = RoundedCornerShape(30.dp),
        enabled = enabled
    ) {
        Text(text = stringResource(id = R.string.register1_next))
    }
}