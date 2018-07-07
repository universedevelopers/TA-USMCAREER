package com.universedeveloper.usmcareer;

import android.content.Context;
import android.app.ProgressDialog;
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

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.universedeveloper.usmcareer.Pelamar.Menudrawerpelamat;
import com.universedeveloper.usmcareer.Utility.AppController;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class Tab1 extends Fragment {

    public final static String TAG_ID_PELAMAR = "id_user";
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";
    private static final String TAG = Tab1.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btnregisterpelamar, btnloginpelamar;
    ProgressDialog pDialog;
    Intent intent;
    int success;
    String tag_json_obj = "json_obj_req";
    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id_user ;
    ConnectivityManager conMgr;
    private EditText txt_usernamepelamar;
    private EditText txt_passwordpelamar;
    private String url = "http://gudangandroid.universedeveloper.com/usmcareer/login_pelamar.php";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public Tab1() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
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
                Toast.makeText(getActivity(), "Tidak ada akses Internet",
                        Toast.LENGTH_LONG).show();
            }
        }

        // Cek session login jika TRUE maka langsung buka MainActivity
        sharedpreferences = getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id_user = sharedpreferences.getString(TAG_ID_PELAMAR, null);

        if (session) {
            Intent intent = new Intent(getActivity(), Menudrawerpelamat.class);
            intent.putExtra(TAG_ID_PELAMAR, id_user);
            startActivity(intent);
            getActivity().finish();

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        Button btnregisterpelamar = (Button) view.findViewById(R.id.btn_registerpelamar);
        txt_usernamepelamar = (EditText) view.findViewById(R.id.txt_usernamepelamar);
        txt_passwordpelamar = (EditText) view.findViewById(R.id.txt_passwordpelamar);

// Cek session login jika TRUE maka langsung buka MainActivity
        sharedpreferences = getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id_user = sharedpreferences.getString(TAG_ID_PELAMAR, null);

        if (session) {
            Intent intent = new Intent(getActivity(), Menudrawerpelamat.class);
            intent.putExtra(TAG_ID_PELAMAR, id_user);
            getActivity().finish();
            startActivity(intent);
        }


        Button btnloginpelamar = (Button) view.findViewById(R.id.btn_loginpelamar);
        btnloginpelamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = txt_usernamepelamar.getText().toString();
                String password = txt_passwordpelamar.getText().toString();

                checkLogin(email, password);


                //Toast.makeText(getActivity(), "email_pass "+email+" "+password, Toast.LENGTH_SHORT).show();

            }
        });

        btnregisterpelamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), daftarpelamar.class);
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

       /// Activity activity = (Activity) context;
       //// loginFormActivityListener = (OnLoginFormActivityListener) activity;

   if (context instanceof OnFragmentInteractionListener) {
          mListener = (OnFragmentInteractionListener) context;
        } else {
           throw new RuntimeException(context.toString()
                  + " must implement OnFragmentInteractionListener");
       }
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
                        String id_user = jObj.getString(TAG_ID_PELAMAR);

                        Log.e("Successfully Login!", jObj.toString());

                        Toast.makeText(getActivity(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        // menyimpan login ke session
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(TAG_ID_PELAMAR, id_user);
                        editor.commit();

                        // Memanggil main activity
                        Intent intent = new Intent(getActivity(), Menudrawerpelamat.class);
                        intent.putExtra(TAG_ID_PELAMAR, id_user);

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
                params.put("email_pelamar", email);
                params.put("password_pelamar", password);

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
