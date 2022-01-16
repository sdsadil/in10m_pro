package com.in10mServiceMan.ui.apis;


import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.in10mServiceMan.Models.BookingHistoryResponse;
import com.in10mServiceMan.Models.CompanyHomeCountResponse;
import com.in10mServiceMan.Models.CompanyServicemanLocationResponse;
import com.in10mServiceMan.Models.CustomerCompleteProfile;
import com.in10mServiceMan.Models.CustomerCompleteProfileAfterUpdate;
import com.in10mServiceMan.Models.EarningsResponse;
import com.in10mServiceMan.Models.HomeCountResponse;
import com.in10mServiceMan.Models.HomeService;
import com.in10mServiceMan.Models.RequestLinkServiceWithServiceMan;
import com.in10mServiceMan.Models.RequestRemoveSubServicesModel;
import com.in10mServiceMan.Models.RequestReviewModel;
import com.in10mServiceMan.Models.RequestUpdateDeviceUser;
import com.in10mServiceMan.Models.RequestUpdateServiceMan;
import com.in10mServiceMan.Models.RequestVerifyMobile;
import com.in10mServiceMan.Models.RequestVerifyOTP;
import com.in10mServiceMan.Models.ResponseServiceWithSubService;
import com.in10mServiceMan.Models.ResponseVerifyMobile;
import com.in10mServiceMan.Models.UpdateBookingStatus;
import com.in10mServiceMan.Models.UpdateServemanLocation;
import com.in10mServiceMan.Models.UpdateServemanWorkingStatus;
import com.in10mServiceMan.Models.ViewModels.CommonApiResponse;
import com.in10mServiceMan.ui.accound_edit.AddService.ServiceResponse;
import com.in10mServiceMan.ui.accound_edit.CheckStripe.CheckStripeResponse;
import com.in10mServiceMan.ui.accound_edit.CheckStripeAccount;
import com.in10mServiceMan.ui.accound_edit.UpdateEstimate.UpdateEstimateResponse;
import com.in10mServiceMan.ui.accound_edit.UpdatePayment.UpdatePaymentResponse;
import com.in10mServiceMan.ui.accound_edit.policy_and_terms_api.PolicyAndTermsResponse;
import com.in10mServiceMan.ui.activities.company_pros.IAResponse;
import com.in10mServiceMan.ui.activities.company_pros.active.active_API.ActiveResponse;
import com.in10mServiceMan.ui.activities.company_pros.serviceman_details_API.ServicemanDetailsResponse;
import com.in10mServiceMan.ui.activities.company_registration.ServiceProviderAddResponse;
import com.in10mServiceMan.ui.activities.contact_us.ContactResponse;
import com.in10mServiceMan.ui.activities.my_bookings.CompanyServiceHistoryResponse;
import com.in10mServiceMan.ui.activities.my_bookings.ServiceHistoryResponse;
import com.in10mServiceMan.ui.activities.my_bookings.invoice_details_API.InvoiceDetailsResponse;
import com.in10mServiceMan.ui.activities.payment.PaymentInitilizeResponse;
import com.in10mServiceMan.ui.activities.profile.ImageUpdateResponse;
import com.in10mServiceMan.ui.activities.rating.ReviewsResponse;
import com.in10mServiceMan.ui.activities.services.ServicesResponse;
import com.in10mServiceMan.ui.activities.sign_in.LinkSendResponse;
import com.in10mServiceMan.ui.activities.sign_in.LoginResponse;
import com.in10mServiceMan.ui.activities.signup.SignupOneResponse;
import com.in10mServiceMan.ui.activities.signup.SignupThreeResponse;
import com.in10mServiceMan.ui.activities.signup.SignupstepTwoResponse;
import com.in10mServiceMan.ui.activities.signup.StatesResponse;
import com.in10mServiceMan.ui.complete_profile_api.CompleteProfileResponse;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lenovo on 5/20/2018.
 */

public class LoginAPI {

    //    private static final String BaseUrl = "http://52.47.100.139/in10m/in10m/public/";  //test
//    public static final String UserImage = "http://52.47.100.139/in10m/in10m/public/serviceprovider/api/get_servicemen_image/";//+phonenumber  //test
    public static final String privacyPolicy = "https://in10m.com/in10mPrivacyPolicy.pdf";
    public static final String aboutUs = "http://www.in10m.com/in10m_about_serviceman.html";
    public static final String termsAndCondition = "https://in10m.com/in10mTermsandCondition.pdf";
    private static final String BaseUrl = "http://www.in10m.com/in10m/public/";  //test
    public static final String UserImage = "http://www.in10m.com/in10m/public/serviceprovider/api/get_servicemen_image/";//+phonenumber  //test
    public static LoginService loginService = null;

