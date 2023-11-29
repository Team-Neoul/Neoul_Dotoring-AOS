package com.example.dotoring.ui.util.bottomsheet

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dotoring.R
import com.example.dotoring.ui.theme.DotoringTheme

@Composable
fun BottomSheetLayout(
    title: String,
    selectedDataList: List<String>,
    optionDataList: List<String>,
    isMentor: Boolean,
    onResetButtonClick: () -> Unit,
    onSelectedDataClick: (String) -> Unit,
    onOptionDataClick: (String) -> Unit

) {
    val sideSpace = 20.dp
    val contentColor = colorResource(R.color.white)
    val titleSize = 20.sp

    val spaceBetweenTitleAndContent = 20.dp
    val spaceBetweenLists = 70.dp

    Column(
        modifier = Modifier.heightIn(min = 0.dp, max = 517.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(R.drawable.bottomsheettopbar),
            contentDescription = null,
            tint = colorResource(R.color.white),
            modifier = Modifier.padding(top = 10.dp)
        )


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(sideSpace)
        ) {
            Row {
                Text(
                    text = title,
                    color = contentColor,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = titleSize
                )
                Spacer(modifier = Modifier.weight(1f))
                ResetButton(
                    onClick = onResetButtonClick,
                    isMentor = isMentor
                )
            }
            Spacer(modifier = Modifier.size(spaceBetweenTitleAndContent))


            LazyColumn() {
                items(selectedDataList) {item ->
                    BottomSheetSelectedData(
                        selectedData = item,
                        onClick = { onSelectedDataClick(item) }
                    )
                }
            }
            Spacer(modifier = Modifier.size(spaceBetweenLists))


            LazyColumn() {
                items(optionDataList) {option ->
                    BottomSheetOptionList(option) {
                        if(option !in selectedDataList) {
                            onOptionDataClick(option)
                        }
                    }
                    Spacer(modifier = Modifier.size(3.dp))
                }
            }

        }
    }
}

@Composable
private fun ResetButton(onClick: () -> Unit, isMentor: Boolean) {

    val buttonText = stringResource(R.string.reset)
    val textSize = 11.sp
    val borderWidth = 0.5.dp
    val buttonSize = Modifier.size(width = 53.dp, height = 20.dp)
    val contentColor = if(isMentor) {
        colorResource(R.color.paragraph_green)
    } else {
        colorResource(R.color.paragraph_navy)
    }
    val backgroundColor = if(isMentor) {
        colorResource(R.color.green)
    } else {
        colorResource(R.color.navy)
    }
    val radius = 20.dp

    OutlinedButton(
        onClick = onClick,
        modifier = buttonSize,
        shape = RoundedCornerShape(radius),
        border = BorderStroke(
            width = borderWidth,
            color = contentColor
        ),
        colors = ButtonDefaults.buttonColors(
            contentColor = contentColor,
            backgroundColor = backgroundColor,
            disabledBackgroundColor = backgroundColor,
            disabledContentColor = contentColor
        ),
        contentPadding = PaddingValues(3.dp)
    ){
        Text(
            text = buttonText,
            fontSize = textSize,
            letterSpacing = (-1).sp
        )
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0x8BD045)
@Composable
private fun RegisterScreenPreview() {
    DotoringTheme {
        BottomSheetLayout(
            title = "뀨뀨",
            selectedDataList = listOf("test1", "test2"),
            optionDataList = listOf("test1", "test2"),
            onResetButtonClick = {},
            onSelectedDataClick = {},
            onOptionDataClick = {},
            isMentor = false
        )
    }
}