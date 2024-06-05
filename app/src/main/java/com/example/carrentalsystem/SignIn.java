package com.example.carrentalsystem;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.app.ProgressDialog;


public class SignIn extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button signInButton;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailEditText = findViewById(R.id.editText_email);
        passwordEditText = findViewById(R.id.editText_password);
        signInButton = findViewById(R.id.button_signIn);
        progressDialog = new ProgressDialog(this);// progress dialog
        progressDialog.setMessage("Signing in occuring ...");
        progressDialog.setCancelable(false);

        signInButton.setOnClickListener(new View.OnClickListener() {// set on action when click login
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    new SignInTask().execute(email, password);
                } else {
                    Toast.makeText(SignIn.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (progressDialog != null && progressDialog.isShowing()==true) {
            progressDialog.dismiss();
        }
    }
    private class SignInTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
            String password = params[1];
            String response = "";

            try {
                URL url = new URL("http://xampp/htdocs/signin.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);

                String postData = "email=" + email + "&password=" + password;
                OutputStream os = conn.getOutputStream();
                os.write(postData.getBytes());
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    StringBuilder sb = new StringBuilder();

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                    }

                    response = sb.toString();
                    in.close();
                } else {
                    response = "Error occuer: " + responseCode;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                JSONObject obj = new JSONObject(result);
                boolean success = obj.getBoolean("success");

                if (success==false) {
                    String message = obj.getString("message");
                    Toast.makeText(SignIn.this, message, Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(SignIn.this, MainActivityForUser.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(SignIn.this, "Sign in done successfully", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(SignIn.this, "Error parsing the json file", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

