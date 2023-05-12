package vn.iotstart.smarthomemobile.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;
import vn.iotstart.smarthomemobile.model.Cart;
import vn.iotstart.smarthomemobile.model.Category;
import vn.iotstart.smarthomemobile.model.Order;
import vn.iotstart.smarthomemobile.model.OrderItem;
import vn.iotstart.smarthomemobile.model.Product;
import vn.iotstart.smarthomemobile.model.User;
import vn.iotstart.smarthomemobile.request.OrderRequest;
import vn.iotstart.smarthomemobile.response.LoginResponse;

public interface ApiService {

<<<<<<< HEAD
    public static final String BASE_URL = "http://192.168.1.5:8085";
=======
    public static final String BASE_URL = "http://192.168.1.6:8085";
>>>>>>> 84e7f6740b470eac79e427a65d82538ea124b0af

    Gson gson = new GsonBuilder().setDateFormat("yyyy MM dd HH:mm:ss").create();

    public static final ApiService apiService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ApiService.class);


    //   User
    @POST("/user/register")
    Call<User> register(@Body User user);

    @POST("/user/login")
    Call<LoginResponse> login(@Body User user);

    @POST("/user/update")
    Call<User> update(@Body User user);

<<<<<<< HEAD
    //    @GET("/forgot/password")
//    Call<String> forgotPassword(@Query("id") String id);
=======
>>>>>>> 84e7f6740b470eac79e427a65d82538ea124b0af
    @GET("/forgot/password")
    Call<List<String>> forgotPassword(@Query("id") String id);

    @POST("/forgot/newpass")
    Call<String> newForgotPassword(@Query("id") String id, @Query("password") String password);




    // Category
    @GET("/category/getAll")
    Call<List<Category>> getCategoryAll();




    // Product
    @GET("product/getProductPupularIndex")
    Call<List<Product>> getProductPupularIndex();

    @GET("product/getProductByCategory")
    Call<List<Product>> getProductByCategoryId(@Query("categoryId") String categoryId);

    @GET("product/productDetail")
    Call<Product> getProductDetail(@Query("productId") String productId);

    @GET("product/searchProduct")
    Call<List<Product>> searchProduct(@Query("search") String search);

    @GET("product/getLatestProduct")
    Call<List<Product>> getLatestProduct();



    // Cart
    @POST("cart/addProductToCart")
    Call<List<Cart>> addProductToCart(@Body Cart cart);

    @POST("cart/minusProductToCart")
    Call<List<Cart>> minusProductToCart(@Body Cart cart);


    @GET("cart/view")
    Call<List<Cart>> getCartItems(@Query("id") String id);

    @DELETE("cart/remove/{cartId}")
    Call<Void> removeProductToCart(@Path("cartId") Integer cartId);




    // Order
    @POST("order/newOrder")
    Call<Order> newOrder(@Body OrderRequest orderRequest);

    @GET("order/listOrder")
    Call<List<Order>> getListOrder(@Query("id") String id);

    @GET("order/test")
    Call<Map<String, String>> getTest(@Query("id") String id);

    @GET("order/orderDetail")
    Call<List<OrderItem>> getOrderDetail(@Query("orderId") String orderId);

    @POST("order/cancelOrder")
    Call<Order> cancelOrder(@Body Order order);




}
