package com.example.dotoring_neoul.ui.detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MemberDetailedViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(MemberDetailedUiState())
    val uiState: StateFlow<MemberDetailedUiState> = _uiState.asStateFlow()

    /**
     * 멤버 정보 불러오기 위한 함수
     */
    fun loadMemberInfo(memberDetail: MemberDetailInformation) {
        _uiState.update { currentState ->
            currentState.copy(
                profileImage = memberDetail.profileImage,
                nickname = memberDetail.nickname,
                mentoringField = memberDetail.mentoringField,
                major = memberDetail.major,
                introduction = memberDetail.introduction,
            //    grade = memberDetail.grade
            )
        }
    }
}