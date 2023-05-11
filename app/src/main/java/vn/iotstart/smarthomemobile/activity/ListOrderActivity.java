package vn.iotstart.smarthomemobile.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.PreManager;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.adapter.OrderAdapter;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.Order;

public class ListOrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private List<Order> listOrder;
    private PreManager preManager;
    TextView textViewUserName, textViewUserEmail;
    Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);

        recyclerView = findViewById(R.id.recyclerViewListOrder);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        preManager = new PreManager(this);

        Log.d("id", "preManager.getId() " + preManager.getId());

        String id = preManager.getId();

        textViewUserName = findViewById(R.id.textViewUserNameToListOrder);
        textViewUserEmail = findViewById(R.id.textViewUserEmailToListOrder);
        back = findViewById(R.id.buttonBackToListOrder);



        textViewUserName.setText("Hi " + preManager.getUser().getUsername());
        textViewUserEmail.setText("Email: "+ preManager.getUser().getEmail());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ApiService.apiService.getTest(id).enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()) {
                    String message = response.body().get("message");
                    Log.e("id", "onResponse: " + message);
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {

                Log.e("id", "onFailure: " + t.getMessage());

            }
        });

        ApiService.apiService.getListOrder(id).enqueue((new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                Log.e("id", "onResponse Order: " + response.body().toString());
                if (response.isSuccessful()) {
                    Log.e("id", "onResponse Order Ok: " + response.body().toString());
                    listOrder = response.body();
                    for (Order order : listOrder) {
                        Log.e("id", "onResponse Order: " + order.getOrderId() + "\n");

                    }
                    adapter = new OrderAdapter(ListOrderActivity.this, listOrder);
                    recyclerView.setAdapter(adapter);
                }else {
                    Log.e("id", "onResponse Order Fail: " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.e("id", "onFailure Order: " + t.getMessage());

            }
        }));



    }
}



//package vn.iotstart.smarthomemobile.activity;
//
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//import retrofit2.Response;
//import vn.iotstart.smarthomemobile.PreManager;
//import vn.iotstart.smarthomemobile.R;
//import vn.iotstart.smarthomemobile.adapter.OrderAdapter;
//import vn.iotstart.smarthomemobile.api.ApiService;
//import vn.iotstart.smarthomemobile.model.Cart;
//import vn.iotstart.smarthomemobile.model.Order;
//
//
//public class OrderActivity extends AppCompatActivity {
//
//
//    private RecyclerView.Adapter adapter;
//    private List<Order> listOrder;
//    PreManager preManager;
//    RecyclerView recyclerView;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_order);
//
//
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerView = findViewById(R.id.recyclerViewListOrder);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        anhXa();
//
//        ApiService.apiService.getListOrder(preManager.getId()).enqueue(new retrofit2.Callback<List<Order>>() {
//            @Override
//            public void onResponse(retrofit2.Call<List<Order>> call, Response<List<Order>> response) {
//                listOrder = response.body();
//                if(listOrder != null) {
//                    adapter = new OrderAdapter(OrderActivity.this, listOrder);
//                    recyclerView.setAdapter(adapter);
//                }
//
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<List<Order>> call, Throwable t) {
//
//            }
//        });
//
//
//    }
//
//    private void anhXa() {
//        preManager = new PreManager(this);
//    }
//}
