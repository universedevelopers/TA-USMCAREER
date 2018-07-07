package com.universedeveloper.usmcareer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.universedeveloper.usmcareer.Perusahaan.PanelAdminPerusahaan;
import com.universedeveloper.usmcareer.Utility.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab2 extends Fragment {
    public final static String TAG_ID_PERUSAHAAN = "id_perusahaan";
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";
    private static final String TAG = Tab2.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btnregisterperusahaan, btnloginperusahaan;
    ProgressDialog pDialog;
    Intent intent;
    int success;
    String tag_json_obj = "json_obj_req";
    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id_perusahaan ;
    ConnectivityManager conMgr;
    private EditText txt_usernameperusahaan;
    private EditText txt_passwordperusahaan;
    private String url = "http://gudangandroid.universedeveloper.com/usmcareer/login_perusahaan.php";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab2 newInstance(String param1, String param2) {
        Tab2 fragment = new Tab2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getActivity(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        // Cek session login jika TRUE maka langsung buka MainActivity
        sharedpreferences = getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id_perusahaan = sharedpreferences.getString(TAG_ID_PERUSAHAAN, null);

        if (session) {
            Intent intent = new Intent(getActivity(), PanelAdminPerusahaan.class);
            intent.putExtra(TAG_ID_PERUSAHAAN, id_perusahaan);
            getActivity().finish();
            startActivity(intent);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        Button btnregisterperusahaan = (Button) view.findViewById(R.id.btn_registerperusahaan);
        txt_usernameperusahaan = (EditText) view.findViewById(R.id.txt_usernameperusahaan);
        txt_passwordperusahaan = (EditText) view.findViewById(R.id.txt_passwordperusahaan);

// Cek session login jika TRUE maka langsung buka MainActivity
        sharedpreferences = getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id_perusahaan = sharedpreferences.getString(TAG_ID_PERUSAHAAN, null);

        if (session) {
            Intent intent = new Intent(getActivity(), PanelAdminPerusahaan.class);
            intent.putExtra(TAG_ID_PERUSAHAAN, id_perusahaan);
            getActivity().finish();
            startActivity(intent);
        }


        Button btnloginperusahaan = (Button) view.findViewById(R.id.btn_loginperusahaan);
       btnloginperusahaan.setOnClickListener(new View.OnClickListener() {
          @Override
            public void onClick(View view) {

              String email = txt_usernameperusahaan.getText().toString();
              String password = txt_passwordperusahaan.getText().toString();

              checkLogin(email, password);


              //Toast.makeText(getActivity(), "email_pass "+email+" "+password, Toast.LENGTH_SHORT).show();

           }
      });

        btnregisterperusahaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), daftarperusahaan.class);
                startActivity(intent);
            }
        });

        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void checkLogin(final String email, final String password) {
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);
        pDialog.setMessage("Proses Login ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {
                        String id_perusahaan = jObj.getString(TAG_ID_PERUSAHAAN);

                        Log.e("Successfully Login!", jObj.toString());

                        Toast.makeText(getActivity(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        // menyimpan login ke session
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(TAG_ID_PERUSAHAAN, id_perusahaan);
                        editor.commit();

                        // Memanggil main activity
                        Intent intent = new Intent(getActivity(), PanelAdminPerusahaan.class);
                        intent.putExtra(TAG_ID_PERUSAHAAN, id_perusahaan);

                        getActivity().finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(),
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
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email_perusahaan", email);
                params.put("password_perusahaan", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
