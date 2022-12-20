package com.ssionii.bloodNote.ui.write.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import com.ssionii.bloodNote.R
import com.ssionii.bloodNote.base.BaseDialogFragment
import com.ssionii.bloodNote.databinding.DialogFragmentBloodSugarDialogBinding
import com.ssionii.bloodNote.ui.main.EmptyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class BloodSugarDialogFragment : BaseDialogFragment<DialogFragmentBloodSugarDialogBinding, EmptyViewModel>() {
    override val layoutResID: Int
        get() = R.layout.dialog_fragment_blood_sugar_dialog
    override val viewModel: EmptyViewModel by viewModel()

    lateinit var listener: OnDialogDismissedListener

    var isBloodSugar = true
    var time = -1f
    var getMeal = -1
    var value = 0
    var result = 0 // 0: 안전, 1: 주의, 2: 위험


    override fun onResume() {
        super.onResume()

        dialog!!.setOnKeyListener(object : DialogInterface.OnKeyListener {
            override fun onKey(
                dialog: DialogInterface, keyCode: Int,
                event: KeyEvent
            ): Boolean {

                return if (keyCode == KeyEvent.KEYCODE_BACK) {
                    //This is the filter
                    if (event.action != KeyEvent.ACTION_DOWN)
                        true
                    else {
                        listener.onDialogDismissed()
                        dismiss()
                        true // pretend we've processed it
                    }
                } else
                    false // pass on to be processed as normal
            }
        })
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.run{
            time = getFloat("time")!!
            getMeal = getInt("getMeal")
            value = getInt("value")
        }

        getResult()

        viewDataBinding.fragment = this

        viewDataBinding.dialogFragmentBloodSugarDialogClDone.setOnClickListener {
            dismiss()
        }

    }

    private fun getResult(){
        if(getMeal == 0){
            if(value <= 99) result = 0
            else if(value <= 125) result = 1
            else result = 2
        } else {
            if(value <= 180) result = 0
            else if(value <= 200) result = 1
            else result = 2
        }
    }


    fun setOnDialogDismissedListener(listener: OnDialogDismissedListener) {
        this.listener = listener
    }

    interface OnDialogDismissedListener {
        fun onDialogDismissed()
    }

    override fun dismiss() {
        listener.onDialogDismissed()
        super.dismiss()

    }
}