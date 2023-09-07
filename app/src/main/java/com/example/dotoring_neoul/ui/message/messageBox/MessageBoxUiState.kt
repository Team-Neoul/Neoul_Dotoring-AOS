package com.example.dotoring_neoul.ui.message.messageBox

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