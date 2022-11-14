package com.example.tp2

import android.app.Activity
import android.app.AlertDialog

class Loading (val mActivity: Activity) {
    private lateinit var isdialog:AlertDialog
    fun startLoad(){
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.p_bar,null)
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(dialogView)
        builder.setCancelable(false)
        isdialog = builder.create()
        isdialog.show()
    }
    fun isDismiss(){
        isdialog.dismiss()
    }
}