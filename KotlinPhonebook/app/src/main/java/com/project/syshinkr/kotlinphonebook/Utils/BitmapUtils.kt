package com.project.syshinkr.kotlinphonebook.Utils

import android.graphics.Matrix
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

object BitmapUtils {

    /**
     * 매개 : 비트맵, 회전각
     * 매트릭스 객체를 통해 회전각만큼 회전시킨 새 비트맵 생성
     */
    fun rotate(bitmap: Bitmap?, degrees: Int): Bitmap? {
        if (degrees != 0 && bitmap != null) {
            val m = Matrix()
            m.setRotate(degrees.toFloat(), bitmap.width.toFloat() / 2,
                    bitmap.height.toFloat() / 2)

            val converted = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.width, bitmap.height, m, true)
            bitmap.recycle()
            return converted
        }
        return bitmap
    }

    // 현재 시간으로 파일명 생성, 파일 경로 리턴
    fun saveBitmap(bitmap: Bitmap): String {
        val fileName = System.currentTimeMillis().toString() + ".jpg"
        val dir = File(Environment.getExternalStorageDirectory().absolutePath
                + "/kotlinPhoneBook/" + fileName)

        if (!dir.parentFile.exists()) {
            dir.parentFile.mkdirs()
        }

        FileOutputStream(dir).use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }

        return dir.path
    }



}