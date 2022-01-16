package com.in10mServiceMan.ui.activities.company_pros.active

interface ActiveInterface {
    fun activeTransaction()
    fun activeEarningsTransaction()
    fun deactivate(companyId: Int, companyProId: Int)
}