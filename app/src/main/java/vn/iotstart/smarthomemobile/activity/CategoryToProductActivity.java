package vn.iotstart.smarthomemobile.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.MainActivity;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.adapter.CategoryToProductAdapter;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.Product;

public class CategoryToProductActivity extends AppCompatActivity {
    TextView tvBackHome;
    String categoryId;
    String categoryName;

    TextView tv_product;

    RecyclerView rcProduct;

    List<Product> productList;

    CategoryToProductAdapter productCategoryAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_to_product);
        anhXa();
        categoryId = (String) getIntent().getSerializableExtra("categoryId");
        categoryName =(String) getIntent().getSerializableExtra("categoryName");
        LoadProduct();
        Log.e("ffff", categoryId + "====================");
        tvBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void LoadProduct() {
        tv_product.setText("All Product of Category: "+ categoryName);
        ApiService.apiService.getProductByCategoryId(categoryId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                productList = response.body();
                try {
                    Log.e("ffff", productList.get(0).toString());
                }
                catch (Exception e)
                {
                    Log.e("ffff", e.toString());
                }
                productCategoryAdapter=new CategoryToProductAdapter(productList,CategoryToProductActivity.this);
                rcProduct.setHasFixedSize(true);
                GridLayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),2);
                rcProduct.setLayoutManager(layoutManager);
                rcProduct.setAdapter(productCategoryAdapter);

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });


    }

    private void anhXa() {
        tvBackHome = findViewById(R.id.tvBackHome);
        rcProduct = findViewById(R.id.rc_product);
        tv_product = findViewById(R.id.tv_product);
    }
}
