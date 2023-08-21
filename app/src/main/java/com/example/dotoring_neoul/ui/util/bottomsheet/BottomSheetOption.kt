package com.example.dotoring_neoul.ui.util.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dotoring.R
import com.example.dotoring_neoul.ui.util.data.SelectedDataSource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetOption(data: String) {
    val interactionSource = remember { MutableInteractionSource() }

    val colors = TextFieldDefaults.textFieldColors(
        textColor = colorResource(id = R.color.white),
        focusedIndicatorColor = colorResource(id = R.color.white),
        unfocusedIndicatorColor = colorResource(id = R.color.white),
        backgroundColor = Color(0x00ffffff),
        placeholderColor = colorResource(id = R.color.white)
    )

    Box(
        contentAlignment = Alignment.CenterEnd
    ) {

        BasicTextField(
            value = data,
            onValueChange = { } ,
            modifier = Modifier
                .background(
                    color = Color(0x00ffffff)
                )
                .size(width = 380.dp, height = 36.dp)
                .indicatorLine(
                    enabled = false,
                    isError = false,
                    interactionSource = interactionSource,
                    colors = colors,
                    focusedIndicatorLineThickness = 1.dp,
                    unfocusedIndicatorLineThickness = 1.dp
                ),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 15.sp,
                color = colorResource(id = R.color.white),
                fontWeight = FontWeight.SemiBold
            ),
            enabled = false,
            readOnly = true,
        ) {
            TextFieldDefaults.TextFieldDecorationBox(
                value = data,
                innerTextField = it,
                enabled = false,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource =  interactionSource,
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    start = 10.dp, top = 1.dp, end = 1.dp, bottom = 3.dp
                )
            )
        }

        IconButton(
            onClick = { SelectedDataSource().addSelectedData(data) } // SelectedDataList에 Data 추가
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter_choose),
                contentDescription = "선택 버튼",
                modifier = Modifier.size(20.dp),
                tint = Color(0xffffffff)
            )
        }
    }
}