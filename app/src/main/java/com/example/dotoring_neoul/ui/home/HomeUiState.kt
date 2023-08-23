package com.example.dotoring_neoul.ui.home

data class HomeUiState(
    val mentiList: List<Mentee> = listOf<Mentee> (),
    val majors: List<String> = listOf(),
    val jobs: List<String> = listOf()
)
