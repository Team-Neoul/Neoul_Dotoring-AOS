package com.example.dotoring.ui.register.third

data class RegisterThirdUiState(
    val nickname: String = "",
    val nicknameConditionError: Boolean = true,
    val nicknameDuplicationError: DuplicationCheckState = DuplicationCheckState.NonChecked,

    val duplicationCheckButtonState: Boolean = false,
    val nextButtonState: Boolean = false
)

enum class DuplicationCheckState{
    NonChecked,
    DuplicationCheckFail,
    DuplicationCheckSuccess
}
