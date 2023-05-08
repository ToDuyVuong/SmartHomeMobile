package vn.iotstart.smarthomemobile.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.adapter.ProductPopularIndexAdapter;
import vn.iotstart.smarthomemobile.api.RetrofitClient;
import vn.iotstart.smarthomemobile.adapter.CategoryAdapter;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.api.contants;
import vn.iotstart.smarthomemobile.model.Category;
import vn.iotstart.smarthomemobile.model.Product;

public class IndexActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterCategory;
    private RecyclerView.Adapter adapterProductPopularIndex;
    private RecyclerView recyclerViewCategoryList;
    private RecyclerView recyclerViewProductPopularIndexList;
    private TextView userName;
    private ImageView avatar;
    private List<Category> categoriesList;
    private  List<Product> productList;
    boolean isDoubleClicked=false;

    Handler handler=new Handler();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        userName = findViewById(R.id.userName);
        avatar = findViewById(R.id.avatar);

        userName.setText("Hi ToDuyVuong");
        String urlImage = "https://res.cloudinary.com/dh6r8je7l/image/upload/v1682876362/a/Avatar-trang-den_qlhteg.png";
        Glide.with(getApplicationContext()).load(urlImage).into(avatar);


        recyclerViewCategory();

        recyclerViewProductPopularIndex();

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
            public void onResponse(Call<List<Category>> call, retrofit2.Response<List<Category>> response) {
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
}
