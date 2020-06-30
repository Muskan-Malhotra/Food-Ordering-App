package com.example.zaika;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zaika.Interface.itemClickListener;
import com.example.zaika.ViewHolder.FoodViewHolder;
import com.example.zaika.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Sec2 extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {


    private DatabaseReference category,foodList;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;


    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        foodList = FirebaseDatabase.getInstance().getReference().child("Food");
        category = FirebaseDatabase.getInstance().getReference().child("Category");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(Sec2.this, Cart.class);
               startActivity(intent);
            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle;
//        toggle = new ActionBarDrawerToggle(this,drawer,toolbar,"Open navigation drawer", "Close navigation drawer");
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this );
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_offer, R.id.nav_cart, R.id.nav_order)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        recycler_menu = (RecyclerView) findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);
        onStart();
    }



    public void onStart() {

        super.onStart();
        //category = FirebaseDatabase.getInstance().getReference().child("Category");
        FirebaseRecyclerOptions<Category> option = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(category, Category.class)
                .build();
        //final FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter = null;Category.class, R.layout.menu_item, MenuViewHolder.class, category
         adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(option) {

            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder hold, int position, @NonNull Category model) {
                hold.txtMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(hold.imageView);
                hold.setItemClickListener(new itemClickListener() {


                    @Override
                    public void onClick(View view, int position, boolean isShortClick) {

                        //Get category and send to new activity
                            Intent foodList = new Intent(Sec2.this, FoodList.class);
                            //because category item is key, so we just get key of this item
                            foodList.putExtra("CategoryId", adapter.getRef(position).getKey());
                            startActivity(foodList);

                    }

                });
            }
             @NonNull
             @Override
             public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                 View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
                 MenuViewHolder hold = new MenuViewHolder(view);
                 return hold;
             }

//
//                public void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
//                viewHolder.txtMenuName.setText(model.getName());
//                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);
//                viewHolder.setItemClickListener(new itemClickListener() {
//
//                    @Override
//                    public void onClick(View view, int position, boolean isShortClick) {
//                        //Get category and send to new activity
//                        Intent foodList = new Intent(Sec2.this,FoodList.class);
//                        //because category item is key, so we just get key of this item
//                        foodList.putExtra("CategoryId",adapter.getRef(position).getKey());
//                        startActivity(foodList);
//                    }
//
//                });
//            }
        };
        recycler_menu.setAdapter(adapter);
        adapter.startListening();

    }

    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sec2, menu);
        return true;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_offer) {
            Toast.makeText(Sec2.this,"You are already on the menu",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_cart) {
            Intent inten = new Intent(this, Cart.class);
            startActivity(inten);}
//        } else if (id == R.id.nav_order) {
//            Toast.makeText(Sec2.this,"order",Toast.LENGTH_SHORT).show();
//}
             else if (id == R.id.nav_exit) {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }

//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
