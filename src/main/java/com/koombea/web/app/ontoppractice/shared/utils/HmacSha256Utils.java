/*
 * @creator: Oswaldo Montes
 * @date: December 14, 2023
 *
 */
package com.koombea.web.app.ontoppractice.shared.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacUtils;

import java.nio.charset.StandardCharsets;

public class HmacSha256Utils {

    private static final String SECRET_KEY = "JFTz35j3_ueAJva_gKL6fxX1tw8kiOqvomCpTt0i8kij58WQ2D_4tQ";

    public static String encodeBase64URLSafeString(String payload) {
        HmacUtils hmacUtils = new HmacUtils("HmacSHA256", SECRET_KEY);
        byte[] digest = hmacUtils.hmac(payload);
        return Base64.encodeBase64URLSafeString(digest);
    }
}
