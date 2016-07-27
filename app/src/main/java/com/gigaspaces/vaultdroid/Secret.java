package com.gigaspaces.vaultdroid;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gigaspaces.vaultdroid.adapters.SecretDataListViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class Secret extends Activity {
    private String mSecretPath;
    private SecretDataListViewAdapter mListViewAdapter;
    private String mAppTitle;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secret);

        mAppTitle = this.getTitle().toString();
        mContext = this;
        setupViews();
    }

    private void setupViews() {
        // Get secret from intent extra
        mSecretPath = getIntent().getStringExtra(getResources().getString(R.string.secretPath));

        this.setTitle(mSecretPath);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String vaultServerIp = sharedPref.getString(getString(R.string.serverIp), "");
        String vaultServerIpPort = sharedPref.getString(getString(R.string.serverIpPort), "");
        String vaultToken = sharedPref.getString(getString(R.string.token), "");

        if (!vaultServerIp.isEmpty() && !vaultServerIpPort.isEmpty() && !vaultToken.isEmpty()) {
            new DisplaySecretDetails(vaultServerIp, vaultServerIpPort, vaultToken).execute();
        }

        mListViewAdapter = new SecretDataListViewAdapter(this, null);
        final ListView mDataListView = (ListView) findViewById(R.id.dataListView);
        mDataListView.setAdapter(mListViewAdapter);
        mDataListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Map.Entry<String, String> item = mListViewAdapter.getItem(position);
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(mAppTitle, item.getValue());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext, R.string.copiedToClipboard, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void fillSecretInfoFields(JSONObject secret) throws JSONException {
        Iterator<?> keys = secret.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();

            switch (key) {
                case "lease_id":
                    TextView leaseIdValue = (TextView) findViewById(R.id.leaseIdValue);
                    leaseIdValue.setText(secret.get(key).toString());
                    break;
                case "renewable":
                    TextView renewableValue = (TextView) findViewById(R.id.renewableValue);
                    renewableValue.setText(secret.get(key).toString());
                    break;
                case "lease_duration":
                    TextView leaseDurationValue = (TextView) findViewById(R.id.leaseDurationValue);
                    leaseDurationValue.setText(secret.get(key).toString());
                    break;
                case "data":
                    JSONObject data = secret.getJSONObject(key);
                    mListViewAdapter.updateParents(data);
                    break;
                case "warnings":
                    TextView warningsValue = (TextView) findViewById(R.id.warningsValue);
                    warningsValue.setText(secret.get(key).toString());
                    break;
                case "auth":
                    TextView authValue = (TextView) findViewById(R.id.authValue);
                    authValue.setText(secret.get(key).toString());
                    break;
            }
        }
    }

    class DisplaySecretDetails extends AsyncTask<String, Void, JSONObject> {
        String mVaultIp;
        String mVaultIpPort;
        String mToken;

        public DisplaySecretDetails(String vaultIp, String vaultIpPort, String token) {
            mVaultIp = vaultIp;
            mVaultIpPort = vaultIpPort;
            mToken = token;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            JSONObject jsonObject = null;
            try {
                URL url = new URL("http://" + mVaultIp + ":" + mVaultIpPort + "/v1/secret/" + mSecretPath);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("X-Vault-Token", mToken);

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                jsonObject = new JSONObject(convertInputStreamToString(in));

            } catch (IOException | JSONException e) {
                Log.e(this.getClass().toString(), e.getMessage());
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }

            return jsonObject;
        }

        protected void onPostExecute(JSONObject secret) {
            Log.d(this.getClass().toString(), "Filling up the fields of: " + secret.toString());
            try {
                fillSecretInfoFields(secret);
            } catch (JSONException e) {
                Log.e(this.getClass().toString(), e.getMessage());
            }
        }

        private String convertInputStreamToString(final InputStream is) {
            final int bufferSize = 1024;
            final char[] buffer = new char[bufferSize];
            final StringBuilder out = new StringBuilder();
            try (Reader in = new InputStreamReader(is, "UTF-8")) {
                for (; ; ) {
                    int rsz = in.read(buffer, 0, buffer.length);
                    if (rsz < 0)
                        break;
                    out.append(buffer, 0, rsz);
                }
            } catch (IOException e) {
                Log.e(this.getClass().toString(), e.getMessage());
            }
            return out.toString();
        }
    }

}
