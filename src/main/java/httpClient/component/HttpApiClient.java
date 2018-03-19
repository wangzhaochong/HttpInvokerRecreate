package httpClient.component;

import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hayden on 2018/3/19.
 */
public class HttpApiClient {

    private CloseableHttpClient httpClient;

    public HttpApiClient(){
        createClinet();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            public void run(){
                try{
                    closeClient();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public String request_get(String url, Map<String,Object> param) throws Exception {
        StringBuilder resBuilder = new StringBuilder();
        url = combineUri(url,param);
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        if(httpResponse.getStatusLine().getStatusCode() == 200){
            byte [] resByte = new byte[1024];
            InputStream is = httpResponse.getEntity().getContent();
            int len = resByte.length;
            while((len = is.read(resByte,0,len)) > 0){
                String res = new String(resByte,0,len, Charset.forName("utf-8"));
                resBuilder.append(res);
            }
            return resBuilder.toString();
        }
        return null;

    }

    public String combineUri(String url, Map<String, Object> param) {
        if(param == null || param.isEmpty())
            return url;

        url += "?";
        for(String key : param.keySet()){
            url = url + key + "=";
            url = url + param.get(key) + "&";
        }
        return url;
    }


    public String request_post(String url, Map<String,Object> param) throws IOException {
        StringBuilder resBuilder = new StringBuilder();
        url = combineUri(url,param);
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        if(httpResponse.getStatusLine().getStatusCode() == 200){
            byte [] resByte = new byte[1024];
            InputStream is = httpResponse.getEntity().getContent();
            int len = resByte.length;
            while((len = is.read(resByte,0,len)) > 0){
                String res = new String(resByte,0,len, Charset.forName("utf-8"));
                resBuilder.append(res);
            }
            return resBuilder.toString();
        }
        return null;

    }

    public String request_post_json(String url, JsonObject jo) throws Exception {
        StringBuilder resBuilder = new StringBuilder();
        HttpPost httpPost = new HttpPost(url);
        combineJsonContext(httpPost, jo);
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        if(httpResponse.getStatusLine().getStatusCode() == 200){
            byte [] resByte = new byte[1024];
            InputStream is = httpResponse.getEntity().getContent();
            int len = resByte.length;
            while((len = is.read(resByte,0,len)) > 0){
                String res = new String(resByte,0,len, Charset.forName("utf-8"));
                resBuilder.append(res);
            }
            return resBuilder.toString();
        }
        return null;

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
            System.out.println("clost client!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createClinet() {
        httpClient = HttpClients.createDefault();
    }



}
