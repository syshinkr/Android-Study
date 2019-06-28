package com.project.syshinkr.kotlinphonebook.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.project.syshinkr.kotlinphonebook.FormActivity
import com.project.syshinkr.kotlinphonebook.Model.PhoneBook
import com.project.syshinkr.kotlinphonebook.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class PhoneBookListAdapter(context: Context,
                           var items: List<PhoneBook>?) : ArrayAdapter<PhoneBook>(context, -1, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        items?.let {
            val item = it[position]

            val layout = with(context) {
                linearLayout {
                    linearLayout {
                        linearLayout {
                            imageView {
                                if (item.photoSrc == null) {
                                    this.setImageDrawable(
                                            context.getDrawable(R.drawable.icon_man))
                                } else {
                                    this.setImageBitmap(
                                            BitmapFactory.decodeFile(Uri.parse(item.photoSrc).path))
                                }
                            }.lparams(width = dip(60), height = dip(60))

                            textView(item.name) {
                                gravity = Gravity.CENTER_VERTICAL
                                textSize = 20f
                            }.lparams(width = dip(0), height = dip(60), weight = 1.0f) {
                                padding = dip(10)
                                gravity = Gravity.CENTER
                                leftMargin = dip(10)
                            }

                            onClick {
                                startActivity<FormActivity>("mode" to FormActivity.MODE_UPDATE,
                                        "bookId" to item.id)

//                                val modifyViewIntent = Intent(this@with, FormActivity::class.java)
//                                modifyViewIntent.putExtra("mode", FormActivity.MODE_UPDATE)
//                                modifyViewIntent.putExtra("bookId", item.id)
//                                startActivity(modifyViewIntent)
                            }


                        }.lparams(width = dip(0), height = dip(60), weight = 1.0f)


                        button {
                            background = context.getDrawable(R.drawable.btn_call)

                            onClick {
                                makeCall("tel:" + item.phone)
                            }
                        }.lparams(width = dip(40), height = dip(40)) {
                            rightMargin = dip(15)
                            gravity = Gravity.CENTER
                        }
                    }.lparams(width = matchParent, height = dip(70)) {
                        topPadding = dip(5)
                        bottomPadding = dip(5)
                    }
                }
            }

            return layout
        }

        return null
    }
}