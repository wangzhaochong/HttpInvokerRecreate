package httpClient;

import com.google.gson.JsonObject;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.ByteArrayBuffer;

import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hayden on 2018/3/17.
 */
public class HttpClientExample {

    private CloseableHttpClient httpClient;

    public static void main(String[] args) throws Exception {
        HttpClientExample example = new HttpClientExample();

        example.createClinet();
        example.request_get("http://localhost/testGet",null);
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("param1","1");
        param.put("param2","2");
        example.request_get("http://localhost/testGetParam",param);
        example.request_post("http://localhost/testPost", null);
        JsonObject jo = new JsonObject();
        jo.addProperty("param1","one");
        jo.addProperty("param2","2");
        example.request_post_json("http://localhost/testJson", jo);
        example.closeClient();
    }

    private void request_get(String url, Map<String,Object> param) throws Exception {
        url = combineUri(url,param);
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        if(httpResponse.getStatusLine().getStatusCode() == 200){
            byte [] resByte = new byte[1024];
            InputStream is = httpResponse.getEntity().getContent();
            int len = resByte.length;
            while((len = is.read(resByte,0,len)) > 0){
                String res = new String(resByte,0,len, Charset.forName("utf-8"));
                System.out.println(res);
            }
        }

    }

    private String combineUri(String url, Map<String, Object> param) {
        if(param == null || param.isEmpty())
            return url;

        url += "?";
        for(String key : param.keySet()){
            url = url + key + "=";
            url = url + param.get(key) + "&";
        }
        return url;
    }


    private void request_post(String url, Map<String,Object> param) throws IOException {
        url = combineUri(url,param);
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        if(httpResponse.getStatusLine().getStatusCode() == 200){
            byte [] resByte = new byte[1024];
            InputStream is = httpResponse.getEntity().getContent();
            int len = resByte.length;
            while((len = is.read(resByte,0,len)) > 0){
                String res = new String(resByte,0,len, Charset.forName("utf-8"));
                System.out.println(res);
            }
        }

    }

    private void request_post_json(String url, JsonObject jo) throws Exception {

        HttpPost httpPost = new HttpPost(url);
        combineJsonContext(httpPost, jo);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        if(httpResponse.getStatusLine().getStatusCode() == 200){
            byte [] resByte = new byte[1024];
            InputStream is = httpResponse.getEntity().getContent();
            int len = resByte.length;
            while((len = is.read(resByte,0,len)) > 0){
                String res = new String(resByte,0,len, Charset.forName("utf-8"));
                System.out.println(res);
            }
        }

    }

    private void combineJsonContext(HttpPost httpPost, JsonObject jo) throws Exception {

        if(jo != null){
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            StringEntity se = new StringEntity(jo.toString());
            se.setContentType("text/json");
            httpPost.setEntity(se);
        }

    }


    private void closeClient() {
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createClinet() {
        httpClient = HttpClients.createDefault();
    }




}
