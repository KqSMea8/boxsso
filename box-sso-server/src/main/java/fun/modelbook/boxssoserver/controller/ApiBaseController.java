package fun.modelbook.boxssoserver.controller;

import com.alibaba.fastjson.JSON;
import fun.modelbook.boxssoserver.constant.CodeMsg;
import net.minidev.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 接口控制器的基础类
 *
 * @author jory
 * @date 2019-03-14
 */
@RequestMapping("api")
public class ApiBaseController {

    private static final Logger LOGGER = LogManager.getLogger(ApiBaseController.class.getName());

    protected String successResult() {
        JSONObject result = new JSONObject();
        result.put("code", 0);
        result.put("message", "success");
        return result.toJSONString();
    }

    protected String successResult(Object object) {
        JSONObject result = new JSONObject();
        result.put("code", 0);
        result.put("message", "success");
        putResultData(result, object);
        return result.toJSONString();
    }

    protected String failResult() {
        JSONObject result = new JSONObject();
        result.put("code", 1);
        result.put("message", "fail");
        return result.toJSONString();
    }

    protected String failResult(Object data) {
        JSONObject result = new JSONObject();
        result.put("code", 1);
        result.put("message", "fail");
        putResultData(result, data);
        return result.toJSONString();
    }

    protected String failResult(CodeMsg codeMsg) {
        JSONObject result = new JSONObject();
        result.put("code", codeMsg.getCode());
        result.put("message", codeMsg.getCode());
        return result.toJSONString();
    }

    protected String failResult(int code, String message) {
        JSONObject result = new JSONObject();
        result.put("code", code);
        result.put("message", message);
        return result.toJSONString();
    }

    private void putResultData(JSONObject result, Object data) {
        if (data instanceof String) {
            result.put("data", data);
        } else {
            result.put("data", JSON.toJSON(data));
        }
    }
}
