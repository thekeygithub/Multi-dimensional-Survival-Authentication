package com.xcuni.guizhouyl.fabric;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class FabricChaincodeResponse {
    String code;
    String message;
    String txId;
    String resultString;


    JSONObject buildJsonResult() {
        JSONObject jsonObject = parseResult(resultString);
        jsonObject.put("txId", txId);
        jsonObject.put("code", code);
        jsonObject.put("message", message);

        return jsonObject;
    }

    private JSONObject parseResult(String result) {
        JSONObject jsonObject = new JSONObject();
        int jsonVerify = isJSONValid(result);
        switch (jsonVerify) {
            case 0:
                jsonObject.put("data", result);
                break;
            case 1:
                jsonObject.put("data", JSONObject.parseObject(result));
                break;
            case 2:
                jsonObject.put("data", JSONObject.parseArray(result));
                break;
        }
        return jsonObject;//确保返回一定是个 json 串
    }

    /**
     * 判断字符串类型
     *
     * @param str 字符串
     * @return 0-string；1-JsonObject；2、JsonArray
     */
    private int isJSONValid(String str) {
        try {
            JSONObject.parseObject(str);
            return 1;
        } catch (JSONException ex) {
            try {
                JSONObject.parseArray(str);
                return 2;
            } catch (JSONException ex1) {
                return 0;
            }
        }
    }
}
