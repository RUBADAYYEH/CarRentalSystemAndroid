package com.example.carrentalsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignOut extends AppCompatActivity {

    EditText firstNameEditText, licenseEditText, phoneEditText, passwordEditText, confirmPasswordEditText;
    Button signUpButton;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);

        firstNameEditText = findViewById(R.id.editText_registerFirstName);
        licenseEditText = findViewById(R.id.editText_license);
        phoneEditText = findViewById(R.id.editText_editPhoneNumber);
        passwordEditText = findViewById(R.id.editText_registerPassword);
        confirmPasswordEditText = findViewById(R.id.editText_registerConfirmPassword);
        signUpButton = findViewById(R.id.button_registerSignUp);

        queue = Volley.newRequestQueue(this);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameEditText.getText().toString().trim();
                String license = licenseEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (!firstName.isEmpty() && !license.isEmpty() && !phone.isEmpty() && !password.isEmpty() && password.equals(confirmPassword)) {
                    signUp(firstName, license, phone, password);
                } else {
                    Toast.makeText(SignOut.this, "Please fill in all the fields correctly", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signUp(String firstName, String license, String phone, String password) {
        String url = "http://10.0.2.2:80/signup.php";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("firstName", firstName);
            jsonBody.put("license", license);
            jsonBody.put("phone", phone);
            jsonBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean success = response.getBoolean("success");
                            if (success) {
                                Toast.makeText(SignOut.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignOut.this, SignIn.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignOut.this, "Sign up failed. Try again.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignOut.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
}
