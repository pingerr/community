package com.nowcoder.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

public class CommunityUtil {

    // 生成随机字符串（eg. 验证码、上传文件名）
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // MD5加密 （只能加密，不能解密）
    // hello -> abc123def456 一一对应，黑客可以用自己的字典破解
    // hello + 3e4a8 （随机字符串）-> abc123def456abc 加盐后，黑客无法盗取
    public static String md5(String key) {
        if(StringUtils.isBlank(key)) { // 判空
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

}
