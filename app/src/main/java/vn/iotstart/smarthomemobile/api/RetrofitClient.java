package vn.iotstart.smarthomemobile.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static RetrofitClient retrofitClient;
    private static Retrofit retrofit = null;



//    public static RetrofitClient getInstance() {
//        if (retrofitClient == null) {
//            retrofitClient = new RetrofitClient();
//        }
//        return retrofitClient;
//    }
//    public Retrofit getRetrofit(String url){
//        return changeUrl(url);
//    }
//    private Retrofit changeUrl(String url) {
//        retrofit = new Retrofit.Builder()
//                .baseUrl(url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        return retrofit;
//    }


    public static Retrofit getRetrofit(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }



}