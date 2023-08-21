package com.example.dotoring_neoul.ui.util.register

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.example.dotoring.R
import com.example.dotoring_neoul.ui.theme.DotoringTheme


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CommonTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    width: Dp = 250.dp,
    imeAction: ImeAction,
    onDone: (KeyboardActionScope.() -> Unit)? = null,
    onNext: (KeyboardActionScope.() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    val interactionSource = remember { MutableInteractionSource() }
    val enabled = true
    val singleLine = true

    val colors = TextFieldDefaults.textFieldColors(
        textColor = colorResource(id = R.color.black),
        focusedIndicatorColor = colorResource(id = R.color.black),
        unfocusedIndicatorColor = colorResource(id = R.color.black),
        backgroundColor = colorResource(id = R.color.white),
        placeholderColor = colorResource(id = R.color.grey_500)
    )

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .background(
                color = colorResource(id = R.color.white),
                shape = TextFieldDefaults.TextFieldShape
            )
            .indicatorLine(
                enabled = enabled,
                isError = false,
                interactionSource = interactionSource,
                colors = colors
            )
            .size(width = width, height = 25.dp),
        visualTransformation = visualTransformation,
        // internal implementation of the BasicTextField will dispatch focus events
        interactionSource = interactionSource,
        enabled = enabled,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onNext = onNext,
            onDone = onDone
        )
    ) {
        TextFieldDefaults.TextFieldDecorationBox(
            value = value,
            visualTransformation = visualTransformation,
            innerTextField = it,
            singleLine = singleLine,
            enabled = enabled,
            // same interaction source as the one passed to BasicTextField to read focus state
            // for text field styling
            placeholder = {Text(
                text = placeholder,
                fontSize = 13.sp) },
            colors = colors,
            interactionSource = interactionSource,
            // keep vertical paddings but change the horizontal
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                start = 2.dp, top = 1.dp, end = 1.dp, bottom = 3.dp
            )
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    var nicknameInput by remember { mutableStateOf("") }

    DotoringTheme {
        CommonTextField( nicknameInput, { nicknameInput = it }, imeAction = ImeAction.Done )
    }
}