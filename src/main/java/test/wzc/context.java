package test.wzc;

import com.google.gson.JsonObject;
import httpClient.httpInterface.testInterface;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Hayden on 2018/3/18.
 */
public class context {

    public static void main(String [] s){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-api.xml");
        testInterface service = (testInterface)context.getBean("testInterface");
        JsonObject jo = new JsonObject();
        jo.addProperty("param1","one");
        jo.addProperty("param2",2);
        System.out.println(service.testJson(jo));
    }

}
