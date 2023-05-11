package vn.iotstart.smarthomemobile.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.PreManager;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.adapter.OrderDetailAdapter;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.Cart;
import vn.iotstart.smarthomemobile.model.Order;
import vn.iotstart.smarthomemobile.model.OrderItem;

public class OrderDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView textViewTotalQuantity, textViewTotalPrice, textViewShippingFee, textViewDate;
    TextView textViewUserName, textViewAddress, textViewPhone, textViewNote, textViewOrderId, textViewStatus;
    Button buttonCanceled, buttonBack;
    private RecyclerView.Adapter adapter;
    private List<OrderItem> orderItemList;
    PreManager preManager;

    String quantity;
    String price;
    String orderId;
    String id;
    Order order;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerViewOrderDetail);
        recyclerView.setLayoutManager(linearLayoutManager);

        anhXa();
        bind();

        Log.e("ffff", order.toString());
        Log.e("ffff", order.getOrderId().toString());
        Log.e("ffff", order.getStatus().toString());

        ApiService.apiService.getOrderDetail(order.getOrderId().toString()).enqueue(new Callback<List<OrderItem>>() {
            @Override
            public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> response) {
                orderItemList = response.body();
                Log.e("ffff", "onResponse: " + orderItemList.toString());
                Log.e("ffff", "onResponse: " + orderItemList.get(0).getProduct().getName());
                Log.e("ffff", "onResponse size: " + orderItemList.size());
                adapter = new OrderDetailAdapter(orderItemList, getApplicationContext());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<OrderItem>> call, Throwable t) {
                Log.e("ffff", "onFailure: " + t.getMessage());
            }
        });


    }

    private void bind() {

        textViewUserName.setText(order.getUser().getUsername());
        textViewAddress.setText(order.getAddress());
        textViewPhone.setText(order.getPhone());
        textViewNote.setText(order.getNote());
        textViewTotalPrice.setText(order.getTotalPrice().toString() + " VNĐ");
        textViewTotalQuantity.setText(order.getTotalQuantity().toString() + " sản phẩm");
        textViewOrderId.setText(order.getOrderId().toString());
        textViewStatus.setText(order.getStatus().toString());
        textViewShippingFee.setText(order.getShipTo().toString());
        textViewDate.setText(order.getDate().toString());
//        recyclerView = findViewById(R.id.recyclerViewOrderDetail);

        buttonBack.setOnClickListener(v -> {
            finish();
        });

        buttonCanceled.setOnClickListener(v -> {
            finish();
        });
    }

    private void anhXa() {

        preManager = new PreManager(getApplicationContext());
        textViewUserName = findViewById(R.id.textViewUserNameToOrderDetail);
        textViewAddress = findViewById(R.id.textViewAddressToOrderDetail);
        textViewPhone = findViewById(R.id.textViewPhoneToOrderDetail);
        textViewNote = findViewById(R.id.textViewNoteToOrderDetail);
        textViewTotalPrice = findViewById(R.id.textViewTotalPriceToOrderDetail);
        textViewTotalQuantity = findViewById(R.id.textViewTotalQuantityToOrderDetail);
        buttonCanceled = findViewById(R.id.bottomCanceledOrderDetail);
        buttonBack = findViewById(R.id.bottomBackListOrder);
        textViewOrderId = findViewById(R.id.textViewOrderIdToOrderDetail);
        textViewStatus = findViewById(R.id.textViewStatusToOrderDetail);
        textViewShippingFee = findViewById(R.id.textViewShippingToOrderDetail);
        textViewDate = findViewById(R.id.textViewDateToOrderDetail);

        id = preManager.getId();
        orderId = (String) getIntent().getSerializableExtra("orderId");
        order = (Order) getIntent().getSerializableExtra("order");
    }
}
