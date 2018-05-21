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
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.jorzet.evalua.R
import com.jorzet.evalua.services.MessageSender

/**
 * Created by Jorge Zepeda Tinoco on 19/05/18.
 * jorzet.94@gmail.com
 */

class PromotorEvaluationFragment : Fragment() {

    /*
     * UI accessors
     */
    private lateinit var mBackButton : View
    private lateinit var mSendButton : View
    private lateinit var mSellerKeyEditText : EditText
    private lateinit var mModuleKeyEditText : EditText
    private lateinit var mWhatPromotorDoingSpinner : Spinner
    private lateinit var mInOrderSpinner : Spinner
    private lateinit var mArtsExposedSpinner : Spinner
    private lateinit var mOffersSpinner : Spinner
    private lateinit var mSellRegisterSpinner : Spinner
    private lateinit var mKnowCurrentPromotionSpinner : Spinner
    private lateinit var mUniformeCodeSpinner : Spinner

    /*
     * String options list
     */
    var promotorDoing = arrayOf("-", "AbordFUERA", "Atendiendo", "VolantFUERA", "Limp/Etiq/Invent", "FueraDeTurno", "NoLocalizado", "PerdElTiempo")
    var yesNotOption = arrayOf("-", "Si", "No")
    var registerSells = arrayOf("-", "AlDia", "Atrasado", "SinEvidencia")
    var goodBadOption = arrayOf("-", "Bien", "Regular", "Mal")

    /*
     * Some variables
     */
    private var mWhatPromotorDoingSelected : String = ""
    private var mInOrderSelected : String = ""
    private var mArtsExposedSelected : String = ""
    private var mOffersSelected : String = ""
    private var mSellRegisterSelected : String = ""
    private var mKnowCurrentPromotionSelected : String = ""
    private var mUniformeCodeSelected : String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (container == null)
            return null

        val rootView = inflater.inflate(R.layout.promotor_evaluation_fragment, container, false)!!

        mBackButton = rootView.findViewById(R.id.rl_back_container)
        mSendButton = rootView.findViewById(R.id.rl_send_container)
        mSellerKeyEditText = rootView.findViewById(R.id.et_seller_key)
        mModuleKeyEditText = rootView.findViewById(R.id.et_module_key)
        mWhatPromotorDoingSpinner = rootView.findViewById(R.id.spn_what_promotor_doing)
        mInOrderSpinner = rootView.findViewById(R.id.spn_order)
        mArtsExposedSpinner = rootView.findViewById(R.id.spn_arts_exposed)
        mOffersSpinner = rootView.findViewById(R.id.spn_offers)
        mSellRegisterSpinner = rootView.findViewById(R.id.spn_sell_register)
        mKnowCurrentPromotionSpinner = rootView.findViewById(R.id.spn_know_current_promotions)
        mUniformeCodeSpinner = rootView.findViewById(R.id.spn_uniforme_code)

        initView()

        return rootView
    }

    /*
     * Create the view initialization
     */
    private fun initView() {
        mBackButton.setOnClickListener(mBackButtonListener)
        mSendButton.setOnClickListener(mSendButtonListener)

        mWhatPromotorDoingSpinner.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, promotorDoing))
        mInOrderSpinner.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, yesNotOption))
        mArtsExposedSpinner.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, yesNotOption))
        mOffersSpinner.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, yesNotOption))
        mSellRegisterSpinner.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, registerSells))
        mKnowCurrentPromotionSpinner.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, goodBadOption))
        mUniformeCodeSpinner.setAdapter(ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, yesNotOption))

        mWhatPromotorDoingSpinner.onItemSelectedListener = mWhatPromotorDoingSpinnerListener
        mInOrderSpinner.onItemSelectedListener = mInOrderSpinnerListener
        mArtsExposedSpinner.onItemSelectedListener = mArtsExposedSpinnerListener
        mOffersSpinner.onItemSelectedListener = mOffersSpinnerListener
        mSellRegisterSpinner.onItemSelectedListener = mSellRegisterSpinnerListener
        mKnowCurrentPromotionSpinner.onItemSelectedListener = mKnowCurrentPromotionSpinnerListener
        mUniformeCodeSpinner.onItemSelectedListener = mUniformeCodeSpinnerListener
    }

    /*
     * This method prepare the message to be sent
     */
    private fun prepareMessage(moduleKey: String, sellerKey: String, activ: String, ordhuec: String,
                               precfictec: String, ofer: String, regpart: String, conoc: String, unif: String) {
        val message = "*%./$moduleKey.:,;$sellerKey:,;.$activ,;.:$ordhuec;.:,$precfictec.,:;$ofer:;.,$regpart,.;:$conoc,:.;$unif.;,:"
        sendMesage(message)
    }

    /*
     * This method sends a message through sms
     */
    private fun sendMesage(message : String) {
        ActivityCompat.requestPermissions(activity as Activity, arrayOf( Manifest.permission.SEND_SMS),1);
        MessageSender.getInstance().sendTextMessage(
                MessageSender.TELEPHONE_NUMBER,
                null,
                message,
                null,
                null)
        activity!!.finish()
    }


    /*
    * Listeners
    */
    private val mBackButtonListener = View.OnClickListener {
        activity!!.onBackPressed()
    }

    private val mSendButtonListener = View.OnClickListener {
        val sellerKey = mSellerKeyEditText.text.toString()
        val moduleKey = mModuleKeyEditText.text.toString()

        if (!sellerKey.equals("") && !moduleKey.equals("") && !mWhatPromotorDoingSelected.equals("") &&
                !mInOrderSelected.equals("") && !mArtsExposedSelected.equals("") &&
                !mOffersSelected.equals("") && !mSellRegisterSelected.equals("") &&
                !mKnowCurrentPromotionSelected.equals("") && !mUniformeCodeSelected.equals("")) {

            prepareMessage(moduleKey, sellerKey, mWhatPromotorDoingSelected, mInOrderSelected,
                    mArtsExposedSelected, mOffersSelected, mSellRegisterSelected,
                    mKnowCurrentPromotionSelected, mUniformeCodeSelected)

        } else {
            Toast.makeText(context!!,"Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private val mWhatPromotorDoingSpinnerListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            mWhatPromotorDoingSelected = promotorDoing.get(position)
        }
    }

    private val mInOrderSpinnerListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            mInOrderSelected = yesNotOption.get(position)
        }
    }

    private val mArtsExposedSpinnerListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            mArtsExposedSelected = yesNotOption.get(position)
        }
    }

    private val mOffersSpinnerListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            mOffersSelected = yesNotOption.get(position)
        }
    }

    private val mSellRegisterSpinnerListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            mSellRegisterSelected = registerSells.get(position)
        }
    }

    private val mKnowCurrentPromotionSpinnerListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            mKnowCurrentPromotionSelected = goodBadOption.get(position)
        }
    }

    private val mUniformeCodeSpinnerListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            mUniformeCodeSelected = yesNotOption.get(position)
        }
    }
}