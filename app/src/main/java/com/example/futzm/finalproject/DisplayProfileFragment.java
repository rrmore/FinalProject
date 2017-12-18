package com.example.futzm.finalproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by futzm on 11/22/2017.
 */

public class DisplayProfileFragment extends Fragment {
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    FirebaseAuth firebaseAuth;
    DatabaseReference myRef;
    private String userChoosenTask;
    ProgressDialog progressDialog;
    String mCurrentPhotoPath;
    Uri photoURI;
    ImageView profilePic;

    public static DisplayProfileFragment newInstance()
    {

        DisplayProfileFragment fragment = new DisplayProfileFragment();
        //dataInCoordinatorlayout = data;
        Bundle args = new Bundle();
        //args.putSerializable(ARG_SECTIONNUMBER, section_number);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        if(savedInstanceState == null) {
            super.onCreate(savedInstanceState);
           // getChildFragmentManager().beginTransaction()
                    //.replace(R.id.replace_framelayout_with_propertdetails, PropertyDetailFragment.newInstance(dataInCoordinatorlayout)).commit();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // Log.d(TAG, "onCreateView: Inflating DisplayProfileFragment");
        View rootView = inflater.inflate(R.layout.display_profile_fragment, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        profilePic=(ImageView) rootView.findViewById(R.id.profile_pic);
        String userName=firebaseAuth.getCurrentUser().getEmail().replace(".","_");
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users").child(userName).child("photoUrl");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String photoUrl=dataSnapshot.getValue(String.class);
                if(!photoUrl.equals(""))
                Picasso.with (getContext()). load (photoUrl). into (profilePic);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FloatingActionButton addPhotoButton=(FloatingActionButton) rootView.findViewById(R.id.add_photo_button);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setBackground(new ColorDrawable(getResources().getColor(R.color.project_orange)));
        getChildFragmentManager().beginTransaction()
                .replace(R.id.framelayout_with_profiledetails, ProfileContentFragment.newInstance()).commitAllowingStateLoss();
        addPhotoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        return rootView;
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add a photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                //boolean result=Utility.checkPermission(DisplayProfileFragment.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                   // if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    ///if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    /*
    private void cameraIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
            }
        }
    }
    */

    private void cameraIntent()
    {

        if (getContext().checkSelfPermission(android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        }
        else
        {
            startCamera();
        }
    }

    private void startCamera()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
            }
        }
    }
    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CAMERA) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(getContext(), "camera permission granted", Toast.LENGTH_LONG).show();

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        photoURI = FileProvider.getUriForFile(getContext(),
                                "com.example.android.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_CAMERA);
                    }
                }
            } else {

                Toast.makeText(getContext(), "camera permission denied", Toast.LENGTH_LONG).show();

            }

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progressDialog=new ProgressDialog(getContext());
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE){
                Uri uri=data.getData();
                progressDialog.setMessage("Uploading...");
                progressDialog.show();
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                String email=firebaseAuth.getCurrentUser().getEmail();
                String userName=email.replace(".","_");
                StorageReference storageReference= FirebaseStorage.getInstance().getReference();
                StorageReference filePath=storageReference.child("Profile_Pics").child(userName+"pic");
                filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Upload Done", Toast.LENGTH_LONG).show();
                        Uri downloadUri=taskSnapshot.getDownloadUrl();
                        String photoPath=downloadUri.toString();
                        Picasso.with (getContext()). load (downloadUri).fit().into (profilePic);
                        updateUserTable(photoPath);
                    }
                });
            }
                //onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA){
                Uri uri=data.getData();
                progressDialog.setMessage("Uploading...");
                progressDialog.show();
                FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
                String email=firebaseAuth.getCurrentUser().getEmail();
                String userName=email.replace(".","_");
                StorageReference storageReference= FirebaseStorage.getInstance().getReference();
                StorageReference filePath=storageReference.child("Profile_Pics").child(userName+"pic");
                filePath.putFile(photoURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Upload Done", Toast.LENGTH_LONG).show();
                        Uri downloadUri=taskSnapshot.getDownloadUrl();
                        String photoPath=downloadUri.toString();
                        Picasso.with (getContext()). load (downloadUri). into (profilePic);
                        updateUserTable(photoPath);
                    }
                });
            }
        }
    }

    public void updateUserTable(String imagePath){
        String email=firebaseAuth.getCurrentUser().getEmail();
        String userName=email.replace(".","_");
        myRef= FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference childRef=myRef.child(userName);
        childRef.child("photoUrl").setValue(imagePath);
    }
}
