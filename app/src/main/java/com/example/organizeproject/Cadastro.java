package com.example.organizeproject;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Cadastro extends AppCompatActivity {

    private EditText nomeCadastroEditText;
    private EditText emailCadastroEditText;
    private EditText senhaCadastroEditText;
    private bdOrganize dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        dbHelper = new bdOrganize(this);

        nomeCadastroEditText = findViewById(R.id.edit_nome);
        emailCadastroEditText = findViewById(R.id.edit_email);
        senhaCadastroEditText = findViewById(R.id.edit_senha);
        Button btnConfirmarCadastro = findViewById(R.id.confirmaCadastro);

        btnConfirmarCadastro.setOnClickListener(view -> {
            String nome = nomeCadastroEditText.getText().toString();
            String email = emailCadastroEditText.getText().toString();
            String senha = senhaCadastroEditText.getText().toString();

            if (TextUtils.isEmpty(nome) || TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {
                Toast.makeText(Cadastro.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            } else {
                boolean isInserted = dbHelper.addUser(nome, email, senha);
                if (isInserted) {
                    Toast.makeText(Cadastro.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Cadastro.this, "Erro ao cadastrar, tente novamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
