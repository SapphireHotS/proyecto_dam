package com.example.sgh;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityPrincipal extends AppCompatActivity implements BackgroundTaskMesas.CustomReqFinished {
    Button btnCerrarSesion, btnLimpiarMesa, btnPedir;
    Spinner spinner;
    TextView totalMesa;
    RequestQueue requestQueue;
    ArrayList<String> listaMesas;
    ArrayList<Pedido> pedidos;
    ListView listviewPedidos;
    ArrayAdapter<String> spinnerAdaptador;
    static PedidosListAdapter adaptadorPedidos;
    double precioTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        btnLimpiarMesa=findViewById(R.id.btnLimpiar);
        listaMesas = new ArrayList<>();
        listviewPedidos=findViewById(R.id.listview);
        listviewPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityPrincipal.this);
                alerta.setMessage("¿Quieres eliminar el pedido?")
                        .setCancelable(true)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Borrar
                                suprimirPedido("http://192.168.1.39:8083/sgh/borrar_pedido.php",position);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alerta.show();

            }
        });
        pedidos = new ArrayList<Pedido>();


        spinner=findViewById(R.id.spinner);
        spinnerAdaptador= new ArrayAdapter<String>(this, R.layout.spinner_layout,R.id.spinnerTexto, listaMesas);
        spinner.setAdapter(spinnerAdaptador);
        BackgroundTaskMesas backgroundTaskMesas = new BackgroundTaskMesas(this);
        backgroundTaskMesas.getList();
        spinnerAdaptador.notifyDataSetChanged();




        totalMesa=findViewById(R.id.total);
        btnCerrarSesion=findViewById(R.id.btnCerrar);
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(),"Se ha cerrado la sesión.",Toast.LENGTH_LONG).show();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Cargar productos de la mesa
                buscarPedidos("http://192.168.1.39:8083/sgh/buscar_pedidos.php?idmesa="+spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnLimpiarMesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityPrincipal.this);
                alerta.setMessage("¿Quieres limpiar la mesa?")
                        .setCancelable(true)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                limpiarMesa("http://192.168.1.39:8083/sgh/limpiar_mesa.php");
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                alerta.show();
            }
        });
        btnPedir=findViewById(R.id.btnPedir);
        btnPedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PedirActivity.class);
                intent.putExtra("idmesa",spinner.getSelectedItem().toString());
                startActivity(intent);
                finish();
            }
        });



    }

    @Override
    public void onCustomReqFinished(ArrayList<String> list) {

        spinnerAdaptador=new ArrayAdapter<String>(this,R.layout.spinner_layout,R.id.spinnerTexto,list);
        spinner.setAdapter(spinnerAdaptador);
    }

    private void buscarPedidos(String URL){
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                precioTotal=0.0;
                pedidos.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {

                        jsonObject = response.getJSONObject(i);
                        Pedido pedido = new Pedido(jsonObject.getInt("idmesa"),jsonObject.getInt("idproducto"),
                                jsonObject.getString("nombreprod"),jsonObject.getInt("cantidad"),jsonObject.getDouble("preciou"),jsonObject.getDouble("preciot"));
                        pedidos.add(pedido);
                        precioTotal+=pedido.getPreciot();
                        if((response.length()-1)==i){
                            adaptadorPedidos = new PedidosListAdapter(getApplicationContext(), R.layout.item_pedido,pedidos);
                            listviewPedidos.setAdapter(adaptadorPedidos);
                            totalMesa.setText(String.format("%.2f€",precioTotal));
                            listviewPedidos.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
                            listviewPedidos.setSelection(1);
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
                pedidos.clear();
                totalMesa.setText(String.format("0€"));
                if(adaptadorPedidos!=null){
                    adaptadorPedidos.notifyDataSetChanged();
                }
            }
        }
        );

        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }


    private void suprimirPedido(String URL, final int position){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//Respuesta del servidor correcta: el pedido está borrado y hace falta quitarlo de la lista
                precioTotal-=pedidos.get(position).getPreciot();
                totalMesa.setText(String.format("%.2f€",precioTotal));
                pedidos.remove(position);
                adaptadorPedidos.notifyDataSetChanged();
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
                parametros.put("idmesa",Integer.toString(pedidos.get(position).getIdmesa()));
                parametros.put("idproducto",Integer.toString(pedidos.get(position).getIdproducto()));
                parametros.put("cantidad",Integer.toString(pedidos.get(position).getCantidad()));
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void limpiarMesa(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//Respuesta del servidor correcta: el pedido está borrado y hace falta quitarlo de la lista
                pedidos.clear();
                precioTotal=0;
                totalMesa.setText("0.00€");
                adaptadorPedidos.notifyDataSetChanged();
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
                parametros.put("idmesa",spinner.getSelectedItem().toString());
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
