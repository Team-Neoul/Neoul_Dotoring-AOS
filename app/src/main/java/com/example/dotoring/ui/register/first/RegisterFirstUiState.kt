package com.example.dotoring.ui.register.first

/**
 * 회원가입 첫번째 화면 Ui State
 */
data class RegisterFirstUiState(
    // TextField 값
    val company: String = "",
    val careerLevel: String = "",
    val field: String = "",
    val major: String = "",


    // BottomSheet OptionList
    val optionFieldList: List<String> = listOf(),
    val optionMajorList: List<String> = listOf(),


    // BottomSheet ChosenList
    val chosenFieldList: MutableList<String> = mutableListOf(),
    val chosenMajorList: MutableList<String> = mutableListOf(),


    // 버튼 활성화 조건과 버튼 활성화 여부에 관한 state
    val fillCompanyField: Boolean = false,
    val fillCareerField: Boolean = false,
    val fillJobField: Boolean = false,
    val fillMajorField: Boolean = false,
    val firstBtnState: Boolean = false
)