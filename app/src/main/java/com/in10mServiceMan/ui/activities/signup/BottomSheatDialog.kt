package com.in10mServiceMan.ui.activities.signup

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.in10mServiceMan.R
import com.in10mServiceMan.utils.CountryAdapter
import kotlinx.android.synthetic.main.bottom_sheat_dialog.mRecyclerView
import kotlinx.android.synthetic.main.bottom_sheat_dialog.searchView
import kotlinx.android.synthetic.main.bottom_sheat_dialog.tvClose

class BottomSheatDialog: BottomSheetDialogFragment(),CountryAdapter.CountrySelectionListener {

    private lateinit var countryAdpater: CountryAdapter
    private var mListner : ItemClickCountry ?= null
    var mCountryList: ArrayList<String> = ArrayList()
    private  var position_name: String=""

    fun newInstance(string: ArrayList<String>): BottomSheatDialog? {
        val f = BottomSheatDialog()
        val args = Bundle()
        args.putStringArrayList("listString", string)
        f.setArguments(args)
        return f
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            if(context is ItemClickCountry){

                mListner =  context
            }else{

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListner =  null
    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val dialog = BottomSheatDialog(requireContext(), theme)
//        dialog.setOnShowListener {
//
//            val bottomSheetDialog = it as BottomSheetDialog
//            val parentLayout =
//                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
//            parentLayout?.let { it ->
//                val behaviour = BottomSheetBehavior.from(it)
//                setupFullHeight(it)
//                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
//            }
//        }
//        return dialog
//    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        dialog.setOnShowListener {
            Handler(Looper.getMainLooper()).post {
                val bottomSheet = (dialog as? BottomSheetDialog)?.findViewById<View>(R.id.design_bottom_sheet) as? FrameLayout
                bottomSheet?.let {
                    BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }

        return dialog
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AlertDialogCustom)
        var view = inflater.inflate(R.layout.bottom_sheat_dialog, container, false)
      //  getDialog()!!.getWindow()!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //  setupFullHeight(view);
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            mCountryList = arguments?.getStringArrayList("listString") as ArrayList<String>
            mRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
           countryAdpater=    CountryAdapter(mCountryList,-1,this)
            mRecyclerView.adapter = countryAdpater
            val searchIcon =
                searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
            searchIcon.setColorFilter(Color.parseColor("#455a64"))
            val cancelIcon =
                searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
            cancelIcon.setColorFilter(Color.parseColor("#455a64"))
            val textView = searchView.findViewById<TextView>(androidx.appcompat.R.id.search_src_text)
            textView.setHint("Search")
            textView.setTextColor(Color.parseColor("#455a64"));
            textView.setHintTextColor(Color.LTGRAY)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    countryAdpater.filter.filter(newText)
                    return false
                }

            })
            tvClose.setOnClickListener {
                if(position_name!=null && position_name.isNotEmpty() ){
                    mListner!!.selectCountryPos(position_name)
                }
                dismissAllowingStateLoss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        val TAG: String?= ""
        val mCountryListDummy :ArrayList<String> = ArrayList()
        fun newInstance(listName: ArrayList<String>) {
            mCountryListDummy.addAll(listName)
        }
    }

    override fun countryListener(position_nam: String) {
      this.  position_name = position_nam
    }

    fun setListener(ctx: ItemClickCountry){
        mListner =  ctx
    }

    interface ItemClickCountry{
        fun selectCountryPos(position: String)
    }
}