package le.zavier.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import le.zavier.exception.CheckException;
import le.zavier.pojo.TransResult;
import le.zavier.util.BaiduTransApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TranslateService {
    private static final Logger logger = LoggerFactory.getLogger(TranslateService.class);

    /**
     * 翻译
     * @param query 翻译原文
     * @param from 原文语言
     * @param to 目标语言
     * @return
     */
    public TransResult translate(String query, String from, String to) {
        BaiduTransApi api = new BaiduTransApi();
        String transResult = api.getTransResult(query, from, to);
        logger.info("调用百度翻译接口，结果为：{}", transResult);
        JSONObject object = JSON.parseObject(transResult);
        TransResult result = new TransResult();
        JSONArray array = object.getJSONArray("trans_result");
        if (array.size() < 1) {
            throw new CheckException("翻译错误，无结果返回");
        }
        JSONObject obj = array.getJSONObject(0);

        result.setFrom(object.getString("from"));
        result.setTo(object.getString("to"));
        result.setSrc(obj.getString("src"));
        result.setDst(obj.getString("dst"));
        return result;
    }
}
