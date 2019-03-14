package fun.modelbook.boxssoserver.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HTTP请求工具类
 * @author jory
 * @date 2019-03-14
 */
public class HttpClientUtil {
    private static final Logger LOGGER = LogManager.getLogger(HttpClientUtil.class.getName());


    /**
     * 默认连接超时时间，单位：毫秒
     */
    private static final int DEFAULT_CONNECTION_TIMEOUT = 5000;
    /**
     * 默认请求超时时间，单位：毫秒
     */
    private static final int DEFAULT_SOCKET_TIMEOUT = 10000;

    /**
     * 默认字符集
     */
    private static final String DEFAULT_CHARSET = "UTF-8";

    private HttpClientUtil() {

    }

    /**
     * 发送一个get请求
     *
     * @param url               请求的连接
     * @param connectionTimeout 连接超时时间，单位：毫秒
     * @param socketTimeout     请求超时时间，单位：毫秒
     * @param charsetName       返回结果字符编码，默认为UTF-8
     * @return 请求结果
     * @throws IOException
     */
    private static String get(String url, Integer connectionTimeout, Integer socketTimeout, String charsetName)
            throws IOException {
        return new String(Request.Get(url)
                .connectTimeout(connectionTimeout == null ? DEFAULT_CONNECTION_TIMEOUT : connectionTimeout)
                .socketTimeout(socketTimeout == null ? DEFAULT_SOCKET_TIMEOUT : socketTimeout).execute()
                .returnContent().asBytes(), charsetName == null ? DEFAULT_CHARSET : charsetName);
    }

    /**
     * 发送一个get请求, 连接超时时间、请求超时时间、编码字符集均使用默认值
     *
     * @param url 请求地址
     * @return 请求结果
     * @throws IOException
     * @see #DEFAULT_CONNECTION_TIMEOUT
     * @see #DEFAULT_SOCKET_TIMEOUT
     * @see #DEFAULT_CHARSET
     */
    private static String get(String url) throws IOException {
        return HttpClientUtil.get(url, DEFAULT_CONNECTION_TIMEOUT, DEFAULT_SOCKET_TIMEOUT, DEFAULT_CHARSET);
    }

    /**
     * 发送一个get请求, 屏蔽异常，异常时返回null
     *
     * @param url               请求的连接
     * @param connectionTimeout 连接超时时间，单位：毫秒
     * @param socketTimeout     请求超时时间，单位：毫秒
     * @param charsetName       返回结果字符编码，默认为UTF-8
     * @return 请求结果
     * @see #get(String, Integer, Integer, String)
     */
    public static String getWithoutException(String url, Integer connectionTimeout, Integer socketTimeout,
                                             String charsetName) {
        try {
            return HttpClientUtil.get(url, connectionTimeout, socketTimeout, charsetName);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

            return null;
        }
    }

    /**
     * 发送一个get请求, 连接超时时间、请求超时时间、编码字符集均使用默认值。该请求屏蔽异常，异常时返回null
     *
     * @param url 请求的连接
     * @return 请求结果
     * @see #DEFAULT_CONNECTION_TIMEOUT
     * @see #DEFAULT_SOCKET_TIMEOUT
     * @see #DEFAULT_CHARSET
     * @see #get(String)
     */
    public static String getWithoutException(String url) {
        try {
            return HttpClientUtil.get(url);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

            return null;
        }
    }

    /**
     * 发送一个post请求
     *
     * @param url               请求地址
     * @param data              请求参数
     * @param encodeCharset     编码字符集
     * @param decodeCharset     解码字符集
     * @param connectionTimeout 连接超时时间，单位：毫秒
     * @param socketTimeout     请求超时时间，单位：毫秒
     * @return 请求结果
     * @throws IOException
     */
    private static String post(String url, String data, String encodeCharset, String decodeCharset,
                               Integer connectionTimeout, Integer socketTimeout) throws IOException {
        Request post = buildPostWithoutBody(url, encodeCharset, connectionTimeout, socketTimeout)
                .bodyString(URLEncoder.encode(data, (encodeCharset == null ? DEFAULT_CHARSET : encodeCharset)), ContentType.APPLICATION_FORM_URLENCODED);
        return new String(post.execute()
                .returnContent().asBytes(), decodeCharset == null ? DEFAULT_CHARSET : decodeCharset);
    }

    /**
     * 发送一个post请求
     *
     * @param url               请求地址
     * @param data              请求参数
     * @param encodeCharset     编码字符集
     * @param decodeCharset     解码字符集
     * @param connectionTimeout 连接超时时间，单位：毫秒
     * @param socketTimeout     请求超时时间，单位：毫秒
     * @return 请求结果
     * @throws IOException
     */
    private static String post(String url, Map<String, String> data, String encodeCharset, String decodeCharset,
                               Integer connectionTimeout, Integer socketTimeout) throws IOException {
        Request post = buildPostWithoutBody(url, encodeCharset, connectionTimeout, socketTimeout);
        if (data != null) {
            Form form = Form.form();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                form.add(entry.getKey(), entry.getValue());
            }
            post.bodyForm(form.build(), Charset.forName(encodeCharset == null ? DEFAULT_CHARSET : encodeCharset));
        }

