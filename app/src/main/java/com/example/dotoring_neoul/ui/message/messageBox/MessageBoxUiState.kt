package com.example.dotoring_neoul.ui.message.messageBox



/**
 * messageList에는 MessageBox의 list가 담김
 * MessageBoxdml
 * roomPk는 방의 ID
 * memberPk는 회원의 ID
 * nickname은 회원 닉네임
 * lastletter는 마지막 대화
 * major는 회원의 학과
 * updateAt은 마지막 대화의 생성시간을 나타냄
 */
data class MessageBoxUiState (
    val messageList: List<MessageBox> = listOf<MessageBox> (),

    )

data class MessageBox(
    val roomPK: Long,
    val memberPK: Long,
    val nickname: String,
    val lastLetter: String,
    val major: String,
    val updateAt: String )