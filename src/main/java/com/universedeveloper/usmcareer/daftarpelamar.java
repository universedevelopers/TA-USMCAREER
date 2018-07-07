package com.universedeveloper.usmcareer;

import android.app.ProgressDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Calendar;

public class daftarpelamar extends AppCompatActivity  {

    private static final String TAG = daftarpelamar.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    public static String my_shared_preferences;
    ProgressDialog pDialog;
    Button btndaftarpelamar , btnloginpelamar, btntanggal;
    EditText txtnamapelamar, txtemailpelamar, txtpasswordpelamar, txtconfrimpasswordpelamar,  txtteleponpelamar, txtalamatpelamar, txtkotapelamar, txtprovinsipelamar ;
    Spinner spinnerkelamin;
    Intent intent;
    DatePicker datePicker1;
    int success;
    ConnectivityManager conMgr;
    String tag_json_obj = "json_obj_req";
    private int mYear, mMonth, mDay;
    private String url = "http://gudangandroid.universedeveloper.com/usmcareer/register_pelamar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftarpelamar);

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

        btnloginpelamar = (Button) findViewById(R.id.btnloginpelamar);
        btndaftarpelamar = (Button) findViewById(R.id.btndaftarpelamar);
        txtnamapelamar = (EditText) findViewById(R.id.txtnamapelamar);
        txtemailpelamar= (EditText) findViewById(R.id.txtemailpelamar);
        txtpasswordpelamar = (EditText) findViewById(R.id.txtpasswordpelamar);
        txtconfrimpasswordpelamar = (EditText) findViewById(R.id.txtconfrimpasswordpelamar);
        ////txttanggallahir = (EditText) findViewById(R.id.txttanggallahir);
        txtalamatpelamar = (EditText) findViewById(R.id.txtalamatpelamar);
        txtkotapelamar = (EditText) findViewById(R.id.txtkotapelamar);
        txtprovinsipelamar = (EditText) findViewById(R.id.txtprovinsipelamar);
        txtteleponpelamar = (EditText) findViewById(R.id.txtteleponpelamar);
        spinnerkelamin = (Spinner) findViewById(R.id.spinnerkelamin) ;
        datePicker1 = (DatePicker) findViewById(R.id.datePicker1);


        btnloginpelamar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                intent = new Intent(daftarpelamar.this, Login.class);
                finish();
                startActivity(intent);
            }
        });

      ///
        btndaftarpelamar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String nama_lengkap = txtnamapelamar.getText().toString();
                String email_pelamar = txtemailpelamar.getText().toString();
                String password_pelamar = txtpasswordpelamar.getText().toString();
                String confirmpassword_pelamar = txtconfrimpasswordpelamar.getText().toString();
                String tggl_lahir = getDate(datePicker1).toString();
                String alamat_pelamar = txtalamatpelamar.getText().toString();
                String kota_pelamar = txtkotapelamar.getText().toString();
                String provinsi_pelamar = txtprovinsipelamar.getText().toString();
                String telepon = txtteleponpelamar.getText().toString();
                String jeniskelamin = spinnerkelamin.getSelectedItem().toString();

                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    checkRegister(nama_lengkap, email_pelamar, password_pelamar, confirmpassword_pelamar, jeniskelamin, tggl_lahir, alamat_pelamar, kota_pelamar, provinsi_pelamar, telepon);
                } else {
                    Toast.makeText(getApplicationContext(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private String getDate(DatePicker dp)
    {
        int day = dp.getDayOfMonth();
        int month = dp.getMonth()+1;
        int year = dp.getYear();
        return String.valueOf(year)+"-"+ String.valueOf(month)+"-"+ String.valueOf(day);

    }

    private void checkRegister(final String nama_lengkap,final String email_pelamar,final String password_pelamar,final String confirmpassword_pelamar, final String jeniskelamin, final String tggl_lahir,final String alamat_pelamar,final String kota_pelamar,final String provinsi_pelamar,final String telepon) {
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

                        txtnamapelamar.setText("");
                        txtemailpelamar.setText("");
                        txtpasswordpelamar.setText("");
                        txtconfrimpasswordpelamar.setText("");
                        txtalamatpelamar.setText("");
                        txtkotapelamar.setText("");
                        txtprovinsipelamar.setText("");
                        txtteleponpelamar.setText("");

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
                params.put("nama_lengkap", nama_lengkap);
                params.put("email_pelamar", email_pelamar);
                params.put("password_pelamar", password_pelamar);
                params.put("confirmpassword_pelamar", confirmpassword_pelamar);
                params.put("jeniskelamin", jeniskelamin);
                params.put("tggl_lahir", tggl_lahir);
                params.put("alamat_pelamar", alamat_pelamar);
                params.put("kota_pelamar", kota_pelamar);
                params.put("provinsi_pelamar", provinsi_pelamar);
                params.put("telepon", telepon);

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
        intent = new Intent(daftarpelamar.this, Login.class);
        finish();
        startActivity(intent);
    }


}

