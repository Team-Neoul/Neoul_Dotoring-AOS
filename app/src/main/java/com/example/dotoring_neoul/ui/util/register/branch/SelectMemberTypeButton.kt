package com.example.dotoring_neoul.ui.util.register.branch

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dotoring.R
import com.example.dotoring_neoul.ui.theme.DotoringTheme

@Composable
fun SelectMemberTypeButton(
    isMentor: Boolean,
    shape: RoundedCornerShape = RoundedCornerShape(20.dp),
    modifier: Modifier = Modifier.size(width = 300.dp, 120.dp),
) {
/*    val onClick: () -> Unit = if(isMentor){

    } else {
    }*/

    val backgroundColor: Color = if(isMentor) {
        colorResource(id = R.color.green)
    } else {
        colorResource(id = R.color.navy)
    }
    val contentColor: Color = colorResource(id = R.color.white)


    val title: String = if(isMentor) {
        stringResource(id = R.string.branch_mento)
    } else {
        stringResource(id = R.string.branch_mentee)
    }
    val titleSize: TextUnit = 20.sp
    val titleWeight: FontWeight = FontWeight.ExtraBold


    val subTitle: String = if(isMentor) {
        stringResource(id = R.string.branch_helper)
    } else {
        stringResource(id = R.string.branch_wanna_grow_up)
    }
    val subTitleSize: TextUnit = 14.sp
    val subTitleWeight: FontWeight = FontWeight.Medium


    Button(
        onClick = { } ,
        shape = shape,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = subTitle,
                fontSize = subTitleSize,
                fontWeight = subTitleWeight,
                modifier = Modifier.padding(3.dp)
            )
            Spacer(modifier = Modifier.size(3.dp))
            Text(
                text = title,
                fontSize = titleSize,
                fontWeight = titleWeight,
                modifier =Modifier.padding(5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    DotoringTheme {
        SelectMemberTypeButton(false)
    }
}