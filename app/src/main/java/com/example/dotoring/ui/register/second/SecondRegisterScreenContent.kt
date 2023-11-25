package com.example.dotoring.ui.register.second

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dotoring.R
import com.example.dotoring.ui.theme.DotoringTheme
import de.charlex.compose.HtmlText

@Composable
fun SecondRegisterScreenContent(
    registerSecondViewModel: RegisterSecondViewModel = viewModel(),
    isMentor: Boolean
) {
    val buttonTitleSize = 20.sp
    val spaceBetweenButtonAndTitle = 20.dp
    val spaceBetweenButtons = 45.dp
    val firstButtonTitleText = if(isMentor) {
        R.string.register2_upload_certificate_of_employment
    } else {
        R.string.register2_upload_certificate_of_enrollment
    }
    HtmlText(
        textId = firstButtonTitleText,
        fontSize = buttonTitleSize)
    Spacer(modifier = Modifier.size(spaceBetweenButtonAndTitle))
    ImageUploadButton(
        registerSecondViewModel = registerSecondViewModel,
        uploadEmploymentFile = true
    )


    if (isMentor) {
        Spacer(modifier = Modifier.size(spaceBetweenButtons))
        Row(verticalAlignment = Alignment.Bottom) {
            HtmlText(
                textId = R.string.register2_upload_certificate_of_graduate,
                fontSize = buttonTitleSize)
            Spacer(modifier = Modifier.size(5.dp))
            Text(
                text = stringResource(id = R.string.register2_upload_choice),
                fontSize = 13.sp)
        }
        Spacer(modifier = Modifier.size(spaceBetweenButtonAndTitle))
        ImageUploadButton(
            registerSecondViewModel = registerSecondViewModel,
            uploadEmploymentFile = false
        )
    }
}

@Composable
private fun ImageUploadButton(
    registerSecondViewModel: RegisterSecondViewModel = viewModel(),
    uploadEmploymentFile: Boolean
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            selectedUri ->
        if (selectedUri != null) {
            Log.d("uri", "selectedUri - selectedUri: $selectedUri")
            Log.d("uri", "selectedUri - selectedUri.path: ${selectedUri.path}")

            if (uploadEmploymentFile) {
                registerSecondViewModel.uploadEmploymentFile()
                registerSecondViewModel.updateEmploymentCertification(selectedUri)

            } else {
                registerSecondViewModel.uploadGraduationFile()
                registerSecondViewModel.updateGraduationCertification(selectedUri)
            }

        } else {
            Log.d("uri", "selectedUri: No file was selected.")
        }
    }
    val buttonHeight = 86.dp
    val buttonWidth = 320.dp
    val borderColor = colorResource(R.color.grey_200)
    val buttonRadius = 20.dp
    val innerButtonTextSize = 15.sp

    Button(
        onClick = {
            launcher.launch("*/*")
        },
        modifier = Modifier.size(width = buttonWidth, height = buttonHeight),
        border = BorderStroke(width = 0.5.dp, color = borderColor),
        colors = ButtonDefaults.buttonColors(
            contentColor = colorResource(id = R.color.black),
            backgroundColor = colorResource(id = R.color.white),
            disabledBackgroundColor = colorResource(id = R.color.white),
            disabledContentColor = colorResource(id = R.color.black)
        ),
        shape = RoundedCornerShape(buttonRadius)
    ) {
        Text(
            text = stringResource(id = R.string.register2_upload_extension),
            fontSize = innerButtonTextSize
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun RegisterScreenPreview() {
    DotoringTheme {
        SecondRegisterScreenContent(isMentor = true)
    }
}