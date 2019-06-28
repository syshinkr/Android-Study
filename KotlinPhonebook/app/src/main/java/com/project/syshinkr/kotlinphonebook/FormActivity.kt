package com.project.syshinkr.kotlinphonebook

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.widget.*
import com.project.syshinkr.kotlinphonebook.Model.PhoneBook
import com.project.syshinkr.kotlinphonebook.Utils.BitmapUtils
import io.realm.Realm
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.io.FileNotFoundException

class FormActivity : AppCompatActivity() {
    private var submitBtn: Button? = null
    private var photoView: ImageView? = null
    private var nameView: EditText? = null
    private var phoneView: EditText? = null
    private var emailView: EditText? = null

    private var realm: Realm? = null
    private var photoPath: String? = null

    private var actionView: LinearLayout? = null
    private var callBtn: Button? = null
    private var smsBtn: Button? = null
    private var deleteBtn: Button? = null

    private var mode: Int = MODE_INSERT
    private var currentPhoneBook: PhoneBook? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = verticalLayout {
            photoView = imageView {
                if (mode == MODE_UPDATE && currentPhoneBook?.photoSrc != null) {
                    imageBitmap = BitmapFactory.decodeFile(currentPhoneBook!!.photoSrc)
                } else {
                    image = getDrawable(R.drawable.icon_man)
                }

                onClick {
                    getPhotoImage()
                }
            }.lparams {
                width = dip(100)
                height = dip(100)
                gravity = Gravity.CENTER_HORIZONTAL
                topMargin = dip(30)
            }

            textView(getString(R.string.insert_photo_comment)) {
                gravity = Gravity.CENTER
            }.lparams {
                width = matchParent
                height = wrapContent
                bottomMargin = dip(30)
            }

            if (mode != MODE_INSERT) {
                actionView = linearLayout {
                    gravity = Gravity.CENTER

                    callBtn = button {
                        background = getDrawable(R.drawable.btn_call)

                        onClick {
                            //바로 전화 걸기
                            makeCall("tel:" + currentPhoneBook!!.phone)

//                            val uri = Uri.parse("tel:" + currentPhoneBook!!.phone)
//                            val intent = Intent(Intent.ACTION_DIAL, uri)
//                            startActivity(intent)
                        }
                    }.lparams {
                        width = dip(50)
                        height = dip(50)
                        rightMargin = dip(30)
                    }

                    smsBtn = button {
                        background = getDrawable(R.drawable.btn_sms)

                        onClick {
                            sendSMS("smsto:" + currentPhoneBook!!.phone)
//                            val uri = Uri.parse("smsto:" + currentPhoneBook!!.phone)
//                            val intent = Intent(Intent.ACTION_SENDTO, uri)
//                            startActivity(intent)
                        }
                    }.lparams {
                        width = dip(50)
                        height = dip(50)
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    bottomMargin = dip(30)
                }
            }

            verticalLayout {
                nameView = editText {
                    hint = getString(R.string.insert_name_hint)

                    if(mode == MODE_UPDATE && currentPhoneBook?.name != null) {
                        setText(currentPhoneBook!!.name)
                    }
                }.lparams {
                    width = matchParent
                    height = dip(40)
                    bottomMargin = dip(20)
                }

                phoneView = editText {
                    hint = getString(R.string.insert_phone_hint)

                    if(mode == MODE_UPDATE && currentPhoneBook?.phone != null) {
                        setText(currentPhoneBook!!.phone)
                    }
                }.lparams {
                    width = matchParent
                    height = dip(40)
                    bottomMargin = dip(20)
                }

                emailView = editText {
                    hint = getString(R.string.insert_email_hint)

                    if(mode == MODE_UPDATE && currentPhoneBook?.email != null) {
                        setText(currentPhoneBook!!.email)
                    }
                }.lparams {
                    width = matchParent
                    height = dip(40)
                    bottomMargin = dip(20)
                }
            }.lparams {
                width = matchParent
                height = wrapContent
                leftPadding = dip(15)
                rightPadding = dip(15)
            }

            linearLayout {
                gravity = Gravity.CENTER

                button(getString(R.string.btn_save)) {
                    onClick {
                        submit()
                    }
                }

                if(mode == MODE_UPDATE) {
                    button(getString(R.string.btn_delete)) {
                        onClick {
                            delete()
                        }
                    }
                }
            }.lparams {
                width = matchParent
                height = wrapContent
                leftPadding = dip(15)
                rightPadding = dip(15)
            }
        }
        setContentView(layout)

        Realm.init(applicationContext)
        realm = Realm.getDefaultInstance()
        setMode()
    }

