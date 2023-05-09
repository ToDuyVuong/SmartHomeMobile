package vn.iotstart.smarthomemobile.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.model.ProductImage;

public class ProductImagesAdapter extends RecyclerView.Adapter<ProductImagesAdapter.ProductImageViewHolder> {

    private List<ProductImage> mImages;
    private ViewPager2 mViewPager2;
    private Context mContext;

    public ProductImagesAdapter(List<ProductImage> images, ViewPager2 viewPager2, Context context) {
        mImages = images;
        mViewPager2 = viewPager2;
        mContext = context;
    }

    @NonNull
    @Override
    public ProductImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_image, parent, false);
        return new ProductImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductImageViewHolder holder, int position) {
        ProductImage image = mImages.get(position);
        Glide.with(mContext)
                .load(image.getImage())
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class ProductImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ProductImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}