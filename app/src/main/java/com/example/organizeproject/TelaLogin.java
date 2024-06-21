package com.example.organizeproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class TelaLogin extends AppCompatActivity {

    private EditText emailEditText;
    private EditText senhaEditText;
    private ProgressBar progressBar;
    private bdOrganize dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        dbHelper = new bdOrganize(this);

        emailEditText = findViewById(R.id.insereEmail);
        senhaEditText = findViewById(R.id.insereSenha);
        Button btnEntrar = findViewById(R.id.entrarButton);
        TextView btnCadastrar = findViewById(R.id.text_tela_cadastro);
        progressBar = findViewById(R.id.progressbar);

        btnEntrar.setOnClickListener(view -> {
            String email = emailEditText.getText().toString();
            String senha = senhaEditText.getText().toString();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(senha)) {
                Toast.makeText(TelaLogin.this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            } else {
                progressBar.setVisibility(ProgressBar.VISIBLE);
                new AuthenticateTask(this, dbHelper).execute(email, senha);
            }
        });

        btnCadastrar.setOnClickListener(view -> {
            Intent intent = new Intent(TelaLogin.this, Cadastro.class);
            startActivity(intent);
        });
    }

    private static class AuthenticateTask extends AsyncTask<String, Void, Boolean> {

        private final WeakReference<TelaLogin> activityReference;
        private final bdOrganize dbHelper;

        AuthenticateTask(TelaLogin context, bdOrganize dbHelper) {
            activityReference = new WeakReference<>(context);
            this.dbHelper = dbHelper;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String email = params[0];
            String password = params[1];

            return dbHelper.checkUser(email, password);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            TelaLogin activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }

            ProgressBar progressBar = activity.progressBar;
            if (progressBar != null) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }

            if (result) {
                activity.navegarTelaPrincipal();
            } else {
                Toast.makeText(activity, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navegarTelaPrincipal() {
        Intent intent = new Intent(TelaLogin.this, TelaInicial.class);
        startActivity(intent);
        finish();
    }
}
