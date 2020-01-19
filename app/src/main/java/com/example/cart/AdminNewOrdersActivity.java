package com.example.cart;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cart.Model.AdminOrders;
import com.example.cart.Model.Products;
import com.example.cart.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AdminNewOrdersActivity extends AppCompatActivity {

    private RecyclerView ordersList;
    private DatabaseReference ordersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);

        ordersRef= FirebaseDatabase.getInstance().getReference().child("Orders");
        ordersList=(RecyclerView) findViewById(R.id.order_list);
        ordersList.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options =
                new FirebaseRecyclerOptions.Builder<AdminOrders>()
                        .setQuery(ordersRef , AdminOrders.class)
                        .build();

        Toast.makeText(AdminNewOrdersActivity.this, "Alishba", Toast.LENGTH_SHORT).show();


        FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter =
                new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, final int position, @NonNull final AdminOrders model) {

                        holder.username.setText("Name : "+model.getName());
                        holder.userTotalPrice.setText("Total Amount : "+model.getTotalAmount());
                        holder.userDateTime.setText("Order at : "+model.getDate() + " "+ model.getTime() );
                        holder.userPhoneNumber.setText("Phone Number : "+model.getPhone());
                        holder.userShippingAddress.setText("Shipping Address : "+model.getAddress()+ " "+ model.getCity());

                        holder.showOrderButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v){
                                
                                Intent intent= new Intent(AdminNewOrdersActivity.this,AdminUserProductsActivity.class);
                                intent.putExtra("uid", model.getPhone());
                                startActivity(intent);
                            }
                        });

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[]= new CharSequence[]
                                {

                                    "Yes",
                                     "No"
                                };

                                AlertDialog.Builder builder=new AlertDialog.Builder(AdminNewOrdersActivity.this);
                                builder.setTitle("Have You Shipped this Order Products ?");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(which==0)
                                        {
                                            String uID=getRef(position).getKey();
                                            RemoverOrder(uID);
                                        }
                                        else if (which==1)
                                        {
                                            finish();
                                        }
                                    }
                                });
                                builder.show();
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout, parent, false);
                        return new AdminOrdersViewHolder(view);
                    }
                };

    }


    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder{

        public TextView username, userPhoneNumber, userTotalPrice, userDateTime, userShippingAddress;
        public Button showOrderButton;


        public AdminOrdersViewHolder(View itemView) {
            super(itemView);

            username=(TextView) itemView.findViewById(R.id.order_user_name) ;
            userPhoneNumber= (TextView)itemView.findViewById(R.id.order_phone_number);
            userTotalPrice=(TextView) itemView.findViewById(R.id.order_total_price);
            userDateTime=(TextView) itemView.findViewById(R.id.order_date_time);
            userShippingAddress=(TextView) itemView.findViewById(R.id.order_address_city);
            showOrderButton=(Button)itemView.findViewById(R.id.show_all_pproducts);

        }

    }


    private void RemoverOrder(String uID) {

        ordersRef.child(uID).removeValue();

    }


}



