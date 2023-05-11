package vn.iotstart.smarthomemobile.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

        buttonCanceled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancelConfirmationDialog();
            }
        });


    }

    private void showCancelConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận hủy đơn hàng");
        builder.setMessage("Bạn có chắc chắn muốn hủy đơn hàng?");

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelOrder();
            }
        });

        builder.setNegativeButton("Hủy", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void cancelOrder() {

        order.setStatus(Order.Status.CANCELED);
        buttonCanceled.setEnabled(false);
        Toast.makeText(this, "Đơn hàng đã được hủy", Toast.LENGTH_SHORT).show();

        ApiService.apiService.cancelOrder(order).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Log.e("ffff", "onResponse: " + response.body().toString());
                reloadPage();

            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.e("ffff", "onFailure: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Lỗi hủy đơn hàng", Toast.LENGTH_SHORT).show();
                buttonCanceled.setEnabled(true);
            }
        });

    }

    // Tai lai trang sau khi huy don hang
    private void reloadPage() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED); // Set the result as canceled
        super.onBackPressed(); // Continue with the default back button behavior
    }

    private void bind() {

        textViewUserName.setText(order.getUser().getUsername());
        textViewAddress.setText(order.getAddress());
        textViewPhone.setText(order.getPhone());
        textViewNote.setText(order.getNote());
        textViewTotalPrice.setText(order.getTotalPrice().toString() + " VNĐ");
        textViewTotalQuantity.setText(order.getTotalQuantity().toString() + " sản phẩm");
        textViewOrderId.setText("Id Order: #" + order.getOrderId().toString());
        textViewStatus.setText(order.getStatus().toString());
        textViewShippingFee.setText(order.getShipTo().toString());
        textViewDate.setText(order.getDate().toString());
//        recyclerView = findViewById(R.id.recyclerViewOrderDetail);

        buttonBack.setOnClickListener(v -> {
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

        if (!order.getStatus().equals(Order.Status.PENDING)) {
            buttonCanceled.setEnabled(false);
        }
    }
}
