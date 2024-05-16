package com.example.app_tusguiasdeviajes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class EliminarPerfil extends AppCompatActivity {

    public EliminarPerfil getActivity;
    Button eliminar, cancelar;

    FirebaseAuth mAunth;
    FirebaseFirestore fStore;

    private String idUser1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_perfil);

        mAunth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAunth.getCurrentUser();
        fStore = FirebaseFirestore.getInstance();
        idUser1 = mAunth.getCurrentUser().getUid();

        eliminar = findViewById(R.id.btn_eliminarPerfil_confirmacion);
        cancelar = findViewById(R.id.btn_Cancelar_eliminar_perfil);

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fStore.collection("User").document(idUser1).delete();

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                user.delete();
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