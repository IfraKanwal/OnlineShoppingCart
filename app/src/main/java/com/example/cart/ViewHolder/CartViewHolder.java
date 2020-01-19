package com.example.cart.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.cart.Interface.ItemClickListner;
import com.example.cart.R;

public class
CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView textProductName,textProductPrice, textProductQuantity;
    private ItemClickListner itemClickListner;

    public CartViewHolder(View itemView) {
        super(itemView);

        textProductName=(TextView)itemView.findViewById(R.id.cart_product_name);
        textProductPrice=(TextView)itemView.findViewById(R.id.cart_product_price);
        textProductQuantity=(TextView)itemView.findViewById(R.id.cart_product_quantity);


    }

    public TextView getTextProductName() {
        return textProductName;
    }

    public void setTextProductName(TextView textProductName) {
        this.textProductName = textProductName;
    }

    public TextView getTextProductPrice() {
        return textProductPrice;
    }

    public void setTextProductPrice(TextView textProductPrice) {
        this.textProductPrice = textProductPrice;
    }

    public TextView getTextProductQuantity() {
        return textProductQuantity;
    }

    public void setTextProductQuantity(TextView textProductQuantity) {
        this.textProductQuantity = textProductQuantity;
    }

    public ItemClickListner getItemClickListner() {
        return itemClickListner;
    }

    @Override
    public void onClick(View v) {

        itemClickListner.onClick(v, getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}
