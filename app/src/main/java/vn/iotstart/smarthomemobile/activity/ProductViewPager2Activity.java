package vn.iotstart.smarthomemobile.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.adapter.ProductImagesAdapter;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.Product;
import vn.iotstart.smarthomemobile.model.ProductImage;

public class ProductViewPager2Activity extends AppCompatActivity {

    private ViewPager2 mViewPager;
    private ProductImagesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_viewpager2);

        // Get the product data from the Intent
        Intent intent = getIntent();
        String productId = (String) intent.getSerializableExtra("productId");

        ApiService.apiService.getProductDetail(productId).enqueue(new retrofit2.Callback<Product>() {
            @Override
            public void onResponse(retrofit2.Call<Product> call, retrofit2.Response<Product> response) {
                if (response.isSuccessful()) {
                    Product product = response.body();
                    if (product != null) {
Log.e("ffff", "aaaaaaaa"+ product.toString());
                        // Set up the ViewPager2
                        mViewPager = findViewById(R.id.view_pager);
                        mAdapter = new ProductImagesAdapter(product.getImages(),  mViewPager, getApplicationContext());
                        mViewPager.setAdapter(mAdapter);



                        // Set up the product details
                        TextView tvName = findViewById(R.id.tv_product_name);
                        TextView tvPrice = findViewById(R.id.tv_product_price);
                        TextView tvDescription = findViewById(R.id.tv_product_description);

                        tvName.setText(product.getName());
                        tvPrice.setText(String.format(Locale.getDefault(), "$%,.2f", product.getPrice() / 100.0));
                        tvDescription.setText(product.getDescription());
                    }else {
                        Log.e("=====", "product is null");
                    }

                }
            }

            @Override
            public void onFailure(retrofit2.Call<Product> call, Throwable t) {

            }
        });



    }
}