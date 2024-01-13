package com.example.dotoring.ui.mypage.updateinfo

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dotoring.R
import com.example.dotoring.ui.theme.DotoringTheme
import com.example.dotoring.ui.util.common.BottomButtonLong
import com.example.dotoring.ui.util.common.EnabledBottomButtonLong

@Composable
fun SubmitDocScreen(
    submitDocViewModel: SubmitDocViewModel = viewModel()
) {
    val submitDocUiState by submitDocViewModel.submitDocUiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Text(
                text = stringResource(R.string.submit_doc_screen_main_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.black)
            )

            Spacer(modifier = Modifier.height(55.dp))
            Text(
                text = stringResource(R.string.submit_doc_screen_sub_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.black)
            )
            Spacer(modifier = Modifier.height(18.dp))

            ImageUploadButton(
                updateEnrollmentFile = { fileUri -> (submitDocViewModel::updateEnrollmentFile)(fileUri) },
                updateFileState = { isFileSelected ->
                    (submitDocViewModel::updateFileState)(
                        isFileSelected
                    )
                }
            )

            Spacer(modifier = Modifier.height(292.dp))
            EnabledBottomButtonLong(
                onClick = { /* TODO: 네비게이션 추가 */ },
                innerText = stringResource(R.string.submit_doc_screen_cancel)
            )
            Spacer(modifier = Modifier.height(5.dp))
            BottomButtonLong(
                onClick = { /* TODO: 통신 추가 */ },
                enabled = submitDocUiState.isEnrollmentFileSelected,
                isMentor = false, /* TODO */
                innerText = stringResource(id = R.string.submit_doc_screen_update)
            )
        }
    }
}

@Composable
private fun ImageUploadButton(
    updateEnrollmentFile: (Uri) -> Unit,
    updateFileState: (Boolean) -> Unit
) {
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { selectedUri ->
            if (selectedUri != null) {
                updateEnrollmentFile(selectedUri)
                updateFileState(true)
            }
        }

    Button(
        onClick = {
            launcher.launch("*/*")
        },
        modifier = Modifier.size(width = 320.dp, height = 86.dp),
        border = BorderStroke(width = 0.5.dp, color = colorResource(R.color.grey_200)),
        colors = ButtonDefaults.buttonColors(
            contentColor = colorResource(id = R.color.black),
            backgroundColor = colorResource(id = R.color.white),
            disabledBackgroundColor = colorResource(id = R.color.white),
            disabledContentColor = colorResource(id = R.color.black)
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Text(
            text = stringResource(id = R.string.submit_doc_screen_pdf_or_image),
            fontSize = 15.sp
        )
    }
}

@Preview(showSystemUi = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun SubmitDocsScreenPreview() {
    DotoringTheme {
        SubmitDocScreen()
    }
}