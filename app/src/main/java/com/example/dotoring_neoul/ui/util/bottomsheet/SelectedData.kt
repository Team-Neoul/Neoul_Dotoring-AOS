package com.example.dotoring_neoul.ui.util.bottomsheet

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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dotoring.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectedData ( selectedData:String, onClick: () -> Unit ) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        contentAlignment = Alignment.CenterEnd
    ) {

        BasicTextField(
            value = selectedData,
            onValueChange = { } ,
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.white),
                    shape = RoundedCornerShape(20.dp)
                )
                .size(width = 380.dp, height = 36.dp),
            textStyle = LocalTextStyle.current.copy(fontSize = 15.sp),
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
                contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                    start = 20.dp, top = 1.dp, end = 1.dp, bottom = 3.dp
                )
            )
        }

        IconButton (onClick = onClick)
        {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter_cancel),
                contentDescription = "취소 버튼",
                modifier = Modifier.size(15.dp),
                tint = Color(0xffA5A5A5)
            )
        }
    }
}