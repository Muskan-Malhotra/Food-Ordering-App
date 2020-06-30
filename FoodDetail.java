package com.example.zaika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Member;
import java.util.HashMap;

public class FoodDetail extends AppCompatActivity {

    TextView food_name,food_price,food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;
    String foodId="";

    FirebaseDatabase database;
    DatabaseReference food,cartfood;
    Foodee member;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

//        firebase
        database = FirebaseDatabase.getInstance();
        food = database.getReference("Food");
        //init view

        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);
        cartfood = FirebaseDatabase.getInstance().getReference().child("Cart List");
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtocart();
            }
        });

        member = new Foodee();
        food_description = (TextView) findViewById(R.id.food_description);
        food_name = (TextView) findViewById(R.id.food_name);
        food_price = (TextView) findViewById(R.id.food_price);
        food_image = (ImageView) findViewById(R.id.img_food);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapseActionView);
//        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
//        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppbar);

        //get food id from intent
        if(getIntent() != null){
            foodId = getIntent().getStringExtra("FoodId");
        }

        if(!foodId.isEmpty()){
            getDetailFood(foodId);
        }
    }

    private void addtocart() {
        String FoodId = cartfood.push().getKey();
        String name = food_name.getText().toString();
        String price = food_price.getText().toString();
        String desc = food_description.getText().toString();
        int quan = Integer.parseInt(numberButton.getNumber());



//        final DatabaseReference cartfood = database.getReference().child("Cart List");
//        final HashMap<String, Object> cartMap = new HashMap<>();
//        cartMap.put("FoodId", foodId);
//        cartMap.put("Name", food_name.getText().toString());;
//        cartMap.put("Price", food_price.getText().toString());
//        cartMap.put("Description", food_description.getText().toString());
//        cartMap.put("Quantity", numberButton.getNumber());
//        String foodId = cartfood.push().getKey();
        Foodee member = new Foodee(name,desc,price,FoodId,quan);
        cartfood.child(FoodId).setValue(member);


        Toast.makeText(FoodDetail.this,"Added to cart",Toast.LENGTH_LONG).show();

    }


    private void getDetailFood(String foodId){
        food.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Food food = dataSnapshot.getValue(Food.class);
                //set Image

                Picasso.with(getBaseContext()).load(food.getImage())
                        .into(food_image);
//                collapsingToolbarLayout.setTitle(food.getName());

                food_price.setText(food.getPrice());
                food_name.setText(food.getName());
                food_description.setText(food.getDescription());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
