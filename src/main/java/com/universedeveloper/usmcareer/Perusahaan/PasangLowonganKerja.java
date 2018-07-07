package com.universedeveloper.usmcareer.Perusahaan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.universedeveloper.usmcareer.R;
import com.universedeveloper.usmcareer.Utility.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PasangLowonganKerja extends AppCompatActivity {

    private static final String TAG = PasangLowonganKerja.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    public static String my_shared_preferences;
    ProgressDialog pDialog;
    Button btnsubmit ;
    Spinner kategoripekerjaan;
    String id_perusahaan,pekerjaan_dipilih;
    DatePicker datePicker1;
    EditText txtjudulpekerjaan, txtdeskripsi, txtgaji ;
    Intent intent;
    String spinner;
    int success;
    ConnectivityManager conMgr;
    String tag_json_obj = "json_obj_req";
    private String url = "http://gudangandroid.universedeveloper.com/usmcareer/perusahaan/pasang_lowongankerja.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasang_lowongan_kerja);

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

        id_perusahaan = getIntent().getStringExtra("id_perusahaan");


        btnsubmit = (Button) findViewById(R.id.btnsubmit);
        txtjudulpekerjaan = (EditText) findViewById(R.id.txtjudulpekerjaan);
        txtdeskripsi = (EditText) findViewById(R.id.txtdeskripsi);
        txtgaji = (EditText) findViewById(R.id.txtgaji);
        kategoripekerjaan = (Spinner) findViewById(R.id.kategoripekerjaan);
        datePicker1 = (DatePicker) findViewById(R.id.datePicker1);
        pekerjaan_dipilih = kategoripekerjaan.getSelectedItem().toString();



        btnsubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String judul_lowongan = txtjudulpekerjaan.getText().toString();
                String deskripsi = txtdeskripsi.getText().toString();
                String gaji = txtgaji.getText().toString();
                String kategori = kategoripekerjaan.getSelectedItem().toString();
                String deadline = getDate(datePicker1).toString();


                if (conMgr.getActiveNetworkInfo() != null
                        && conMgr.getActiveNetworkInfo().isAvailable()
                        && conMgr.getActiveNetworkInfo().isConnected()) {
                    checkData(judul_lowongan, deskripsi, gaji, kategori,  deadline);
                } else {
                    Toast.makeText(getApplicationContext(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void checkData(final String judul_lowongan, final String deskripsi,final  String gaji,final  String kategori,final  String deadline) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Upload Data ...");
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

                        txtdeskripsi.setText("");
                        txtjudulpekerjaan.setText("");
                        txtgaji.setText("");
                        pekerjaan_dipilih.equals("Pilih Kategori Pekerjaan");


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
                params.put("id_perusahaan", id_perusahaan);
                params.put("judul_lowongan", judul_lowongan);
                params.put("deskripsi", deskripsi);
                params.put("gaji", gaji);
                params.put("kategori", kategori);
                params.put("deadline", deadline);

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

    private String getDate(DatePicker dp)
    {
        int day = dp.getDayOfMonth();
        int month = dp.getMonth()+1;
        int year = dp.getYear();
        return String.valueOf(year)+"-"+ String.valueOf(month)+"-"+ String.valueOf(day);

    }
}
