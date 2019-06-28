package com.project.syshinkr.firststagram.model

data class AlarmDTO (
        var destinationUid: String? = null, // 받는 이 UID
        var userId: String? = null, // 보내는 이 이메일
        var uid: String? = null, // 보내는 이 UID
        var kind: Int = 0, //0 : 좋아요, 1: 메세지, 2: 팔로우
        var message: String? = null,
        var timestamp: Long? = null
)