package com.example.sgh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.security.ProviderInstaller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText edtCorreo, edtPassword;
    Switch recordarInicio;
    Button logIn;
    String nombre="";
    String correo,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtCorreo=findViewById(R.id.campoCorreo);
        edtPassword=findViewById(R.id.campoPassword);
        recordarInicio=findViewById(R.id.idSwitch);
        logIn=findViewById(R.id.botonLogin);
        recuperarPreferencias();
        recordarInicio=findViewById(R.id.idSwitch);
        recordarInicio.setChecked(true);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo=edtCorreo.getText().toString();
                password=edtPassword.getText().toString();
                if(!correo.isEmpty() && !password.isEmpty()){
                    validarUsuario("http://192.168.1.39:8083/sgh/login.php");
                }else{
                    Toast.makeText(getApplicationContext(),"Por favor, rellene los campos",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    private void updateAndroidSecurityProvider() {
        try {
            ProviderInstaller.installIfNeeded(this);
        } catch (Exception e) {
            e.getMessage();
        }
    }
    private void validarUsuario(String URL){
        updateAndroidSecurityProvider();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    if(recordarInicio.isChecked()){
                        guardarPreferencias();
                    }
                    Toast.makeText(getApplicationContext(),"Sesión iniciada con éxito.",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),ActivityPrincipal.class);
                    startActivity(intent);
                    finish();


                }else{
                    Toast.makeText(getApplicationContext(),"Usiario y/o contraseña incorrectos.",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("correo",correo);
                parametros.put("password",password);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void guardarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("correo",correo);
        editor.putString("password",password);
        editor.putBoolean("sesion",true);
        editor.commit();

    }

    private void recuperarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("preferenciasLogin",Context.MODE_PRIVATE);
        edtCorreo.setText(preferences.getString("correo",""));
        edtPassword.setText(preferences.getString("password",""));
    }



}
