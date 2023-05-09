package vn.iotstart.smarthomemobile.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;


import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.adapter.ProductImagesAdapter;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.Product;

public class ProductDetailActivity extends AppCompatActivity {

    Product product;
    private ViewPager2 mViewPager;
    private ProductImagesAdapter mAdapter;
//    TextView tvNameProduct, tvPrice, tvCount, tvDescription;
//    ImageView ivImage, ivPlus, ivMinus;
//
//    Button btnAddToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
//        anhXa();
        LoadProductDetail();
    }

    private void LoadProductDetail() {

//        int id = (int) getIntent().getSerializableExtra("id");
        Intent intent = getIntent();
        String productId = (String) intent.getSerializableExtra("productId");

        ApiService.apiService.getProductDetail(productId).enqueue(new retrofit2.Callback<Product>() {
            @Override
            public void onResponse(retrofit2.Call<Product> call, retrofit2.Response<Product> response) {
                if (response.isSuccessful()) {
                    Product product = response.body();
                    if (product != null) {
                        Log.e("ffff", "aaaaaaaa" + product.toString());
                        // Set up the ViewPager2
                        mViewPager = findViewById(R.id.view_pager_product_images);
                        mAdapter = new ProductImagesAdapter(product.getImages(), mViewPager, getApplicationContext());
                        mViewPager.setAdapter(mAdapter);


                        // Set up the product details
                        TextView tvName = findViewById(R.id.textViewProductName);
                        TextView tvPrice = findViewById(R.id.textViewProductPrice);
                        TextView tvDescription = findViewById(R.id.textViewProductDescription);

                        tvName.setText(product.getName());
                        tvPrice.setText(String.format(Locale.getDefault(), "$%,.2f", product.getPrice() / 100.0));
                        tvDescription.setText(product.getDescription());

                        TextView back = findViewById(R.id.textViewBack);
                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                    } else {
                        Log.e("=====", "product is null");
                    }

                }
            }

            @Override
            public void onFailure(retrofit2.Call<Product> call, Throwable t) {

            }
        });



//        ApiService.apiService.getProductDetail(String.valueOf(id)).enqueue(new Callback<Product>() {
//            @Override
//            public void onResponse(Call<Product> call, Response<Product> response) {
////                product = response.body().getResult();
//                product = response.body();
//                Log.e("=====", product.toString());
//                Log.e("=====", "Call successfully");
//                tvNameProduct.setText(product.getName());
//                tvPrice.setText(String.valueOf(product.getPrice()));
//                Glide.with(getApplicationContext()).load(product.getImages().get(0).getImage()).into(ivImage);
//                tvDescription.setText(product.getDescription());
//
//                tvBackHome.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent =new Intent(ProductDetailActivity.this, CategoryToProductActivity.class);
////                        intent.putExtra("categoryId", product.getCategory().getCategoryId().toString());
////                        intent.putExtra("categoryName", product.getCategory().getName().toString());
////                        startActivity(intent);
//                        finish();
//
//
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Call<Product> call, Throwable t) {
//                Log.e("=====", "Call fail");
//                Log.e("=====", t.getMessage());
//
//
//
//            }
//        });
    }

    private void anhXa() {
//        tvNameProduct =findViewById(R.id.tvNameProduct);
//        tvPrice = findViewById(R.id.textViewProductPrice);
//        tvCount = findViewById(R.id.tvCount);
//        tvDescription = findViewById(R.id.textViewProductDescription);
////        ivImage = findViewById(R.id.ivImage);
//        ivPlus  = findViewById(R.id.imageViewPlus);
//        ivMinus = findViewById(R.id.imageViewMinus);
//        btnAddToCart = findViewById(R.id.buttonAddToCart);
    }
}
