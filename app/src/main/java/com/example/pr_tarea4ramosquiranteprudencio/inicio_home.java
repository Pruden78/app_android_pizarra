package com.example.pr_tarea4ramosquiranteprudencio;

import androidx.lifecycle.ViewModelProvider;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.IBinder;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class inicio_home extends Fragment {

    private MediaPlayer mediaPlayer;
    public static inicio_home newInstance() {
        return new inicio_home();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configuración del botón para navegar al fragmento "pizarra"
        Button miBoton = view.findViewById(R.id.botonIrADibujar);
        miBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_inicio_home_to_pizarra);
                mediaPlayer.stop();
                onDestroy();
            }
        });

        //Creación del mediaplayer y comienzo de la reproducción
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.cancion_intro);
        mediaPlayer.start();

    }

    public class MyService extends Service {
        MediaPlayer mediaPlayer;
        // ...

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (mediaPlayer != null) mediaPlayer.release();
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }



}
