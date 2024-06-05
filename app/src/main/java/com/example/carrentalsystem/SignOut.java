package com.example.carrentalsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignOut extends AppCompatActivity {

    private EditText editTextFirstName;// initialize the inputs
    private EditText editTextLastName;
    private EditText editTextPhoneNumber;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Spinner spinnerGender;
    private Spinner spinnerCity;
    private Spinner spinnerCountry;
    private Button buttonSignUp;//signup button
    private ImageButton imgBtnVisibility;//for the visibility of the pass
    private ImageButton imgBtnConfirmVisibility;//for the visibility of the pass
    private ProgressDialog progressDialog;// initialize progress dialog
    private boolean isPasswordVisible = false;//boolean value to check the visiblity of the password
    private boolean isConfirmPasswordVisible = false;//boolean value to check the visiblity of the password

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);// connect it with the xml file

        editTextFirstName = findViewById(R.id.editText_registerFirstName);// find view by id for whole components
        editTextLastName = findViewById(R.id.editText_registerLastName);
        editTextPhoneNumber = findViewById(R.id.editText_editPhoneNumber);
        editTextEmail = findViewById(R.id.editText_registerEmail);
        editTextPassword = findViewById(R.id.editText_registerPassword);
        editTextConfirmPassword = findViewById(R.id.editText_registerConfirmPassword);
        spinnerGender = findViewById(R.id.spinner_registerGender);
        spinnerCity = findViewById(R.id.spinner_registerCity);
        spinnerCountry = findViewById(R.id.spinner_registerCountry);
        buttonSignUp = findViewById(R.id.button_registerSignUp);
        imgBtnVisibility = findViewById(R.id.imgBtn_visibility);
        imgBtnConfirmVisibility = findViewById(R.id.imgBtn_confirm_visibility);
        progressDialog = new ProgressDialog(this);// create a new dialog
        progressDialog.setMessage("Registering the user...");// display loading dialog message
        progressDialog.setCancelable(false);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {//when click at the sign up button
            @Override
            public void onClick(View v) {
                registerUser();// call the regester method
            }
        });

        imgBtnVisibility.setOnClickListener(new View.OnClickListener() {// for the pass visibility button
            @Override
            public void onClick(View v) {
                passvisib(editTextPassword, imgBtnVisibility);//dispay the password and image to visible
            }
        });

        imgBtnConfirmVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passvisib(editTextConfirmPassword, imgBtnConfirmVisibility);//dispay the password and image to visibility
            }
        });
    }

    private void passvisib(EditText passfld, ImageButton tglbtn) {// password visibility method
        if (passfld.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            passfld.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            tglbtn.setImageResource(R.drawable.ic_visible);
        } else {

            passfld.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            tglbtn.setImageResource(R.drawable.ic_invisible);
        }
        passfld.setSelection(passfld.getText().length());  // move the cursor to the end
    }

    private void registerUser() {// method to regester the user
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        String gender = spinnerGender.getSelectedItem().toString();
        String city = spinnerCity.getSelectedItem().toString();
        String country = spinnerCountry.getSelectedItem().toString();

        if (password.equals(confirmPassword)== false) {
            Toast.makeText(this, "The Password is not matching!! ", Toast.LENGTH_SHORT).show();
        } else {
            new RegisterUserTask().execute(firstName, lastName, phoneNumber, email, password, gender, city, country);

        }
    }

    private class RegisterUserTask extends AsyncTask<String, Void, String> {// implement async task to make the user regestered for the dialog

        @Override
        protected void onPreExecute() {// overrid the on pre excute to show the dialog
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {// get array of str parameters
            int responseCode;
            String firstName = params[0];
            String lastName = params[1];
            String phoneNumber = params[2];
            String email = params[3];
            String password = params[4];
            String gender = params[5];
            String city = params[6];
            String country = params[7];

            try {
                URL url = new URL("http://xampp/htdocs/signup.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                Map<String, String> postDataParams = new HashMap<>();
                postDataParams.put("first_name", firstName);
                postDataParams.put("last_name", lastName);
                postDataParams.put("phone_number", phoneNumber);
                postDataParams.put("email", email);
                postDataParams.put("password", password);
                postDataParams.put("gender", gender);
                postDataParams.put("city", city);
                postDataParams.put("country", country);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getstrpostdata(postDataParams));
                writer.flush();
                writer.close();
                os.close();

                 responseCode = conn.getResponseCode();

                if (responseCode != HttpURLConnection.HTTP_OK) {//if the response is not http code
                    return new String("false!! : " + responseCode);//return false
                } else {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));// read the file and get the connection info
                    StringBuilder sb = new StringBuilder("");// string builder to add str on it
                    String line;

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    in.close();// close the file
                    return sb.toString();                }
            } catch (Exception e) {
                return new String("Exception occure: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();// ehn port excute stop the progress
            try {
                JSONObject obj = new JSONObject(result);// create json obj to set the result in it
                boolean success = obj.getBoolean("success");
                String message = obj.getString("message");

                Toast.makeText(SignOut.this, message, Toast.LENGTH_LONG).show();

                if (success==false) {
                    save();// save the user info
                    Intent intent = new Intent(SignOut.this, SignIn.class);// go to the sign in activity
                    startActivity(intent);// start the activite
                    finish();        //end the sign up activity
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(SignOut.this, "Error occure whileparsing server response", Toast.LENGTH_LONG).show();
            }
        }

        private String getstrpostdata(Map<String, String> params) throws Exception {// get the post datea from php
            StringBuilder result = new StringBuilder();
            ArrayList<Map.Entry<String, String>> list = new ArrayList<>(params.entrySet());

            if (list.isEmpty()== false) {
                Map.Entry<String, String> firstEntry = list.get(0);
                result.append(firstEntry.getKey());
                result.append("=");
                result.append(firstEntry.getValue());
            }

            for (int i = 1; i < list.size(); i++) {
                Map.Entry<String, String> entry = list.get(i);
                result.append("&");
                result.append(entry.getKey());
                result.append("=");
                result.append(entry.getValue());
            }

            return result.toString();
        }


        private void save() {// method to save user information using shared prefrences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("firstname", editTextFirstName.getText().toString().trim());
            editor.putString("lastname", editTextLastName.getText().toString().trim());
            editor.putString("phone", editTextPhoneNumber.getText().toString().trim());
            editor.putString("email", editTextEmail.getText().toString().trim());
            editor.putString("gender", spinnerGender.getSelectedItem().toString());
            editor.putString("city", spinnerCity.getSelectedItem().toString());
            editor.putString("country", spinnerCountry.getSelectedItem().toString());
            editor.apply();
        }
    }
}