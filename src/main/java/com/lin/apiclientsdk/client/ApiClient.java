package com.lin.apiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.lin.apiclientsdk.ApiClientProperties;
import com.lin.apiclientsdk.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.lin.apiclientsdk.utils.SignUtils.getSign;

@Component
// 调用第三方接口的客户端
public class ApiClient {

    private String accessKey;
    private String secretKey;

    public ApiClient(ApiClientProperties properties) {
        this.accessKey = properties.getAccessKey();
        this.secretKey = properties.getSecretKey();
    }

    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.get("http://localhost:8123/api/name/", paramMap);
        // System.out.println(result);
        return result;
    }

    public String getNameByPost(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        String result = HttpUtil.post("http://localhost:8123/api/name/", paramMap);
        // System.out.println(result);
        return result;
    }

    private Map<String, String> getHeaderMap(String body) {
        Map<String, String> headers = new HashMap<>();
        headers.put("accessKey", accessKey);
        // headers.put("secretKey", secretKey); // ！！不能直接传递secretKey！！
        headers.put("nonce", RandomUtil.randomNumbers(3));
        headers.put("body", body);
        headers.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
        // secretKey放在加密后的签名中
        headers.put("sign", getSign(body, secretKey));
        return headers;
    }

    public String getUserNameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post("http://localhost:8123/api/name/user/")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        //System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        //System.out.println(result);
        return result;
    }
}
