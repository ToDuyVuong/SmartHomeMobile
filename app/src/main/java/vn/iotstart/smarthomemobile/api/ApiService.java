package vn.iotstart.smarthomemobile.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import vn.iotstart.smarthomemobile.model.Category;
import vn.iotstart.smarthomemobile.model.Product;
import vn.iotstart.smarthomemobile.model.User;

public interface ApiService {

    public static final String BASE_URL = "http://192.168.1.8:8085";

    Gson gson = new GsonBuilder().setDateFormat("yyyy MM dd HH:mm:ss").create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiService.class);


    //    @FormUrlEncoded
    @POST("/user/register")
    Call<User> register(@Body User user);


    //    @GET("/forgot/password")
//    Call<String> forgotPassword(@Query("id") String id);
    @GET("/forgot/password")
    Call<List<String>> forgotPassword(@Query("id") String id);

    @POST("/forgot/newpass")
    Call<String> newForgotPassword(@Query("id") String id, @Query("password") String password);

    @GET("/category/getAll")
    Call<List<Category>> getCategoryAll();

    @GET("product/getProductPupularIndex")
    Call<List<Product>> getProductPupularIndex();


}