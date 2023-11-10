package com.example.dotoring_neoul.ui.register.sixth

data class RegisterSixthUiState(
    val memberId: String = "",
    val isIdConditionSatisfied: Boolean = false,
    val isDuplicationCheckButtonEnabled: Boolean = false,
    val isIdAvailable: IdDuplicationCheckState = IdDuplicationCheckState.NonChecked,


    val password: String = "",
    val isPasswordConditionSatisfied: Boolean = false,
    val passwordCertification: String = "",
    val isPasswordCertified: Boolean = false,


    val email: String = "",
    val isEmailConditionSatisfied: Boolean = false,
    val codeState: CodeState = CodeState.NonSent,
    val certificationPeriod: String = "",

    val validationCode: String = "",
    val isValidationButtonEnabled: Boolean = false,
    val emailValidationState: EmailValidationState = EmailValidationState.NonChecked,


    val isToLoginButtonEnabled: Boolean = false
)

enum class IdDuplicationCheckState {
    NonChecked,
    DuplicationCheckFail,
    DuplicationCheckSuccess
}

enum class CodeState {
    /**
     * NonSent: 인증 코드 발송 버튼 클릭 전.
     *          혹은 Valid, Expired 상태에서 input이 변경된 경우.
     * - 인증하기 버튼 enabled = false
     */
    NonSent,

    /**
     * Valid: 인증 코드 발송 버튼 클릭 후, 시간이 안 지났을 때.
     * - 인증하기 버튼 enabled = true
     */
    Valid,

    /**
     * Expired: 인증 코드 발송 버튼 클릭 후, 시간이 지났을 때.
     * - 인증하기 버튼 enabled = false
     */
    Expired
}

enum class EmailValidationState {

    /**
     * NonChecked: 인증 코드 발송 버튼 누르기 전
     */
    NonChecked,

    /**
     * AlreadySigned: 이메일이 조건을 만족하여, 인증 코드 발송 버튼 눌렀을 때 현재 가입자 중 중복되는 이메일이 존재한다고 나온 경우.
     */
    AlreadySigned,

    /**
     * Valid: 이메일이 조건을 만족하여, 인증 코드 발송 버튼을 누르고, 알맞은 인증 코드를 입력하여 인증된 상태.
     */
    Valid,

    /**
     * CodeInvalid: 이메일이 조건을 만족하여, 인증 코드 발송 버튼을 누르고, 잘못된 코드를 입력하여 인증에 실패한 경우.
     */
    CodeInvalid
}
