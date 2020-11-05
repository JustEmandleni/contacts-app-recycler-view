package com.contacts.my.are.these;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;

public class JournalEntryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ImageView mImage;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private Spinner mEntryType; //<-------- spinner
    private EditText mDescription;
    private FloatingActionButton mSaveButton;
    ActionBar actionBar;

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;

    private String[] cameraPermissions;
    private String[] storagePermissions;

    private Uri imageUri;

    private String date, entryType, description, timestamp;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_entry);

        mImage = findViewById(R.id.uploadImageView);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //choose image from gallery or open camera
                imageChooser();
            }
        });

        mDisplayDate = findViewById(R.id.etDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(JournalEntryActivity.this,
                        android.R.style.Theme,
                        mDateSetListener,
                        year, month, day);
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String date = dayOfMonth+"/"+month+"/"+year;
                mDisplayDate.setText(date);
            }
        };

        mEntryType = findViewById(R.id.entryTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.eventTypes,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEntryType.setAdapter(adapter);
        mEntryType.setOnItemSelectedListener(this);

        mDescription = findViewById(R.id.descriptionEditText);
        mSaveButton = findViewById(R.id.floatingSaveButton);

        cameraPermissions = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        storagePermissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        databaseHelper = new DatabaseHelper(this);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //commit changes
                commit();
                Toast.makeText(JournalEntryActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(JournalEntryActivity.this, MainActivity.class));
            }
        });

        actionBar = getSupportActionBar();
        actionBar.setTitle("Create Journal Entry");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void commit() {
        date = "" + mDisplayDate.getText().toString().trim();
        entryType = "" + mEntryType.getSelectedItem().toString().trim();
        description = "" + mDescription.getText().toString().trim();
        timestamp = "" + System.currentTimeMillis();
        databaseHelper.insertInfo(
                "" + date,
                "" + entryType,
                "" + description,
                "" + imageUri,
                "" + timestamp,
                "" + timestamp
        );
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
            }
            else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult activityResult = CropImage.getActivityResult(data);
                if(resultCode != CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Uri uri = activityResult.getUri();
                    imageUri = uri;
                    mImage.setImageURI(uri);
                }
                else {
                    Exception exceptionE = activityResult.getError();
                    Toast.makeText(this, ""+exceptionE,Toast.LENGTH_SHORT).show();
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean checkStoragePermission(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkCameraPermission(){
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED
                                                        &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_REQUEST_CODE: {
                if(grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && storageAccepted) {
                        captureImage();
                    }
                    else {
                        Toast.makeText(this, "Please grant camera permission.", Toast.LENGTH_LONG).show();
                    }
                }
            }
            break;

            case STORAGE_REQUEST_CODE: {
                if(grantResults.length > 0){
                    //boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if(storageAccepted) {
                        chooseFromGallery();
                    }
                    else {
                        Toast.makeText(this, "Please grant storage permission.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    private void imageChooser(){
        String[] choices = {
                "Camera",
                "Gallery"};
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Choose Image");
        alertDialogBuilder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        captureImage();
                    }
                }
                else {
                    if(which == 1) {
                        if (!checkStoragePermission()) {
                            requestStoragePermission();
                        }
                        else {
                            chooseFromGallery();
                        }
                    }
                }
            }
        });
        alertDialogBuilder.create().show();
    }

    private void chooseFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }

    private void captureImage() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Image title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Image Description");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
