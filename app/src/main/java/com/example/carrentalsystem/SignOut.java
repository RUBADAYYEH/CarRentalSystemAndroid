package com.example.carrentalsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class SignOut extends AppCompatActivity {

    private EditText editTextFirstName;
    private EditText license;

    private EditText editTextPhoneNumber;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;

    private Button buttonSignUp;
    private ImageButton imgBtnVisibility;
    private ImageButton imgBtnConfirmVisibility;
    private ProgressDialog progressDialog;
    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;
    private RequestQueue requestQueue;
    private static final String URL = "http://xampp/htdocs/signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);

        editTextFirstName = findViewById(R.id.editText_registerFirstName);
        license=findViewById(R.id.editText_license);
        editTextPhoneNumber = findViewById(R.id.editText_editPhoneNumber);
        editTextEmail = findViewById(R.id.editText_registerEmail);
        editTextPassword = findViewById(R.id.editText_registerPassword);
        editTextConfirmPassword = findViewById(R.id.editText_registerConfirmPassword);
        buttonSignUp = findViewById(R.id.button_registerSignUp);
        imgBtnVisibility = findViewById(R.id.imgBtn_visibility);
        imgBtnConfirmVisibility = findViewById(R.id.imgBtn_confirm_visibility);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering the user...");
        progressDialog.setCancelable(false);
        requestQueue = Volley.newRequestQueue(this);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        imgBtnVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(editTextPassword, imgBtnVisibility);
            }
        });

        imgBtnConfirmVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(editTextConfirmPassword, imgBtnConfirmVisibility);
            }
        });
    }

    private void togglePasswordVisibility(EditText passwordField, ImageButton toggleButton) {
        if (!isPasswordVisible) {
            passwordField.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            toggleButton.setImageResource(R.drawable.ic_visible);
        } else {
            passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            toggleButton.setImageResource(R.drawable.ic_invisible);
        }
        isPasswordVisible = !isPasswordVisible;
        passwordField.setSelection(passwordField.getText().length());
    }

    private void registerUser() {
        String firstName = editTextFirstName.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();


        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();
                            try {
                                JSONObject obj = new JSONObject(response);
                                boolean success = obj.getBoolean("success");
                                String message = obj.getString("message");
                                Toast.makeText(SignOut.this, message, Toast.LENGTH_SHORT).show();
                                if (success) {
                                    saveUserData();
                                    startActivity(new Intent(SignOut.this, SignIn.class));
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(SignOut.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Log.e("SignUp", "Error: " + error.getMessage());
                            Toast.makeText(SignOut.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("first_name", firstName);
                    params.put("license", String.valueOf(license));
                    params.put("phone_number", phoneNumber);
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }

    private void saveUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("first_name", editTextFirstName.getText().toString().trim());
        editor.putString("last_name", license.getText().toString().trim());
        editor.putString("phone_number", editTextPhoneNumber.getText().toString().trim());
        editor.putString("email", editTextEmail.getText().toString().trim());
        editor.apply();
    }
}
