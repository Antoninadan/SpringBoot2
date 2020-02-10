package com.mainacad.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.security.MD5Encoder;

import java.io.UnsupportedEncodingException;

public class TestUtil {
    public static void main(String[] args) {
        String pass = "345345";
        String encodedPass = DigestUtils.md5Hex(pass);
        System.out.println("encodedPass = " + encodedPass);

    }
}
