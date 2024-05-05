package com.example.financeutp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.financeutp.R;
import com.example.financeutp.config.ConfiguracionFirebase;
import com.example.financeutp.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textLogin;
    private TextInputEditText etName, etEmail, etPassword;
    private Button btnSignIn;
    private FirebaseAuth autentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //getSupportActionBar().hide();

        findViews();
        events();
    }

    private void findViews() {
        textLogin = findViewById(R.id.textViewLogin);
        etName  = findViewById(R.id.editName);
        etEmail = findViewById(R.id.editEmail);
        etPassword = findViewById(R.id.editPassword);
        btnSignIn = findViewById(R.id.buttonSignIn);
    }

    private void events() {
        btnSignIn.setOnClickListener(this);
        textLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.textViewLogin:
                goToLogin();
                break;
            case R.id.buttonSignIn:
                gotToSignIn();
                break;
        }
    }

    private void goToLogin() {
        Intent i = new Intent(SignInActivity.this, LoginActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        finish();
    }

    private void gotToSignIn() {
        //Recuperar textos dos campos
        String textoName  = etName.getText().toString();
        String textoEmail = etEmail.getText().toString();
        String textoPassword = etPassword.getText().toString();

        if( !textoName.isEmpty() ) {//verifica nome
            if( !textoEmail.isEmpty() ) {//verifica e-mail
                if ( !textoPassword.isEmpty() ) {

                    Usuario usuario = new Usuario();
                    usuario.setName( textoName );
                    usuario.setEmail( textoEmail );
                    usuario.setPassword( textoPassword );

                    SignInUser( usuario );

                }else {
                    Toast.makeText(SignInActivity.this,
                            "Completa la contraseña!",
                            Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(SignInActivity.this,
                        "Completa email!",
                        Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(SignInActivity.this,
                    "Completa nombre!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public void SignInUser(Usuario usuario){

        autentication = ConfiguracionFirebase.getFirebaseAutentication();
        autentication.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getPassword()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {

                if ( task.isSuccessful() ){
                    Toast.makeText(SignInActivity.this,"Bienvenido " + etName.getText().toString(), Toast.LENGTH_LONG).show();
                    Intent i = new Intent(SignInActivity.this, DashboardActivity.class);
                    startActivity(i);
                    finish();
                }else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthWeakPasswordException e){
                        excecao = "Digite una constraseña mas fuerte!";
                    }catch ( FirebaseAuthInvalidCredentialsException e){
                        excecao= "Por favor, digite um e-mail válido";
                    }catch ( FirebaseAuthUserCollisionException e){
                        excecao = "Esta cuenta ya ha sido registrada";
                    }catch (Exception e){
                        excecao = "Error al registrar usuario: "  + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(SignInActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}
