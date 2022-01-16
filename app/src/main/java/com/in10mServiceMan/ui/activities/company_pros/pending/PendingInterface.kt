package com.in10mServiceMan.ui.activities.company_pros.pending

interface PendingInterface {
    fun pendingTransaction(servicemanId: Int)
    fun pendingEarningsTransaction()
    fun delete(companyId: Int, companyProId: Int)
}