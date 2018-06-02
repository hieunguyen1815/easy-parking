package app.android.easygroup.easyparking;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import app.android.easygroup.easyparking.services.internal.volley.VolleyService;

public class HttpClient {

    private static final int DEFAULT_TIMEOUT = 30000;

    private static HttpClient instance;

    private HttpClient() {}

    public static HttpClient getInstance() {
        if (instance == null) {
            instance = new HttpClient();
        }
        return instance;
    }

    public String get(String API, String[] pathParams, final Map<String, String> headers) throws InterruptedException, ExecutionException, TimeoutException {
        String url = String.format(API, pathParams);

        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest request = new StringRequest(Request.Method.GET, url, future, future) {
            @Override
            public Map<String, String> getHeaders() {
                return headers == null ? new HashMap<String, String>() : headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyService.getInstance().addToRequestQueue(request);

        return future.get(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public String post(String API, String[] pathParams, final Map<String, String> body, final Map<String, String> headers) throws InterruptedException, ExecutionException, TimeoutException {
        String url = String.format(API, pathParams);

        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest request = new StringRequest(Request.Method.POST, url, future, future) {
            @Override
            protected Map<String, String> getParams() {
                return body == null ? new HashMap<String, String>() : body;
            }

            @Override
            public Map<String, String> getHeaders() {
                return headers == null ? new HashMap<String, String>() : headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyService.getInstance().addToRequestQueue(request);

        return future.get(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public String put(String API, String[] pathParams, final Map<String, String> body, final Map<String, String> headers) throws InterruptedException, ExecutionException, TimeoutException {
        String url = String.format(API, pathParams);

        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest request = new StringRequest(Request.Method.PUT, url, future, future) {
            @Override
            protected Map<String, String> getParams() {
                return body == null ? new HashMap<String, String>() : body;
            }

            @Override
            public Map<String, String> getHeaders() {
                return headers == null ? new HashMap<String, String>() : headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyService.getInstance().addToRequestQueue(request);

        return future.get(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public String delete(String API, String[] pathParams, final Map<String, String> headers) throws InterruptedException, ExecutionException, TimeoutException {
        String url = String.format(API, pathParams);

        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest request = new StringRequest(Request.Method.DELETE, url, future, future) {
            @Override
            public Map<String, String> getHeaders() {
                return headers == null ? new HashMap<String, String>() : headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DEFAULT_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleyService.getInstance().addToRequestQueue(request);

        return future.get(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
    }
}
