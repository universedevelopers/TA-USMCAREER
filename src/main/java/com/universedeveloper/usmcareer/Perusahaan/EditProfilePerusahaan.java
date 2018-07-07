package com.universedeveloper.usmcareer.Perusahaan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.universedeveloper.usmcareer.R;
import com.universedeveloper.usmcareer.Utility.AppController;
import com.universedeveloper.usmcareer.Utility.JSONParser;
import com.universedeveloper.usmcareer.daftarperusahaan;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditProfilePerusahaan extends AppCompatActivity {
    private static final String TAG = EditProfilePerusahaan.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    EditText editnamaperusahaan, editemailperusahaan, editpasswordperusahaan, editteleponperusahaan, editalamatperusahaan, editkotaperusahaan, editprovinsiperusahaan;
    String id_perusahaan, nama_perusahaan , email_perusahaan , password_perusahaan, alamat_perusahaan, kota_perusahaan, provinsi_perusahaan, telepon_perusahaan;
    Button btnsimpanperusahaan;
    ConnectivityManager conMgr;
    ProgressDialog pDialog;
    JSONArray string_json = null;
    JSONParser jsonParser = new JSONParser();
    String tag_json_obj = "json_obj_req";
    Intent i;
    int success;
    private String url_detail_profile = "http://gudangandroid.universedeveloper.com/usmcareer/perusahaan/detail_profileperusahaan.php";
    private String url_update_profile = "http://gudangandroid.universedeveloper.com/usmcareer/perusahaan/update_profileperusahaan.php";  //directory php ning server

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofil__perusahaan);
        new AmbilDetail().execute();
        id_perusahaan = getIntent().getStringExtra("id_perusahaan");
       /// Toast.makeText(this, "id "+ id_perusahaan, Toast.LENGTH_SHORT).show();
        nama_perusahaan = getIntent().getStringExtra("nama_perusahaan");
        email_perusahaan = getIntent().getStringExtra("email_perusahaan");
        password_perusahaan = getIntent().getStringExtra("password_perusahaan");
        alamat_perusahaan = getIntent().getStringExtra("alamat_perusahaan");
        kota_perusahaan = getIntent().getStringExtra("kota_perusahaan");
        provinsi_perusahaan = getIntent().getStringExtra("provinsi_perusahaan");
        telepon_perusahaan = getIntent().getStringExtra("telepon_perusahaan");

        btnsimpanperusahaan = (Button) findViewById(R.id.btnsimpanperusahaan);
        editnamaperusahaan = (EditText) findViewById(R.id.editnamaperusahaan);
        editemailperusahaan= (EditText) findViewById(R.id.editemailperusahaan);
        editpasswordperusahaan = (EditText) findViewById(R.id.editpasswordperusahaan);
        editteleponperusahaan = (EditText) findViewById(R.id.editteleponperusahaan);
        editalamatperusahaan = (EditText) findViewById(R.id.editalamatperusahaan);
        editkotaperusahaan = (EditText) findViewById(R.id.editkotaperusahaan);
        editprovinsiperusahaan = (EditText) findViewById(R.id.editprovinsiperusahaan);

        editnamaperusahaan.setText(nama_perusahaan);
        editemailperusahaan.setText(email_perusahaan);
        editpasswordperusahaan.setText(password_perusahaan);
        editalamatperusahaan.setText(alamat_perusahaan);
        editkotaperusahaan.setText(kota_perusahaan);
        editprovinsiperusahaan.setText(provinsi_perusahaan);
        editteleponperusahaan.setText(telepon_perusahaan);

        btnsimpanperusahaan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                /// String nama_perusahaan = editnamaperusahaan.getText().toString();
                /// String email_perusahaan = editemailperusahaan.getText().toString();
                /// String password_perusahaan = editpasswordperusahaan.getText().toString();
                /// String telepon_perusahaan = editteleponperusahaan.getText().toString();
                ///  String alamat_perusahaan = editalamatperusahaan.getText().toString();
                /// String kota_perusahaan = editkotaperusahaan.getText().toString();
                ///  String provinsi_perusahaan = editprovinsiperusahaan.getText().toString();

                ///id_perusahaan = getIntent().getStringExtra("id_perusahaan");
                /// if (conMgr.getActiveNetworkInfo() != null
                ///  && conMgr.getActiveNetworkInfo().isAvailable()
                ///   && conMgr.getActiveNetworkInfo().isConnected()) {
                 checkData();
              ///  nama_perusahaan, email_perusahaan, password_perusahaan,  telepon_perusahaan, alamat_perusahaan, kota_perusahaan, provinsi_perusahaan
                /// } else {

                }
            });
        }


  ///  final String nama_perusahaan,final String email_perusahaan,final String password_perusahaan,final String telepon_perusahaan,final String alamat_perusahaan,final String kota_perusahaan,final String provinsi_perusahaan
    private void checkData() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Proses update data ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, url_update_profile, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Update data: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Update data telah berhasil!", jObj.toString());
                        Toast.makeText(EditProfilePerusahaan.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        ////Toast.makeText(getApplicationContext(),
                               ///// jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                       editnamaperusahaan.setText("");
                        editemailperusahaan.setText("");
                        editalamatperusahaan.setText("");
                       editpasswordperusahaan.setText("");
                        editkotaperusahaan.setText("");
                        editprovinsiperusahaan.setText("");
                        editteleponperusahaan.setText("");

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
                Log.e(TAG, "Update data Error: " + error.getMessage());

                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_perusahaan", id_perusahaan  );
                params.put("nama_perusahaan", editnamaperusahaan.getText().toString());
                params.put("email_perusahaan", editemailperusahaan.getText().toString());
                params.put("password_perusahaan", editpasswordperusahaan.getText().toString());
                params.put("telepon_perusahaan", editteleponperusahaan.getText().toString());
                params.put("alamat_perusahaan", editalamatperusahaan.getText().toString());
                params.put("kota_perusahaan", editkotaperusahaan.getText().toString());
                params.put("provinsi_perusahaan", editprovinsiperusahaan.getText().toString());


               // params.put("nama_perusahaan", nama_perusahaan);
              //  params.put("email_perusahaan", email_perusahaan);
               // params.put("password_perusahaan", password_perusahaan);
               // params.put("telepon_perusahaan", telepon_perusahaan);
               // params.put("alamat_perusahaan", alamat_perusahaan);
               /// params.put("kota_perusahaan", kota_perusahaan);
               /// params.put("provinsi_perusahaan", provinsi_perusahaan);
                Log.e(TAG, "" + params);
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

    class AmbilDetail extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditProfilePerusahaan.this);
            pDialog.setMessage("Loading data ... !");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {

            try {

                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("id_perusahaan", id_perusahaan));

                JSONObject json = jsonParser.makeHttpRequest(url_detail_profile, "GET", params1);
                string_json = json.getJSONArray("perusahaan");

                runOnUiThread(new Runnable() {
                    public void run() {

                        TextView editnamaperusahaan = (TextView) findViewById(R.id.editnamaperusahaan);
                        TextView editemailperusahaan = (TextView) findViewById(R.id.editemailperusahaan);
                        TextView editpasswordperusahaan = (TextView) findViewById(R.id.editpasswordperusahaan);
                        TextView editalamatperusahaan = (TextView) findViewById(R.id.editalamatperusahaan);
                        TextView editkotaperusahaan = (TextView) findViewById(R.id.editkotaperusahaan);
                        TextView editprovinsiperusahaan = (TextView) findViewById(R.id.editprovinsiperusahaan);
                        TextView editteleponperusahaan = (TextView) findViewById(R.id.editteleponperusahaan);

                        try {
                            // ambil objek member pertama dari JSON Array
                            JSONObject ar = string_json.getJSONObject(0);
                            String nama_perusahaan_d = ar.getString("nama_perusahaan");
                            String email_perusahaan_d = ar.getString("email_perusahaan");
                            String password_perusahaan_d = ar.getString("password_perusahaan");
                            String alamat_perusahaan_d = ar.getString("alamat_perusahaan");
                            String kota_perusahaan_d = ar.getString("kota_perusahaan");
                            String provinsi_perusahaan_d = ar.getString("provinsi_perusahaan");
                            String telepon_perusahaan_d = ar.getString("telepon_perusahaan");

                            editnamaperusahaan.setText(nama_perusahaan_d);
                            editemailperusahaan.setText(email_perusahaan_d);
                            editpasswordperusahaan.setText(password_perusahaan_d);
                            editalamatperusahaan.setText(alamat_perusahaan_d);
                            editkotaperusahaan.setText(kota_perusahaan_d);
                            editprovinsiperusahaan.setText(provinsi_perusahaan_d);
                            editteleponperusahaan.setText(telepon_perusahaan_d);


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();

                        }
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }





        protected void onPostExecute(String file_url) {

            pDialog.dismiss();
        }


    }
    }


