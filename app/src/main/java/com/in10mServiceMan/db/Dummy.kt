package com.in10mServiceMan.db

import com.in10mServiceMan.R
import com.in10mServiceMan.utils.in10mApplication

object Dummy {

    fun getServiceManList():List<ServiceMan>{
        val serviceList: MutableList<ServiceMan> = ArrayList()
        val serviceOne= ServiceMan("Mujeeb Muhammed", R.drawable.dummy_user,
                "Working for a Non-Proﬁt Org","123 street, Mubarak Bin Mutlaq Al Radaan street",
                29.3750618,47.9783259,12,0.8,30,10.5,4.7)

        val serviceTwo= ServiceMan("Yusri el-Amini", R.drawable.dummy_user,
                "Independent professional","123 street, Mubarak Bin Mutlaq Al Radaan street",
                29.372009, 47.976461,12,1.5,25,3.5,5.0)

        val serviceThree= ServiceMan("Muhhammed Ali Jaffer", R.drawable.dummy_user,
                "On Contract","123 street, Mubarak Bin Mutlaq Al Radaan street",
                29.1965558, 48.1094814,12,2.1,20,5.5,2.7)

        val serviceFour= ServiceMan("Saleem Kaif", R.drawable.dummy_user,
                "Independent professional","123 street, Mubarak Bin Mutlaq Al Radaan street",
                29.376856, 47.976401,12,0.4,21,1.5,3.7)


        val serviceFive= ServiceMan("Muhammed", R.drawable.dummy_user,
                "Working for a Non-Proﬁt Org","123 street, Mubarak Bin Mutlaq Al Radaan street",
                29.3753518,47.9783259,17,1.8,30,10.5,4.7)

        val serviceSix= ServiceMan("Yusri", R.drawable.dummy_user,
                "Independent professional","123 street, Mubarak Bin Mutlaq Al Radaan street",
                29.375009, 47.976461,12,3.5,25,3.5,5.0)

        val serviceSeven= ServiceMan("Jaffer", R.drawable.dummy_user,
                "On Contract","123 street, Mubarak Bin Mutlaq Al Radaan street",
                29.1965558, 48.1094004,12,2.1,20,5.5,2.7)

        val serviceEight= ServiceMan("Saleem", R.drawable.dummy_user,
                "Independent professional","123 street, Mubarak Bin Mutlaq Al Radaan street",
                29.376556, 47.976301,12,0.4,21,1.5,3.7)

        serviceList.add(serviceOne);
        serviceList.add(serviceTwo);
        serviceList.add(serviceThree);
        serviceList.add(serviceFour);

        serviceList.add(serviceFive);
        serviceList.add(serviceSix);
        serviceList.add(serviceSeven);
        serviceList.add(serviceEight);


        return serviceList;
    }

     fun  getServicesList():List<ServiceItem>{

        val serviceList: MutableList<ServiceItem> = ArrayList()

        val serviceOne= ServiceItem("Plumbing", R.drawable.icon_plumbing)
        val serviceTwo= ServiceItem("Air Conditioning", R.drawable.icon_ac)
        val serviceThree= ServiceItem("Cleaning", R.drawable.icon_cleaning)
        val serviceFour= ServiceItem("Appliance", R.drawable.icon_applicances)
        val serviceFive= ServiceItem("Plumbing", R.drawable.icon_plumbing)
        val serviceSix= ServiceItem("Air Conditioning", R.drawable.icon_ac)
        val serviceSeven= ServiceItem("Cleaning", R.drawable.icon_cleaning)
        val serviceEight= ServiceItem("Appliance", R.drawable.icon_applicances)


        serviceList.add(serviceOne)
        serviceList.add(serviceTwo)
        serviceList.add(serviceThree)
        serviceList.add(serviceFour)

        serviceList.add(serviceFive)
        serviceList.add(serviceSix)
        serviceList.add(serviceSeven)
        serviceList.add(serviceEight)

        serviceList.add(serviceOne)
        serviceList.add(serviceTwo)
        serviceList.add(serviceThree)
        serviceList.add(serviceFour)

        return serviceList
    }