    public static String TOkenn = "";

    private static int isTokenSet = 0;

    public void setPublicAccessToken(String token) {
        TOkenn = token;
        isTokenSet = 1;
        loginService = null;
    }

    public String getPublicAccessToken() {
        return TOkenn;
    }

    public static LoginService loginUser() {

        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.connectTimeout(120, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(120, TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(120, TimeUnit.SECONDS);


        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(httpLoggingInterceptor);
        Retrofit retrofit;

        if (loginService == null && isTokenSet == 0) {

            // Log.i("i'm here",TOkenn);

           /* OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(loggingInterceptor)
                    .connectTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS);*/


            retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .client(okhttpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .build();
            loginService = retrofit.create(LoginService.class);
        } else if (isTokenSet == 1) {
            isTokenSet = 2;
            okhttpClientBuilder.addInterceptor(chain -> {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", "Bearer " + TOkenn); // <-- this is the important line

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }).connectTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS);

            retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .client(okhttpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .build();
            loginService = retrofit.create(LoginService.class);
        }
        return loginService;
    }

    public void clearToken() {
        TOkenn = "";
        isTokenSet = 0;
    }

    public interface LoginService {
        @GET("service/api/services")
        Call<HomeService> getServices();

        @GET("booking/api/service_invoice/{bookingId}")
        Call<InvoiceDetailsResponse> getInvoiceDetails(@Path("bookingId") int bookingId, @Query("user_id") int UserId, @Query("user_type") int userType);

        @FormUrlEncoded
        @POST("serviceman/api/add_service")
        Call<ServiceResponse> addNewServiceman(@Field("serviceman_id") int servicemanId, @Field("services") String services);

        @GET("serviceman/api/check_stripe_account")
        Call<CheckStripeResponse> checkStripe(@Query("serviceman_id") String servicemanId);

        @FormUrlEncoded
        @POST("settings/api/accept_privacy_policy")
        Call<PolicyAndTermsResponse> privacyPolicy(@Field("user_id") int userId, @Field("user_type") int userType);

        @FormUrlEncoded
        @POST("settings/api/accept_terms_condition")
        Call<PolicyAndTermsResponse> termsConditions(@Field("user_id") int userId, @Field("user_type") int userType);

        @GET("serviceprovider/api/get_serviceproviderinfo/{serviceManId}")
        Call<ServicemanDetailsResponse> getServiceManInfo(@Path("serviceManId") int serviceManId);

        @FormUrlEncoded
        @POST("serviceman/api/enable_company_crew")
        Call<IAResponse> activate(@Field("company_id") int companyId, @Field("company_pro_id") int companyProId);

        @FormUrlEncoded
        @POST("serviceman/api/disable_company_crew")
        Call<IAResponse> deactivate(@Field("company_id") int companyId, @Field("company_pro_id") int companyProId);

        @FormUrlEncoded
        @POST("serviceman/api/delete_company_crew")
        Call<IAResponse> delete(@Field("company_id") int companyId, @Field("company_pro_id") int companyProId);

        @FormUrlEncoded
        @POST("serviceman/api/update_payment_method")
        Call<UpdatePaymentResponse> updatePayment(@Field("serviceman_id") int servicemanId, @Field("payment_method") String paymentMethod);

        @FormUrlEncoded
        @POST("serviceman/api/update_free_estimate")
        Call<UpdateEstimateResponse> updateEstimate(@Field("serviceman_id") int servicemanId,
                                                    @Field("free_estimate") String freeEstimate,
                                                    @Field("estimation_fee") String estimateFee);

        @POST("serviceprovider/api/verify_mobile")
        Call<ResponseVerifyMobile> postVerifyMobile(@Body() RequestVerifyMobile verifyMobile);

        @POST("serviceprovider/api/verify_otp")
        Call<ResponseVerifyMobile> postOtpMobile(@Body() RequestVerifyOTP verifyOtp);

        @POST("serviceprovider/api/update_serviceprovider")
        Call<CustomerCompleteProfileAfterUpdate> updateServiceManProfile(@Body() RequestUpdateServiceMan requestUpdateServiceMan);

        @POST("serviceprovider/api/link_service_with_provider")
        Call<Void> linkServiceAndSubService(@Body() ArrayList<RequestLinkServiceWithServiceMan> requestUpdateServiceMan);

        @GET("serviceprovider/api/get_serviceprovider_profile/{UserId}")
        Call<CustomerCompleteProfile> getCompleteProfile(@Path("UserId") int UserId);

        @GET("serviceprovider/api/get_serviceprovider_profile/{UserId}")
        Call<CompleteProfileResponse> completeProfile(@Path("UserId") int UserId);

        @GET("serviceman/api/my_crew/{CompanyId}")
        Call<ActiveResponse> getActiveCompanyDetails(@Path("CompanyId") int CompanyId, @Query("crew_status") int crewStatus);

        @POST("serviceprovider/api/update_location")
        Call<JsonElement> updateServiceManLocation(@Body UpdateServemanLocation servemanLocation);

        @POST("serviceprovider/api/working_status")
        Call<JsonElement> updateServiceWorkingStatus(@Body UpdateServemanWorkingStatus status);

        @POST("serviceprovider/api/working_status")
        Call<CommonApiResponse> updateServiceWorkingStatusWithHeader(@Body UpdateServemanWorkingStatus status);

        @POST("booking/api/booking_status")
        Call<Void> updateBookingStatus(@Body UpdateBookingStatus status);

        @GET("serviceprovider/api/get_servicemen_service_count/{UserId}")
        Call<HomeCountResponse> getCountDashBoard(@Path("UserId") int UserId);

        @GET("serviceman/api/finished_jobs")
        Call<CompanyHomeCountResponse> getFinishedJobCount(@Query("user_id") int UserId);

        @GET("booking/api/get_serviceprovider_booking/{UserId}")
        Call<BookingHistoryResponse> getHistory(@Path("UserId") int UserId);

        @GET("booking/api/get_serviceprovider_booking/{UserId}")
        Call<BookingHistoryResponse> getHistoryWithHeader(@Header("Authorization") String header, @Path("UserId") int UserId);

        @GET("serviceman/api/service_history")
        Call<ServiceHistoryResponse> getServiceHistory(@Header("Authorization") String header,
                                                       @Query("user_id") int UserId,
                                                       @Query("status") String status,
                                                       @Query("count") int count,
                                                       @Query("page") int page
        );

        @GET("serviceman/api/company_service_history")
        Call<ServiceHistoryResponse> getCompanyServiceHistory(@Header("Authorization") String header,
                                                              @Query("user_id") int UserId,
                                                              @Query("company_id") int CompanyId,
                                                              @Query("count") int count,
                                                              @Query("page") int page
        );

        @POST("serviceproviderrating/api/ratingCustomer")
        Call<JsonElement> updateReview(@Body RequestReviewModel reviewModel);

        @GET("customer/api/get_profile/{custId}")
        Call<CustomerCompleteProfileAfterUpdate> getCustomerProfile(@Path("custId") int custId);

        @GET("service/api/service/{serviceId}")
        Call<ResponseServiceWithSubService> getServiceDetails(@Path("serviceId") int serviceId);

        @GET("serviceprovider/api/get_servicemen_service/{UserId}")
        Call<HomeService> getExistingServiceDetails(@Path("UserId") int UserId);

        @GET("serviceprovider/api/get_servicemen_service/{UserId}")
        Call<HomeService> getExistingServiceDetailsWithHeader(@Header("Authorization") String header, @Path("UserId") int UserId);

        @GET("serviceprovider/api/get_servicemen_service/{UserId}")
        Call<ServicesResponse> getExistingServiceDetailsWithHeaderAndExperience(@Header("Authorization") String header, @Path("UserId") int UserId);


        @POST("serviceprovider/api/remove_servicemen_service")
        Call<JsonElement> removeSubServices(@Body RequestRemoveSubServicesModel removeSubServicesModel);

        @POST("notification/api/register_device")
        Call<Void> saveDeviceId(@Body RequestUpdateDeviceUser requestUpdateDeviceUser);
        // @PUT("customer/api/update_customer")
        // Call<Void> updateUser(@Body() RequestUpdateUser requestUpdateUser);

        @GET("api/country_states?country_id=118")
        Call<StatesResponse> getStates();


        @FormUrlEncoded
        @POST("auth/api/register_step1")
        Call<SignupOneResponse> SignupOne(@Field("type") int type,
                                          @Field("fname") String fname,
                                          @Field("lname") String lname,
                                          @Field("dob") String dob,
                                          @Field("address1") String street,
                                          @Field("address2") String suite,
                                          @Field("city") String city,
                                          @Field("state") String state,
                                          @Field("country") String country,
                                          @Field("zipcode") String zipcode,
                                          @Field("email") String email,
                                          @Field("country_code") String country_code,
                                          @Field("contact_no") String contact_no,
                                          @Field("password") String password,
                                          @Field("services") String services);


        @FormUrlEncoded
        @POST("auth/api/company_register_step1")
        Call<SignupOneResponse> SignupOneCompany(@Field("type") int type,
                                                 @Field("fname") String fname,
                                                 @Field("lname") String lname,
                                                 @Field("dob") String dob,
                                                 @Field("address1") String street,
                                                 @Field("address2") String suite,
                                                 @Field("city") String city,
                                                 @Field("state") String state,
                                                 @Field("country") String country,
                                                 @Field("zipcode") String zipcode,
                                                 @Field("email") String email,
                                                 @Field("country_code") String country_code,
                                                 @Field("contact_no") String contact_no,
                                                 @Field("password") String password,
                                                 @Field("services") String services,
                                                 @Field("company_name") String company_name);

        @FormUrlEncoded
        @POST("auth/api/verify_otp")
        Call<SignupOneResponse> verifyOtp(@Header("Authorization") String header,
                                          @Field("otp") String otp,
                                          @Field("mobile") String mobile,
                                          @Field("email") String email);


        @FormUrlEncoded
        @POST("auth/api/forgot_password")
        Call<LinkSendResponse> sendResetPasswordLink(@Field("email") String email);

        @FormUrlEncoded
        @POST("auth/api/verify_user")
        Call<LoginResponse> userLogin(@Field("email") String email, @Field("password") String password);

        @Multipart
        @POST("auth/api/register_step2")
        Call<SignupstepTwoResponse> profilePictureUpdate(@Header("Authorization") String header,
                                                         @Part("user_id") RequestBody id,
                                                         @Part("state_id") RequestBody stateId,
                                                         @Part("certificate") RequestBody certificate,
                                                         @Part MultipartBody.Part file);

        @Multipart
        @POST("auth/api/register_step2")
        Call<SignupstepTwoResponse> companyProfilePictureUpdate(@Header("Authorization") String header,
                                                                @Part("user_id") RequestBody id,
                                                                @Part MultipartBody.Part file);


        @FormUrlEncoded
        @POST("auth/api/register_step3")
        Call<SignupThreeResponse> registrationLevelThreeCash(@Header("Authorization") String header,
                                                             @Field("user_id") String userId,
                                                             @Field("email") String email,
                                                             @Field("free_estimate") String free_estimate,
                                                             @Field("estimation_fee") String estimation_fee,
                                                             @Field("payment_type") String payout_type);

        @FormUrlEncoded
        @POST("auth/api/register_step3")
        Call<SignupThreeResponse> registrationLevelBankPay(@Header("Authorization") String header,
                                                           @Field("user_id") String userId,
                                                           @Field("email") String email,
                                                           @Field("free_estimate") String free_estimate,
                                                           @Field("estimation_fee") String estimation_fee,
                                                           @Field("payment_type") String payout_type,
                                                           @Field("account_number") String accountNo,
                                                           @Field("routing_number") String routingNo,
                                                           @Field("ssn") String SSN,
                                                           @Field("payout_type") String payment_type,
                                                           @Field("address1") String street,
                                                           @Field("address2") String suite,
                                                           @Field("city") String city,
                                                           @Field("state") String state,
                                                           @Field("country") String country,
                                                           @Field("zipcode") String zipcode);

        @FormUrlEncoded
        @POST("auth/api/register_step3")
        Call<SignupThreeResponse> registrationLevelDebitCard(@Header("Authorization") String header,
                                                             @Field("user_id") String userId,
                                                             @Field("email") String email,
                                                             @Field("free_estimate") String free_estimate,
                                                             @Field("estimation_fee") String estimation_fee,
                                                             @Field("payment_type") String payout_type,
                                                             @Field("expiry_month") String expiryMonth,
                                                             @Field("expiry_year") String expiryYear,
                                                             @Field("number") String number,
                                                             @Field("payout_type") String payment_type,
                                                             @Field("address1") String street,
                                                             @Field("address2") String suite,
                                                             @Field("city") String city,
                                                             @Field("state") String state,
                                                             @Field("country") String country,
                                                             @Field("ssn") String SSN,
                                                             @Field("zipcode") String zipcode);


        @Multipart
        @POST("serviceman/api/update_serviceman_image")
        Call<ImageUpdateResponse> UpdateServiceManProfilePicture(@Header("Authorization") String header,
                                                                 @Part("serviceman_id") RequestBody id,
                                                                 @Part MultipartBody.Part file);


        @GET("serviceman/api/serviceman_total_earnings")
        Call<EarningsResponse> getServicemanEarnings(@Header("Authorization") String header, @Query("serviceman_id") String servicemanId);


        @FormUrlEncoded
        @POST("auth/api/company_register_step4")
        Call<ServiceProviderAddResponse> addServiceman(@Header("Authorization") String header,
                                                       @Field("user_id") String id,
                                                       @Field("service_providers") String list);

        @FormUrlEncoded
        @POST("serviceman/api/add_company_crew")
        Call<ServiceProviderAddResponse> addServicemanExtra(@Header("Authorization") String header,
                                                            @Field("user_id") String id,
                                                            @Field("service_providers") String list);

        @FormUrlEncoded
        @POST("serviceman/api/payment_initialize")
        Call<PaymentInitilizeResponse> paymentInitilize(@Header("Authorization") String header,
                                                        @Field("booking_id") String bookingId,
                                                        @Field("total_amount") String totalAmount,
                                                        @Field("payment_type") String paymentType,
                                                        @Field("service_man_id") String serviemanId,
                                                        @Field("description") String workDescription);

        @GET("serviceman/api/serviceman_reviews")
        Call<ReviewsResponse> getOwnReviews(@Header("Authorization") String header, @Query("serviceman_id") String servicemanId);//

        @GET("serviceman/api/check_stripe_account")
        Call<CheckStripeAccount> checkStripAccount(@Header("Authorization") String header, @Query("serviceman_id") String servicemanId);

        @FormUrlEncoded
        @POST("support/api/support_query")
        Call<ContactResponse> putQueries(@Header("Authorization") String header,
                                         @Field("issue") String issue,
                                         @Field("user_name") String name,
                                         @Field("user_id") String id);

        @GET("serviceman/api/service_men_locations")
        Call<CompanyServicemanLocationResponse> getServicemanLocation(@Header("Authorization") String header,
                                                                      @Query("user_id") String userID,
                                                                      @Query("company_id") String companyID);

        @FormUrlEncoded
        @POST("serviceman/api/create_stripe_account")
        Call<SignupThreeResponse> createOnlinePaymentAccountBank(@Header("Authorization") String header,
                                                                 @Field("user_id") String userId,
                                                                 @Field("email") String email,
                                                                 @Field("free_estimate") String free_estimate,
                                                                 @Field("estimation_fee") String estimation_fee,
                                                                 @Field("payment_type") String payout_type,
                                                                 @Field("account_number") String accountNo,
                                                                 @Field("routing_number") String routingNo,
                                                                 @Field("ssn") String SSN,
                                                                 @Field("payout_type") String payment_type,
                                                                 @Field("address1") String street,
                                                                 @Field("address2") String suite,
                                                                 @Field("city") String city,
                                                                 @Field("state") String state,
                                                                 @Field("country") String country,
                                                                 @Field("zipcode") String zipcode);

        @FormUrlEncoded
        @POST("serviceman/api/create_stripe_account")
        Call<SignupThreeResponse> createOnlinePaymentAccountCard(@Header("Authorization") String header,
                                                                 @Field("user_id") String userId,
                                                                 @Field("email") String email,
                                                                 @Field("free_estimate") String free_estimate,
                                                                 @Field("estimation_fee") String estimation_fee,
                                                                 @Field("payment_type") String payout_type,
                                                                 @Field("expiry_month") String expiryMonth,
                                                                 @Field("expiry_year") String expiryYear,
                                                                 @Field("number") String number,
                                                                 @Field("payout_type") String payment_type,
                                                                 @Field("address1") String street,
                                                                 @Field("address2") String suite,
                                                                 @Field("city") String city,
                                                                 @Field("state") String state,
                                                                 @Field("country") String country,
                                                                 @Field("ssn") String SSN,
                                                                 @Field("zipcode") String zipcode);
    }


}
