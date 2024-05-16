package com.example.app_tusguiasdeviajes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {

    TextInputEditText nombre, telefono, email, password;
    Button buttonReg;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    ProgressBar progressBar;
    TextView textView;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        nombre = findViewById(R.id.nombre);
        telefono = findViewById(R.id.telefono);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);

        textView = findViewById(R.id.loginNow);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                String nombreUSer = nombre.getText().toString().trim();
                String telefonoUSer = telefono.getText().toString().trim();
                String emailUSer = email.getText().toString().trim();
                String passwordUSer = password.getText().toString().trim();

                if(nombreUSer.isEmpty() && telefonoUSer.isEmpty() && emailUSer.isEmpty() && passwordUSer.isEmpty()){
                    Toast.makeText(Register.this, "Complete los datos", Toast.LENGTH_SHORT).show();
                }else{
                    if(passwordUSer.length()>5 && telefonoUSer.length() == 9){
                        registerUser(nombreUSer, telefonoUSer, emailUSer, passwordUSer);

                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                        finish();

                    }else {
                        if(telefonoUSer.length()<9 && telefonoUSer.length() > 9) {
                            Toast.makeText(Register.this, "El numero de telefono debe ser de 9 digitos", Toast.LENGTH_SHORT).show();
                        }
                        if(passwordUSer.length()<5) {
                            Toast.makeText(Register.this, "La contraseña debe tener 6 o mas caracteres", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    private void registerUser(String nombreUSer, String telefonoUSer, String emailUSer, String passwordUSer) {
        mAuth.createUserWithEmailAndPassword(emailUSer, passwordUSer).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try {
                    FirebaseUser user = mAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        String id = user.getUid();
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", id);
                        map.put("Nombre", nombreUSer);
                        map.put("Telefono", telefonoUSer);
                        map.put("Email", emailUSer);
                        map.put("Password", passwordUSer);

                        mFirestore.collection("User").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                finish();
                                startActivity(new Intent(Register.this, Login.class));
                                Toast.makeText(Register.this, "Usuario guardado con exito", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Register.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                            }
                        });



                    } else {
                        // Handle the situation, for example, redirecting the user to the login screen
                    }

                }catch(NullPointerException ex){
                    ex.printStackTrace();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Register.this, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}