package com.in10mServiceMan.ui.apis;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.in10mServiceMan.models.BookingHistoryResponse;
import com.in10mServiceMan.models.CivilIdPojo;
import com.in10mServiceMan.models.CompanyHomeCountResponse;
import com.in10mServiceMan.models.CompanyServicemanLocationResponse;
import com.in10mServiceMan.models.CountryPojo;
import com.in10mServiceMan.models.CustomerCompleteProfile;
import com.in10mServiceMan.models.CustomerCompleteProfileAfterUpdate;
import com.in10mServiceMan.models.DeviceTokenPojo;
import com.in10mServiceMan.models.EarningsResponse;
import com.in10mServiceMan.models.HomeCountResponse;
import com.in10mServiceMan.models.HomeService;
import com.in10mServiceMan.models.PortfolioPojo;
import com.in10mServiceMan.models.RequestLinkServiceWithServiceMan;
import com.in10mServiceMan.models.RequestRemoveSubServicesModel;
import com.in10mServiceMan.models.RequestReviewModel;
import com.in10mServiceMan.models.RequestUpdateDeviceUser;
import com.in10mServiceMan.models.RequestUpdateServiceMan;
import com.in10mServiceMan.models.RequestVerifyMobile;
import com.in10mServiceMan.models.RequestVerifyOTP;
import com.in10mServiceMan.models.ResponseServiceWithSubService;
import com.in10mServiceMan.models.ResponseVerifyMobile;
import com.in10mServiceMan.models.UpdateBookingStatus;
import com.in10mServiceMan.models.UpdateServemanLocation;
import com.in10mServiceMan.models.UpdateServemanWorkingStatus;
import com.in10mServiceMan.models.viewmodels.CommonApiResponse;
import com.in10mServiceMan.ui.accound_edit.addService.ServiceResponse;
import com.in10mServiceMan.ui.accound_edit.checkStripe.CheckStripeResponse;
import com.in10mServiceMan.ui.accound_edit.CheckStripeAccount;
import com.in10mServiceMan.ui.accound_edit.updateEstimate.UpdateEstimateResponse;
import com.in10mServiceMan.ui.accound_edit.updatePayment.UpdatePaymentResponse;
import com.in10mServiceMan.ui.accound_edit.policy_and_terms_api.PolicyAndTermsResponse;
import com.in10mServiceMan.ui.activities.company_pros.IAResponse;
import com.in10mServiceMan.ui.activities.company_pros.active.active_API.ActiveResponse;
import com.in10mServiceMan.ui.activities.company_pros.serviceman_details_API.ServicemanDetailsResponse;
import com.in10mServiceMan.ui.activities.company_registration.ServiceProviderAddResponse;
import com.in10mServiceMan.ui.activities.contact_us.ContactResponse;
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
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
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

public interface ApiInterface {
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
                                      @Field("services") String services,
                                      @Field("country_id") String country_id);


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
    @POST("auth/api/upload_idproof_images")
    Call<CivilIdPojo> uploadCivilID(@Header("Authorization") String header,
                                    @Part("user_id") RequestBody id,
                                    @Part MultipartBody.Part idproof_image1, @Part MultipartBody.Part idproof_image2);

    @Multipart
    @POST("auth/api/update_profile_pictures")
    Call<PortfolioPojo> addPortfolio(@Header("Authorization") String header,
                                     @Part("user_id") RequestBody id,
                                     @Part List<MultipartBody.Part> files);
//                                   @Part   MultipartBody.Part file

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
                                                         @Field("dob") String dob,
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

    @PUT("customer/api/customers/{id}/save_device_token")
    Call<DeviceTokenPojo> updateDeviceToken(@Path("id") int UserId, @Body JsonObject params);

    @GET("customer/api/get_country")
    Call<CountryPojo> getCountry();
}
