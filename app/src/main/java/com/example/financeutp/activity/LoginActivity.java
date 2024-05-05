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
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textSignIn;
    private Button btnLogin;
    private TextInputEditText editEmail, editPassword;
    private FirebaseAuth autentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getSupportActionBar().hide();

        autentication = ConfiguracionFirebase.getFirebaseAutentication();

        findViews();
        events();
    }

    private void findViews() {
        textSignIn = findViewById(R.id.textViewSignin);
        editEmail = findViewById(R.id.editLoginEmail);
        editPassword = findViewById(R.id.editLoginPassword);
        btnLogin = findViewById(R.id.buttonLogin);
    }

    private void events() {
        textSignIn.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.textViewSignin:
                goToSignin();
                break;
            case R.id.buttonLogin:
                goToLogin();
                break;
        }
    }

    private void goToSignin() {
        Intent i = new Intent(LoginActivity.this, SignInActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

    private void goToLogin() {
        //Recuperar textos dos campos
        String textoEmail = editEmail.getText().toString();
        String textoPassword = editPassword.getText().toString();

        //Validar se e-mail e senha foram digitados
        if( !textoEmail.isEmpty() ){//verifica e-mail
            if( !textoPassword.isEmpty() ){//verifica senha

                Usuario usuario = new Usuario();
                usuario.setEmail( textoEmail );
                usuario.setPassword( textoPassword );

                logarUsuario( usuario );

            }else {
                Toast.makeText(LoginActivity.this,
                        "Completa la contraseña!",
                        Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(LoginActivity.this,
                    "Completa correo!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void logarUsuario(Usuario usuario){

        autentication.signInWithEmailAndPassword (
                usuario.getEmail(), usuario.getPassword()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {

                if( task.isSuccessful() ){
                    pantallaInicial();
                }else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch ( FirebaseAuthInvalidUserException e ) {
                        excecao = "El usuario no está registrado";
                    }catch ( FirebaseAuthInvalidCredentialsException e ){
                        excecao = "El correo electrónico y la contraseña no corresponden a un usuario registrado";
                    }catch (Exception e){
                        excecao = "Error al registrar usuario: "  + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
/*
    public void validarAutenticacaoUsuario(View view){

        //Recuperar textos dos campos
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();

        //Validar se e-mail e senha foram digitados
        if( !textoEmail.isEmpty() ){//verifica e-mail
            if( !textoSenha.isEmpty() ){//verifica senha

                Usuario usuario = new Usuario();
                usuario.setEmail( textoEmail );
                usuario.setPassword( textoSenha );

                logarUsuario( usuario );

            }else {
                Toast.makeText(LoginActivity.this,
                        "Preencha a senha!",
                        Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(LoginActivity.this,
                    "Preencha o email!",
                    Toast.LENGTH_SHORT).show();
        }


    }*/

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser usuarioAtual = autentication.getCurrentUser();
        if ( usuarioAtual != null ){
            pantallaInicial();
        }
    }



    public void pantallaInicial(){
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity( intent );
        finish();
    }
}
