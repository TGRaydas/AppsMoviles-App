package com.example.tgraydas.billsmanager;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class NetworkManager {

    private static NetworkManager mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private static final String BASE_URL = "http://18.188.109.236/";

    private static String token =  "WENA PETER";

    private NetworkManager(Context context){
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized NetworkManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkManager(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public void login(final Response.Listener<JSONObject> responseListener,
                      Response.ErrorListener errorListener) throws JSONException {

        String url = BASE_URL + "authenticate";

        JSONObject payload = new JSONObject();
        payload.put("email", "user");
        payload.put("password", "123123");


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, payload, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        JSONObject headers = response.optJSONObject("headers");
                        token = headers.optString("Authorization", "");
                        responseListener.onResponse(response);
                    }
                }, errorListener){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                    JSONObject jsonResponse = new JSONObject(jsonString);
                    jsonResponse.put("headers", new JSONObject(response.headers));
                    return Response.success(jsonResponse,
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };

        mRequestQueue.add(jsonObjectRequest);
    }

    public void getProducts(Response.Listener<JSONObject> listener,
                            Response.ErrorListener errorListener){

        String url = BASE_URL + "products";
        makeApiCall(Request.Method.GET, url, null,listener, errorListener);
    }

    private void makeApiCall(int method, String url, JSONObject payload, Response.Listener<JSONObject> listener,
                             Response.ErrorListener errorListener){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("head","peter");
            jsonObject.put("haaaead","pesdsr");
            url += "?object=" + jsonObject.toString();
            JsonObjectArrayRequest jsonObjectRequest = new JsonObjectArrayRequest
                    (method, url, payload, listener, errorListener){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", token);
                    return headers;
                }
            };
            mRequestQueue.add(jsonObjectRequest);

        } catch (JSONException e){

        }


    }
}