package httpClient.httpInterface;

import com.google.gson.JsonObject;
import httpClient.annotation.ApiInfo;
import httpClient.annotation.ApiParam;

/**
 * Created by Hayden on 2018/3/18.
 */
public interface testInterface {

    @ApiInfo(method = "GET", url = "http://localhost/testGetParam", timeout = 2000)
    String testGetParam(@ApiParam(name="param1") String param1,
                        @ApiParam(name="param2") Integer param2);

    @ApiInfo(method = "GET", url = "http://localhost/testGet", timeout = 2000)
    String testGet();

    @ApiInfo(method = "POST", url = "http://localhost/testJson", timeout = 2000)
    String testJson(@ApiParam(name="req", json = true) JsonObject jo);

}
