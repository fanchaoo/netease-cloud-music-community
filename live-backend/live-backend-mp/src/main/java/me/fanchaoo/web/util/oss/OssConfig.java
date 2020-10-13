package me.fanchaoo.web.util.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * OssConfig
 *
 * @author lirenjie
 */
@Data
@Component
@ConfigurationProperties(prefix = "oss")
public class OssConfig {

    private String domainUrl;
    /**
     * 授权ID
     */
    private String accessKeyId;

    /**
     * 授权秘钥
     */
    private String accessKeySecret;

    /**
     * 指定桶名
     */
    private String bucketName;

    /**
     * 访问域名，也指文件存储在哪个区域节点，如华北1
     */
    private String endPoint;

    /**
     * 上传内网地址
     */
    private String endPointInner;

    /**
     * sts授权角色
     */
    private String rolearn;

    /**
     * roleSessionName
     */
    private String roleSessionName;

    /**
     * regionId
     */
    private String regionId;

    private String baseDownloadUrl;

}
