package com.example.dotoring_neoul.ui.register.first

data class RegisterFirstUiState(

    // TextField 값
    val company: String = "",
    val careerLevel: String = "",
    val job: String = "",
    val major: String = "",

    // BottomSheet OptionList
    val optionJobList: List<String> = listOf(),
    val optionMajorList: List<String> = listOf(),

    // BottomSheet ChosenList
    val chosenJobList: MutableList<String> = mutableListOf(),
    val chosenMajorList: MutableList<String> = mutableListOf(),

    // 버튼 활성화 조건과 버튼 활성화 여부에 관한 state
    val fillCompanyField: Boolean = false,
    val fillCareerField: Boolean = false,
    val fillJobField: Boolean = false,
    val fillMajorField: Boolean = false,
    val firstBtnState: Boolean = true
)