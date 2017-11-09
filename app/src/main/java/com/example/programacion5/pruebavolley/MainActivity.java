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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button enviar;
    private EditText nombre, apellido, mail, telefono, campoMensaje;
    private Spinner opcion;
    private String uri = "http://181.14.240.59/Portal/contacto-5/";
    private Map parametros = new HashMap();
    private StringRequest post;

    RequestQueue coladesolicitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nombre   = (EditText)findViewById(R.id.etNombre);
        apellido = (EditText)findViewById(R.id.etApellido);
        mail     = (EditText)findViewById(R.id.etEmail);
        telefono = (EditText)findViewById(R.id.etTelefono);
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

                        try {
                            getFormParams(response);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            // los header de la consulta del metodo GET
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                map.put("Accept-Encoding","gzip, deflate");
                map.put("Accept-Language","es-ES,es;q=0.8");
                map.put("Cache-Control","max-age=0");
                map.put("Connection","keep-alive");
                map.put("Host","181.14.240.59");
                map.put("Referer","http://181.14.240.59/Portal/");
                map.put("Upgrade-Insecure-Requests","1");
                map.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
                return super.getHeaders();
            }
        };
        coladesolicitud.add(get);

       post = new StringRequest(Request.Method.POST, uri,
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
                map.put("Accept-Language","es-ES,es;q=0.8");
                map.put("Cache-Control","max-age=0");
                map.put("Connection","keep-alive");
                map.put("Host","181.14.240.59");
                map.put("Referer","http://181.14.240.59/Portal/");
                map.put("Upgrade-Insecure-Requests","1");
                map.put("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
                return super.getHeaders();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return parametros;
            }
        };


        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] formulario = new String[6];

                opcion.getSelectedItem().toString();
                apellido.getText();
                nombre.getText();
                mail.getText();
                telefono.getText();
                campoMensaje.getText();

                parametros.keySet().add(opcion);
                parametros.keySet().add(apellido);
                parametros.keySet().add(nombre);
                parametros.keySet().add(mail);
                parametros.keySet().add(telefono);
                parametros.keySet().add(campoMensaje);


                if(parametros.size()!= 0){

                    coladesolicitud.add(post);
                }

            }
        });
        opcion.getSelectedItem().toString();
        apellido.setText("");
        nombre.setText("");
        mail.setText("");
        telefono.setText("");
        campoMensaje.setText("");

    }


    public void  getFormParams// pasamos los parametros parseados  en un Map
            (String pagFor) throws UnsupportedEncodingException {
        int init = 0;
        int sesion = 0;
        int nm = 0;


        Document doc = Jsoup.parse(pagFor);

        Elements select = doc.getElementsByTag("select");
        Elements inputElements = doc.getElementsByTag("input");
        Elements textArea = doc.getElementsByTag("textarea");



        for(Element selecion : select){
            String key = selecion.attr("name");
            String value = selecion.attr("value");
            if (key.equals("Destinatario")) {
                value = opcion.getSelectedItem().toString();
            }
            if(value != null && key != null){
                parametros.put(key, value);}
        }

        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");

            if (key.equals("Nombre")) {
                value = String.valueOf(nombre.getText());
            } else {
                if (key.equals("Apellido")) {
                    value = String.valueOf(apellido.getText());
                } else {
                    if (key.equals("Email")) {
                        value = String.valueOf(mail.getText());
                    } else {
                        if (key.equals("Telefono")) {
                            value = String.valueOf(telefono.getText());
                        }

                         else{
                            if (key.equals("script_case_session") && sesion != 1 && !value.equals("")) {
                                sesion = 1;
                                parametros.put(key,URLEncoder.encode(value, "UTF-8"));

                            } else {
                                if (key.equals("script_case_init") && init != 1 && !value.equals("")) {
                                    init = 1;

                                    parametros.put(key,URLEncoder.encode(value, "UTF-8") );
                                } else {
                                    if (key.equals("nm_form_submit") && nm != 1 && !value.equals("")) {
                                        nm = 1;
                                        parametros.put(key, URLEncoder.encode(value, "UTF-8"));

                                    }
                                }
                            }
                        }
                    }
                }
            }
            if(value != null && key != null){
                if (!value.equals("") && !key.equals("") && !key.equals("script_case_session") && !key.equals("nm_form_submit") && !key.equals("script_case_init")) {
                    parametros.put(key,URLEncoder.encode(value, "UTF-8") );

                }
            }
        }

        for(Element textarea : textArea){
            String key = textarea.attr("name");
            String value = textarea.attr("value");
            if (key.equals("Mensaje")) {
                value = String.valueOf(campoMensaje.getText());
            }
            if(value != null && key != null){
                parametros.put(key, value);}
        }


    }




}
