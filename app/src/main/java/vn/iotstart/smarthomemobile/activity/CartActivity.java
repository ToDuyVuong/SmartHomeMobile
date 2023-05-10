package vn.iotstart.smarthomemobile.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.PreManager;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.adapter.CartAdapter;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.Cart;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterCart;
    private  List<Cart> cartitems;
    PreManager preManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerView_cart);
        recyclerView.setLayoutManager(linearLayoutManager);

//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new CartAdapter(new ArrayList<>());
//        recyclerView.setAdapter(adapter);
        preManager = new PreManager(getApplicationContext());

        String id = preManager.getId();

        ApiService.apiService.getCartItems(id).enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                if (response.isSuccessful()) {
//                    adapter.setCartItems(response.body());
//                    adapter.notifyDataSetChanged();

                    cartitems=response.body();
                    adapterCart = new CartAdapter(cartitems, CartActivity.this);
                    recyclerView.setAdapter(adapterCart);
                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                // Handle error
            }
        });
    }
}

