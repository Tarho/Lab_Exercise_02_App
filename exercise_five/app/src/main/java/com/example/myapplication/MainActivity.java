package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Method to handle button click to open Google Maps
    public void SubmitBTN(View view) {
        Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void sendEmail(View view) {
        // Email address to send the email to
        String[] recipient = {"recipient@example.com"};

        // Subject of the email
        String subject = "Subject of the email";

        // Content of the email
        String emailContent = "Content of the email goes here.";

        // Create an intent to send an email
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822"); // Define the MIME type
        intent.putExtra(Intent.EXTRA_EMAIL, recipient); // Set the recipient email addresses
        intent.putExtra(Intent.EXTRA_SUBJECT, subject); // Set the subject of the email
        intent.putExtra(Intent.EXTRA_TEXT, emailContent); // Set the content of the email

        // Verify that the intent will resolve to an activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "Send Email")); // Display the chooser dialog
        } else {
            Toast.makeText(this, "No email app found", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareText(View view) {
        // Text content to share
        String textToShare = "This is the text I want to share.";

        // Create an intent to share text
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, textToShare);

        // Verify that the intent will resolve to an activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(Intent.createChooser(intent, "Share Text")); // Display the chooser dialog
        } else {
            Toast.makeText(this, "No app found to handle text sharing", Toast.LENGTH_SHORT).show();
        }
    }
}
