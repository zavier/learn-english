package le.zavier.util;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BaiduTransApi {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";
    private static RestTemplate restTemplate = new RestTemplate();

    // 添加自己的百度翻译账号信息
    private static String APP_ID = "";
    private static String SECURITY_KEY = "";

    private String appid;
    private String securityKey;

    public BaiduTransApi() {
        this.appid = APP_ID;
        this.securityKey = SECURITY_KEY;
    }

    public BaiduTransApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }

    public String getTransResult(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        return restTemplate.getForObject(TRANS_API_HOST + "?" + mapParamToUrlStringParam(params), String.class);
    }

    private String mapParamToUrlStringParam(Map<String, String> map) {
        StringBuilder builder = new StringBuilder();
        map.forEach((k, v) -> {
            builder.append("&" + k + "=" + v);
        });
        return builder.substring(1);
    }

    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", DigestUtils.md5Hex(src));

        return params;
    }

}
