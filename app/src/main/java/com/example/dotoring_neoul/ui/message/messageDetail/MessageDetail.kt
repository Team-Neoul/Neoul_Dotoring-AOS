package com.example.dotoring_neoul.ui.message.messageDetail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * chatList에 들어갈 MessageDetail class
 * letterId: 대화의 ID
 * content: 대화 내용
 * writer: 본인이면 true, 타인이면 false
 * nickname은 상대방의 닉네임
 * createdAt은 대화별 생성시간
 * Parcelable 을 이용하여 다른 페이지로 이동
 */
@Parcelize
class MessageDetail (
    val letterId:Long,
    val content:String,
    val writer: Boolean,
    val nickname: String,
    val createdAt:String,
): Parcelable