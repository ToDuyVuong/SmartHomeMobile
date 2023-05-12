package vn.iotstart.smarthomemobile.adapter;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.bumptech.glide.Glide;

import vn.iotstart.smarthomemobile.PreManager;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.model.Product;

public class ProductAllIndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public List<Product> listProduct;
    Context context;

    public ProductAllIndexAdapter(List<Product> listProduct, Context context) {
        this.listProduct = listProduct;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_all_index, parent, false);
            return new ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progressbar, parent, false);
            return new Loading(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if(viewHolder instanceof ViewHolder){
            popLateItem((ViewHolder) viewHolder, position);
        }
        else if(viewHolder instanceof Loading){
            showLoadingView((Loading) viewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        return listProduct == null ? 0 : listProduct.size();
    }

    @Override
    public int getItemViewType(int position) {
        return listProduct.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private void popLateItem(ViewHolder holder, int position) {
        Product product = listProduct.get(position);
        holder.title.setText(product.getName());
        holder.fee.setText(String.valueOf(product.getPrice()));

        Glide.with(context)
                .load(product.getImages().get(0).getImage())
                .into(holder.image);
    }



    private  class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, fee;

        ImageView image;
        TextView addBtn;
        PreManager preManager;
        Context context;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            title = itemView.findViewById(R.id.titleProductAll);
            fee = itemView.findViewById(R.id.feeProductAll);
            image = itemView.findViewById(R.id.picProductAll);
            addBtn = itemView.findViewById(R.id.buttonAddProductAll);
            preManager = new PreManager(context);
        }
    }
    private class Loading extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public Loading(@NonNull View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }
    private void showLoadingView(Loading viewHolder, int position) {
        //ProgressBar would be displayed
    }

}
