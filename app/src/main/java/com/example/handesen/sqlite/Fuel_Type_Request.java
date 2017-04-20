package com.example.handesen.sqlite;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Handesen on 2017. 04. 18..
 */

public class Fuel_Type_Request extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://szakdolgozat.comxa.com/fuel_type.php";
    private Map<String, String> params;

    public Fuel_Type_Request(Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}