package com.in10mServiceMan.utils

import com.in10mServiceMan.models.CompanyServimenRequest


/**
 * Created by HashInclude on 25-04-2018
 * Copyright © 2018 HashInclude IO
 */
class Constants {
    class SharedPrefs {
        class User {
            companion object {
                val IS_LOGGED_IN = "user_is_logged_in"
                val AUTH_TOKEN = "user_auth_token"
                val REGISTRATION_OTP = "user_auth_registration_otp"
                val MOBILE_NUMBER = "user_mobile_number"
                val IS_REGISTERED_BEFORE: String? = "user_is_registered_before"
                val USER_ID = "user_id"
                val USER_NAME = "user_name"
                val SELECTED_MODEL_ID = "user_selected_model"
                val ONGOING_SHIPMENTS_COUNT = "user_ongoing_shipments_count"
                val EMAIL = "user_email"
                val PROFILE_PICTURE = "user_profile_picture"
                val USER_TYPE = "user_type"
                val COMPANY_NAME = "user_company_name"
                val WALLET_BALANCE = "user_wallet_balance"
                val USER_LANGUAGE = "user_language"
                val RATING = "user_rating"
                val PUSH_ENABLED = "user_push_enabled"
                val KUWAIT_PORTAL_TOKEN = "user_kuwait_portal_token"
                val COMMON_ID = "common_id"

                val USER_IMAGE = "user_image"
                const val FIRST_NAME = "first_name"
                const val LAST_NAME = "last_name"
                const val DATE_OF_BIRTH = "date_of_birth"
                const val STREET = "street"
                const val SUITE = "suite"
                const val CITY = "city"
                const val STATE = "state"
                const val ZIPCODE = "zipcode"
                const val PASSWORD = "password"
                const val COUNTRY = "country"
                const val COUNTRY_CODE = "country_code"
                const val NAME_OF_COMPANY = "name_of_company"
                const val PERSON_COMPANY_NAME="person_company_name"
                const val PERSON_TYPE="person_type"


                const val COMPLETE_LOGIN_DETAILS = "complete_login_details"
                const val ACCOUNT_TYPE = "account_type"
                const val ESTIMATE_AMOUNT = "estimate_amount"

                const val SERVICES_PROVIDED = "services_provided"
                const val SERVICES_PROVIDED_STRING = "services_provided_string"
                const val SELECTED_IMAGE = "selected_image"

                const val SM_ADDRESS_ONE = "sm_address_one"
                const val SM_ADDRESS_TWO = "sm_address_two"
                const val SM_STREET_NAME = "sm_street_name"
                const val SM_CITY = "sm_city"
                const val SM_STATE = "sm_state"
                const val SM_ZIP_CODE = "sm_zip_code"
                const val SM_PAYMENT_TYPE = "sm_payment_type"

                const val ACCOUNT_NUMBER = "account_number"
                const val SSN_NUMBER = "ssn_number"
                const val ROUTING_NUMBER = "routing_number"
                const val PAYOUT_TYPE = "payment_type"
                const val EXPIRY_MONTH = "expiry_month"
                const val EXPIRY_YEAR = "expiry_year"
                const val CARD_NUMBER = "card_number"
                const val PAYMENT_TYPE = "payment_type"
                const val IS_LANG_ARB = "isLangArb"
                const val IS_DEVICETOKEN_UPDATED = "isDeviceTokenUpdated"
                const val FREE_ESTIMATE = "free_estimate"

                const val LEVEL_THREE_DATA = "level_three_data"

                var position: Int = 0
                var servicemenRequestList: ArrayList<CompanyServimenRequest> = ArrayList()
                var totalStringRequest: String = ""
            }
        }

    }

    class GlobalSettings {
        companion object {
            val MAP_DEFAULT_ZOOM_LEVEL = 17f
            val AVAILABLE_WAGONS_DELAY: Long = 5000
            val MAP_PADDING = 100
            val MAP_MAX_ZOOM_LEVEL = 20f
            val MAP_MIN_ZOOM_LEVEL = 0f

            var isCashPaid = true
            var isBankAccount = false
            var isDebitCard = false
            var isFreeEstimate = true
            var fromAccount = false
            var fromPayment = false
            var fromIA = false
            var fromIAS = false
            var signupTerms = false
            var owner=""
            var image=""

            var streetName=""
            var aptNo=""
            var cityName=""
            var stateName=""
            var zipCode=""
            var paymentType=0

            var privacyPolicy = false
            var termsConditions = false
        }
    }

    public class Bucket {
        public companion object {
//            val BUCKET_NAME = if (BuildConfig.FLAVOR.equals("production")) "in10mdevimages" else "in10mdevimages"
            val PACKAGE_FOLDER = "packageimages"
            public val USER_FOLDER = "profileimage"
            val REGION = "eu-west-3"

        }
    }

    companion object {
        /*const val ENDPOINT: String = BuildConfig.HOST
        const val FUNCTIONS_ENDPOINT: String = BuildConfig.FUNCTION_HOST
        const val TERMS_URL = Constants.ENDPOINT + "policy_terms/WagonTermsConditionsEN.pdf"
        const val TERMS_URL_AR = Constants.ENDPOINT + "policy_terms/WagonTermsConditionsAR.pdf"
        const val PRIVACY_POLICY = Constants.ENDPOINT + "policy_terms/WagonPrivacyPolicy.pdf"
        const val DOC_VIEWER = "http://docs.google.com/gview?embedded=true&url="
        const val LINK_FB = "https://www.facebook.com/urwagon/"
        const val LINK_TWITTER = "https://twitter.com/urwagonn"
        const val LINK_YT = "https://www.youtube.com/user/urwagon"
        const val LINK_INSTA = "https://www.instagram.com/urwagon/"*/
    }

    class ShipmentStatus {
        companion object {
            const val REQUESTED = 1
            const val ACCEPTED = 2
            const val ARRIVED = 3
            const val IN_TRANSIT = 4
            const val REACHED = 5
            const val COMPLETE = 6
            const val CUSTOMER_CANCELLED = 7
            const val DRIVER_CANCELLED = 8
            const val SCHEDULED = 9
            const val SCHEDULED_CONFIRMED = 11
            const val OPEN_REQUEST = 10

        }
    }

    class Language {
        companion object {
            const val ENGLISH = "en"
            const val ARABIC = "ar"
        }
    }
}