        List<BasicNameValuePair> basicNameValuePairs = toNameValuePairList(data);
        if (CollectionUtils.isNotEmpty(basicNameValuePairs)) {
            String printString = URLEncodedUtils.format(basicNameValuePairs, '&', "utf8");
            System.out.println("Final Query UriConstant:" + url + "?" + printString);
        }

        return new String(post.execute().returnContent().asBytes(), decodeCharset == null ? DEFAULT_CHARSET
                : decodeCharset);
    }

    /**
     * 构建一个无body的post对象
     *
     * @param url               请求地址
     * @param encodeCharset     编码字符集
     * @param connectionTimeout 连接超时时间，单位：毫秒
     * @param socketTimeout     请求超时时间，单位：毫秒
     * @return post对象
     * @throws IOException
     */
    private static Request buildPostWithoutBody(String url, String encodeCharset,
                                                Integer connectionTimeout, Integer socketTimeout) throws IOException {
        return Request
                .Post(url)
                .setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .bodyString(URLEncoder.encode(url, (encodeCharset == null ? DEFAULT_CHARSET : encodeCharset)),
                        ContentType.APPLICATION_FORM_URLENCODED)
                .connectTimeout(connectionTimeout == null ? DEFAULT_CONNECTION_TIMEOUT : connectionTimeout)
                .socketTimeout(socketTimeout == null ? DEFAULT_SOCKET_TIMEOUT : socketTimeout);
    }

    private static List<BasicNameValuePair> toNameValuePairList(Map<String, String> args) {
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        for (Map.Entry<String, String> entry : args.entrySet()) {
            BasicNameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            params.add(nameValuePair);
        }
        return params;
    }

    /**
     * 发送一个post请求, 屏蔽异常，异常时返回null
     *
     * @param url               请求地址
     * @param data              请求参数
     * @param encodeCharset     编码字符集
     * @param decodeCharset     解码字符集
     * @param connectionTimeout 连接超时时间，单位：毫秒
     * @param socketTimeout     请求超时时间，单位：毫秒
     * @return 请求结果
     */
    public static String postWithoutException(String url, String data, String encodeCharset, String decodeCharset,
                                              Integer connectionTimeout, Integer socketTimeout) {
        try {
            return HttpClientUtil.post(url, data, encodeCharset, decodeCharset, connectionTimeout, socketTimeout);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

            return null;
        }
    }

    /**
     * 发送一个post请求, 屏蔽异常，异常时返回null
     *
     * @param url               请求地址
     * @param data              请求参数
     * @param encodeCharset     编码字符集
     * @param decodeCharset     解码字符集
     * @param connectionTimeout 连接超时时间，单位：毫秒
     * @param socketTimeout     请求超时时间，单位：毫秒
     * @return 请求结果
     */
    public static String postWithoutException(String url, Map<String, String> data, String encodeCharset,
                                              String decodeCharset, Integer connectionTimeout, Integer socketTimeout) {
        try {
            return HttpClientUtil.post(url, data, encodeCharset, decodeCharset, connectionTimeout, socketTimeout);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

            return null;
        }
    }

    /**
     * 发送一个post请求，编解码字符集、超时时间均使用默认值
     *
     * @param url  请求地址
     * @param data 请求参数
     * @return 请求结果
     * @throws IOException
     * @see #DEFAULT_CHARSET
     * @see #DEFAULT_SOCKET_TIMEOUT
     * @see #DEFAULT_CONNECTION_TIMEOUT
     */
    private static String post(String url, String data) throws IOException {
        return HttpClientUtil.post(url, data, DEFAULT_CHARSET, DEFAULT_CHARSET, DEFAULT_CONNECTION_TIMEOUT,
                DEFAULT_SOCKET_TIMEOUT);
    }

    /**
     * 发送一个post请求，屏蔽异常，异常时返回null，编解码字符集、超时时间均使用默认值.
     *
     * @param url  请求地址
     * @param data 请求参数
     * @return 请求结果
     * @see #DEFAULT_CHARSET
     * @see #DEFAULT_SOCKET_TIMEOUT
     * @see #DEFAULT_CONNECTION_TIMEOUT
     */
    public static String postWithoutException(String url, String data) {
        try {
            return HttpClientUtil.post(url, data);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

            return null;
        }
    }

    /**
     * 发送一个post请求，编解码字符集、超时时间均使用默认值
     *
     * @param url  请求地址
     * @param data 请求参数
     * @return 请求结果
     * @throws IOException
     * @see #DEFAULT_CHARSET
     * @see #DEFAULT_SOCKET_TIMEOUT
     * @see #DEFAULT_CONNECTION_TIMEOUT
     */
    private static String post(String url, Map<String, String> data) throws IOException {
        return HttpClientUtil.post(url, data, DEFAULT_CHARSET, DEFAULT_CHARSET, DEFAULT_CONNECTION_TIMEOUT,
                DEFAULT_SOCKET_TIMEOUT);
    }

    /**
     * 发送一个post请求，屏蔽异常，异常时返回null，编解码字符集、超时时间均使用默认值.
     *
     * @param url  请求地址
     * @param data 请求参数
     * @return 请求结果
     * @see #DEFAULT_CHARSET
     * @see #DEFAULT_SOCKET_TIMEOUT
     * @see #DEFAULT_CONNECTION_TIMEOUT
     */
    public static String postWithoutException(String url, Map<String, String> data) {
        try {
            return HttpClientUtil.post(url, data);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

            return null;
        }
    }


    /**
     * 发送一个get请求
     * 并添加一个自定义的头部属性
     *
     * @param url               请求的连接
     * @param connectionTimeout 连接超时时间，单位：毫秒
     * @param socketTimeout     请求超时时间，单位：毫秒
     * @param charsetName       返回结果字符编码，默认为UTF-8
     * @param headerName        自定义的header名称
     * @param headerValue       自定义的header值
     * @return 请求结果
     * @throws IOException
     */
    private static String getWithHeader(String url, Integer connectionTimeout, Integer socketTimeout, String charsetName, String headerName, String headerValue)
            throws IOException {
        return new String(Request.Get(url)
                .addHeader(headerName, headerValue)
                .connectTimeout(connectionTimeout == null ? DEFAULT_CONNECTION_TIMEOUT : connectionTimeout)
                .socketTimeout(socketTimeout == null ? DEFAULT_SOCKET_TIMEOUT : socketTimeout).execute()
                .returnContent().asBytes(), charsetName == null ? DEFAULT_CHARSET : charsetName);
    }

    private static String getWithJsonHeader(String url, String headerName, String headerValue)
            throws IOException {
        return new String(Request.Get(url)
                .addHeader(headerName, headerValue)
                .addHeader("Accept", "application/json, text/plain, /")
                .connectTimeout(DEFAULT_CONNECTION_TIMEOUT)
                .socketTimeout(DEFAULT_SOCKET_TIMEOUT).execute()
                .returnContent().asBytes(), DEFAULT_CHARSET);
    }

    /**
     * 发送一个get请求, 连接超时时间、请求超时时间、编码字符集均使用默认值
     * 并添加一个自定义的头部属性
     *
     * @param url         请求地址
     * @param headerName  自定义的header名称
     * @param headerValue 自定义的header值
     * @return 请求结果
     * @throws IOException
     * @see #DEFAULT_CONNECTION_TIMEOUT
     * @see #DEFAULT_SOCKET_TIMEOUT
     * @see #DEFAULT_CHARSET
     */
    private static String getWithHeader(String url, String headerName, String headerValue) throws IOException {
        return getWithHeader(url, DEFAULT_CONNECTION_TIMEOUT, DEFAULT_SOCKET_TIMEOUT, DEFAULT_CHARSET, headerName, headerValue);
    }

    /**
     * 发送一个get请求, 屏蔽异常，异常时返回null
     * 并添加一个自定义的头部属性
     *
     * @param url               请求的连接
     * @param connectionTimeout 连接超时时间，单位：毫秒
     * @param socketTimeout     请求超时时间，单位：毫秒
     * @param charsetName       返回结果字符编码，默认为UTF-8
     * @param headerName        自定义的header名称
     * @param headerValue       自定义的header值
     * @return 请求结果
     * @see #get(String, Integer, Integer, String)
     */
    public static String getWithHeaderWithoutException(String url, Integer connectionTimeout, Integer socketTimeout,
                                                       String charsetName, String headerName, String headerValue) {
        try {
            return getWithHeader(url, connectionTimeout, socketTimeout, charsetName, headerName, headerValue);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

            return null;
        }
    }

    /**
     * 发送一个get请求, 连接超时时间、请求超时时间、编码字符集均使用默认值。该请求屏蔽异常，异常时返回null
     * 并添加一个自定义的头部属性
     *
     * @param url         请求的连接
     * @param headerName  自定义的header名称
     * @param headerValue 自定义的header值
     * @return 请求结果
     * @see #DEFAULT_CONNECTION_TIMEOUT
     * @see #DEFAULT_SOCKET_TIMEOUT
     * @see #DEFAULT_CHARSET
     * @see #get(String)
     */
    public static String getWithHeaderWithoutException(String url, String headerName, String headerValue) {
        try {
            return getWithHeader(url, headerName, headerValue);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

            return null;
        }
    }

    public static String getWithJsonHeaderWithoutException(String url) {
        String headerName = "Accept";
        String headerValue = "application/json, text/plain, /";
        try {
            return getWithHeader(url, headerName, headerValue);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

            return null;
        }
    }

    /**
     * 带cookie请求
     *
     * @param url
     * @param headerName
     * @param headerValue
     * @return
     */
    public static String getWithCookieWithoutException(String url, String headerName, String headerValue) {
        try {
            return getWithJsonHeader(url, headerName, headerValue);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

            return null;
        }
    }

}
