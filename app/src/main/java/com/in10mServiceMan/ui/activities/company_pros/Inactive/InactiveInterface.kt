package com.in10mServiceMan.ui.activities.company_pros.Inactive

interface InactiveInterface {
    fun inactiveTransaction(servicemanId: Int)
    fun inactiveEarningsTransaction()
    fun activate(companyId: Int, companyProId: Int)
}