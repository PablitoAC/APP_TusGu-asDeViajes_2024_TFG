package com.example.app_tusguiasdeviajes;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;




public class Perfil extends Fragment {
    TextView nombreUser, emailUser, telefonoUser, modificarDatos;
    FirebaseAuth mAunth;
    FirebaseFirestore fStore;

    Button BtmEliminarPerfil;

    private String idUser;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        super.onCreate(savedInstanceState);

        mAunth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        nombreUser = view.findViewById(R.id.nombreDatos);
        emailUser = view.findViewById(R.id.emailDatos);
        telefonoUser = view.findViewById(R.id.telefonoDatos);
        BtmEliminarPerfil = view.findViewById(R.id.btn_eliminarPerfil);
        modificarDatos = view.findViewById(R.id.modificarDatos);

        emailUser.setText(user.getEmail());

        idUser = mAunth.getCurrentUser().getUid();

        DocumentReference docRef = fStore.collection("User").document(idUser);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        nombreUser.setText(document.getString("Nombre"));
                        telefonoUser.setText(document.getString("Telefono"));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        BtmEliminarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EliminarPerfil.class);
                startActivity(i);
            }
        });

        modificarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ModificarDatos.class);
                startActivity(i);
            }
        });

    }
}