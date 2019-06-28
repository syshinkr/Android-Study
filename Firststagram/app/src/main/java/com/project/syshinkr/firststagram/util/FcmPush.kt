package com.project.syshinkr.firststagram.util

import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.project.syshinkr.firststagram.model.PushDTO
import okhttp3.*
import java.io.IOException

class FcmPush() {
    val JSON = MediaType.parse("application/json; charset=utf-8")
    val url = "https://fcm.googleapis.com/fcm/send"
    val serverKey = "AAAAqGDkxtA:APA91bFVTaclnbeZDbqbRZPk4ZNixs9h7oc81Y45dPpbSDenhbegG4CWYPd5oVGjN5ryXxbXr0mfDld3OpJmIb43rw2D9f4mL8nNmRDkw8h5aess_t-cA21eZ9oox4ANiRGSesCHQchp"

    var okHttpClient: OkHttpClient? = null
    var gson: Gson? = null

    init {
        gson = Gson()
        okHttpClient = OkHttpClient()
    }

    fun sendMessage(destinationUid: String, title: String, message: String) {
        FirebaseFirestore.getInstance().collection("pushtokens").document(destinationUid).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var token = task.result["pushtoken"].toString()
                println(token)
                var pushDTO = PushDTO()
                pushDTO.to = token
                pushDTO.notification?.title = title
                pushDTO.notification?.body = message

                var body = RequestBody.create(JSON, gson?.toJson(pushDTO))
                var request = Request.Builder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "key=" + serverKey)
                        .url(url)
                        .post(body)
                        .build()
                okHttpClient?.newCall(request)?.enqueue(object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        println(response?.body()?.string())
                    }
                })
            }
        }
    }
}