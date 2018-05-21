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
import android.widget.Button
import com.jorzet.evalua.R

/**
 * Created by Jorge Zepeda Tinoco on 19/05/18.
 * jorzet.94@gmail.com
 */

class ChooseActionFragment : Fragment() {

    /*
     * UI accessors
     */
    private lateinit var mCheckListButton : Button
    private lateinit var mSlopesButton : Button
    private lateinit var mCelsDeliverButton : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (container == null)
            return null

        val rootView = inflater.inflate(R.layout.choose_action_fragment, container, false)!!

        mCheckListButton = rootView.findViewById(R.id.btn_check_list)
        mSlopesButton = rootView.findViewById(R.id.btn_slopes)
        mCelsDeliverButton = rootView.findViewById(R.id.btn_cels_delivery)

        initView()

        return rootView
    }

    private fun initView() {
        mCheckListButton.setOnClickListener(mCheckListButtonListener)
        mSlopesButton.setOnClickListener(mSlopesButtonListener)
        mCelsDeliverButton.setOnClickListener(mCelsDeliverButtonListener)

        // request SMS permissions
        ActivityCompat.requestPermissions(activity as Activity, arrayOf( Manifest.permission.SEND_SMS),1);
    }

    /*
     * Listeners
     */
    private val mCheckListButtonListener = View.OnClickListener {
        goPromotorEvaluationFragment()
    }

    private val mSlopesButtonListener = View.OnClickListener {
        goSlopesFragment()
    }

    private val mCelsDeliverButtonListener = View.OnClickListener {
        goMciaDeliverFragment()
    }

    /*
     * Methods to change current fragment
     */
    private fun goPromotorEvaluationFragment() {
        val transaction = fragmentManager!!.beginTransaction();
        transaction.replace(R.id.choose_actions_container, PromotorEvaluationFragment());
        transaction.commit()
    }

    private fun goSlopesFragment() {
        val transaction = fragmentManager!!.beginTransaction();
        transaction.replace(R.id.choose_actions_container, SlopesFragment());
        transaction.commit()
    }

    private fun goMciaDeliverFragment() {
        val transaction = fragmentManager!!.beginTransaction();
        transaction.replace(R.id.choose_actions_container, MciaDeliverFragment());
        transaction.commit()
    }

}