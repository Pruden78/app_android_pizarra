package com.example.pr_tarea4ramosquiranteprudencio;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class pizarra extends Fragment {

    List<String> listaDatos = new ArrayList<>();
    EditText editText;
    Button btnAgregar;
    RecyclerView recyclerView;

    private CanvasPizarra.DrawView drawView;

    public static pizarra newInstance() {
        return new pizarra();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pizarra, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        //---- CARGAR EL CANVAS Y MARCAR POR DEFECTO EL PINCEL REDONDO Y EL COLOR NEGRO------------

        //Cargar el canvas con el pincel redondo negro
        FrameLayout lienzo = view.findViewById(R.id.lienzoDrawView);
        drawView = new CanvasPizarra.DrawView(getContext());
        lienzo.addView(drawView); // Agregar tu DrawView al lienzo


        //Hacer que se desactive el grupo de colores si seleccionas estrella o cara.
        RadioButton botonEstrella = view.findViewById(R.id.pincelEstrella);
        RadioButton botonCara = view.findViewById(R.id.pincelCaraPru);
        LinearLayout coloresPinceles = view.findViewById(R.id.seleccionPincelesTipo);

        CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(botonCara.isChecked() || botonEstrella.isChecked()){
                    coloresPinceles.setVisibility(View.GONE);
                } else {
                    coloresPinceles.setVisibility(View.VISIBLE);
                }
            }
        };

        botonCara.setOnCheckedChangeListener(listener);
        botonEstrella.setOnCheckedChangeListener(listener);






        //------ FUNCIONALIDAD BOTÓN BORRAR -------------
        //Declaramos el botón
        Button btnBorrar = view.findViewById(R.id.btn_borrar);
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               drawView.borrarDibujo();
            }
        });

        //--------  FUNCIONALIDAD BOTÓN VOLVER AL MENÚ   ---------
        Button btnInicio = view.findViewById(R.id.btn_volverInicio);

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_pizarra_to_inicio_home);
            }
        });


    }

}