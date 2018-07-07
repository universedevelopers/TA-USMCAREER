package com.universedeveloper.usmcareer.Perusahaan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.universedeveloper.usmcareer.R;
import com.universedeveloper.usmcareer.Utility.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfilePerusahaan extends AppCompatActivity {
    public final static String TAG_ID_PERUSAHAN = "id_perusahaan";
    private static final String url_detail_profile = "http://universedeveloper.com/gudangandroid/usmcareer/perusahaan/detail_perusahaan.php";
    TextView tvnamaperusahaan, tvemailperusahaan, tvalamatperusahaan, tvkotaperusahaan,tvprovinsiperusahaan, tvtelpperusahaan ;
    String id_perusahaan;
    JSONArray string_json = null;
    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_perusahaan);

        id_perusahaan = getIntent().getStringExtra("id_perusahaan");
        new AmbilDetail().execute();



        Button btneditbiodata = (Button) findViewById(R.id.btneditbiodata);
        btneditbiodata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(view.getContext(), EditProfilePerusahaan.class);
                Intent.putExtra(TAG_ID_PERUSAHAN, id_perusahaan);
                view.getContext().startActivity(Intent);
            }
        });
    }
    class AmbilDetail extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProfilePerusahaan.this);
            pDialog.setMessage("Mohon Tunggu ... !");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... params) {

            try {

                List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                params1.add(new BasicNameValuePair("id_perusahaan",id_perusahaan));

                JSONObject json = jsonParser.makeHttpRequest(url_detail_profile, "GET", params1);
                string_json = json.getJSONArray("perusahaan");

                runOnUiThread(new Runnable() {
                    public void run() {

                        TextView tvnamaperusahaan = (TextView) findViewById(R.id.tvnamaperusahaan);
                        TextView tvemailperusahaan = (TextView) findViewById(R.id.tvemailperusahaan);
                        TextView tvalamatperusahaan = (TextView) findViewById(R.id.tvalamatperusahaan);
                        TextView tvkotaperusahaan = (TextView) findViewById(R.id.tvkotaperusahaan);
                        TextView tvprovinsiperusahaan = (TextView) findViewById(R.id.tvprovinsiperusahaan);
                        TextView tvtelpperusahaan = (TextView) findViewById(R.id.tvtelpperusahaan);

                        try {
                            // ambil objek member pertama dari JSON Array
                            JSONObject ar = string_json.getJSONObject(0);
                            String nama_perusahaan_d = ar.getString("nama_perusahaan");
                            String email_perusahaan_d = ar.getString("email_perusahaan");
                            String alamat_perusahaan_d = ar.getString("alamat_perusahaan");
                            String kota_perusahaan_d = ar.getString("kota_perusahaan");
                            String provinsi_perusahaan_d = ar.getString("provinsi_perusahaan");
                            String telepon_perusahaan_d = ar.getString("telepon_perusahaan");

                            tvnamaperusahaan.setText(nama_perusahaan_d);
                            tvemailperusahaan.setText(email_perusahaan_d);
                            tvalamatperusahaan.setText(alamat_perusahaan_d);
                            tvkotaperusahaan.setText(kota_perusahaan_d);
                            tvprovinsiperusahaan.setText(provinsi_perusahaan_d);
                            tvtelpperusahaan.setText(telepon_perusahaan_d);


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
