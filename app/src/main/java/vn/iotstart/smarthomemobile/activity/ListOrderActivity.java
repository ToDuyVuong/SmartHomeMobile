package vn.iotstart.smarthomemobile.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.PreManager;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.adapter.ListOrderAdapter;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.Order;

public class ListOrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListOrderAdapter adapter;
    private List<Order> listOrder;
    private PreManager preManager;
    TextView textViewUserName, textViewUserEmail;
    Button back;
    TextView textViewOderStatusPending, textViewOderStatusProcessing, textViewOderStatusDelivering;
    TextView textViewOderStatusDelivered, textViewOderStatusCancel , textViewOderStatusAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);

        recyclerView = findViewById(R.id.recyclerViewListOrder);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        anhXa();



        Log.d("id", "preManager.getId() " + preManager.getId());

        String id = preManager.getId();





        textViewUserName.setText("Hi: " + preManager.getUser().getUsername());
        textViewUserEmail.setText("Email: "+ preManager.getUser().getEmail());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ApiService.apiService.getListOrder(id).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                int totalPending = 0;
                int totalProcessing = 0;
                int totalDelivering = 0;
                int totalDelivered = 0;
                int totalCancel = 0;
                int totalAll = 0;
                if (response.isSuccessful()) {
                    Toast.makeText(ListOrderActivity.this, "List Order For You", Toast.LENGTH_SHORT).show();
                    Log.e("id", "onResponse Order Ok: " + response.body().toString());
                    listOrder = response.body();
                    for (Order order : listOrder) {
                        totalAll++;
                        switch (order.getStatus()) {
                            case PENDING:
                                totalPending++;
                                break;
                            case PROCESSING:
                                totalProcessing++;
                                break;
                            case DELIVERING:
                                totalDelivering++;
                                break;
                            case DELIVERED:
                                totalDelivered++;
                                break;
                            case CANCELED:
                                totalCancel++;
                                break;
                        }
                    }


                    adapter = new ListOrderAdapter(ListOrderActivity.this, listOrder);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(ListOrderActivity.this, "List Order For You Is Null", Toast.LENGTH_SHORT).show();
                    Log.e("id", "onResponse Order Fail: " + response.body().toString());
                }

                bind(totalPending, totalProcessing, totalDelivering, totalDelivered, totalCancel, totalAll);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(ListOrderActivity.this, "Failed to fetch orders", Toast.LENGTH_SHORT).show();
                Log.e("id", "onFailure: " + t.getMessage());
            }
        });



//        ApiService.apiService.getListOrder(id).enqueue((new Callback<List<Order>>() {
//            @Override
//            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
//               int totalPending = 0;
//                int totalProcessing = 0;
//                int totalDelivering = 0;
//                int totalDelivered = 0;
//                int totalCancel = 0;
//                int totalAll = 0;
//                if (response.isSuccessful()) {
//                    Toast.makeText(ListOrderActivity.this, "List Order For You", Toast.LENGTH_SHORT).show();
//                    Log.e("id", "onResponse Order Ok: " + response.body().toString());
//                    listOrder = response.body();
//                    for (Order order : listOrder) {
//                        totalAll++;
//                        Switch (order.getStatus()) {
//                            case "Pending":
//                                totalPending++;
//                                break;
//                            case "Processing":
//                                totalProcessing++;
//                                break;
//                            case "Delivering":
//                                totalDelivering++;
//                                break;
//                            case "Delivered":
//                                totalDelivered++;
//                                break;
//                            case "Cancel":
//                                totalCancel++;
//                                break;
//                        }
//
//                    }
//                    adapter = new OrderAdapter(ListOrderActivity.this, listOrder);
//                    recyclerView.setAdapter(adapter);
//                }else {
//                    Toast.makeText(ListOrderActivity.this, "List Order For You Is Null", Toast.LENGTH_SHORT).show();
//                    Log.e("id", "onResponse Order Fail: " + response.body().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Order>> call, Throwable t) {
//                Log.e("id", "onFailure Order: " + t.getMessage());
//
//            }
//        }));



    }

    private void bind(int totalPending, int totalProcessing, int totalDelivering, int totalDelivered, int totalCancel, int totalAll) {
        textViewOderStatusPending.setText("Total Order Pending: " + totalPending);
        textViewOderStatusProcessing.setText("Total Order Processing: " + totalProcessing);
        textViewOderStatusDelivering.setText("Total Order Delivering: " + totalDelivering);
        textViewOderStatusDelivered.setText("Total Order Delivered: " + totalDelivered);
        textViewOderStatusCancel.setText("Total Order Cancel: " + totalCancel);
        textViewOderStatusAll.setText("Total Order All: " + totalAll);
    }

    private void anhXa() {

        preManager = new PreManager(this);
        textViewUserName = findViewById(R.id.textViewUserNameToListOrder);
        textViewUserEmail = findViewById(R.id.textViewUserEmailToListOrder);
        back = findViewById(R.id.buttonBackToListOrder);
        textViewOderStatusPending = findViewById(R.id.textViewTotalListOrderStatusPending);
        textViewOderStatusProcessing = findViewById(R.id.textViewTotalListOrderStatusProcessing);
        textViewOderStatusDelivering = findViewById(R.id.textViewTotalListOrderStatusDelivering);
        textViewOderStatusDelivered = findViewById(R.id.textViewTotalListOrderStatusDelivered);
        textViewOderStatusCancel = findViewById(R.id.textViewTotalListOrderStatusCanceled);
        textViewOderStatusAll = findViewById(R.id.textViewTotalListOrderStatusAll);
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
