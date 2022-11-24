package com.linwei.cams.component.web.weight

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.linwei.cams.component.web.databinding.DialogLayoutWebBinding

class WebDialog(val url: String) : DialogFragment() {

    companion object {
        fun newInstance(url: String): WebDialog {
            return WebDialog(url)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mViewBinding = DialogLayoutWebBinding.inflate(inflater, container, false)
        mViewBinding.cancelView.setOnClickListener {
            dismiss()
        }
        mViewBinding.openWebView.setOnClickListener {
            if (!TextUtils.isEmpty(url) && (url.startsWith("http") || url.startsWith("https"))) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
            dismiss()
        }
        return mViewBinding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(true)
            setCanceledOnTouchOutside(true) //点击边际可消失
        }
        return dialog
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        dialog?.apply {
            val dm = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(dm)
            window?.setLayout((dm.widthPixels * 1), ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }
}