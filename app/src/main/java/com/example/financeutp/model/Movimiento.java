package com.example.financeutp.model;

import com.example.financeutp.config.ConfiguracionFirebase;
import com.example.financeutp.helper.Base64Custom;
import com.example.financeutp.helper.DateCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Movimiento {
    private String data;
    private String category;
    private String description;
    private String tipo;
    private double value;
    private String key;

    public Movimiento() {
    }

    public Movimiento(String data, String category, String description, String tipo, double value, String key) {
        this.data = data;
        this.category = category;
        this.description = description;
        this.tipo = tipo;
        this.value = value;
        this.key = key;
    }

    public void guardar(String dataEscolhida){

        FirebaseAuth autentication = ConfiguracionFirebase.getFirebaseAutentication();
        String idUsuario = Base64Custom.codificarBase64( autentication.getCurrentUser().getEmail() );
        String monthYear = DateCustom.monthYearData( dataEscolhida );

        DatabaseReference firebase = ConfiguracionFirebase.getFirebaseDatabase();
        firebase.child("movimiento")
                .child( idUsuario )
                .child( monthYear )
                .push()
                .setValue(this);

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
