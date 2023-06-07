package com.example.testregistr.ViewModel

import com.example.testregistr.Model.DataInfo
import org.json.JSONObject




interface CallBackRequest {
    fun successReq(response: String?)
    fun successReqCode(response:DataInfo?)
    fun errorReq(error: String?)
}