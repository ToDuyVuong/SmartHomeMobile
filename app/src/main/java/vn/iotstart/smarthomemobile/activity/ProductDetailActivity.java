package vn.iotstart.smarthomemobile.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.PreManager;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.adapter.ProductImagesAdapter;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.Cart;
import vn.iotstart.smarthomemobile.model.Product;
import vn.iotstart.smarthomemobile.model.User;

public class ProductDetailActivity extends AppCompatActivity {

    Product product;
    private ViewPager2 mViewPager;
    private ProductImagesAdapter mAdapter;

    TextView tvQuantity;
    TextView tvName;
    TextView tvPrice;
    TextView tvDescription;
    TextView tvBack;
    ImageView Plus;
    ImageView Minus;
    TextView tvCount;
    PreManager preManager;
    Button btnAddToCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        anhXa();
        LoadProductDetail();
    }

    private void LoadProductDetail() {

        Intent intent = getIntent();
        String productId = (String) intent.getSerializableExtra("productId");

        ApiService.apiService.getProductDetail(productId).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful()) {
                    Product product = response.body();
                    if (product != null) {
                        Log.e("ffff", "aaaaaaaa" + product.toString());
                        // Set up the ViewPager2
                        mViewPager = findViewById(R.id.view_pager_product_images);
                        mAdapter = new ProductImagesAdapter(product.getImages(), mViewPager, getApplicationContext());
                        mViewPager.setAdapter(mAdapter);

                        tvName.setText(product.getName());
                        tvPrice.setText(String.valueOf(product.getPrice()) + " VNĐ");
                        tvDescription.setText(product.getDescription());
                        tvQuantity.setText("Quantity: " + String.valueOf(product.getQuantity()));

                        Plus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int count = Integer.parseInt(tvCount.getText().toString());
                                if (count < product.getQuantity()) {
                                    count++;
                                    tvCount.setText(String.valueOf(count));
                                }else {
                                    Toast.makeText(ProductDetailActivity.this, "Quantity must be less than " + product.getQuantity(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        Minus.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int count = Integer.parseInt(tvCount.getText().toString());
                                if (count > 1) {
                                    count--;
                                    tvCount.setText(String.valueOf(count));
                                }else {
                                    Toast.makeText(ProductDetailActivity.this, "Quantity must be greater than 0", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        tvBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });

                        btnAddToCart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView textViewCount = findViewById(R.id.textViewCount);
                                int count = Integer.parseInt(textViewCount.getText().toString());
//                                int count = Integer.parseInt(tvCount.getText().toString());
                                if (count > 0) {
                                    addProductToCart(count, product);
                                   }else {
                                    Toast.makeText(ProductDetailActivity.this, "Quantity must be greater than 0", Toast.LENGTH_SHORT).show();
                                }
                            }


                        });


                    } else {
                        Log.e("=====", "product is null");
                    }

                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });


    }

    private void addProductToCart(int count, Product product) {

        User user = new User();
        user.setId(preManager.getId());
        Cart cart = new Cart();
        cart.setUser(user); // currentUser là đối tượng User đang đăng nhập
        cart.setProduct(product);
        cart.setQuantity(count);

        ApiService.apiService.addCart(cart).enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                Log.e("=====", "add cart successfully");
                finish();
//                notify();
                Toast.makeText(ProductDetailActivity.this, "Add to cart successfully", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                Log.e("=====", "add cart failed");
            }
        });
    }

    private void anhXa() {

        tvQuantity = findViewById(R.id.textViewQuantity);
        tvName = findViewById(R.id.textViewProductName);
        tvPrice = findViewById(R.id.textViewProductPrice);
        tvDescription = findViewById(R.id.textViewProductDescription);
        tvBack = findViewById(R.id.textViewBack);
        Plus = findViewById(R.id.imageViewPlus);
        Minus = findViewById(R.id.imageViewMinus);
        tvCount = findViewById(R.id.textViewCount);
        preManager = new PreManager(getApplicationContext());
        btnAddToCart = findViewById(R.id.buttonAddToCart);
    }
}
