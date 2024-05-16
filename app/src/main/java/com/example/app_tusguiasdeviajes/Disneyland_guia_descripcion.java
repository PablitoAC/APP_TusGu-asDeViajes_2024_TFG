package com.example.app_tusguiasdeviajes;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ResourceBundle;

public class Disneyland_guia_descripcion extends AppCompatActivity {

    Button cancelar;
    TextView descripcion, destino, precio, descargar;
    FirebaseAuth mAunth;
    FirebaseFirestore fStore;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disneyland_guia_descripcion);

        mAunth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        cancelar = findViewById(R.id.btn_Cancelar_guia_paris);
        descargar = findViewById(R.id.descargar_guia_diney);
        descripcion = findViewById(R.id.descrpcionGuia);
        destino = findViewById(R.id.destino);
        precio = findViewById(R.id.precio);

        DocumentReference docRef = fStore.collection("guias").document("xi1MLquYoY16Spm9Zznt");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        descripcion.setText(document.getString("descripcionGuia"));
                        destino.setText(document.getString("destino"));
                        precio.setText(document.getString("precio"));
                        String url = document.getString("enlace");

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        descargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference docRef = fStore.collection("guias").document("xi1MLquYoY16Spm9Zznt");
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String url = document.getString("enlace");
                                Uri link = Uri.parse((url));
                                Intent i = new Intent(Intent.ACTION_VIEW, link);
                                startActivity(i);
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment();
            }
        });
    }

    private void openFragment() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("fragmentToLoad", "home"); // Puedes pasar información adicional si es necesario
        startActivity(intent);
        finish(); // Termina esta actividad para volver a MainActivity
    }


}