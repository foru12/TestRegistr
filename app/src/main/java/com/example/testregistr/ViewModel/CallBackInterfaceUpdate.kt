package com.example.testregistr.ViewModel

import org.json.JSONObject

interface CallBackInterfaceUpdate {
    fun executeUpdate(url: String?, callback: CallBackRequest?,token : String,jsonObj : JSONObject)

}