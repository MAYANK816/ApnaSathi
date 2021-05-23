package com.example.apnasathi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class CropInsurance extends AppCompatActivity {
    ImageView imageView;
    Button button;
    Uri selectedImage;
    FirebaseDatabase database;
    DatabaseReference myRef;
    StorageReference mStorageRef,Ref;
    ProgressBar progressBar1;
    String farmerName,farmerNumber,farmerAddress,farmerArea,farmerCropType;
    EditText Name, Number, Address, Area, CropType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_insurance);
        init();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryOpens();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }

        }
    }

    private void init() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("cropinsurance");
        mStorageRef = FirebaseStorage.getInstance().getReference("images");
        imageView=findViewById(R.id.proofImage);
        progressBar1=findViewById(R.id.progressBar);
        Name = findViewById(R.id.farmername);
        Number = findViewById(R.id.farmernumber);
        Address = findViewById(R.id.farmeraddress);
        Area = findViewById(R.id.farmerarea);
        CropType = findViewById(R.id.croptype);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                galleryOpens();
            }
        }

    }
    public void galleryOpens() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null)
        {
            try {
                selectedImage=data.getData();
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(this,"Please Select Image",Toast.LENGTH_SHORT).show();
        }
    }
    public void uploadImage(View view) {
        farmerName=Name.getText().toString();
        farmerNumber=Number.getText().toString();
        farmerAddress=Address.getText().toString();
        farmerArea=Area.getText().toString();
        farmerCropType=CropType.getText().toString();
        if(TextUtils.isEmpty(farmerName) || TextUtils.isEmpty(farmerAddress) || TextUtils.isEmpty(farmerNumber) || TextUtils.isEmpty(farmerArea) || TextUtils.isEmpty(farmerCropType) ){
            Toast.makeText(CropInsurance.this,"Check the details",Toast.LENGTH_SHORT).show();
        }
        else{
            Ref=mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(selectedImage));
            Ref.putFile(selectedImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            linkUp();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            progressBar1.setVisibility(View.VISIBLE);
                        }
                    });
        }

    }
    private void linkUp() {
        imageView.setVisibility(View.INVISIBLE);
        progressBar1.setVisibility(View.INVISIBLE);
        Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Uri downloadUrl = uri;
                String mdid=myRef.push().getKey();
                farmerName=Name.getText().toString();
                farmerNumber=Number.getText().toString();
                farmerAddress=Address.getText().toString();
                farmerArea=Area.getText().toString();
                farmerCropType=CropType.getText().toString();
                if(TextUtils.isEmpty(farmerName) || TextUtils.isEmpty(farmerAddress) || TextUtils.isEmpty(farmerNumber) || TextUtils.isEmpty(farmerArea) || TextUtils.isEmpty(farmerCropType) ){
                    Toast.makeText(CropInsurance.this,"Check the details",Toast.LENGTH_SHORT).show();
                }
                else {
                    CropInsure helper=new CropInsure(downloadUrl.toString(),farmerName,farmerNumber,farmerAddress,farmerArea,farmerCropType);
                    myRef.child(mdid).setValue(helper);
                    notification();
                    Toast.makeText(getApplicationContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CropInsurance.this, MainActivity2.class));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Upload Failed Please Check Your Network",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void notification() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"n")
                    .setContentText("Image Uploaded")
                    .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                    .setAutoCancel(true)
                    .setContentText("New Data Is Uploaded");
            NotificationManagerCompat managerCompat=NotificationManagerCompat.from(this);
            managerCompat.notify(999,builder.build());
        }
    }

    private String getFileExtension(Uri selectedImage) {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(selectedImage));
    }
}