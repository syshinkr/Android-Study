package com.project.syshinkr.firststagram.model

data class PushDTO(var to: String? = null, // PushToken 입력 부분 푸시 수신 사용자
                   var notification: Notification? = Notification()) { // 백그라운드 푸시 호출 변수
    data class Notification(var body: String? = null,
                            var title: String? = null)
}