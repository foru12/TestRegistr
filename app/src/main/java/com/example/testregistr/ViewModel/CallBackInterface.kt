package com.example.testregistr.ViewModel

interface CallBackInterface {
    fun execute(url: String?, callback: CallBackRequest?,phoneMap:MutableMap<String, String>)
}