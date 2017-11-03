package com.example.programacion5.pruebavolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button enviar;
    private EditText Nombre, Apellido, Mail, Telefono, campoMensaje;
    private Spinner opcion;
    private String uri = "http://181.14.240.59/Portal/contacto-5/";

    RequestQueue coladesolicitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Nombre   = (EditText)findViewById(R.id.etNombre);
        Apellido = (EditText)findViewById(R.id.etApellido);
        Mail     = (EditText)findViewById(R.id.etEmail);
        Telefono = (EditText)findViewById(R.id.etTelefono);
        campoMensaje = (EditText)findViewById(R.id.etMensaje);

        enviar = (Button) findViewById(R.id.bEnviarMensaje);
        opcion = (Spinner)findViewById(R.id.spinnerDestino);

        final String[] valores= new String[]{"Capacitacion","Intermediación Laboral","Emprendimientos","Programas de Empleo","Información Institucional"};
        ArrayAdapter<String> adaptadorSpinner = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, valores){};

        opcion.setAdapter(adaptadorSpinner);

        coladesolicitud = new Volley().newRequestQueue(this);

        final String[] paginacontacto = new String[1];

        StringRequest get = new StringRequest(Request.Method.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        paginacontacto[0] = response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                map.put("Accept-Encoding","gzip, deflate");
//                map.put("","");
//                map.put("","");
//                map.put("","");
//                map.put("","");
                return super.getHeaders();
            }
        };
        StringRequest post = new StringRequest(Request.Method.POST, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                map.put("Accept-Encoding","gzip, deflate");
//                map.put("","");
//                map.put("","");
//                map.put("","");
//                map.put("","");
                return super.getHeaders();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
//                aqui van los parametro para el post
                return super.getParams();
            }
        };
        coladesolicitud.add(get);
    }
}
