package com.in10mServiceMan.ui.activities.select_language

import android.app.Activity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.in10mServiceMan.R
import com.in10mServiceMan.db.Dummy
import com.in10mServiceMan.db.LanguageItem

class LanguagesAdapter(var activity: Activity, var selectedLanguageCallback: SelectedLanguageCallback?): RecyclerView.Adapter<LanguagesAdapter.SubServiceVH>() {

    private var languageList= Dummy.getLanguagesList()
    private var selectLanguageIndex=0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubServiceVH {

        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_sub_service, parent, false)
        return SubServiceVH(v)
    }

    override fun getItemCount(): Int {
        return this.languageList.count()
    }

    override fun onBindViewHolder(holder: SubServiceVH, position: Int) {

        val language= this.languageList.get(position);
        holder.itemView.findViewById<TextView>(R.id.subServiceTV)?.text = language.Name
        holder.itemView.findViewById<TextView>(R.id.subServiceTV2)?.text = language.Name



        if(position==selectLanguageIndex)
        {
            holder.itemView.findViewById<CardView>(R.id.selectedCard).visibility= View.VISIBLE
            holder.itemView.findViewById<ConstraintLayout>(R.id.defaultConstraintLayout).visibility= View.GONE

        }else{

            holder.itemView.findViewById<ConstraintLayout>(R.id.defaultConstraintLayout).visibility= View.VISIBLE
            holder.itemView.findViewById<CardView>(R.id.selectedCard).visibility= View.GONE
        }

        holder.itemView.setOnClickListener {
            selectLanguageIndex=position
            selectedLanguageCallback?.ChoosenLanguage(language)

            notifyDataSetChanged()

        }
    }


    inner class SubServiceVH(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {}


    interface SelectedLanguageCallback {

        fun ChoosenLanguage(item: LanguageItem)
    }
}