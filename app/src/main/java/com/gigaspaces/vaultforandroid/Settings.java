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
    EditText mVaultServerIp;
    EditText mVaultServerIpPort;
    EditText mVaultToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        setupViews();
    }

    private void setupViews() {
        final Button saveButton = (Button) findViewById(R.id.saveButton);
        final Context context = this;

        mVaultServerIp = (EditText) findViewById(R.id.vaultServerIp);
        mVaultServerIpPort = (EditText) findViewById(R.id.vaultServerIpPort);
        mVaultToken = (EditText) findViewById(R.id.vaultToken);

        mVaultServerIp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mVaultServerIpPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mVaultToken.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vaultServerIp = mVaultServerIp.getText().toString();
                if (!vaultServerIp.contains(":")) {
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.serverIp), mVaultServerIp.getText().toString());
                    editor.putString(getString(R.string.serverIpPort), mVaultServerIpPort.getText().toString());
                    editor.putString(getString(R.string.token), mVaultToken.getText().toString());
                    editor.apply();
                    saveButton.setEnabled(false);

                    Toast.makeText(context, R.string.toastSaved, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, R.string.mistplacesPortNumber, Toast.LENGTH_LONG).show();
                }
            }
        });

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String vaultServerIp = sharedPref.getString(getString(R.string.serverIp), "");
        String vaultServerIpPort = sharedPref.getString(getString(R.string.serverIpPort), "");
        String vaultToken = sharedPref.getString(getString(R.string.token), "");

        if (!vaultServerIp.isEmpty() && !vaultToken.isEmpty()) {
            mVaultServerIp.setText(vaultServerIp);
            mVaultServerIpPort.setText(vaultServerIpPort);
            mVaultToken.setText(vaultToken);
            saveButton.setEnabled(false);
        }
    }
}
