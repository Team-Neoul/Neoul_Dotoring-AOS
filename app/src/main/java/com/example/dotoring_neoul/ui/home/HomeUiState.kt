package com.example.dotoring_neoul.ui.home


/**
 * 홈화면 UiState
 */
data class HomeUiState(
    val memberList: List<Member> = listOf<Member> (),
    val majors: List<String> = listOf(),
    val fields: List<String> = listOf(),

    val optionFieldList: List<String> = listOf(),
    val chosenFieldList: MutableList<String> = mutableListOf(),

    val optionMajorList: List<String> = listOf(),
    val chosenMajorList: MutableList<String> = mutableListOf(),

    val hasChosenMajor: Boolean = false,
    val hasChosenField: Boolean = false
)