    private fun takePicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQ_TAKE_PICTURE)
    }

    private fun getPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQ_PICK_GALLERY)
    }

    private fun getPhotoImage() {
        val items = listOf("카메라에서 가져오기", "앨범에서 가져오기")
        selector("사진 가져오기", items, { _, whichButton ->
            if(whichButton == SELECT_TAKE_PICTURE) {
                takePicture()
            } else if(whichButton == SELECT_PICK_GALLERY) {
                getPhotoFromGallery()
            }
        })
//        val items = arrayOf("카메라에서 가져오기", "앨범에서 가져오기")
//        val ab = AlertDialog.Builder(this)
//
//        ab.setTitle("사진 가져오기")
//        ab.setItems(items) { _, whichButton ->
//            if (whichButton == SELECT_TAKE_PICTURE) {
//                takePicture()
//            } else if (whichButton == SELECT_PICK_GALARY) {
//                getPhotoFromGallery()
//            }
//        }.setNegativeButton("취소") { dialog, _ -> dialog.cancel() }
//        ab.show()
    }

    private fun setMode() {
        mode = intent.getIntExtra("mode", MODE_INSERT)

        if (mode == MODE_UPDATE) {
            val phoneId = intent.getIntExtra("bookId", -1)

            if (phoneId == -1) {
                toast("잘못된 접근입니다.")
                finish()
                return
            }

            currentPhoneBook = realm!!
                    .where<PhoneBook>(PhoneBook::class.java)
                    .equalTo("id", phoneId)
                    .findFirst()

            currentPhoneBook ?: let {
                toast("존재하지 않는 연락처입니다")
                finish()
                return
            }
        }
    }

    private fun submit() {
        val name = nameView?.text.toString()
        val phone = phoneView?.text.toString()
        val email = emailView?.text.toString()

        if ("" == name || "" == phone) {
            toast("이름, 휴대폰은 필수입니다")
//            longToast("이름, 휴대폰은 필수입니다") --> Toast.LENGTH_LONG
//            Toast.makeText(this, "이름, 휴대폰은 필수입니다", Toast.LENGTH_SHORT)
            return
        }

        realm?.executeTransaction { realm ->
            if (mode == MODE_INSERT) {
                currentPhoneBook = PhoneBook()
                val currentIdNum = realm
                        .where<PhoneBook>(PhoneBook::class.java)
                        .max("id")
                val nextId = (currentIdNum?.toInt() ?: 0) + 1
                currentPhoneBook?.id = nextId
            }

            currentPhoneBook?.let {
                it.name = name
                it.phone = phone
                it.email = email
                it.photoSrc = photoPath

                realm.insertOrUpdate(it)
            }
        }

        finish()
    }

    private fun delete() {
        alert("정말 삭제하시겠습니까?") {
            yesButton {
                realm?.executeTransaction {
                    currentPhoneBook?.deleteFromRealm()
                }
                finish()
            }

            noButton {}
        }.show()

//        val ab = AlertDialog.Builder(this@FormActivity)
//
//        ab.setTitle("정말 삭제하시겠습니까?")
//        ab.setPositiveButton("예") { _, _ ->
//            realm?.executeTransaction {
//                currentPhoneBook?.deleteFromRealm()
//            }
//            finish()
//        }.setNegativeButton("아니요") { dialog, _ -> dialog.cancel() }
//        ab.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQ_TAKE_PICTURE -> {
                    val thumbnail = data.extras.get("data") as Bitmap
                    var dst = Bitmap.createScaledBitmap(thumbnail, 100, 100, true)
                    dst = BitmapUtils.rotate(dst, 90)

                    photoPath = BitmapUtils.saveBitmap(dst)
                    photoView?.setImageBitmap(dst)
                }
                REQ_PICK_GALLERY -> {
                    try {
                        contentResolver.openInputStream(data.data).use {
                            var photo = BitmapFactory.decodeStream(it)
                            photo = Bitmap.createScaledBitmap(photo, 100, 100, true)

                            photoPath = BitmapUtils.saveBitmap(photo)
                            photoView?.setImageBitmap(photo)
                        }
                    } catch (e: FileNotFoundException) {
                        toast("이미지를 불러오는데 실패했습니다.")
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm?.close()
    }

    companion object {
        val MODE_INSERT = 100
        val MODE_UPDATE = 101

        private val REQ_TAKE_PICTURE = 200
        private val REQ_PICK_GALLERY = 201

        private val SELECT_TAKE_PICTURE = 0
        private val SELECT_PICK_GALLERY = 1
    }
}
