package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtName;
    TextView txtEmail;
    TextView txtProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtProject = findViewById(R.id.txtProject);

        Button btnViewContact = findViewById(R.id.btnViewContact);
        btnViewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGetContactInfo = new Intent(MainActivity.this, ViewContactActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nameKey",  txtName.getText().toString());
                bundle.putString("emailKey",  txtEmail.getText().toString());
                bundle.putString("projectKey",  txtProject.getText().toString());

                iGetContactInfo.putExtras(bundle);
                startActivity(iGetContactInfo);
            }
        });
    }
}
