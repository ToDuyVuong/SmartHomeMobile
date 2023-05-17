package vn.iotstart.smarthomemobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.PreManager;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.adapter.ListOrderAdapter;
import vn.iotstart.smarthomemobile.adapter.ProductPopularIndexAdapter;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.Product;

public class SearchProductActivity extends AppCompatActivity {

    List<Product> listProduct;
    RecyclerView recyclerView;
    ProductPopularIndexAdapter adapter;
    String search;
    LinearLayout home, cart, account;
    ImageView avatar;
    PreManager preManager;
    FloatingActionButton buttonCartProfile;
    private TextView userName;
    EditText editTextSearch;
    Button buttonSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);


        anhXa();
        bindingData();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        ApiService.apiService.searchProduct(search).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                try{
                    listProduct = response.body();
                    Log.e("TAG", "onResponse: " + listProduct.get(0).getName());

                    adapter = new ProductPopularIndexAdapter(listProduct, SearchProductActivity.this);
                    recyclerView.setAdapter(adapter);
                }
                catch (Exception e){
                    Toast.makeText(SearchProductActivity.this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(SearchProductActivity.this, "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }

    private void bindingData() {

        home.setOnClickListener(v -> {
            finish();
        });

        buttonCartProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchProductActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchProductActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });


        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchProductActivity.this, SearchProductActivity.class);
                intent.putExtra("search", editTextSearch.getText().toString());
                startActivity(intent);
            }
        });

        preManager = new PreManager(this);
        Glide.with(this).load(preManager.getUser().getAvatar()).into(avatar);
        userName.setText("Hi: "+preManager.getUser().getUsername());
        editTextSearch.setText(search);


    }

    private void anhXa() {
        recyclerView = findViewById(R.id.recyclerViewSearchProduct);
        search = getIntent().getStringExtra("search");
        home = findViewById(R.id.homeBtn);
        userName = findViewById(R.id.userName);
        avatar = findViewById(R.id.avatar);
        editTextSearch = findViewById(R.id.editTextSearch);
        account = findViewById(R.id.profileBtn);
        buttonCartProfile = findViewById(R.id.buttonCart);
        home = findViewById(R.id.homeBtn);
        buttonSearch = findViewById(R.id.buttonSearch);
    }
}
