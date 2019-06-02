package com.example.loginapp.Activities;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class BaseApp extends Application {
    public static FirebaseFirestore db;
    public static FirebaseStorage storage;
    public static FirebaseAuth mAuth;
    public static StorageReference storageRef;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.getInstance();
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
    }
}
