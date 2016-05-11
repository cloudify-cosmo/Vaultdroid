package com.gigaspaces.vaultforandroid;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends Activity {
    EditText vaultServerIp;
    EditText vaultToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        setupViews();
    }

    private void setupViews() {
        final Button saveButton = (Button) findViewById(R.id.saveButton);
        final Context context = this;

        vaultServerIp = (EditText) findViewById(R.id.vaultServerIp);
        vaultToken = (EditText) findViewById(R.id.vaultToken);

        vaultServerIp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        vaultToken.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.serverIp), vaultServerIp.getText().toString());
                editor.putString(getString(R.string.token), vaultToken.getText().toString());
                editor.apply();
                saveButton.setEnabled(false);

                Toast.makeText(context, R.string.toastSaved, Toast.LENGTH_SHORT).show();
            }
        });

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String vaultServerIpText = sharedPref.getString(getString(R.string.serverIp), "");
        String vaultTokenText = sharedPref.getString(getString(R.string.token), "");

        if (!vaultServerIpText.isEmpty() && !vaultTokenText.isEmpty()) {
            vaultServerIp.setText(vaultServerIpText);
            vaultToken.setText(vaultTokenText);
            saveButton.setEnabled(false);
        }
    }
}
