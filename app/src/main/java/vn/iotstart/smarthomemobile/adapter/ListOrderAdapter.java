package vn.iotstart.smarthomemobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.activity.CategoryToProductActivity;
import vn.iotstart.smarthomemobile.activity.OrderDetailActivity;
import vn.iotstart.smarthomemobile.model.Order;

public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.ViewHolder> {
    private List<Order> listOrder;
    private Context context;

    private static final int REQUEST_ORDER_DETAIL = 1;

    public ListOrderAdapter(Context context, List<Order> listOrder) {
        this.listOrder = listOrder;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listOrder.get(position));
        Order currentOrder = listOrder.get(position);
        holder.itemView.setTag(currentOrder);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), OrderDetailActivity.class);
                intent.putExtra("orderId", holder.txtOrderId.getText().toString());
                intent.putExtra("order", currentOrder);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderId, txtPrice, txtQuantity;
        TextView txDate, txtStatus;
        TextView txtAddress, txtPhone;
        TextView tvNote, tvShipTo;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderId = itemView.findViewById(R.id.textViewOrderId);
            txtPrice = itemView.findViewById(R.id.textViewOrderTotalPrice);
            txtQuantity = itemView.findViewById(R.id.textViewOrderTotalQuantity);
            txDate = itemView.findViewById(R.id.textViewOrderDate);
            txtStatus = itemView.findViewById(R.id.textViewOrderStatus);
            txtAddress = itemView.findViewById(R.id.textViewOrderAddress);
            txtPhone = itemView.findViewById(R.id.textViewOrderPhone);
            tvNote = itemView.findViewById(R.id.textViewOrderNote);
            tvShipTo = itemView.findViewById(R.id.textViewOrderShipTo);
        }

        public void bind(Order order) {
            txtOrderId.setText("Id: " + order.getOrderId().toString());
            txtPrice.setText(String.valueOf(order.getTotalPrice()) + " VNĐ");
            txtQuantity.setText(String.valueOf("Quantity: " + order.getTotalQuantity()));
            txDate.setText("Date: " + order.getDate());
            txtStatus.setText("Status: " + order.getStatus().toString());
            txtAddress.setText("Address: " + order.getAddress());
            txtPhone.setText("Phone: " + order.getPhone());
            tvNote.setText("Note: " + order.getNote());
            tvShipTo.setText("Shipping: " + order.getShipTo());
        }
    }
}




//package vn.iotstart.smarthomemobile.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.net.CookieHandler;
//import java.util.List;
//
//import vn.iotstart.smarthomemobile.R;
//import vn.iotstart.smarthomemobile.model.Cart;
//import vn.iotstart.smarthomemobile.model.Order;
//
//public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.viewHolder> {
//
//
//    List<Order> listOrder;
//    private Context context;
//
//
//    public OrderAdapter(Context context,List<Order> listOrder) {
//        this.listOrder = listOrder;
//        this.context = context;
//    }
//
//
//    @NonNull
//    @Override
//    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
//        return new viewHolder(view);
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
//
//        holder.bind(listOrder.get(position));
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return listOrder.size();
//    }
//
//    public class viewHolder extends RecyclerView.ViewHolder {
//
//        TextView txtOrderId, txtPrice, txtQuantity;
//        TextView txDate, txtStatus;
//        TextView txtAddress, txtPhone;
//        TextView tvNote, tvShipTo;
//        public viewHolder(@NonNull View itemView) {
//            super(itemView);
//            anhXa();
//        }
//
//        private void anhXa() {
//            txtOrderId = itemView.findViewById(R.id.textViewOrderId);
//            txtPrice = itemView.findViewById(R.id.textViewOrderTotalPrice);
//            txtQuantity = itemView.findViewById(R.id.textViewOrderTotalQuantity);
//            txDate = itemView.findViewById(R.id.textViewOrderDate);
//            txtStatus = itemView.findViewById(R.id.textViewOrderStatus);
//            txtAddress = itemView.findViewById(R.id.textViewOrderAddress);
//            txtPhone = itemView.findViewById(R.id.textViewOrderPhone);
//            tvNote = itemView.findViewById(R.id.textViewOrderNote);
//            tvShipTo = itemView.findViewById(R.id.textViewOrderShipTo);
//        }
//
//        public void bind(Order order) {
//            txtOrderId.setText(order.getOrderId());
//            txtPrice.setText(Math.toIntExact(order.getTotalPrice()));
//            txtQuantity.setText(order.getTotalQuantity());
//            txDate.setText(order.getDate());
//            txtStatus.setText(order.getStatus().toString());
//            txtAddress.setText(order.getAddress());
//            txtPhone.setText(order.getPhone());
//            tvNote.setText(order.getNote());
//            tvShipTo.setText(order.getShipTo());
//        }
//    }
//}
