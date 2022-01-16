package com.in10mServiceMan.db

import java.io.Serializable

data class WagonItem(var wagonName: String,
                     var wagonImage: Int,
                     var wagonPrice: String,
                     var wagonWeightLimit: String): Serializable


data class ItemSize(var itemName: String,
                     var itemImage: Int): Serializable

data class ServiceItem(var ServiceName:String,
                       var ServiceImage:Int
                       ):Serializable

data class SubServiceItem(var SubServiceName:String )

data class ServiceMan(var Name:String,
                      var Image:Int,
                      var Title:String,
                      var Address:String,
                      var latitude:Double,
                      var Longitude:Double,
                      var ETA : Int,
                      var Distance:Double,
                      var Age:Int,
                      var Experiance:Double,
                      var Rating:Double

)
data class LanguageItem(
        var Name: String,
        var Id:Int
)