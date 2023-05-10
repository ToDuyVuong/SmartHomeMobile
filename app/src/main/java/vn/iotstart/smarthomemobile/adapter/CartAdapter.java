package vn.iotstart.smarthomemobile.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.activity.ProductDetailActivity;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.Cart;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Cart> cartItems;
    Context context;


    public CartAdapter(List<Cart> cartItems, Context context) {
        this.cartItems = cartItems;
        this.context = context;
    }

    public void setCartItems(List<Cart> cartItems, Context context) {
        this.cartItems = cartItems;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bind(cartItems.get(position));

        Glide.with(context)
                .load(cartItems.get(position).getProduct().getImages().get(0).getImage())
                .into(holder.imageViewProduct);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewProduct;
        TextView textViewProductName;
        ImageButton buttonRemove;
        ImageButton buttonPlus;
        ImageButton buttonMinus;
        TextView textViewQuantity;
        TextView textViewPrice;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewProduct = itemView.findViewById(R.id.imageViewProductImageToCart);
            textViewProductName = itemView.findViewById(R.id.textViewProductNameToCart);
            buttonRemove = itemView.findViewById(R.id.buttonRemoveProductToCart);
            buttonPlus = itemView.findViewById(R.id.buttonPlus);
            buttonMinus = itemView.findViewById(R.id.buttonMinus);
            textViewQuantity = itemView.findViewById(R.id.textViewProductQuantityToCart);
            buttonMinus = itemView.findViewById(R.id.buttonMinusProductQuantityToCart);
            buttonPlus = itemView.findViewById(R.id.buttonPlusProductQuantityToCart);
            textViewPrice = itemView.findViewById(R.id.textViewProductPriceToCart);

            buttonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    int count = Integer.parseInt(textViewQuantity.getText().toString());
                    int maxQuantity = cartItems.get(position).getProduct().getQuantity();

                    if (count < maxQuantity) {
                        count++;
                        textViewQuantity.setText(String.valueOf(count));
                        cartItems.get(position).setQuantity(1);

                        // Update cart item on the server
                        ApiService.apiService.addProductToCart(cartItems.get(position)).enqueue(new Callback<List<Cart>>() {
                            @Override
                            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                                if (response.isSuccessful()) {
                                    Log.e("=====", "Update cart successfully");
                                    Toast.makeText(context, "Update to cart successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("=====", "Failed to update cart");
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Cart>> call, Throwable t) {
                                Log.e("=====", "Failed to update cart");
                            }
                        });
                    } else {
                        Toast.makeText(context, "Quantity must be less than " + maxQuantity, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            buttonMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int position = getAdapterPosition();
                    int count = Integer.parseInt(textViewQuantity.getText().toString());
                    if (count > 1) {
                        count--;
                        textViewQuantity.setText(String.valueOf(count));
                        // Update cart item on the server
                        ApiService.apiService.minusProductToCart(cartItems.get(position)).enqueue(new Callback<List<Cart>>() {
                            @Override
                            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                                if (response.isSuccessful()) {
                                    Log.e("=====", "Update cart successfully");
                                    Toast.makeText(context, "Update to cart successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("=====", "Failed to update cart");
                                }
                            }
                            @Override
                            public void onFailure(Call<List<Cart>> call, Throwable t) {
                                Log.e("=====", "Failed to update cart");
                            }
                        });
                    } else {
                        Toast.makeText(context, "Quantity must be greater than 0", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            buttonRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Integer cartId = cartItems.get(position).getCartId();
                    ApiService.apiService.removeProductToCart(cartId).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                cartItems.remove(position);
                                notifyItemRemoved(position);
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            // Handle error
                        }
                    });
                }
            });
        }

        public void bind(Cart cart) {
            textViewProductName.setText(cart.getProduct().getName());
            textViewQuantity.setText(String.valueOf(cart.getQuantity()));
            textViewPrice.setText(String.valueOf(cart.getProduct().getPrice()) + " VNĐ");

        }
    }
}

