package vn.iotstart.smarthomemobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import vn.iotstart.smarthomemobile.adapter.CartAdapter;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.Cart;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterCart;
    private List<Cart> cartitems;
    PreManager preManager;

    CheckBox checkboxSelectAll;
    RecyclerView recyclerViewCart;
    TextView textViewUsername;
    TextView textViewTotalPriceToCart;
    TextView textViewTotalQuantityToCart;
    TextView textViewSelectedProductCountTocCart;

    Button btnBack;
    Button btnOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerView_cart);
        recyclerView.setLayoutManager(linearLayoutManager);

        anhXa();

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

                    cartitems = response.body();
                    adapterCart = new CartAdapter(cartitems, CartActivity.this);
                    textViewUsername.setText("Xin Chao: " + preManager.getUser().getUsername());
                    recyclerView.setAdapter(adapterCart);
                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                // Handle error
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(CartActivity.this, IndexActivity.class);
//                startActivity(intent);
                finish();
            }
        });


// Set a listener for the "Select All" checkbox
        checkboxSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Iterate through all items in the RecyclerView and update their selection state
                int totalPrice = 0;
                int totalQuantity = 0;
                int selectedCount = 0;
                if (checkboxSelectAll.isChecked()) {
                    for (int i = 0; i < recyclerViewCart.getChildCount(); i++) {
                        View itemView = recyclerViewCart.getChildAt(i);
                        CheckBox checkboxPayment = itemView.findViewById(R.id.checkboxPayment);
                        checkboxPayment.setChecked(isChecked);
                        selectedCount += 1;
                        totalQuantity = totalQuantity + cartitems.get(i).getQuantity();
                        totalPrice = (int) (totalPrice + cartitems.get(i).getQuantity() * cartitems.get(i).getProduct().getPrice());

                    }
                } else {
                    for (int i = 0; i < recyclerViewCart.getChildCount(); i++) {
                        View itemView = recyclerViewCart.getChildAt(i);
                        CheckBox checkboxPayment = itemView.findViewById(R.id.checkboxPayment);
                        checkboxPayment.setChecked(isChecked);
                    }
                }
                textViewTotalPriceToCart.setText("Total Price: " + totalPrice + " VNĐ");
                textViewTotalQuantityToCart.setText("Total Quantity: " + totalQuantity);
                textViewSelectedProductCountTocCart.setText("Selected Product Count: " + selectedCount);

            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cartitems.size() == 0){
                    Toast.makeText(CartActivity.this, "Không có sản phẩm nào trong giỏ hàng", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(CartActivity.this, NewOrderActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void anhXa() {
        checkboxSelectAll = findViewById(R.id.checkboxSelectAllProductsToCart);
        recyclerViewCart = findViewById(R.id.recyclerView_cart);
        textViewUsername = findViewById(R.id.textViewUsernameToCart);
        textViewTotalPriceToCart = findViewById(R.id.textViewTotalPriceToCart);
        textViewTotalQuantityToCart = findViewById(R.id.textViewTotalQuantityToCart);
        textViewSelectedProductCountTocCart = findViewById(R.id.textViewSelectedProductCountTocCart);
        btnBack = findViewById(R.id.buttonBack);
        btnOrder = findViewById(R.id.buttonOrder);

    }
}


//package vn.iotstart.smarthomemobile.activity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import vn.iotstart.smarthomemobile.PreManager;
//import vn.iotstart.smarthomemobile.R;
//import vn.iotstart.smarthomemobile.adapter.CartAdapter;
//import vn.iotstart.smarthomemobile.api.ApiService;
//import vn.iotstart.smarthomemobile.model.Cart;
//
//public class CartActivity extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter adapterCart;
//    private List<Cart> cartitems;
//    PreManager preManager;
//
//    CheckBox checkboxSelectAll;
//    RecyclerView recyclerViewCart;
//    TextView textViewUsername;
//    TextView textViewTotalPriceToCart;
//    TextView textViewTotalQuantityToCart;
//    TextView textViewSelectedProductCountTocCart;
//
//    private int totalPrice = 0;
//    private int totalQuantity = 0;
//    private int selectedCount = 0;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cart);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerView = findViewById(R.id.recyclerView_cart);
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        anhXa();
//
//        preManager = new PreManager(getApplicationContext());
//        String id = preManager.getId();
//
//        ApiService.apiService.getCartItems(id).enqueue(new Callback<List<Cart>>() {
//            @Override
//            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
//                if (response.isSuccessful()) {
//                    cartitems = response.body();
//                    adapterCart = new CartAdapter(cartitems, CartActivity.this);
//                    textViewUsername.setText("Xin Chao: " + preManager.getId());
//                    recyclerView.setAdapter(adapterCart);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Cart>> call, Throwable t) {
//                // Handle error
//            }
//        });
//
//        // Set a listener for the "Select All" checkbox
//        checkboxSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                // Iterate through all items in the RecyclerView and update their selection state
//                totalPrice = 0;
//                totalQuantity = 0;
//                selectedCount = 0;
//
//                for (Cart cartItem : cartitems) {
//                    cartItem.setSelected(isChecked);
//                    if (isChecked) {
//                        totalQuantity += cartItem.getQuantity();
//                        totalPrice += cartItem.getQuantity() * cartItem.getProduct().getPrice();
//                    }
//                }
//
//                adapterCart.notifyDataSetChanged();
//                updateCartSummary();
//            }
//        });
//
//        adapterCart = new CartAdapter(cartitems, CartActivity.this);
//        recyclerView.setAdapter(adapterCart);
//    }
//
//    private void anhXa() {
//        checkboxSelectAll = findViewById(R.id.checkboxSelectAllProductsToCart);
//        recyclerViewCart = findViewById(R.id.recyclerView_cart);
//        textViewUsername = findViewById(R.id.textViewUsernameToCart);
//        textViewTotalPriceToCart = findViewById(R.id.textViewTotalPriceToCart);
//        textViewTotalQuantityToCart = findViewById(R.id.textViewTotalQuantityToCart);
//        textViewSelectedProductCountTocCart = findViewById(R.id.textViewSelectedProductCountTocCart);
//    }
//
//    public void onCheckboxPaymentClicked(int position, boolean isChecked) {
//        Cart cartItem = cartitems.get(position);
//        cartItem.setSelected(isChecked);
//
//        if (isChecked) {
//            totalQuantity += cartItem.getQuantity();
//            totalPrice += cartItem.getQuantity() * cartItem.getProduct().getPrice();
//            selectedCount++;
//        } else {
//            totalQuantity -= cartItem.getQuantity();
//            totalPrice -= cartItem.getQuantity() * cartItem.getProduct().getPrice();
//            selectedCount--;
//        }
//
//        adapterCart.notifyItemChanged(position);
//        updateCartSummary();
//    }
//
//    private void updateCartSummary() {
//        textViewTotalPriceToCart.setText("Total Price: " + totalPrice + " VNĐ");
//        textViewTotalQuantityToCart.setText("Total Quantity: " + totalQuantity);
//        textViewSelectedProductCountTocCart.setText("Selected Product Count: " + selectedCount);
//
//        // Update the "Select All" checkbox state based on the number of selected items
//        checkboxSelectAll.setChecked(selectedCount == cartitems.size());
//    }
//}


