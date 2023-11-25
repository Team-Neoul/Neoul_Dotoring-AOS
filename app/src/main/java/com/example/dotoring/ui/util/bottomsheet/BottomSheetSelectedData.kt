package com.example.dotoring.ui.util.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dotoring.R
import com.example.dotoring.ui.theme.DotoringTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetSelectedData(selectedData:String, onClick: () -> Unit ) {
    val interactionSource = remember { MutableInteractionSource() }
    
    val backgroundColor = colorResource(R.color.white)
    val radius = 20.dp
    val backgroundWidth = 380.dp
    val backgroundHeight = 36.dp

    val fontSize = 15.sp
    val fontWeight = FontWeight.Bold
    val contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
        start = 20.dp, top = 1.dp, end = 1.dp, bottom = 3.dp
    )

    val iconColor = Color(0xffA5A5A5)
    val iconSize = 15.dp

    Box(
        contentAlignment = Alignment.CenterEnd
    ) {
        BasicTextField(
            value = selectedData,
            onValueChange = { } ,
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(radius)
                )
                .size(width = backgroundWidth, height = backgroundHeight),
            textStyle = LocalTextStyle.current.copy(
                fontSize = fontSize,
                fontWeight = fontWeight
            ),
            enabled = false,
            readOnly = true,
        ) {
            TextFieldDefaults.TextFieldDecorationBox(
                value = selectedData,
                innerTextField = it,
                enabled = false,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource =  interactionSource,
                contentPadding = contentPadding
            )
        }

        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter_cancel),
                contentDescription = "선택 취소 버튼",
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
        BottomSheetSelectedData(selectedData = "테스트") {
            
        }
    }
}