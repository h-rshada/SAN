package com.example.android.san;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ContactUs extends AppCompatActivity {
    @InjectView(R.id.img_mail)
    Button mail;
    @InjectView(R.id.img_number)
    Button number;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.inject(this);

    }

    @OnClick({R.id.img_mail, R.id.img_number})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_mail:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc802");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"xoxytech@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Any subject if you want");
                intent.setPackage("com.google.android.gm");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                    finish();
                }

                else
                    Toast.makeText(this, "Gmail App is not installed", Toast.LENGTH_SHORT).show();
                break;

            case R.id.img_number:

                intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:7719869229"));
                if (ContextCompat.checkSelfPermission(ContactUs.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ContactUs.this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                } else {

                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ContactUs.this, HomeActivity.class);
        finish();
        startActivity(intent);

    }

}
