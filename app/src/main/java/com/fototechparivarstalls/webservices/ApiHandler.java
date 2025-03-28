package com.fototechparivarstalls.webservices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHandler {

    private static WebServices apiService;

    private static String authToken;  // Store the token here

    public static void setAuthToken(String token) {
        authToken = token;
    }


    public static WebServices getApiService() {

        if (apiService == null) {

            // Here a logging interceptor is created
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Interceptor to add the Authorization header
            Interceptor authInterceptor = chain -> {
                okhttp3.Request originalRequest = chain.request();

                if (authToken == null) {
                    return chain.proceed(originalRequest);  // No token, proceed without adding Authorization header
                }

                okhttp3.Request newRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer " + authToken)  // Add Bearer Token here
                        .build();
                return chain.proceed(newRequest);
            };

            // The logging and auth interceptors will be added to the HTTP client
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);
            httpClient.addInterceptor(authInterceptor);

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(httpClient.build())
                    .baseUrl(Constant.str_BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            apiService = retrofit.create(WebServices.class);
        }

        return apiService;
    }
}
