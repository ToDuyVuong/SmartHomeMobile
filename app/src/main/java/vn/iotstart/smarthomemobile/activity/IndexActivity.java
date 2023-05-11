package vn.iotstart.smarthomemobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import vn.iotstart.smarthomemobile.adapter.CategoryAdapter;
import vn.iotstart.smarthomemobile.adapter.CategoryToProductAdapter;
import vn.iotstart.smarthomemobile.adapter.ProductPopularIndexAdapter;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.Category;
import vn.iotstart.smarthomemobile.model.Product;

public class IndexActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterCategory;
    private RecyclerView.Adapter adapterProductPopularIndex;
    private RecyclerView.Adapter adapterProductAllIndex;
    private RecyclerView recyclerViewCategoryList;
    private RecyclerView recyclerViewProductPopularIndexList;
    private RecyclerView recyclerViewProductAll;
    private TextView userName;
    private ImageView avatar;

    FloatingActionButton buttonCart;
    private List<Category> categoriesList;
    private  List<Product> productList;
    boolean isDoubleClicked=false;

    Handler handler=new Handler();

    PreManager preManager;
    TextView textViewOderSmarthome;

    ImageView imageViewOder;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        userName = findViewById(R.id.userName);
        avatar = findViewById(R.id.avatar);
        preManager = new PreManager(this);
        imageViewOder = findViewById(R.id.imageViewOrderIndex);

        userName.setText("Hi "+ preManager.getUser().getUsername());
        String urlImage = preManager.getUser().getAvatar();
        Glide.with(getApplicationContext()).load(urlImage).into(avatar);


        recyclerViewCategory();

        recyclerViewProductPopularIndex();

        recyclerViewProductAllIndex();



        imageViewOder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, ListOrderActivity.class);
                startActivity(intent);
            }
        });


        buttonCart = findViewById(R.id.buttonCart);
        buttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, CartActivity.class);

                startActivity(intent);
            }
        });




        //view profile info
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfileInfo();
            }
        });


    }

    private void recyclerViewProductAllIndex() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewProductAll = findViewById(R.id.recyclerViewProductAll);
        recyclerViewProductAll.setLayoutManager(linearLayoutManager);
        ApiService.apiService.getProductPupularIndex().enqueue(new Callback<List<Product>>() {
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
                adapterProductAllIndex=new CategoryToProductAdapter(productList,IndexActivity.this);
                recyclerViewProductAll.setHasFixedSize(true);
                GridLayoutManager layoutManager=new GridLayoutManager(getApplicationContext(),2);
                recyclerViewProductAll.setLayoutManager(layoutManager);
                recyclerViewProductAll.setAdapter(adapterProductAllIndex);

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });
    }

    private void recyclerViewProductPopularIndex() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewProductPopularIndexList = findViewById(R.id.recyclerView2);
        recyclerViewProductPopularIndexList.setLayoutManager(linearLayoutManager);

        ApiService.apiService.getProductPupularIndex().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    productList=response.body();
                    adapterProductPopularIndex = new ProductPopularIndexAdapter(productList, IndexActivity.this);
                    recyclerViewProductPopularIndexList.setAdapter(adapterProductPopularIndex);




                }else{
                    int statusCode=response.code();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("logg",t.getMessage());
            }
        });
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recyclerViewCategory);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);


        ApiService.apiService.getCategoryAll().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    categoriesList=response.body();
                    adapterCategory = new CategoryAdapter(categoriesList, IndexActivity.this);
                    recyclerViewCategoryList.setAdapter(adapterCategory);
                }else{
                    int statusCode=response.code();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("logg",t.getMessage());
            }
        });
    }

    private void showProfileInfo(){
        startActivity(new Intent(this, ProfileActivity.class));
    }
}
