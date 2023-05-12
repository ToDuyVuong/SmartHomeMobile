package vn.iotstart.smarthomemobile.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.PreManager;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.adapter.CartAdapter;
import vn.iotstart.smarthomemobile.adapter.NewOrderAdapter;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.Cart;
import vn.iotstart.smarthomemobile.model.Order;
import vn.iotstart.smarthomemobile.model.OrderItem;
import vn.iotstart.smarthomemobile.model.Product;
import vn.iotstart.smarthomemobile.request.OrderRequest;

public class NewOrderActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView textViewTotalQuantity, textViewTotalPrice;
    EditText editTextUserName, editTextAddress, editTextPhone, editTextNote;
    Button buttonOrder, buttonBack;
    private RecyclerView.Adapter adapter;
    private List<Cart> cartItem;
    PreManager preManager;

    String quantity;
    String price;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerView_newOrder);
        recyclerView.setLayoutManager(linearLayoutManager);

        anhXa();

        String id = preManager.getId();

        getCartItem(id);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });

    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận đặt hàng");
        builder.setMessage("Bạn có chắc chắn muốn đặt hàng?");

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkout(quantity, price);
            }
        });

        builder.setNegativeButton("Hủy", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void checkout(String quantity, String price) {

        Order order = new Order();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date = formatter.format(new Date());
        String ship = "basic";
        order.setUser(preManager.getUser());
        order.setTotalPrice(Long.valueOf(price));
        order.setTotalQuantity(Integer.valueOf(quantity));
        order.setNote(editTextNote.getText().toString());
        order.setDate(date);
        order.setAddress(editTextPhone.getText().toString());
        order.setPhone(editTextPhone.getText().toString());
        order.setStatus(Order.Status.PENDING);
        order.setShipTo(ship);

        List<OrderItem> orderItemList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();
        List<Cart> cartItemList = new ArrayList<>();
        for (Cart cartItem : cartItem) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItemList.add(orderItem);

            Product product = cartItem.getProduct();
            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
            productList.add(product);

            cartItemList.add(cartItem);
        }

        apiOrder(order, orderItemList);

    }

    private void apiOrder(Order order, List<OrderItem> orderItemList) {

        OrderRequest orderRequest = new OrderRequest(order, orderItemList);

        ApiService.apiService.newOrder(orderRequest).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {

//                    finish();
                    Toast.makeText(getApplicationContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
//                    finish();
                    Intent intent = new Intent(NewOrderActivity.this, OrderDetailActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                // Handle error
            }
        });
    }

    private void getCartItem(String id) {

        ApiService.apiService.getCartItems(id).enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                if (response.isSuccessful()) {

                    cartItem = response.body();
                    adapter = new NewOrderAdapter(cartItem, NewOrderActivity.this);
                    recyclerView.setAdapter(adapter);

                    int totalPrice = 0;
                    int totalQuantity = 0;
                    for(int i = 0; i < cartItem.size(); i++) {
                        totalPrice += cartItem.get(i).getProduct().getPrice();
                        totalQuantity += cartItem.get(i).getQuantity();
                    }
                    quantity = String.valueOf(totalQuantity);
                    price = String.valueOf(totalPrice);
                    textViewTotalPrice.setText(String.valueOf(totalPrice) + "  VNĐ");
                    textViewTotalQuantity.setText(String.valueOf(totalQuantity) + " sản phẩm");
                    editTextUserName.setText(preManager.getUser().getUsername());
                    editTextAddress.setText(preManager.getUser().getAddress());
                    editTextPhone.setText(preManager.getUser().getPhoneNumber());

                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                // Handle error
            }
        });
    }

    private void anhXa() {

        preManager = new PreManager(getApplicationContext());
        editTextUserName = findViewById(R.id.editTextUserNameToNewOrder);
        editTextAddress = findViewById(R.id.editTextAddressToNewOrder);
        editTextPhone = findViewById(R.id.editTextPhoneToNewOrder);
        editTextNote = findViewById(R.id.editTextNoteToNewOrder);
        textViewTotalPrice = findViewById(R.id.textViewTotalPriceToNewOrder);
        textViewTotalQuantity = findViewById(R.id.textViewTotalQuantityToNewOrder);
        buttonOrder = findViewById(R.id.bottomOrderCreate);
        buttonBack = findViewById(R.id.bottomBackOrder);

    }
}
