package com.example.sgh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PedirActivity extends AppCompatActivity implements BackgroundTaskGrupos.CustomReqFinished {
    Button btnVolver;
    Spinner spinnerGrupos;
    ArrayAdapter<String> spinGrpAdapter;
    ArrayList<String> listaGrupos;
    ArrayList<Producto> productos;
    String idmesa;
    RequestQueue requestQueue;
    GridView gridView;
    ProductosListAdapter adaptadorProductos;
    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir);
        spinnerGrupos=findViewById(R.id.spinnerGrupos);
        spinGrpAdapter= new ArrayAdapter<String>(this, R.layout.spinner_layout,R.id.spinnerTexto,listaGrupos);
        BackgroundTaskGrupos backgroundTaskGrupos = new BackgroundTaskGrupos(this);
        backgroundTaskGrupos.getList();
        productos= new ArrayList<>();
        gridView=findViewById(R.id.grid);
        if (savedInstanceState == null) {//Cogemos el número de la mesa en la que estamos pidiendo
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
            } else {
                idmesa= extras.getString("idmesa");
            }
        } else {
            idmesa= (String) savedInstanceState.getSerializable("idmesa");
        }

        btnVolver=findViewById(R.id.btnVolverPedir);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ActivityPrincipal.class);
                startActivity(intent);
                finish();
            }
        });

        spinnerGrupos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Cargar productos de la mesa
                //buscarProductos("http://192.168.1.39:8083/sgh/buscar_pedidos.php?idmesa="+spinner.getSelectedItem().toString());
                buscarProductos("http://192.168.1.39:8083/sgh/buscar_productos.php?nombre="+spinnerGrupos.getSelectedItem().toString());
                //Toast.makeText(getApplicationContext(),"hola",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        myDialog= new Dialog(this);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showPopup(productos.get(position));
            }
        });







    }


    @Override
    public void onCustomReqFinished(ArrayList<String> list) {

        spinGrpAdapter=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.spinnerTexto,list);
        spinnerGrupos.setAdapter(spinGrpAdapter);
    }


    private void buscarProductos(final String URL){
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                productos.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        byte [] encodeByte = Base64.decode(jsonObject.getString("nombrecorto"),Base64.DEFAULT);
                        String nombre = new String(encodeByte, StandardCharsets.UTF_8);

                        byte [] encodeByteFoto = Base64.decode(jsonObject.getString("fotoprod"),Base64.NO_WRAP);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByteFoto, 0, encodeByteFoto.length);
                        Producto producto = new Producto(nombre,bitmap);
                        productos.add(producto);
                        if((response.length()-1)==i){
                            adaptadorProductos = new ProductosListAdapter(getApplicationContext(), R.layout.item_producto,productos);
                            gridView.setAdapter(adaptadorProductos);
                        }

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                productos.clear();
            }
        }
        );

        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }



    public void showPopup(final Producto producto){
        myDialog.setContentView(R.layout.popup_pedir);
        Button btnCerrar= myDialog.findViewById(R.id.cerrarPedido);
        Button btnPedirPedido= myDialog.findViewById(R.id.btnPedirPedido);
        ImageView imgPedido= myDialog.findViewById(R.id.imgPedido);
        TextView txtNombre = myDialog.findViewById(R.id.nombretv);
        final EditText editText= myDialog.findViewById(R.id.etCantidad);
        imgPedido.setImageBitmap(producto.getImagen());
        txtNombre.setText(producto.getNombre());
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
        btnPedirPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarPedido("http://192.168.1.39:8083/sgh/insertar_pedido.php",producto.getNombre(),editText.getText().toString());
                myDialog.dismiss();
            }
        });

    }

    private void insertarPedido(String URL, final String nombre, final String cantidad){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"Pedido realizado con éxito!",Toast.LENGTH_LONG).show();
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
                parametros.put("nombre",nombre);
                parametros.put("cantidad",cantidad);
                parametros.put("idmesa",idmesa);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



}
