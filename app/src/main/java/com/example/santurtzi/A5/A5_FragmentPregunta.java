package com.example.santurtzi.A5;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.santurtzi.R;

public class A5_FragmentPregunta extends Fragment {

    private View v;
    private TextView tvPregunta;
    private RadioButton rbRespuesta1, rbRespuesta2, rbRespuesta3, rbRespuesta4;
    private RadioGroup rbGroup;
    private A5_Pregunta p;
    private Button btnResponder;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        OnBackPressedCallback callback = new OnBackPressedCallback(
                true // default to enabled
        ) {
            @Override
            public void handleOnBackPressed() {
                A5_Sotera activity = (A5_Sotera) getActivity();
                activity.finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(
                this, // LifecycleOwner
                callback);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_a5_pregunta, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        v = getView();

        p = new A5_Pregunta(getArguments().getString("pregunta"), getArguments().getInt("respuesta"), getArguments().getStringArray("opciones"));
        tvPregunta = (TextView) v.findViewById(R.id.textViewPregunta);
        btnResponder = (Button) v.findViewById(R.id.btnResponder);
        rbRespuesta1 = (RadioButton) v.findViewById(R.id.radioButton1);
        rbRespuesta2 = (RadioButton) v.findViewById(R.id.radioButton2);
        rbRespuesta3 = (RadioButton) v.findViewById(R.id.radioButton3);
        rbRespuesta4 = (RadioButton) v.findViewById(R.id.radioButton4);
        rbGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        String[] opciones = p.getOpciones();

        tvPregunta.setText(p.getPregunta());
        rbRespuesta1.setText(opciones[0]);
        rbRespuesta2.setText(opciones[1]);
        rbRespuesta3.setText(opciones[2]);
        rbRespuesta4.setText(opciones[3]);

        listeners();
    }

    private void listeners() {
        // Enviar si la respuesta es correcta a la actividad
        btnResponder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // final MediaPlayer mp = MediaPlayer.create(this, R.raw.A5_error);
                int selectedId = rbGroup.getCheckedRadioButtonId();
                if(rbRespuesta1.getId() == selectedId) {
                    if(p.getRespuesta_correcta() == 1) {
                        A5_Sotera activity = (A5_Sotera) getActivity();
                        activity.loadNextQuestion();
                    }
                    else {
                        // mp.start();
                    }
                }
                if(rbRespuesta2.getId() == selectedId) {
                    if(p.getRespuesta_correcta() == 2) {
                        A5_Sotera activity = (A5_Sotera) getActivity();
                        activity.loadNextQuestion();
                    }
                    else {
                        // mp.start();
                    }
                }
                if(rbRespuesta3.getId() == selectedId) {
                    if(p.getRespuesta_correcta() == 3){
                        A5_Sotera activity = (A5_Sotera) getActivity();
                        activity.loadNextQuestion();

                    }
                    else {
                        // mp.start();
                    }
                }
                if(rbRespuesta4.getId() == selectedId) {
                    if(p.getRespuesta_correcta() == 14) {
                        A5_Sotera activity = (A5_Sotera) getActivity();
                        activity.loadNextQuestion();
                    }
                    else {
                        // mp.start();
                    }
                }

            }
        });
    }

}