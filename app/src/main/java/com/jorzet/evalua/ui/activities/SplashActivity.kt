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

package com.jorzet.evalua.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.jorzet.evalua.R

/**
 * Created by Jorge Zepeda Tinoco on 19/05/18.
 * jorzet.94@gmail.com
 */

class SplashActivity : BaseActivityLifeCycle() {

    private val TIME_DELAY : Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initHandler()
    }

    private fun initHandler() {
        Handler().postDelayed(object : Runnable {
            override fun run() {
                goContentActivity()
            }
        }, TIME_DELAY)
    }

    private fun goContentActivity() {
        val intent = Intent(this, ContentActivity::class.java)
        this.startActivity(intent)
        this.finish()
    }
}
