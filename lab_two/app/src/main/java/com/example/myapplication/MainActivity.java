package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    TextView textViewGender;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextText);
        button = findViewById(R.id.button);
        textViewGender = findViewById(R.id.textViewGender);
        imageView = findViewById(R.id.imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageUrl = editText.getText().toString();
                new LoadImageTask().execute(imageUrl);
                new FaceDetectionTask().execute(imageUrl);
            }
        });
    }

    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            String imageUrl = strings[0];
            Bitmap bitmap = null;

            try {
                InputStream inputStream = new URL(imageUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);
            } else {
                imageView.setVisibility(View.GONE);
            }
        }
    }

    private class FaceDetectionTask extends AsyncTask<String, Void, String> {

        private static final String API_KEY = "PP7_KfgF-5mJbq4Wv6pRgOQ-C2_zSos1";
        private static final String API_SECRET = "E01q8ugCM1ZfquFpesXp7yXfLQgZcoY3";
        private static final String API_URL = "https://api-us.faceplusplus.com/facepp/v3/detect";

        @Override
        protected String doInBackground(String... strings) {
            String imageUrl = strings[0];
            String gender = null;

            try {
                URL url = new URL(API_URL + "?api_key=" + API_KEY + "&api_secret=" + API_SECRET + "&image_url=" + imageUrl + "&return_attributes=gender");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray faces = jsonObject.getJSONArray("faces");
                    if (faces.length() > 0) {
                        JSONObject attributes = faces.getJSONObject(0).getJSONObject("attributes");
                        JSONObject genderObject = attributes.getJSONObject("gender");
                        gender = genderObject.getString("value");
                    }
                } else {
                    // Handle HTTP error response
                }
                connection.disconnect();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return gender;
        }

        @Override
        protected void onPostExecute(String gender) {
            if (gender != null) {
                textViewGender.setText("Gender: " + gender);
            } else {
                textViewGender.setText("Failed to detect gender");
            }
        }
    }
}
