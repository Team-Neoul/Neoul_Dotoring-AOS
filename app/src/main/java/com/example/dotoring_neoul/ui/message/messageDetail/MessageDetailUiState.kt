package com.example.dotoring_neoul.ui.message.messageDetail


/**
 * chatList는 대화 내용을 리스트로 불러올 때 MessageDetail에서 빼와서 사용
 * writeContent: textField에서 작성한 쪽지 내용
 */
data class MessageDetailUiState (
    val chatList: List<MessageDetail> = listOf<MessageDetail> (),
    val writeContent: String = "",

    )
