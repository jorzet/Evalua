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

package com.jorzet.evalua.services

import android.telephony.gsm.SmsManager

/**
 * Created by Jorge Zepeda Tinoco on 19/05/18.
 * jorzet.94@gmail.com
 */

class MessageSender {

    companion object {
        val TELEPHONE_NUMBER : String = "5559810374"
        private var instance : SmsManager? = null

        fun  getInstance(): SmsManager {
            if (instance == null)  // NOT thread safe!
                instance = SmsManager.getDefault()

            return instance!!
        }
    }
}