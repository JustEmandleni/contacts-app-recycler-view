package com.contacts.my.are.these;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addContact;
    ActionBar actionBar;
    RecyclerView mRecView;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setTitle("All Contacts");

        mRecView = findViewById(R.id.recylcerV);
        databaseHelper = new DatabaseHelper(this);
        
        populate();

        addContact = findViewById(R.id.floatingActionButton);
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddContactActivity.class));
            }
        });
    }

    private void populate() {
        Adapter adapter = new Adapter(MainActivity.this, databaseHelper.getData(Constants.C_ADD_TIMESTAMP + " DESC"));
        mRecView.setAdapter(adapter);
    }

    public void onResume(){
        super.onResume();
        populate();
    }
}