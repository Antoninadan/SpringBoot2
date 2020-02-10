package com.mainacad.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;

public class PasswordUtil {

    public static final String KEY = "ghfgf";

    public static String encodePassword(String pass) {
        return KEY + DigestUtils.md5Hex(pass.getBytes());
    }

    public static boolean isAnable(String passInDB, String inputPass){
        String encodedInputPass = encodePassword(inputPass);
        return encodedInputPass.equals(passInDB);
    }
}
