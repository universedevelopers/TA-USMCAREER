package com.universedeveloper.usmcareer.Pelamar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.universedeveloper.usmcareer.Perusahaan.EditProfilePerusahaan;
import com.universedeveloper.usmcareer.R;
import com.universedeveloper.usmcareer.Utility.AppController;
import com.universedeveloper.usmcareer.Utility.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.DatePicker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Calendar;


public class EditProfilePelamar extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = EditProfilePelamar.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    EditText editnamapelamar, editemailpelamar, editpasswordpelamar, editteleponpelamar,editalamatpelamar, editkotapelamar, editprovinsipelamar ,edittanggallahir;
    Button btnuploadfoto, btnsimpanpelamar ;
    ImageButton btntanggal;
    String id_user, nama_lengkap, email_pelamar, password_pelamar, tggl_lahir, alamat_pelamar, kota_pelamar, provinsi_pelamar, telepon, foto;
    Spinner jeniskelamin;
    DatePicker datePicker1;
    ImageView image;
    ConnectivityManager conMgr;
    ProgressDialog pDialog;
    JSONArray string_json = null;
    JSONParser jsonParser = new JSONParser();
    String tag_json_obj = "json_obj_req";
    Intent i;
    int success;
    String pilihan_kelamin, pilihan_tgl;
    private String url_detail_profile = "http://gudangandroid.universedeveloper.com/usmcareer/pelamar/detail_pelamar.php";
    private String url_update_profile = "http://gudangandroid.universedeveloper.com/usmcareer/pelamar/update_profilepelamar.php";  //directory php ning server
    private int mYear, mMonth, mDay;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile_pelamar);
        new AmbilDetail().execute();

        id_user = getIntent().getStringExtra("id_user");
         Toast.makeText(this, "id "+ id_user, Toast.LENGTH_SHORT).show();
        nama_lengkap = getIntent().getStringExtra("nama_lengkap");
        email_pelamar = getIntent().getStringExtra("email_pelamar");
        password_pelamar = getIntent().getStringExtra("password_pelamar");
        tggl_lahir = getIntent().getStringExtra("tggl_lahir");
        ////tggl_lahir = getApplicationContext().("tggl_lahir");
        alamat_pelamar = getIntent().getStringExtra("alamat_pelamar");
        kota_pelamar = getIntent().getStringExtra("kota_perusahaan");
        provinsi_pelamar = getIntent().getStringExtra("provinsi_pelamar");
        telepon = getIntent().getStringExtra("telepon");
        foto = getIntent().getStringExtra("foto");

        btnsimpanpelamar = (Button) findViewById(R.id.btnsimpanpelamar);
        btntanggal = (ImageButton) findViewById(R.id.btntanggal);
        editnamapelamar = (EditText) findViewById(R.id.editnamapelamar);
        editemailpelamar= (EditText) findViewById(R.id.editemailpelamar);
        editpasswordpelamar = (EditText) findViewById(R.id.editpasswordpelamar);
        editalamatpelamar = (EditText) findViewById(R.id.editalamatpelamar);
        edittanggallahir = (EditText) findViewById(R.id.edittanggallahir);
        editkotapelamar = (EditText) findViewById(R.id.editkotapelamar);
        editprovinsipelamar = (EditText) findViewById(R.id.editprovinsipelamar);
        editteleponpelamar = (EditText) findViewById(R.id.editteleponpelamar);
        jeniskelamin = (Spinner) findViewById(R.id.jeniskelamin);

        ///datePicker1 = (DatePicker) findViewById(R.id.datePicker1);

        pilihan_kelamin = jeniskelamin.getSelectedItem().toString();


        editnamapelamar.setText(nama_lengkap);
        editemailpelamar.setText(email_pelamar);
        editpasswordpelamar.setText(password_pelamar);
        edittanggallahir.setText(tggl_lahir);
        editteleponpelamar.setText(telepon);
        editalamatpelamar.setText(alamat_pelamar);
        editkotapelamar.setText(kota_pelamar);
        editprovinsipelamar.setText(provinsi_pelamar);

        btntanggal.setOnClickListener(this);
        btnsimpanpelamar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pilihan_kelamin = jeniskelamin.getSelectedItem().toString();
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
    private void checkData() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Menyimpan data ...");
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, url_update_profile, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Menyimpan data: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        Log.e("Data berhasil disimpan!", jObj.toString());
                        Toast.makeText(EditProfilePelamar.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        ////Toast.makeText(getApplicationContext(),
                        ///// jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        editnamapelamar.setText("");
                        editemailpelamar.setText("");
                        editpasswordpelamar.setText("");
                        edittanggallahir.setText("");
                        editalamatpelamar.setText("");
                        editkotapelamar.setText("");
                        editprovinsipelamar.setText("");
                        editteleponpelamar.setText("");


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

            ///String id_user, nama_lengkap, email_pelamar, password_pelamar, tggl_lahir, alamat_pelamar, kota_pelamar, provinsi_pelamar, telepon, foto;
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id_user", id_user  );
                params.put("nama_lengkap", editnamapelamar.getText().toString());
                params.put("email_pelamar", editemailpelamar.getText().toString());
                params.put("password_pelamar", editpasswordpelamar.getText().toString());
                params.put("jeniskelamin", pilihan_kelamin);
                params.put("tggl_lahir", edittanggallahir.getText().toString());
                params.put("kota_pelamar", editkotapelamar.getText().toString());
                params.put("provinsi_pelamar", editprovinsipelamar.getText().toString());
                params.put("telepon", editteleponpelamar.getText().toString());
                params.put("alamat_pelamar", editalamatpelamar.getText().toString());


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btntanggal:

                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                edittanggallahir.setText( year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
        }
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
            pDialog = new ProgressDialog(EditProfilePelamar.this);
            pDialog.setMessage("Mohon Tunggu ... !");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {

            try {

                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("id_user",id_user));

                JSONObject json = jsonParser.makeHttpRequest(url_detail_profile, "GET", params1);
                string_json = json.getJSONArray("pelamar");

                runOnUiThread(new Runnable() {
                    public void run() {

                        TextView editnamapelamar = (TextView) findViewById(R.id.editnamapelamar);
                        TextView editemailpelamar = (TextView) findViewById(R.id.editemailpelamar);
                        TextView editpasswordpelamar = (TextView) findViewById(R.id.editpasswordpelamar);
                        Spinner jeniskelamin = (Spinner) findViewById(R.id.jeniskelamin);
                        TextView edittanggallahir = (TextView) findViewById(R.id.edittanggallahir);
                        ///DatePicker datePicker1 = (DatePicker) findViewById(R.id.datePicker1);
                        TextView editalamatpelamar = (TextView) findViewById(R.id.editalamatpelamar);
                        TextView editkotapelamar = (TextView) findViewById(R.id.editkotapelamar);
                        TextView editprovinsipelamar = (TextView) findViewById(R.id.editprovinsipelamar);
                        TextView editteleponpelamar = (TextView) findViewById(R.id.editteleponpelamar);

                        try {
                            // ambil objek member pertama dari JSON Array
                            JSONObject ar = string_json.getJSONObject(0);
                            String namalengkap_pelamar_d = ar.getString("nama_lengkap");
                            String email_pelamar_d = ar.getString("email_pelamar");
                            String password_pelamar_d = ar.getString("password_pelamar");
                            String jeniskelamin_pelamar_d = ar.getString("jeniskelamin");
                            String tanggallahir_pelamar_d = ar.getString("tggl_lahir");
                            String alamat_pelamar_d = ar.getString("alamat_pelamar");
                            String kota_pelamar_d = ar.getString("kota_pelamar");
                            String provinsi_pelamar_d = ar.getString("provinsi_pelamar");
                            String telepon_pelamar_d = ar.getString("telepon");
                            String foto_pelamar_d = ar.getString("foto");

                           editnamapelamar.setText(namalengkap_pelamar_d);
                           editemailpelamar.setText(email_pelamar_d);
                           editpasswordpelamar.setText(password_pelamar_d);
                           jeniskelamin.setSelected(Boolean.parseBoolean(jeniskelamin_pelamar_d));
                           edittanggallahir.setText(tanggallahir_pelamar_d);
                           ///datePicker1.setEnabled(Boolean.parseBoolean(tanggallahir_pelamar_d));
                           editalamatpelamar.setText(alamat_pelamar_d);
                           editkotapelamar.setText(kota_pelamar_d);
                           editprovinsipelamar.setText(provinsi_pelamar_d);
                           editteleponpelamar.setText(telepon_pelamar_d);


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
