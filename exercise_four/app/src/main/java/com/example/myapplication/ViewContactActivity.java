package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewContactActivity extends MainActivity {
    TextView txtNameValue;
    TextView txtEmailValue;
    TextView txtProjectValue;

    Button finishBtn;

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.contactinfo);

        txtNameValue = findViewById(R.id.txtNameValue);
        txtEmailValue = findViewById(R.id.txtEmailValue);
        txtProjectValue = findViewById(R.id.txtProjectValue);

        finishBtn = (Button) findViewById(R.id.btnFinish);
        finishBtn.setOnClickListener(mClickFinishListener);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("nameKey");
        String email = bundle.getString("emailKey");
        String project = bundle.getString("projectKey");

        txtNameValue.setText(name);
        txtEmailValue.setText(email);
        txtProjectValue.setText(project);
    }

    private View.OnClickListener mClickFinishListener = new  View.OnClickListener(){
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
