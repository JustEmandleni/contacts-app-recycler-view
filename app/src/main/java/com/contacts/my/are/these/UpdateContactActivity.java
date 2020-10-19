package com.contacts.my.are.these;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class UpdateContactActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;
    ActionBar actionBar;
    private ImageView mImage;
    private EditText mFullName, mPhone, mCompany;
    private Button mSaveButton;
    private String[] cameraPermissions;
    private String[] storagePermissions;

    private Uri imageUri;

    private String id, name, phone, company, addtimestamp, updatetimestamp;
    private boolean editMode = false;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        mImage = findViewById(R.id.uploadImageView);
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //choose image from gallery or open camera
                imageChooser();
            }
        });
        mFullName = findViewById(R.id.fullNameEditText);
        mPhone = findViewById(R.id.mobilePhoneEditText);
        mCompany = findViewById(R.id.companyEeditText);
        mSaveButton = findViewById(R.id.saveButton);

        Intent intent = getIntent();
        editMode = intent.getBooleanExtra("editMode", editMode);
        id = intent.getStringExtra("Id");
        imageUri = Uri.parse(intent.getStringExtra("Image"));
        name = intent.getStringExtra("Name");
        phone = intent.getStringExtra("Phone");
        company = intent.getStringExtra("Company");
        addtimestamp = intent.getStringExtra("AddTimeStamp");
        updatetimestamp = intent.getStringExtra("UpdateTimeStamp");

        if(editMode) {
            actionBar.setTitle("Edit Contact");

            editMode = intent.getBooleanExtra("editMode", editMode);
            id = intent.getStringExtra("Id");
            imageUri = Uri.parse(intent.getStringExtra("Image"));
            name = intent.getStringExtra("Name");
            phone = intent.getStringExtra("Phone");
            company = intent.getStringExtra("Company");
            addtimestamp = intent.getStringExtra("AddTimeStamp");
            updatetimestamp = intent.getStringExtra("UpdateTimeStamp");

            mFullName.setText(name);
            mPhone.setText(phone);
            mCompany.setText(company);
            if (imageUri.toString().equals("null"))
                mImage.setImageResource(R.drawable.ic_upload_image);
            else mImage.setImageURI(imageUri);
        } else {
            actionBar.setTitle("Add Contact");
        }

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
                retrieveValues();
                Toast.makeText(UpdateContactActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateContactActivity.this, MainActivity.class));
            }
        });


        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void retrieveValues() {
        name = "" + mFullName.getText().toString().trim();
        phone = "" + mPhone.getText().toString().trim();
        company = "" + mCompany.getText().toString().trim();

        if(editMode)
        {
            String newUpdateTime = ""+System.currentTimeMillis();
            databaseHelper.updateInfo(
                    "" + id,
                    ""+ name,
                    "" + phone,
                    "" + company,
                    "" + imageUri,
                    "" + addtimestamp,
                    "" + newUpdateTime);
        }
        else
        {
            String timeStamp = "" + System.currentTimeMillis();

            databaseHelper.insertInfo(
                    "" + name,
                    "" + phone,
                    "" + company,
                    "" + imageUri,
                    "" + timeStamp,
                    "" + timeStamp
            );
        }
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
                        chooseFromCamera();
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
                        chooseFromCamera();
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

    private void chooseFromCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Image title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Image Description");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }
}