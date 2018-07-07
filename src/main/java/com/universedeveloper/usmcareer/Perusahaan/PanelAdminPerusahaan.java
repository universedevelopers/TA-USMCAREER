package com.universedeveloper.usmcareer.Perusahaan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.universedeveloper.usmcareer.Login;
import com.universedeveloper.usmcareer.R;
import com.universedeveloper.usmcareer.Tab2;

public class PanelAdminPerusahaan extends AppCompatActivity {
    public final static String TAG_ID_PERUSAHAAN = "id_perusahaan";
    public final static String TAG_ID_EMAIL = "email_perusahaan";
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";
    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id_perusahaan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_admin_perusahaan);

        id_perusahaan = getIntent().getStringExtra("id_perusahaan");


        Button btnprofilperusahaan = (Button) findViewById(R.id.btn_profilperusahaan);
        btnprofilperusahaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pindah activity + kirim data id_perusakaan selanjutnya di cari detail nya
                Intent intent = new Intent(PanelAdminPerusahaan.this, ProfilePerusahaan.class);
                intent.putExtra(TAG_ID_PERUSAHAAN, id_perusahaan);
                startActivity(intent);
            }
        });

        Button btnpasanglowongankerja = (Button) findViewById(R.id.btn_pasanglowongankerja);
        btnpasanglowongankerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PanelAdminPerusahaan.this, PasangLowonganKerja.class);
                intent.putExtra(TAG_ID_PERUSAHAAN, id_perusahaan);
                startActivity(intent);
            }
        });

        Button btnhistorylowongankerja = (Button) findViewById(R.id.btn_historylowongankerja);
        btnhistorylowongankerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(view.getContext(), HistoryLowonganPekerjaan.class);
                view.getContext().startActivity(Intent);
            }
        });

        Button btndaftarpelamarbaru = (Button) findViewById(R.id.btn_daftarpelamarbaru);
        btndaftarpelamarbaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Intent = new Intent(view.getContext(), DetailPelamarBaru.class);
                view.getContext().startActivity(Intent);
            }
        });


        Button btn_keluar = (Button) findViewById(R.id.btn_keluarperusahaan);
        btn_keluar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(Tab2.session_status, false);
                editor.putString(TAG_ID_PERUSAHAAN, null);
                editor.putString(TAG_ID_EMAIL, null);
                editor.apply();

                Intent intent = new Intent(PanelAdminPerusahaan.this, Login.class);
                finish();
                startActivity(intent);
            }

        });
    }
}