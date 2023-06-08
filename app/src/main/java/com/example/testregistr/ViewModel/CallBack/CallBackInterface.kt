package com.example.testregistr.ViewModel.CallBack

interface CallBackInterface {
    fun execute(url: String?, callback: CallBackRequest?, phoneMap:MutableMap<String, String>)
}