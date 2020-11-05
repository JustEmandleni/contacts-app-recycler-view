package com.contacts.my.are.these;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton actionButtonAdd;
    ActionBar actionBar;
    RecyclerView mRecView;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Pet Journal");

        mRecView = findViewById(R.id.recylcerV);
        databaseHelper = new DatabaseHelper(this);
        
        populate();

        actionButtonAdd = findViewById(R.id.floatingActionButton);
        actionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JournalEntryActivity.class);
                intent.putExtra("editMode", true);
                startActivity(intent);
            }
        });
    }

    private void populate() {
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        mRecView.setLayoutManager(layoutManager);
        Adapter adapter = new Adapter(MainActivity.this, databaseHelper.getData(Constants.C_ADD_TIMESTAMP + " DESC"));
        mRecView.setAdapter(adapter);
    }

    public void onResume(){
        super.onResume();
        populate();
    }
}