package com.example.testregistr.ViewModel

import org.json.JSONObject




interface CallBackRequest {
    fun successReq(response: String?)
    fun errorReq(error: String?)
}