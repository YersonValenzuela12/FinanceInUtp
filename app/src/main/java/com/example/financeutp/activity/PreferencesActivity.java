package com.example.financeutp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.financeutp.R;
import com.example.financeutp.config.ConfiguracionFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class PreferencesActivity extends AppCompatActivity {

    private FirebaseAuth autentication = ConfiguracionFirebase.getFirebaseAutentication();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Preferencias");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material,null);
        upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

    }

    public void perfil(View view) {
        Intent i = new Intent(PreferencesActivity.this, PerfilActivity.class);
        startActivity(i);
    }

    public void seguridad(View view) {
        Intent i = new Intent(PreferencesActivity.this, SecurityActivity.class);
        startActivity(i);
    }

    public void salir(View view) {
        autentication.signOut();
        Intent i = new Intent(PreferencesActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }
}
