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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.PreManager;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.activity.ProductDetailActivity;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.Cart;
import vn.iotstart.smarthomemobile.model.Product;
import vn.iotstart.smarthomemobile.model.User;

public class ProductPopularIndexAdapter extends RecyclerView.Adapter<ProductPopularIndexAdapter.ViewHolder> {

    List<Product> products;
    Context context;
//    PreManager preManager;


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
        holder.fee.setText(String.valueOf(products.get(position).getPrice()));

//        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(products.get(position).getImages().get(0).getImage(), "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(context)
                .load(products.get(position).getImages().get(0).getImage())
                .into(holder.image);


//        Toast.makeText(context, "Bạn đã chọn product" + holder.title.getText().toString(), Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(context, ProductViewPager2Activity.class);
//        intent.putExtra("productId", products.get(position).getProductId());
//        holder.itemView.getContext().startActivity(intent);

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setId(holder.preManager.getId());
                Cart cart = new Cart();
                cart.setUser(user); // currentUser là đối tượng User đang đăng nhập
                cart.setProduct(products.get(position));
                cart.setQuantity(1);
                cart.setSelected(false);

                ApiService.apiService.addProductToCart(cart).enqueue(new Callback<List<Cart>>() {
                    @Override
                    public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                        Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<List<Cart>> call, Throwable t) {

                    }
                });


            }});


        holder.image.setOnClickListener(new View.OnClickListener() {
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
        PreManager preManager;
        Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            title = itemView.findViewById(R.id.title);
            fee = itemView.findViewById(R.id.fee);
            image = itemView.findViewById(R.id.pic);
            addBtn = itemView.findViewById(R.id.buttonAdd);
            preManager = new PreManager(context);

        }
    }
}
