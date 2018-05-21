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
import android.widget.*
import com.jorzet.evalua.R
import com.jorzet.evalua.services.MessageSender
import java.util.concurrent.TimeUnit
import android.widget.Toast
import com.jorzet.evalua.R.id.send
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager


/**
 * Created by Jorge Zepeda Tinoco on 19/05/18.
 * jorzet.94@gmail.com
 */

class MciaDeliverFragment : Fragment() {

    /*
     * UI accessors
     */
    private lateinit var mBackButton : View
    private lateinit var mSendButton : View
    private lateinit var mChooseDatePicker: DatePicker
    private lateinit var mChooseTimePicker: TimePicker
    private lateinit var mStringSpinner : Spinner
    private lateinit var mFactureEditText: EditText
    private lateinit var mFolioEditText: EditText

    /*
     * String options list
     */
    var options = arrayOf("BHbonanza", "BHcelcua1", "BHcelcua2", "BHcuauhtemoc", "BHbaeza", "Elektra", "Famsa", "Sams", "Soriana", "Walmart", "BodegaAurrera", "Telmex")

    /*
     * Some variables
     */
    private var mSelectedStringOption : String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (container == null)
            return null

        val rootView = inflater.inflate(R.layout.mcia_deliver_fragment, container, false)!!

        mBackButton = rootView.findViewById(R.id.rl_back_container)
        mSendButton = rootView.findViewById(R.id.rl_send_container)
        mChooseDatePicker = rootView.findViewById(R.id.choose_date_picker)
        mChooseTimePicker = rootView.findViewById(R.id.choose_time_picker)
        mStringSpinner = rootView.findViewById(R.id.spn_string)
        mFactureEditText = rootView.findViewById(R.id.et_facture)
        mFolioEditText = rootView.findViewById(R.id.et_folio)

        initView()

        return rootView
    }

    /*
     * Create the view initialization
     */
    private fun initView() {
        mBackButton.setOnClickListener(mBackButtonListener)
        mSendButton.setOnClickListener(mSendButtonListener)
        mStringSpinner.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, options));

        mStringSpinner.onItemSelectedListener = mStringSpinnerListener
        // set 24 hrs mode timePicker
        mChooseTimePicker.setIs24HourView(true);

        mFolioEditText.setOnEditorActionListener(onSendFormListener)

    }

    /*
     * This method check if has info, and call prepareMessage
     */
    private fun send() {
        val hours = Integer.toString(mChooseTimePicker.getCurrentHour())
        val minutes = Integer.toString(mChooseTimePicker.getCurrentMinute())
        val day = mChooseDatePicker.getDayOfMonth().toString()
        val month = (mChooseDatePicker.getMonth() + 1).toString()
        val year = mChooseDatePicker.getYear().toString()
        val deliverDate = year + "-" + month + "-" + day + "-" + hours + "-" + minutes
        val facture = mFactureEditText.text.toString()
        val folio = mFolioEditText.text.toString()

        if (!deliverDate.equals("") && !mSelectedStringOption.equals("")
                && !facture.equals("") && !folio.equals("")) {

            prepareMessage(deliverDate, mSelectedStringOption, facture, folio)

        } else {
            Toast.makeText(context!!,"Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     * This method prepare the message to be sent
     */
    private fun prepareMessage(deliverDate : String, string : String, facture : String, folio : String) {
        val message = "****$deliverDate*.*.$string.*.*$facture..**$folio**.."
        sendMesage(message)
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
    private val mStringSpinnerListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            mSelectedStringOption = options.get(position)
        }
    }

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