package vn.iotstart.smarthomemobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.PreManager;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.adapter.CategoryAdapter;
import vn.iotstart.smarthomemobile.adapter.ProductAllIndexAdapter;
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
    private List<Product> productList;
    boolean isDoubleClicked = false;

    Handler handler = new Handler();

    PreManager preManager;
    TextView textViewOderSmarthome;

    ImageView imageViewOder;
    List<Product> arrayListProduct = new ArrayList<>();
    boolean isLoading = false;
    LinearLayout buttonHome, buttonCategory, buttonProfile, buttonLogout;
    Button buttonSearch;
    EditText editTextSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        anhXa();

        recyclerViewCategory();

        recyclerViewProductPopularIndex();

        recyclerViewProductAllIndex();

        binding();
    }

    private void binding() {

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = editTextSearch.getText().toString();
                Intent intent = new Intent(IndexActivity.this, SearchProductActivity.class);
                intent.putExtra("search", search);
                startActivity(intent);
            }
        });
        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, IndexActivity.class);
                startActivity(intent);
            }
        });


        imageViewOder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndexActivity.this, IndexActivity.class);
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

    private void anhXa() {
        userName = findViewById(R.id.userName);
        avatar = findViewById(R.id.avatar);
        preManager = new PreManager(this);
        imageViewOder = findViewById(R.id.imageViewOrderIndex);
        buttonHome = findViewById(R.id.homeBtn);
        buttonProfile = findViewById(R.id.profileBtn);

        userName.setText("Hi " + preManager.getUser().getUsername());
        String urlImage = preManager.getUser().getAvatar();
        Glide.with(getApplicationContext()).load(urlImage).into(avatar);

        buttonSearch = findViewById(R.id.buttonSearch);
        editTextSearch = findViewById(R.id.editTextSearch);
    }

    private void initScrollListener() {
        recyclerViewProductAll.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == arrayListProduct.size() - 1) {
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        arrayListProduct.add(null);
        adapterProductAllIndex.notifyItemInserted(arrayListProduct.size() - 1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                arrayListProduct.remove(arrayListProduct.size() - 1);
                int scrollPosition = arrayListProduct.size();
                adapterProductAllIndex.notifyItemRemoved(scrollPosition);

                int currentSize = scrollPosition;
                int nextLimit = currentSize + 1;
                while (currentSize - 1 < nextLimit && currentSize - 1 < productList.size()) {
                    arrayListProduct.add(productList.get(currentSize - 1));
                    currentSize++;
                }
                if (arrayListProduct.size() % 2 != 0) {

                    arrayListProduct.add(productList.get(productList.size() - 1));
                }
                adapterProductAllIndex.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
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
                    if (productList.size() > 0) {
                        pupulateData(productList);
                        intiAdapter();
                        initScrollListener();
                        Log.e("ffffff", productList.get(0).toString());

                    }
                } catch (Exception e) {
                    Log.e("ffff", e.toString());
                }


            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
            }
        });
    }

    private void intiAdapter() {
        adapterProductAllIndex = new ProductAllIndexAdapter(arrayListProduct, IndexActivity.this);
        recyclerViewProductAll.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerViewProductAll.setLayoutManager(layoutManager);
        recyclerViewProductAll.setAdapter(adapterProductAllIndex);
    }

    private void pupulateData(List<Product> productList) {
        for (int i = 0; i < 2; i++) {
            arrayListProduct.add(productList.get(i));
        }
    }

    private void recyclerViewProductPopularIndex() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewProductPopularIndexList = findViewById(R.id.recyclerView2);
        recyclerViewProductPopularIndexList.setLayoutManager(linearLayoutManager);

        Log.e("ffffa", "recyclerViewProductPopularIndex");
        ApiService.apiService.getLatestProduct().enqueue(new Callback<List<Product>>() {

            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.e("ffffa", "recyclerViewProductPopularIndex22222");
                if (response.isSuccessful()) {
                    productList = response.body();
                    Log.e("fffflast", productList.get(0).toString());
                    adapterProductPopularIndex = new ProductPopularIndexAdapter(productList, IndexActivity.this);
                    recyclerViewProductPopularIndexList.setAdapter(adapterProductPopularIndex);


                } else {
                    int statusCode = response.code();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d("logg", t.getMessage());
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
                if (response.isSuccessful()) {
                    categoriesList = response.body();
                    adapterCategory = new CategoryAdapter(categoriesList, IndexActivity.this);
                    recyclerViewCategoryList.setAdapter(adapterCategory);
                } else {
                    int statusCode = response.code();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Log.d("logg", t.getMessage());
            }
        });
    }

    private void showProfileInfo() {
        startActivity(new Intent(this, EditProfileActivity.class));
    }
}
