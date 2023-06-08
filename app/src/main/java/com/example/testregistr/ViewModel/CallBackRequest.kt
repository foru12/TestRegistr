package com.example.testregistr.ViewModel

import com.example.testregistr.Model.DataInfo
import com.example.testregistr.Model.DataMe
import org.json.JSONObject




interface CallBackRequest {
    fun successReq(response: String?)
    fun successReqRegistr(response: DataInfo?)
    fun successReqCode(response:DataInfo?)
    fun successReqMe(response:DataMe?)
    fun errorReq(error: String?)
}