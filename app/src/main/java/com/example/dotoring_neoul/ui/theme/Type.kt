package com.example.dotoring_neoul.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.dotoring.R

// 나눔 스퀘어 폰트 패밀리 저장
val NanumSquare = FontFamily(
    Font(R.font.nanumsquare_light, FontWeight.Light),
    Font(R.font.nanumsquare_regular, FontWeight.Normal),
    Font(R.font.nanumsquare_bold, FontWeight.Bold),
    Font(R.font.nanumsquare_extrabold, FontWeight.ExtraBold),
    Font(R.font.nanumsquare_ac_light, FontWeight.Light),
    Font(R.font.nanumsquare_ac_regular, FontWeight.Normal),
    Font(R.font.nanumsquare_ac_bold, FontWeight.Bold),
    Font(R.font.nanumsquare_ac_extrabold, FontWeight.ExtraBold)
)

// Dotoring Typography 설정
val DotoringTypography = Typography(
    defaultFontFamily = NanumSquare
)