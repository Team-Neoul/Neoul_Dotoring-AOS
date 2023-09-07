package com.example.dotoring_neoul.ui.message.messageDetail

data class MessageDetailUiState (
    val chatList: List<MessageDetail> = listOf<MessageDetail> (),
    val writeContent: String = "",

    )
