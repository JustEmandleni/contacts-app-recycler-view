package com.contacts.my.are.these;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UpdateJournalActivity extends AppCompatActivity {

    ActionBar actionBar;
    private ImageView mImage;
    private TextView mDate;
    private Spinner mEntryType;
    private EditText mDescription;
    private FloatingActionButton mSaveButton;

    private Uri imageUri;
    private String id, date, entryType, description, addTimeStamp, updateTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_journal);

        mImage = findViewById(R.id.uImageView);
        mDate = findViewById(R.id.uDate);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.eventTypes,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEntryType.setAdapter(adapter);

        mEntryType = findViewById(R.id.uEntryType);
        mDescription = findViewById(R.id.uDescription);
        mSaveButton = findViewById(R.id.uSave);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        imageUri = Uri.parse(intent.getStringExtra("IMAGE"));
        date = intent.getStringExtra("DATE");
        entryType = intent.getStringExtra("ENTRY_TYPE");
        description = intent.getStringExtra("DESCRIPTION");
        addTimeStamp = intent.getStringExtra("ADDTIMESTAMP");
        updateTimeStamp = intent.getStringExtra("UPDATETIMESTAMP");

        mDate.setText(date);
        //mEntryType.setSelection(getItemIndex(entryType)); // <--- NB will this work ??????
        mDescription.setText(description);

        if (!imageUri.toString().equals("null")) {
            mImage.setImageURI(imageUri);
        }
        else
            {
            mImage.setImageResource(R.drawable.ic_upload_image);
        }

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private int getItemIndex(String value){
//        for(int i = 0; i < mEntryType.getAdapter().getCount() - 1; i ++){
//            if(value.equals(mEntryType.getItemAtPosition(i).toString()))
//                return i;
//        }
//        return -1;
        ArrayAdapter adapter = (ArrayAdapter) mEntryType.getAdapter();
        return adapter.getPosition(value);
    }
}