    fun getSubServicesList():List<SubServiceItem>{
        val SubServiceList: MutableList<SubServiceItem> = ArrayList()

        val serviceOne= SubServiceItem("Sink")
        val serviceTwo= SubServiceItem("Shower / Bathtub")
        val serviceThree= SubServiceItem("Toilet")
        val serviceFour= SubServiceItem("Piping & Water Tanks")
        val serviceFive= SubServiceItem("Boiler")
        val serviceSix= SubServiceItem("Filters")
        val serviceSeven= SubServiceItem("Pumps")


        SubServiceList.add(serviceOne)
        SubServiceList.add(serviceTwo)
        SubServiceList.add(serviceThree)
        SubServiceList.add(serviceFour)

        SubServiceList.add(serviceFive)
        SubServiceList.add(serviceSix)
        SubServiceList.add(serviceSeven)

        SubServiceList.add(serviceOne)
        SubServiceList.add(serviceTwo)
        SubServiceList.add(serviceThree)
        SubServiceList.add(serviceFour)

        return SubServiceList
    }
    fun getLanguagesList():List<LanguageItem>{
        val languageList: MutableList<LanguageItem> = ArrayList()

        val language1= LanguageItem("ENGLISH (USA)",1)
        val language2= LanguageItem("ENGLISH (UK)",2)
        val language3= LanguageItem("عربی",3)
        val language4= LanguageItem("हिंदी",4)
        val language5= LanguageItem("ESPANOL",5)
        val language6= LanguageItem("DEUTSCHE",6)
        val language7= LanguageItem("中文",7)
       // val language8= LanguageItem("中文",7)


        languageList.add(language1)
        languageList.add(language2)
        languageList.add(language3)
        languageList.add(language4)
        languageList.add(language5)
        languageList.add(language6)
        languageList.add(language7)

        return languageList
    }

    fun getWagonList(): List<WagonItem> {

        val wagonList: MutableList<WagonItem> = ArrayList()

        val smallWagonItem= WagonItem(in10mApplication.instance!!.getString(R.string.small_car),
                R.drawable.ic_small_car_selector,"124.56","50")

        val halfLorryWagonItem= WagonItem(in10mApplication.instance!!.getString(R.string.half_lorry),
                R.drawable.ic_half_lorry_selector,"165.64","120")

        val truckItem= WagonItem(in10mApplication.instance!!.getString(R.string.truck),
                R.drawable.ic_truck_selector,"235.53","300")

        wagonList.add(smallWagonItem)
        wagonList.add(halfLorryWagonItem)
        wagonList.add(truckItem)

        return wagonList

    }

    fun getItemSizeList(): List<ItemSize> {

        val itemList: MutableList<ItemSize> = ArrayList()

        val smallItem= ItemSize(in10mApplication.instance!!.getString(R.string.small),
                R.drawable.ic_small_selector)

        val mediumItem= ItemSize(in10mApplication.instance!!.getString(R.string.medium),
                R.drawable.ic_medium_selector)

        val largeItem= ItemSize(in10mApplication.instance!!.getString(R.string.large),
                R.drawable.ic_large_selector)

        val xLargeItem= ItemSize(in10mApplication.instance!!.getString(R.string.x_large),
                R.drawable.ic_xlarge_selector)

        itemList.add(smallItem)
        itemList.add(mediumItem)
        itemList.add(largeItem)
        itemList.add(xLargeItem)

        return itemList

    }

    fun getHandleOptionsList(): List<ItemSize> {

        val itemList: MutableList<ItemSize> = ArrayList()

        val bagItem= ItemSize(in10mApplication.instance!!.getString(R.string.bag),
                R.drawable.ic_bag_selector)

        val fragileItem= ItemSize(in10mApplication.instance!!.getString(R.string.fragile),
                R.drawable.ic_fragile_selector)

        val coldItem= ItemSize(in10mApplication.instance!!.getString(R.string.cold),
                R.drawable.ic_cold_selector)

        val xHelpItem= ItemSize(in10mApplication.instance!!.getString(R.string.extra_help),
                R.drawable.ic_extra_help_selector)

        itemList.add(bagItem)
        itemList.add(fragileItem)
        itemList.add(coldItem)
        itemList.add(xHelpItem)

        return itemList

    }

}