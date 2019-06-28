package com.project.syshinkr.kotlinphonebook

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.widget.*
import com.project.syshinkr.kotlinphonebook.Adapter.PhoneBookListAdapter
import com.project.syshinkr.kotlinphonebook.Model.PhoneBook
import io.realm.Realm
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onKey

class MainActivity : AppCompatActivity() {
    private var phoneBookListView: ListView? = null
    private var insertBtn: Button? = null
    private var searchText: EditText? = null
    private var items: ArrayList<PhoneBook>? = null
    private var adapter: PhoneBookListAdapter? = null
    private var realm: Realm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var layout = verticalLayout {
            this.padding = dip(5)

            linearLayout {

                searchText = editText {
                    this.hint = getString(R.string.main_search_hint)
                    this.onKey { _, _, _ ->
                        findByName(searchText?.text.toString())
                        false
                    }
                }.lparams(width = dip(0), height = matchParent, weight = 1.0f)

                insertBtn = button {
                    this.gravity = Gravity.CENTER_VERTICAL
                    this.background = getDrawable(R.drawable.btn_plus)
                    this.onClick {
                        startActivity<FormActivity>()

//                        val intent = Intent(this@MainActivity, FormActivity::class.java)
//                        startActivity(intent)
                    }
                } .lparams(width = dip(40), height=dip(40)) {
                    leftMargin = dip(10)
                    rightMargin = dip(10)
                }

            }.lparams(width = matchParent, height=dip(60))


            phoneBookListView = listView {

            }.lparams(width = matchParent, height =dip(0), weight = 1.0f)
        }

        setContentView(layout)

        permissionCheck()

        Realm.init(applicationContext)
        realm = Realm.getDefaultInstance()
        items = ArrayList()
        adapter = PhoneBookListAdapter(this, items)
        phoneBookListView?.adapter = adapter
    }

    private fun findByName(name: String) {
        items?.clear()
        val allUser = realm!!.where<PhoneBook>(PhoneBook::class.java)
                .beginsWith("name", name)
                .findAll()
                .sort("name")
        for (p in allUser) {
            items?.add(p)
        }

        adapter?.notifyDataSetChanged()
    }

    private fun permissionCheck() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            var permissionCheck = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 100)
            }

            permissionCheck = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
            }

            permissionCheck = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA)
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.CAMERA), 100)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        findByName(searchText?.text.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        realm?.close()
    }
}
