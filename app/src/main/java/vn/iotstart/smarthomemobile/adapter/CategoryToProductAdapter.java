package vn.iotstart.smarthomemobile.adapter;


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

import vn.iotstart.smarthomemobile.MainActivity;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.activity.ProductDetailActivity;
import vn.iotstart.smarthomemobile.model.Product;

public class CategoryToProductAdapter extends RecyclerView.Adapter<CategoryToProductAdapter.MyViewHolder> {
    List<Product> array;
    Context context;

    public CategoryToProductAdapter(List<Product> array, Context context) {
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_to_product, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView images;
        public TextView tenSp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.image_product);
            tenSp = itemView.findViewById(R.id.tvNameProduct);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = array.get(position);
        holder.tenSp.setText(product.getName());

//        Glide.with(context)
//                .load(product.getImages().get(0))
//                .into(holder.images);

        Glide.with(context)
                .load(product.getImages().get(0).getImage())
                .into(holder.images);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Bạn đã chọn product" + holder.tenSp.getText().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("productId", product.getProductId().toString());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return array == null ? 0 : array.size();
    }
}
