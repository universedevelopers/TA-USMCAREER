package com.universedeveloper.usmcareer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.universedeveloper.usmcareer.Utility.AppController;
////import com.dedykuncoro.login.app.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class daftarperusahaan extends AppCompatActivity {

    private static final String TAG = daftarperusahaan.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    public static String my_shared_preferences;
    ProgressDialog pDialog;
    Button btndaftarperusahaan , btn_loginperusahaan;
    EditText txtnamaperusahaan, txtemailperusahaan, txtpasswordperusahaan, txtconfrimpasswordperusahaan, txtteleponperusahaan, txtalamatperusahaan, txtkotaperusahaan, txtprovinsiperusahaan ;
    Intent intent;
    int success;
    ConnectivityManager conMgr;
    String tag_json_obj = "json_obj_req";
    private String url = "http://gudangandroid.universedeveloper.com/usmcareer/register_perusahaan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftarperusahaan);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "Tidak ada koneksi internet",
                        Toast.LENGTH_LONG).show();
            }
        }

        btn_loginperusahaan = (Button) findViewById(R.id.btn_loginperusahaan);
        btndaftarperusahaan = (Button) findViewById(R.id.btndaftarperusahaan);
        txtnamaperusahaan = (EditText) findViewById(R.id.txtnamaperusahaan);
        txtemailperusahaan= (EditText) findViewById(R.id.txtemaiperusahaan);
        txtpasswordperusahaan = (EditText) findViewById(R.id.txtpasswordperusahaan);
        txtconfrimpasswordperusahaan = (EditText) findViewById(R.id.txtconfrimpasswordperusahaan);
        txtteleponperusahaan = (EditText) findViewById(R.id.txtteleponperusahaan);
        txtalamatperusahaan = (EditText) findViewById(R.id.txtalamatperusahaan);
        txtkotaperusahaan = (EditText) findViewById(R.id.txtkotaperusahaan);
        txtprovinsiperusahaan = (EditText) findViewById(R.id.txtprovinsiperusahaan);

        btn_loginperusahaan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                intent = new Intent(daftarperusahaan.this, Login.class);
                finish();
                startActivity(intent);
            }
        });


        btndaftarperusahaan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String nama_perusahaan = txtnamaperusahaan.getText().toString();
                String email_perusahaan = txtemailperusahaan.getText().toString();
                String password_perusahaan = txtpasswordperusahaan.getText().toString();
                String confirmpassword_perusahaan = txtconfrimpasswordperusahaan.getText().toString();
                String telepon_perusahaan = txtteleponperusahaan.getText().toString();
                String alamat_perusahaan = txtalamatperusahaan.getText().toString();
                String kota_perusahaan = txtkotaperusahaan.getText().toString();
                String provinsi_perusahaan = txtprovinsiperusahaan.getText().toString();

                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    checkRegister(nama_perusahaan, email_perusahaan, password_perusahaan, confirmpassword_perusahaan,  telepon_perusahaan, alamat_perusahaan, kota_perusahaan, provinsi_perusahaan);
                } else {
                    Toast.makeText(getApplicationContext(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void checkRegister( final String nama_perusahaan, final String email_perusahaan, final String password_perusahaan, final String confirmpassword_perusahaan, final String telepon_perusahaan, final String alamat_perusahaan, final String kota_perusahaan, final String provinsi_perusahaan) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Silahkan Tunggu ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Registrasi telah berhasil!", jObj.toString());

                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        txtnamaperusahaan.setText("");
                        txtemailperusahaan.setText("");
                        txtpasswordperusahaan.setText("");
                        txtconfrimpasswordperusahaan.setText("");
                        txtalamatperusahaan.setText("");
                        txtteleponperusahaan.setText("");
                        txtkotaperusahaan.setText("");
                        txtprovinsiperusahaan.setText("");

                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nama_perusahaan", nama_perusahaan);
                params.put("email_perusahaan", email_perusahaan);
                params.put("password_perusahaan", password_perusahaan);
                params.put("confirmpassword_perusahaan", confirmpassword_perusahaan);
                params.put("telepon_perusahaan", telepon_perusahaan);
                params.put("alamat_perusahaan", alamat_perusahaan);
                params.put("kota_perusahaan", kota_perusahaan);
                params.put("provinsi_perusahaan", provinsi_perusahaan);

                return params;
            }

        };

        // Adding request to request queue

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
        ///AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        intent = new Intent(daftarperusahaan.this, Login.class);
        finish();
        startActivity(intent);
    }
}

