package com.example.mypersistence.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mypersistence.ProductListActivity;
import com.example.mypersistence.R;
import com.example.mypersistence.entity.StoreEntity;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecyclerViewStoreAdapter extends RecyclerView.Adapter<RecyclerViewStoreAdapter.ViewHolder> {

    private ArrayList<StoreEntity> storeEntityArrayList;
    private Context context;
    private OnLongItemCustomListener onLongItemCustomListener;

    public RecyclerViewStoreAdapter(Context context, ArrayList<StoreEntity> storeEntityArrayList){
        this.context = context;
        this.storeEntityArrayList = storeEntityArrayList;
    }

    public interface OnLongItemCustomListener{
        void itemLongClicked(View v, int position);
    }

    public void setOnLongItemCustomListener(OnLongItemCustomListener onLongItemCustomListener){
        this.onLongItemCustomListener = onLongItemCustomListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_view_store_item, null, false);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);




        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setValues(new StoreEntity(
                storeEntityArrayList.get(position).get_id(),
                storeEntityArrayList.get(position).getName(),
                storeEntityArrayList.get(position).getAddress()));
    }


    @Override
    public int getItemCount() {
        return storeEntityArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnCreateContextMenuListener{
        TextView textViewStoreName, textViewStoreAddress;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.layoutStoreItem);
            textViewStoreName = (TextView) itemView.findViewById(R.id.textViewStoreName);
            textViewStoreAddress = (TextView) itemView.findViewById(R.id.textViewStoreAddress);

            linearLayout.setOnClickListener(this);
            linearLayout.setOnCreateContextMenuListener(this);

        }
        public void setValues(StoreEntity storeEntity){
            textViewStoreName.setText(storeEntity.getName());
            textViewStoreAddress.setText(storeEntity.getAddress());
        }


        @Override
        public void onClick(View v) {
            StoreEntity storeEntity = storeEntityArrayList.get(getAdapterPosition());
            Intent intent = new Intent(context, ProductListActivity.class);
            intent.putExtra("store_id", storeEntity.get_id());
            intent.putExtra("store_name", storeEntity.getName());
            context.startActivity(intent);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }
}
