package com.example.testregistr.ViewModel.CallBack

interface CallBackInterfaceRefresh {
    fun executeRefresh(url: String?, callback: CallBackRequest?, token : String,tokenMap: MutableMap<String,String>)

}