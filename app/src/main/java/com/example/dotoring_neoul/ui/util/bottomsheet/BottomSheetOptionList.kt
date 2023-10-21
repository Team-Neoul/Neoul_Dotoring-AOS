package com.example.dotoring_neoul.ui.util.bottomsheet

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dotoring.R
import com.example.dotoring_neoul.ui.theme.DotoringTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetOptionList(optionData: String, onClick: () -> Unit = {}) {
    val interactionSource = remember { MutableInteractionSource() }

    val contentColor = colorResource(id = R.color.white)
    val backgroundColor = colorResource(id = R.color.transparent)

    val fieldWidth = 380.dp
    val fieldHeight = 36.dp
    val indicatorLineThickness = 1.5.dp
    val contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
        start = 20.dp, top = 1.dp, end = 1.dp, bottom = 3.dp
    )

    val iconSize = 20.dp
    val iconColor = colorResource(R.color.white)

    Box(
        contentAlignment = Alignment.CenterEnd
    ) {
        BasicTextField(
            value = optionData,
            onValueChange = { } ,
            modifier = Modifier
                .size(width = fieldWidth, height = fieldHeight)
                .indicatorLine(
                    enabled = false,
                    isError = false,
                    interactionSource = interactionSource,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = contentColor,
                        focusedIndicatorColor = contentColor,
                        unfocusedIndicatorColor = contentColor,
                        backgroundColor = backgroundColor,
                        placeholderColor = contentColor
                    ),
                    focusedIndicatorLineThickness = indicatorLineThickness,
                    unfocusedIndicatorLineThickness = indicatorLineThickness
                )
                .clickable(onClick = onClick),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 16.sp,
                color = colorResource(id = R.color.white),
                fontWeight = FontWeight.SemiBold
            ),
            enabled = false,
            readOnly = true,
        ) {
            TextFieldDefaults.TextFieldDecorationBox(
                value = optionData,
                innerTextField = it,
                enabled = false,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource =  interactionSource,
                contentPadding = contentPadding
            )
        }
        IconButton(
            onClick = onClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter_choose),
                contentDescription = "선택 버튼",
                modifier = Modifier.size(iconSize),
                tint = iconColor
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0x8BD045)
@Composable
private fun RegisterScreenPreview() {
    DotoringTheme {
        BottomSheetOptionList(optionData = "테스트")
    }
}