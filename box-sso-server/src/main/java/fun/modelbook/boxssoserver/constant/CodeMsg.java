package fun.modelbook.boxssoserver.constant;

import lombok.Getter;
import org.omg.PortableInterceptor.SUCCESSFUL;

/**
 * 返回码枚举
 *
 * @author jory
 * @date 2019-03-14
 */
@Getter
public enum CodeMsg {

    /**
     * 通用错误信息
     */
    SUCCESS(0, "success"),
    FAIL(1, "fail"),

    /**
     * 权限错误信息
     */
    NOT_LOGIN(100, "未登录"),
    EMAIL_PASS_NOT_MATCH(101, "用户名或密码错误"),
    ACCOUNT_NOT_BIND(102, "黑名单用户，请联系管理员"),
    TOKEN_INVALID(103, "token无效"),
    TOKEN_EXPIRED(104, "token过期"),
    USER_INFO_INVALID(105, "用户个人信息异常"),

    /**
     * 参数检查错误信息
     */
    ARGS_MISSING(201, "参数不全"),
    ARGS_ILLEGAL(202, "非法参数"),
    SIGN_INCORRECT(203, "签名错误"),
    ;


    private int code;
    private String msg;

    CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
