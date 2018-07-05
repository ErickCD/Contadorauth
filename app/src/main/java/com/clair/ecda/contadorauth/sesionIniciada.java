package com.clair.ecda.contadorauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class sesionIniciada extends AppCompatActivity {

    private Button btnAdd, btnLess, btnRestart, btnSalir;
    private TextView tvContador;
    private int numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion_iniciada);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference fbNumero = database.getReference();

        btnAdd = (Button) findViewById(R.id.btn_add);
        btnLess = (Button) findViewById(R.id.btn_less);
        btnRestart = (Button) findViewById(R.id.btn_restart);
        btnSalir = (Button) findViewById(R.id.btn_salir);

        numero = 0;

        tvContador = (TextView) findViewById(R.id.tv_numero);
        //tvContador.setText("Oprime un boton");
        fbNumero.child("contador").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                numero = Integer.valueOf(dataSnapshot.getValue().toString());
                tvContador.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numero++;
                fbNumero.child("contador").setValue(numero);
            }
        });

        btnLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numero--;
                fbNumero.child("contador").setValue(numero);
            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numero = 0;
                fbNumero.child("contador").setValue(numero);
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(sesionIniciada.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                startActivity(new Intent(sesionIniciada.this, inicio.class));
                                finish();
                            }
                        });

            }
        });
    }
}
