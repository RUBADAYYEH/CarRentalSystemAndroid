package com.example.carrentalsystem.ui.callus;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carrentalsystem.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CallUsFragment extends Fragment {

    private ImageView phoneImageView, emailImageView, instaImageView, teleImageView, fbookImageView, whatsImageView;
    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;
    private static final String PREFERENCES_FILE = "com.example.myapp.PREFERENCES_FILE";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_us, container, false);

        sharedPreferences = getActivity().getSharedPreferences(PREFERENCES_FILE, getActivity().MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(getActivity());

        phoneImageView = view.findViewById(R.id.phoneImageView);
        emailImageView = view.findViewById(R.id.emailImageView);
        instaImageView = view.findViewById(R.id.instaImageView);
        teleImageView = view.findViewById(R.id.teleImageView);
        fbookImageView = view.findViewById(R.id.fbookImageView);
        whatsImageView = view.findViewById(R.id.whatsImageView);

        phoneImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeCall();
            }
        });

        emailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        instaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInstagram();
            }
        });

        teleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTelegram();
            }
        });

        fbookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFacebook();
            }
        });

        whatsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp();
            }
        });

        fetchContactDetails();

        return view;
    }

    private void makeCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+123456789"));
        startActivity(intent);
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:contact@myapp.com"));
        startActivity(intent);
    }

    private void openInstagram() {
        Uri uri = Uri.parse("http://instagram.com/_u/myapp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.instagram.android");
        try {
            startActivity(intent);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/myapp")));
        }
    }

    private void openTelegram() {
        Uri uri = Uri.parse("https://t.me/myapp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void openFacebook() {
        Uri uri = Uri.parse("fb://page/myapp");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(intent);
        } catch (Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://facebook.com/myapp")));
        }
    }

    private void openWhatsApp() {
        Uri uri = Uri.parse("https://wa.me/123456789");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void fetchContactDetails() {
        String url = "https://api.myapp.com/contact-details";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String phone = response.getString("phone");
                            String email = response.getString("email");
                            String instagram = response.getString("instagram");
                            String telegram = response.getString("telegram");
                            String facebook = response.getString("facebook");
                            String whatsapp = response.getString("whatsapp");

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("phone", phone);
                            editor.putString("email", email);
                            editor.putString("instagram", instagram);
                            editor.putString("telegram", telegram);
                            editor.putString("facebook", facebook);
                            editor.putString("whatsapp", whatsapp);
                            editor.apply();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }
}
