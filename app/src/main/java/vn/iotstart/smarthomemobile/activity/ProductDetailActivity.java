package vn.iotstart.smarthomemobile.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.Product;

public class ProductDetailActivity extends AppCompatActivity {

    Product product;
    TextView tvNameProduct, tvPrice, tvCount, tvDescription;
    ImageView ivImage, ivPlus, ivMinus;
    TextView tvBackHome;
    Button btnAddToCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        anhXa();
        LoadProductDetail();
    }

    private void LoadProductDetail() {

        int id = (int) getIntent().getSerializableExtra("id");
        ApiService.apiService.getProductDetail(String.valueOf(id)).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
//                product = response.body().getResult();
                product = response.body();
                Log.e("=====", product.toString());
                Log.e("=====", "Call successfully");
                tvNameProduct.setText(product.getName());
                tvPrice.setText(String.valueOf(product.getPrice()));
                Glide.with(getApplicationContext()).load(product.getImages().get(0).getImage()).into(ivImage);
                tvDescription.setText(product.getDescription());

                tvBackHome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent =new Intent(ProductDetailActivity.this, CategoryToProductActivity.class);
                        intent.putExtra("categoryId", product.getCategory().getCategoryId().toString());
                        intent.putExtra("categoryName", product.getCategory().getName().toString());
                        startActivity(intent);
                        finish();


                    }
                });
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Log.e("=====", "Call fail");
                Log.e("=====", t.getMessage());



            }
        });
    }

    private void anhXa() {
        tvNameProduct =findViewById(R.id.tvNameProduct);
        tvPrice = findViewById(R.id.tvPrice);
        tvCount = findViewById(R.id.tvCount);
        tvDescription = findViewById(R.id.tvDescription);
        ivImage = findViewById(R.id.ivImage);
        ivPlus  = findViewById(R.id.ivPlus);
        ivMinus = findViewById(R.id.ivMinus);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        tvBackHome = findViewById(R.id.tvBackHome);
    }
}
