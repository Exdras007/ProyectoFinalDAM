package com.example.proyectofinaldam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_inicio_sesion extends AppCompatActivity
{

    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private EditText edt_emailUsuario;
    private EditText edt_claveUsuario;

    @Override
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        if(currentUser != null)
        {
            currentUser.reload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        // -----------------Enlace de los atributos-----------------------
        edt_emailUsuario = (EditText) findViewById(R.id.edt_correo);
        edt_claveUsuario = (EditText) findViewById(R.id.edt_clave);
        // ---Fire Base---
        mAuth = FirebaseAuth.getInstance();
        // Pruebas firebase realTime
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
    }

    public void registrarUsuario(View view)
    {
        String email = String.valueOf(edt_emailUsuario.getText()).trim();
        String clave = String.valueOf(edt_claveUsuario.getText()).trim();

        if(email.isEmpty())
        {
            edt_emailUsuario.setError("El email no puede estar vacio");
            return;
        }
        else if(clave.length()<5)
        {
            edt_claveUsuario.setError("La clave debe tener al menos 6 caracteres");
            return;
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(email, clave)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                // Registro completado, update UI with the signed-in user's information
                                Toast.makeText(activity_inicio_sesion.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                // Si el logeo falla, mandas un mensaje al usuario
                                Toast.makeText(activity_inicio_sesion.this, "No se pudo registrar al usuario", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void VolverInicio(View view)
    {
        Intent intent = new Intent(activity_inicio_sesion.this, MainActivity.class);
        startActivity(intent);
    }
}