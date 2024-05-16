package com.example.app_tusguiasdeviajes;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

public class ModificarDatos extends AppCompatActivity {
    EditText telefonoUser;
    TextView nombreUser;

    String email;
    FirebaseAuth mAunth;
    FirebaseFirestore fStore;

    Button BtnCancelar, BtnModificar;

    private String idUser;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_datos);

        BtnCancelar = findViewById(R.id.btn_cancelar_modificar);
        BtnModificar = findViewById(R.id.btn_modificar_datos);

        mAunth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        idUser = mAunth.getCurrentUser().getUid();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        BtnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment();
            }
        });

        BtnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                telefonoUser = findViewById(R.id.telefonoDatos);
                String telefono = telefonoUser.getText().toString().trim();
                DocumentReference docRef = fStore.collection("User").document(idUser);
                fStore.runTransaction(new Transaction.Function<Object>() {
                    public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                        DocumentSnapshot snapshot = transaction.get(docRef);
                        String newphone = (telefono)  ;
                        transaction.update(docRef, "Telefono", newphone);

                        // Success
                        return null;
                    }
                });
                Toast.makeText(ModificarDatos.this, "Numero modificado correctamente", Toast.LENGTH_SHORT).show();

            }
        });

        nombreUser = findViewById(R.id.nombre);

        DocumentReference docRef = fStore.collection("User").document(idUser);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        nombreUser.setText(document.getString("Nombre") + (" procede a cambiar su telefono:"));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
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