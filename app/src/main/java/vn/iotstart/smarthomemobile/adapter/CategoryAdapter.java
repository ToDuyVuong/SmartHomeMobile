package vn.iotstart.smarthomemobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.activity.CategoryToProductActivity;
import vn.iotstart.smarthomemobile.activity.test.TestActivity;
import vn.iotstart.smarthomemobile.model.Category;
import vn.iotstart.smarthomemobile.model.Product;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {



    List<Category> category;
    Context context;
//    List<Product> productList;

    public CategoryAdapter(List<Category> category, Context context) {
        this.category = category;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_index, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String id= String.valueOf(category.get(position).getCategoryId());
        holder.categoryId.setText(id);
        holder.categoryName.setText(category.get(position).getName());
        String picUrl = category.get(position).getImage();
        holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(), R.drawable.category_background1));
        Glide.with(context)
                .load(category.get(position).getImage())
                .into(holder.categoryImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("CategoryAdapter", "onBindViewHolder: " + holder.categoryId.getText().toString());

                Intent intent = new Intent(holder.itemView.getContext(), CategoryToProductActivity.class);
                intent.putExtra("categoryId", holder.categoryId.getText().toString());
                intent.putExtra("categoryName", holder.categoryName.getText().toString());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName;
        ImageView categoryImage;
        TextView categoryId;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryId = itemView.findViewById(R.id.categoryId);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
