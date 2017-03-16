package jasoliya1.ravin.smsbroadcast;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class AsyncTaskLoader extends AsyncTask<Void, Void, String> {

    private Context context;
    private OnAsyncResult listener;
    private ProgressDialog progressDialog;
    private String requestUrl;
    private HashMap<String, String> hashMap;


    public AsyncTaskLoader(Context context, OnAsyncResult listener, HashMap<String, String> hashMap, String url) {
        this.context = context;
        this.listener = listener;
        this.requestUrl = url;
        this.hashMap = hashMap;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please Wait . . .");
        progressDialog.setIndeterminate(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

       // progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {

        URL url;
        String response = "";
        progressDialog.incrementProgressBy(15);

        try {
            url = new URL(requestUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (hashMap != null) {
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                progressDialog.incrementProgressBy(15);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(hashMap));

                writer.flush();
                writer.close();
                os.close();
            }
            else {
                conn.disconnect();
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                progressDialog.incrementProgressBy(15);
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
                conn.disconnect();
            }

            else {
                response = "error";
                conn.disconnect();
            }
            progressDialog.setProgress(+15);
        } catch (Exception e) {
            response = "error";
            e.printStackTrace();
        }
        progressDialog.incrementProgressBy(15);
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        try {

            progressDialog.setProgress(100);
          //  progressDialog.dismiss();

            if(!result.equals("error") && !result.contains("errors"))
            {
                if(listener != null){
                    listener.onAsyncResult(result);
                }
            }
            else {
                if(listener != null){
                    listener.onAsyncResult("Error");
                }
            }
            
        }catch (Exception e)
        {
            Toast.makeText(context, "Slow Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
