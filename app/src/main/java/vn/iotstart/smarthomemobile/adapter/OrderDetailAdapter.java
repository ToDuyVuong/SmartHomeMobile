package vn.iotstart.smarthomemobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.model.Order;
import vn.iotstart.smarthomemobile.model.OrderItem;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>{

    private List<OrderItem> listOrderItem;
    static Context context;

    public OrderDetailAdapter( List<OrderItem> listOrderItem, Context context) {
        this.listOrderItem = listOrderItem;
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
        holder.bind(listOrderItem.get(position));

    }

    @Override
    public int getItemCount() {
        return listOrderItem.size();
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

        public void bind(OrderItem orderItem) {
            textViewProductName.setText(orderItem.getProduct().getName());
            textViewQuantity.setText(String.valueOf(orderItem.getQuantity()));
            textViewPrice.setText(String.valueOf(orderItem.getPrice()));
            Glide.with(context)
                    .load(orderItem.getProduct().getImages().get(0).getImage())
                    .into(imageViewProduct);
        }
    }
}
