package com.example.handesen.sqlite;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Handesen on 2017. 04. 17..
 */

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://szakdolgozat.comxa.com/Register.php";

    private Map<String, String> params;

    public RegisterRequest(String name, String username, String email, String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(c.getTime());
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("username", username);
        params.put("createdat", strDate);

        params.put("password", password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
