package com.in10mServiceMan.ui.apis;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lenovo on 5/20/2018.
 */

public class APIClient {

//    private static final String BaseUrl = "http://52.47.100.139/in10m/in10m/public/";  //test
//    public static final String UserImage = "http://52.47.100.139/in10m/in10m/public/serviceprovider/api/get_servicemen_image/";//+phonenumber  //test

    //Live
    private static final String BaseUrl = "http://www.in10m.com/in10m/public/";
    public static final String UserImage = "http://www.in10m.com/in10m/public/serviceprovider/api/get_servicemen_image/";

    //Dev
//    private static final String BaseUrl = "http://34.224.101.230/in10m/public/";
//    public static final String UserImage = "http://34.224.101.230/in10m/public/serviceprovider/api/get_servicemen_image/";


    public static final String privacyPolicy = "https://in10m.com/in10mPrivacyPolicy.pdf";
    public static final String aboutUs = "http://www.in10m.com/in10m_about_serviceman.html";
    public static final String termsAndCondition = "https://in10m.com/in10mTermsandCondition.pdf";

    public static ApiInterface apiInterface = null;

    public static String Token = "";

    private static int isTokenSet = 0;

    public void setPublicAccessToken(String token) {
        Token = token;
        isTokenSet = 1;
        apiInterface = null;
    }

    public String getPublicAccessToken() {
        return Token;
    }

    public static ApiInterface getApiInterface() {

        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.connectTimeout(120, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(120, TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(120, TimeUnit.SECONDS);


        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpClientBuilder.addInterceptor(httpLoggingInterceptor);
        Retrofit retrofit;

        if (apiInterface == null && isTokenSet == 0) {

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
            apiInterface = retrofit.create(ApiInterface.class);
        } else if (isTokenSet == 1) {
            isTokenSet = 2;
            okhttpClientBuilder.addInterceptor(chain -> {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", "Bearer " + Token); // <-- this is the important line

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }).connectTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS);

            retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .client(okhttpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .build();
            apiInterface = retrofit.create(ApiInterface.class);
        }
        return apiInterface;
    }

    public void clearToken() {
        Token = "";
        isTokenSet = 0;
    }
}
