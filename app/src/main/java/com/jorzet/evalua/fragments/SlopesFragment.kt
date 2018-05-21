/*
 * Copyright [2018] [Jorge Zepeda Tinoco]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jorzet.evalua.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.jorzet.evalua.R
import com.jorzet.evalua.services.MessageSender
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat



/**
 * Created by Jorge Zepeda Tinoco on 19/05/18.
 * jorzet.94@gmail.com
 */

class SlopesFragment : Fragment() {

    /*
     * UI accessors
     */
    private lateinit var mBackButton : View
    private lateinit var mSendButton : View
    private lateinit var mModuleEditTextView: TextView
    private lateinit var mObservationsEditText: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (container == null)
            return null

        val rootView = inflater.inflate(R.layout.slopes_fragment, container, false)!!

        mBackButton = rootView.findViewById(R.id.rl_back_container)
        mSendButton = rootView.findViewById(R.id.rl_send_container)
        mModuleEditTextView = rootView.findViewById(R.id.et_module_key)
        mObservationsEditText = rootView.findViewById(R.id.et_observations)

        initView()

        return rootView
    }

    /*
     * Create the view initialization
     */
    private fun initView() {
        mBackButton.setOnClickListener(mBackButtonListener)
        mSendButton.setOnClickListener(mSendButtonListener)
        mObservationsEditText.setOnEditorActionListener(onSendFormListener)
    }

    /*
     * This method check if the editText has info, and call prepareMessage
     */
    private fun send() {
        val module = mModuleEditTextView.text.toString()
        val observations = mObservationsEditText.text.toString()

        if (!module.equals("") && !observations.equals("")) {
            prepareMessage(mModuleEditTextView.text.toString(), mObservationsEditText.text.toString())
        } else {
            Toast.makeText(context!!,"Ingrese todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    /*
     * This method prepare the message to be sent
     */
    private fun prepareMessage(module : String, observations : String) {
        val text = "%/*.$module*%$observations%*"
        sendMesage(text)
    }

    /*
     * This method sends a message through sms
     */
    private fun sendMesage(message : String) {
        if (isSmsPermissionGranted()) {
            MessageSender.getInstance().sendTextMessage(
                    MessageSender.TELEPHONE_NUMBER,
                    null,
                    message,
                    null,
                    null)
            activity!!.finish()
        } else {
            Toast.makeText(context!!,"No tiene permisos para enviar mensages SMS", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * Check if we have SMS permission
     */
    fun isSmsPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(activity!!, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
    }

    /*
     * Listeners
     */
    private val mBackButtonListener = View.OnClickListener {
        activity!!.onBackPressed()
    }

    private val mSendButtonListener = View.OnClickListener {
        send()
    }

    private val onSendFormListener = object : TextView.OnEditorActionListener {
        override fun onEditorAction(textView: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            var action = false
            if (actionId === EditorInfo.IME_ACTION_SEND) {
                // hide keyboard
                val inputMethodManager = textView!!.getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0)
                send()
                action = true
            }
            return action
        }
    }
}