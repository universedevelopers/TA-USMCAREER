package com.universedeveloper.usmcareer.Pelamar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.universedeveloper.usmcareer.R;
import com.universedeveloper.usmcareer.Utility.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InputDataPendidikan extends AppCompatActivity {
    private static final String TAG = InputDataPendidikan.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    EditText txtinstansi, txtprogdi, txttahunmasuk, txttahunlulus, txtipk;
    Button btnijazah, btnsimpanpendidikan;
    ImageView image;
    String id_user, instansi, program_studi, tahun_masuk, tahun_lulus, ipk;
    Bitmap bitmap, decoded;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60; // range 1 - 100
    ConnectivityManager conMgr;
    ProgressDialog pDialog;
    String tag_json_obj = "json_obj_req";
    Intent i;
    int success;
    private String url = "http://gudangandroid.universedeveloper.com/usmcareer/pelamar/input_pendidikan.php";  //directory php ning server

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data_pendidikan);

        id_user = getIntent().getStringExtra("id_user");
        Toast.makeText(this, "id "+ id_user, Toast.LENGTH_SHORT).show();
        instansi = getIntent().getStringExtra("instansi");
        program_studi = getIntent().getStringExtra("program_studi");
        tahun_masuk = getIntent().getStringExtra("tahun_masuk");
        tahun_lulus = getIntent().getStringExtra("tahun_lulus");
        ipk = getIntent().getStringExtra("ipk");


        btnijazah = (Button) findViewById(R.id.btnijazah);
        btnsimpanpendidikan = (Button) findViewById(R.id.btnsimpanpendidikan);
        txtinstansi = (EditText) findViewById(R.id.txtinstansi);
        txtprogdi = (EditText) findViewById(R.id.txtprogdi);
        txttahunmasuk = (EditText) findViewById(R.id.txtinstansi);
        txttahunlulus = (EditText) findViewById(R.id.txttahunlulus);
        txtipk = (EditText) findViewById(R.id.txtipk);
        image = (ImageView) findViewById(R.id.image);

        btnijazah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        btnsimpanpendidikan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String instansi = txtinstansi.getText().toString();
                String program_studi = txtprogdi.getText().toString();
                String tahun_masuk = txttahunmasuk.getText().toString();
                String tahun_lulus = txttahunlulus.getText().toString();
                String ipk = txtipk.getText().toString();

               /// if (conMgr.getActiveNetworkInfo() != null
                   ///     && conMgr.getActiveNetworkInfo().isAvailable()
                     ///   && conMgr.getActiveNetworkInfo().isConnected()) {
                    checkData(instansi, program_studi, tahun_masuk, tahun_lulus, ipk);
               /// } else {
               ///     Toast.makeText(getApplicationContext(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
               //   nn }
            }
        });
    }

    private void checkData(final String instansi,final String program_studi,final String tahun_masuk, final String tahun_lulus,final String ipk) {
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

                        txtinstansi.setText("");
                        txtprogdi.setText("");
                        txttahunmasuk.setText("");
                        txttahunlulus.setText("");
                        txtipk.setText("");


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
                params.put("id_user", id_user);
                params.put("instansi", instansi);
                params.put("program_studi", program_studi);
                params.put("tahun_masuk", tahun_masuk);
                params.put("tahun_lulus", tahun_lulus);
                params.put("ipk", ipk);

                return params;
            }

        };

        // Adding request to request queue

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
        ///AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);



    }

    //untuk upload image, compress .JPEG ke bitmap
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    //untuk memilih gambar
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //mengambil fambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                setToImageView(getResizedBitmap(bitmap, 512));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        image.setImageBitmap(decoded);
    }
    // fungsi resize image
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
