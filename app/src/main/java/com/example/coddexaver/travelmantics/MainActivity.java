package com.example.coddexaver.travelmantics;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
//ArrayList<TravelDeal> deals;
    //  private FirebaseDatabase mFirebaseDatabase;
    //private DatabaseReference mDatabaseReference;
    // private ChildEventListener mChildListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_activity_menu, menu);
        MenuItem insertMenu = menu.findItem(R.id.insert_menu);
if(FirebaseUtil.isAdmin == true){
    insertMenu.setVisible(true);
}else {
    insertMenu.setVisible(false);
}
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.insert_menu:
                startActivity(new Intent(this, DealActivity.class));
            case R.id.logout_menu:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                           FirebaseUtil.attachListener();
                            }
                        });


        }
        FirebaseUtil.detachListener();
        Toast.makeText(this, "Logout successfully", Toast.LENGTH_LONG).show();
        return true;
    }

    @Override
    protected void onPause() {
        FirebaseUtil.detachListener();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtil.openFbReference("traveldeals", this);
        RecyclerView rvDeals = findViewById(R.id.rvDeals);
        LinearLayoutManager dealsLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvDeals.setLayoutManager(dealsLayoutManager);
        final DealAdapter adapter = new DealAdapter();
        rvDeals.setAdapter(adapter);

        FirebaseUtil.attachListener();
    }


    public void showMenu(){
        invalidateOptionsMenu();
    }
}
