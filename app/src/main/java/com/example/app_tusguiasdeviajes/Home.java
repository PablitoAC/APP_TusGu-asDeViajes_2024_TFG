package com.example.app_tusguiasdeviajes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class Home extends Fragment {

    ImageView paris, lisboa, parisDisney, marrakech;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        super.onCreate(savedInstanceState);

        paris = view.findViewById(R.id.paris);
        lisboa = view.findViewById(R.id.lisboa);
        parisDisney = view.findViewById(R.id.parisDisney);
        marrakech = view.findViewById(R.id.marrakech);

        paris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Paris_guia_descripcion.class);
                startActivity(i);
            }
        });

        lisboa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Lisboa_guia_descripcion.class);
                startActivity(i);
            }
        });

        parisDisney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Disneyland_guia_descripcion.class);
                startActivity(i);
            }
        });

        marrakech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Marrakech_guia_descripcion.class);
                startActivity(i);
            }
        });
    }
}