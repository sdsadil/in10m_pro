package com.in10mServiceMan.ui.activities.intro

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.sign_in.LoginActivity
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper.getBoolean
import com.in10mServiceMan.utils.SharedPreferencesHelper.putBoolean
import com.in10mServiceMan.utils.localStorage
import kotlinx.android.synthetic.main.activity_intro.*
import kotlinx.android.synthetic.main.intro_first_screen.*
import java.util.*


class IntroActivity : In10mBaseActivity() {


    //private val dots: Array<TextView>? = null
//     var viewPager: ViewPager? = findViewById(R.id.view_pager)
    //val numbers: IntArray = intArrayOf();
    var number = IntArray(3)

    //    val viewPager:ViewPager = intro_view_pager;
    val layouts: IntArray = intArrayOf(
        R.layout.intro_first_screen,
        R.layout.intro_second_screen,
        R.layout.intro_third_screen
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        addBottomDots(0)

        val myViewPagerAdapter = MyViewPagerAdapter()
        intro_view_pager.adapter = myViewPagerAdapter
        // val numbers: IntArray = intArrayOf(R.layout.intro_first_screen,R.layout.intro_second_screen,R.layout.intro_third_screen);

        btn_next.setOnClickListener {
            localStorage(this@IntroActivity).introDone = true
            startActivity(Intent(this, LoginActivity::class.java))//EnterPhoneNumberActivity
        }
        button.setOnClickListener {
            putBoolean(this@IntroActivity, Constants.SharedPrefs.User.IS_LANG_ARB, false)
            setLangFunc2()
        }
        button1.setOnClickListener {
            putBoolean(this@IntroActivity, Constants.SharedPrefs.User.IS_LANG_ARB, true)
            setLangFunc2()
        }

        intro_view_pager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {


            }

            override fun onPageSelected(position: Int) {
                addBottomDots(position)
                if (position == 0) {
                    button_close.setOnClickListener {
                        finish()
                    }
                }
                if (position == 2) {
                    btn_next.visibility = View.INVISIBLE
                } else
                    btn_next.visibility = View.INVISIBLE

            }
        })
    }
    //  viewpager change listener


    /**
     * View pager adapter
     */
    inner class MyViewPagerAdapter : PagerAdapter() {
        private var layoutInflater: LayoutInflater? = null

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val view = layoutInflater!!.inflate(layouts[position], container, false)
            container.addView(view)

            return view
        }

        override fun getCount(): Int {
            return layouts.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }


        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }

    private fun addBottomDots(currentPage: Int) {
        val dots = arrayOfNulls<TextView>(layouts.size)


        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)

        layoutDots.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i]!!.text = Html.fromHtml("&#8226;")
            dots[i]!!.textSize = 35.0f
            dots[i]!!.setTextColor(colorsInactive[currentPage])
            layoutDots.addView(dots[i])
        }

        if (dots.isNotEmpty())
            dots[currentPage]!!.setTextColor(colorsActive[currentPage])
    }

    fun showBtn(view: View) {
        view.visibility = if (view.visibility == View.INVISIBLE) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }

    fun hideBtn(view: View) {
        view.visibility = if (view.visibility == View.VISIBLE) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }


    fun setLangFunc2() {
        val isLangArabic = getBoolean(
            this@IntroActivity,
            Constants.SharedPrefs.User.IS_LANG_ARB, false
        )!!
        if (isLangArabic) {
            setLanguage2(this, "ar")
        } else {
            setLanguage2(this, "en")
        }
    }

    fun setLanguage2(context: Context, languageCode: String?) {
        val localeNew = Locale(languageCode!!)
        Locale.setDefault(localeNew)
        val res = context.resources
        val newConfig = Configuration(res.configuration)
        newConfig.locale = localeNew
        newConfig.setLayoutDirection(localeNew)
        res.updateConfiguration(newConfig, res.displayMetrics)
        newConfig.setLocale(localeNew)
        context.createConfigurationContext(newConfig)

       /* val locale = Locale(languageCode!!)
        Locale.setDefault(locale)
        val config = Configuration()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setSystemLocale(config, locale)
        } else {
            setSystemLocaleLegacy(config, locale)
        }*/

        localStorage(this@IntroActivity).introDone = true
        startActivity(Intent(this, LoginActivity::class.java))//EnterPhoneNumberActivity
        finishAffinity()
        overridePendingTransition(0, 0)
    }

}
