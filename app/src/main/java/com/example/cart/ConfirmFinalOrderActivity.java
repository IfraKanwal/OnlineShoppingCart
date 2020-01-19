package com.example.cart;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cart.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private EditText nameEditText, phoneNumberEditText, addressEditText, cityEditText;
    private Button confirmOrderButton;

    private String totalAmount="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);


        nameEditText= findViewById(R.id.shippment_name);
        phoneNumberEditText= findViewById(R.id.shippment_phone_number);
        addressEditText= findViewById(R.id.shippment_address);
        cityEditText=findViewById(R.id.shippment_city);
        confirmOrderButton= findViewById(R.id.confirm_final_order_btn);

        totalAmount =getIntent().getStringExtra("Total Price");

        Toast.makeText(ConfirmFinalOrderActivity.this, "Total Price = "+ totalAmount, Toast.LENGTH_SHORT).show();

        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
    }

    private void check()
    {
        if(TextUtils.isEmpty(nameEditText.getText().toString()))
        {
            Toast.makeText(ConfirmFinalOrderActivity.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(phoneNumberEditText.getText().toString()))
        {
            Toast.makeText(ConfirmFinalOrderActivity.this, "Please Enter Your Phone Number", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(cityEditText.getText().toString()))
        {
            Toast.makeText(ConfirmFinalOrderActivity.this, "Please Enter Your City", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            Toast.makeText(ConfirmFinalOrderActivity.this, "Please Enter Your Address", Toast.LENGTH_SHORT).show();

        }
        else
        {
            confirmOrder();
        }
    }

    private void confirmOrder() {

        String saveCurrentDate, saveCurrentTime;

        Calendar calforDate= Calendar.getInstance();
        SimpleDateFormat currentDate= new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate=currentDate.format(calforDate.getTime());


        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentDate.format(calforDate.getTime());

        final DatabaseReference ordersRef= FirebaseDatabase.getInstance().getReference()
                .child("Orders")
                .child(Prevalent.currentOnlineUser.getPhone());

        final HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("name", nameEditText.getText().toString());
        ordersMap.put("phone", phoneNumberEditText.getText().toString());
        ordersMap.put("address", addressEditText.getText().toString());
        ordersMap.put("city", cityEditText.getText().toString());
        ordersMap.put("state", "not shipped");
        ordersMap.put("date", saveCurrentDate);
        ordersMap.put("time", saveCurrentTime);
        ordersMap.put("totalAmount", totalAmount);

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseDatabase.getInstance().getReference().child("Cart List")
                                    .child("User View")
                                    .child(Prevalent.currentOnlineUser.getPhone())
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(ConfirmFinalOrderActivity.this, "Your Oder has been Plased Successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(ConfirmFinalOrderActivity.this, HomeActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                        }
                    }
                });



    }
}
