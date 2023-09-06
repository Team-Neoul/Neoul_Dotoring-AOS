package com.example.dotoring_neoul.ui.message.messageDetail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class MessageDetail (
    val letterId:Long,
    val content:String,
    val writer: Boolean,
    val nickname: String,
    val createdAt:String,
): Parcelable