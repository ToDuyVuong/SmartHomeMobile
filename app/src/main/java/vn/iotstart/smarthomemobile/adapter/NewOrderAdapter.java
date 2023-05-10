package vn.iotstart.smarthomemobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.model.Cart;

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.ViewHolder> {

    private List<Cart> cartItems;
    static Context context;


    public NewOrderAdapter(List<Cart> cartItems, Context context) {
        this.cartItems = cartItems;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_order_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(cartItems.get(position));

//        Glide.with(context)
//                .load(cartItems.get(position).getProduct().getImages().get(0).getImage())
//                .into(holder.imageViewProduct);
    }

    @Override
    public int getItemCount()  {
        return cartItems.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewProduct;
        TextView textViewProductName;
        TextView textViewQuantity;
        TextView textViewPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            anhXa(itemView);


        }

        private void anhXa(View itemView) {
            imageViewProduct = itemView.findViewById(R.id.imageViewProductImageToNewOrder);
            textViewProductName = itemView.findViewById(R.id.textViewProductNameToNewOrder);
            textViewQuantity = itemView.findViewById(R.id.textViewProductQuantityToNewOrder);
            textViewPrice = itemView.findViewById(R.id.textViewProductPriceToNewOrder);
        }


        public void bind(Cart cart) {

            textViewProductName.setText(cart.getProduct().getName());
            textViewQuantity.setText("Số lượng: " + String.valueOf(cart.getQuantity()));
            textViewPrice.setText(String.valueOf(cart.getProduct().getPrice()) + " VNĐ");
            Glide.with(context)
                    .load(cart.getProduct().getImages().get(0).getImage())
                    .into(imageViewProduct);

        }
    }
}
