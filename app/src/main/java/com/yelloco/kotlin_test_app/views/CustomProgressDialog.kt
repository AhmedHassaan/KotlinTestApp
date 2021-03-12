package com.yelloco.kotlin_test_app.views

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.google.android.material.card.MaterialCardView
import com.yelloco.kotlin_test_app.R

class CustomProgressDialog(private val activity: Activity, private val rootView: ViewGroup) {

    private lateinit var dialog: Dialog

    init {
        initializeProgressDialog()
    }

    private fun initializeProgressDialog() {
        val layoutInflater: LayoutInflater = activity.layoutInflater
        val view: View = layoutInflater.inflate(
            R.layout.custom_progress_dialog_view,
            rootView,
            false
        )

        val progressBar = view.findViewById<ProgressBar>(R.id.progress_dialog_view_progress_bar)
        val cardView =
            view.findViewById<MaterialCardView>(R.id.custom_progress_dialog_view_card_view)
        cardView.setCardBackgroundColor(Color.TRANSPARENT)

        progressBar.indeterminateDrawable.colorFilter = BlendModeColorFilterCompat
            .createBlendModeColorFilterCompat(
                ResourcesCompat
                    .getColor(activity.resources, R.color.white, null), BlendModeCompat.SRC_ATOP
            )

        dialog = Dialog(activity)
        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setOnShowListener {
            dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
    }

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }

}