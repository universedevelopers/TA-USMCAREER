package com.universedeveloper.usmcareer.Pelamar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.universedeveloper.usmcareer.Pelamar.ProfilePelamar;
import com.universedeveloper.usmcareer.R;
import com.universedeveloper.usmcareer.Utility.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfilePelamar extends AppCompatActivity {
    public final static String TAG_ID_PELAMAR = "id_user";
    private static final String url_detail_profile = "http://universedeveloper.com/gudangandroid/usmcareer/pelamar/detail_pelamar.php";
    TextView   tvnamalengkappelamar, tvemailpelamar, tvjeniskelaminpelamar, tvtanggallahirpelamar, tvalamatpelamar, tvkotapelamar, tvprovinsipelamar, tvteleponpelamar ;
    String id_user;
    JSONArray string_json = null;
    JSONParser jsonParser = new JSONParser();
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pelamar);

        id_user = getIntent().getStringExtra("id_user");
        Toast.makeText(this, "id "+ id_user, Toast.LENGTH_SHORT).show();
        new AmbilDetail().execute();


        Button btneditbiodatapelamar = (Button) findViewById(R.id.btneditbiodatapelamar);
        btneditbiodatapelamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(view.getContext(), EditProfilePelamar.class);
                Intent.putExtra(TAG_ID_PELAMAR, id_user);
                view.getContext().startActivity(Intent);
            }
        });
    }

    class AmbilDetail extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProfilePelamar.this);
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



                        TextView tvnamalengkappelamar = (TextView) findViewById(R.id.tvnamalengkappelamar);
                        TextView tvemailpelamar = (TextView) findViewById(R.id.tvemailpelamar);
                        TextView tvjeniskelaminpelamar = (TextView) findViewById(R.id.tvjeniskelaminpelamar);
                        TextView tvtanggallahirpelamar = (TextView) findViewById(R.id.tvtanggallahirpelamar);
                        TextView tvalamatpelamar = (TextView) findViewById(R.id.tvalamatpelamar);
                        TextView tvkotapelamar = (TextView) findViewById(R.id.tvkotapelamar);
                        TextView tvprovinsipelamar = (TextView) findViewById(R.id.tvprovinsipelamar);
                        TextView tvteleponpelamar = (TextView) findViewById(R.id.tvteleponpelamar);

                        try {
                            // ambil objek member pertama dari JSON Array
                            JSONObject ar = string_json.getJSONObject(0);
                            String namalengkap_pelamar_d = ar.getString("nama_lengkap");
                            String email_pelamar_d = ar.getString("email_pelamar");
                            String jeniskelamin_pelamar_d = ar.getString("jeniskelamin");
                            String tanggallahir_pelamar_d = ar.getString("tggl_lahir");
                            String alamat_pelamar_d = ar.getString("alamat_pelamar");
                            String kota_pelamar_d = ar.getString("kota_pelamar");
                            String provinsi_pelamar_d = ar.getString("provinsi_pelamar");
                            String telepon_pelamar_d = ar.getString("telepon");
                            String foto_pelamar_d = ar.getString("foto");

                            tvnamalengkappelamar.setText(namalengkap_pelamar_d);
                            tvemailpelamar.setText(email_pelamar_d);
                            tvjeniskelaminpelamar.setText(jeniskelamin_pelamar_d);
                            tvtanggallahirpelamar.setText(tanggallahir_pelamar_d);
                            tvalamatpelamar.setText(alamat_pelamar_d);
                            tvkotapelamar.setText(kota_pelamar_d);
                            tvprovinsipelamar.setText(provinsi_pelamar_d);
                            tvteleponpelamar.setText(telepon_pelamar_d);


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
