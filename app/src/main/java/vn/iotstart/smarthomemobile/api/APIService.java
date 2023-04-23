package vn.iotstart.smarthomemobile.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    public static final String BASE_URL="http://192.168.1.8:8085";


    Gson gson = new GsonBuilder().setDateFormat("yyyy MM dd HH:mm:ss").create();

    APIService apiSevice = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(APIService.class) ;


    @FormUrlEncoded
    @POST("/user/register")
    Call<ObjectCall> register(@Field("username") String username,
                              @Field("email") String email/*,
                              @Field("password") String password,
                              @Field("gender") String gender*/);
}
