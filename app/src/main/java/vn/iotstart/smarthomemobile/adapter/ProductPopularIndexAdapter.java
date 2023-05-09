package vn.iotstart.smarthomemobile.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.activity.ProductDetailActivity;
import vn.iotstart.smarthomemobile.model.Product;

public class ProductPopularIndexAdapter extends RecyclerView.Adapter<ProductPopularIndexAdapter.ViewHolder> {

    List<Product> products;
    Context context;

    public ProductPopularIndexAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_popular_index, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(products.get(position).getName());
        holder.fee.setText(String.valueOf(products.get(position).getProductId()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(products.get(position).getImages().get(0).getImage(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(context)
                .load(products.get(position).getImages().get(0).getImage())
                .into(holder.image);


//        Toast.makeText(context, "Bạn đã chọn product" + holder.title.getText().toString(), Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(context, ProductViewPager2Activity.class);
//        intent.putExtra("productId", products.get(position).getProductId());
//        holder.itemView.getContext().startActivity(intent);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Bạn đã chọn product" + holder.title.getText().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("productId", products.get(position).getProductId().toString());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, fee;
        ImageView image;
        TextView addBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            fee = itemView.findViewById(R.id.fee);
            image = itemView.findViewById(R.id.pic);
            addBtn = itemView.findViewById(R.id.addBtn);
        }
    }
}
