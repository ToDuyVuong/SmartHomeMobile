package vn.iotstart.smarthomemobile.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;
import vn.iotstart.smarthomemobile.model.Cart;
import vn.iotstart.smarthomemobile.model.Category;
import vn.iotstart.smarthomemobile.model.Product;
import vn.iotstart.smarthomemobile.model.User;
import vn.iotstart.smarthomemobile.response.LoginResponse;

public interface ApiService {

    public static final String BASE_URL = "http://192.168.1.8:8085";

    Gson gson = new GsonBuilder().setDateFormat("yyyy MM dd HH:mm:ss").create();

    public static final ApiService apiService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiService.class);


    //    @FormUrlEncoded
    @POST("/user/register")
    Call<User> register(@Body User user);

    @POST("/user/login")
    Call<LoginResponse> login(@Body User user);

    @GET("/forgot/password")
    Call<List<String>> forgotPassword(@Query("id") String id);

    @POST("/forgot/newpass")
    Call<String> newForgotPassword(@Query("id") String id, @Query("password") String password);

    @GET("/category/getAll")
    Call<List<Category>> getCategoryAll();

    @GET("product/getProductPupularIndex")
    Call<List<Product>> getProductPupularIndex();

    @GET("product/getProductByCategory")
    Call<List<Product>> getProductByCategoryId(@Query("categoryId") String categoryId);

    @GET("product/productDetail")
    Call<Product> getProductDetail(@Query("productId") String productId);

    @POST("cart/add")
    Call<List<Cart>> addCart(@Body Cart cart);



}
