package httpClient.component;

import com.google.gson.JsonObject;
import httpClient.annotation.ApiInfo;
import httpClient.annotation.ApiParam;
import httpClient.vo.httpMethodVo;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hayden on 2018/3/19.
 */
public class httpApiInvocationHandler implements InvocationHandler {

    private HttpApiClient client;


    public httpApiInvocationHandler(HttpApiClient client) {
        this.client = client;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Annotation[] annotations = method.getAnnotations();

        for(Annotation annotation : annotations){
            if(annotation.annotationType().equals(ApiInfo.class)){
                ApiInfo info = (ApiInfo)annotation;
                JsonObject jo = null;
                Map<String,Object> params = new HashMap<String, Object>();
                if(args != null){
                    Annotation[][] paramAnnotation = method.getParameterAnnotations();
                    for(int i=0;i<args.length;i+=1){
                        ApiParam apiParam = (ApiParam)paramAnnotation[i][0];
                        params.put(apiParam.name(),args[i]);
                        if(args[i] instanceof JsonObject){
                            jo = (JsonObject)args[i];
                        }
                    }
                }
                httpMethodVo vo = resolveMethodVo(info, params, jo);
                if(vo.getMethod().equals("GET")){
                    return client.request_get(vo.getUrl(),vo.getParams());
                }else if(vo.getMethod().equals("POST")){
                    return client.request_post(vo.getUrl(),vo.getParams());
                }else if(vo.getMethod().equals("POST_JSON")){
                    return client.request_post_json(vo.getUrl(),vo.getJo());
                }
            }
        }



        return "unsupport";
    }

    private httpMethodVo resolveMethodVo(ApiInfo info, Map<String, Object> params, JsonObject jo) {
        httpMethodVo vo = new httpMethodVo();
        vo.setUrl(info.url());
        vo.setMethod(info.method());
        if(params != null && !params.isEmpty()){
            vo.setParams(params);
        }
        if(jo != null){
            vo.setJo(jo);
            vo.setMethod("POST_JSON");
        }
        return vo;
    }
}
