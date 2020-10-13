package me.fanchaoo.web.util.oss;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;
import me.fanchaoo.web.util.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * OssUtils
 *
 * @author lirenjie
 */
@Slf4j
public class OssUtils {

    private static final String DOT = ".";
    private static OssConfig ossConfig;
    private static OSSClient ossClient;
    private static final int EXPIRE_TIME = 60 * 15 * 1000;
    private static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat OSS_DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    static {
        init();
    }

    /**
     * 初始化
     */
    private static void init() {
        ossConfig = BeanUtils.getBean(OssConfig.class);
        try {
            ClientConfiguration config = new ClientConfiguration();
            // 配置参数
            // 允许打开的最大HTTP连接数
            config.setMaxConnections(100);
            // Socket层传输数据的超时时间,10秒
            config.setSocketTimeout(10000);
            // 建立连接的超时时间,100秒
            config.setConnectionTimeout(10000);
            // 从连接池中获取连接的超时时间,10秒
            config.setConnectionRequestTimeout(10000);
            String endPoint = ossConfig.getEndPoint();
            ossClient = new OSSClient(endPoint, (new DefaultCredentialProvider(ossConfig.getAccessKeyId(),
                    ossConfig.getAccessKeySecret())), config);
        } catch (Exception e) {
            log.error("阿里云oss服务创建失败", e);
        }
    }

    /**
     * 上传文件
     *
     * @param bytes    bytes
     * @param key      key
     * @param fileName name
     */
    public static void uploadFile(byte[] bytes, String key, String fileName) throws UnsupportedEncodingException {
        uploadFile(new ByteArrayInputStream(bytes), key, fileName);
    }

    /**
     * 获取后缀名
     *
     * @param filename
     * @return
     */
    public static String getSuffix(String filename) {
        if (StringUtils.isBlank(filename)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String suffix = "";
        if (filename.contains(DOT)) {
            suffix = filename.toLowerCase().substring(filename.lastIndexOf('.'));
        }
        return suffix;
    }

    /**
     * 上传文件
     *
     * @param is       is
     * @param key      key
     * @param fileName namme
     */
    public static void uploadFile(InputStream is, String key, String fileName) throws UnsupportedEncodingException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        String disposition = getDisposition(fileName);
        objectMetadata.setContentDisposition(disposition);
        ossClient.putObject(ossConfig.getBucketName(), key, is, objectMetadata);
    }

    /**
     * 获取临时访问链接
     *
     * @param key        key
     * @param expireTime 有效时长（单位毫秒）
     * @return 临时访问链接
     */
    public static String getTemporaryUrl(String key, Integer expireTime) {
        if (expireTime == null || expireTime < 0) {
            expireTime = EXPIRE_TIME;
        }
        long expireDate = System.currentTimeMillis() + expireTime;
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(ossConfig.getBucketName(), key, HttpMethod.GET);
        req.setExpiration(new Date(expireDate));
        URL tempUrl = ossClient.generatePresignedUrl(req);
        String result = tempUrl.toString();
        if (StringUtils.isNotBlank(result) && StringUtils.isNotBlank(ossConfig.getEndPointInner())) {
            result = result.replaceAll(ossConfig.getEndPointInner(), ossConfig.getEndPoint());
        }
        return result;
    }



    /**
     * 生成key
     *
     * @param app  应用
     * @param uuid uuid
     * @return key
     */
    public static String genarateKey(String app, String uuid) {
        return app + "/" + DF.format(new Date()) + "/" + uuid;
    }

    /**
     * 下载文件
     *
     * @param key key
     * @return bytes
     * @throws IOException IOException
     */
    public static byte[] downloadFile(String key) throws IOException {
        OSSObject ossObject = ossClient.getObject(ossConfig.getBucketName(), key);
        byte[] bytes;
        try {
            bytes = IOUtils.toByteArray(ossObject.getObjectContent());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if (ossObject != null) {
                ossObject.close();
            }
        }
        return bytes;
    }

    /**
     * shutdown
     */
    public static void shutdown() {
        if (ossClient != null) {
            ossClient.shutdown();
        }
    }

    /**
     * getDisposition
     *
     * @param filename filename
     * @return filename
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    private static String getDisposition(String filename) throws UnsupportedEncodingException {
        String headerValue = "attachment;";
        headerValue += " filename=\"" + URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20") + "\";";
        headerValue += " filename*=utf-8''" + URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        return headerValue;
    }

}
