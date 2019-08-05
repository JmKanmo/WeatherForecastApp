package com.junmo.weather_application

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.account_delete_dialog.*

class AccountDeleteDialog : DialogFragment() {

    interface AccountDeleteDiaglogInterface {
        fun delete()
        fun cancle()
    }

    private var accountDeleteDiaglogInterface: AccountDeleteDiaglogInterface? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListener()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.account_delete_dialog, container, false)
    }

    fun addAccountDeleteDiaglogInterface(listener: AccountDeleteDiaglogInterface) {
        accountDeleteDiaglogInterface = listener
    }


    private fun setupListener() {
        delete_no.setOnClickListener {
            accountDeleteDiaglogInterface?.cancle()
            dismiss()
        }

        delete_yes.setOnClickListener {
            accountDeleteDiaglogInterface?.delete()
            dismiss()
        }
    }
}

