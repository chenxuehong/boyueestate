package com.tencent.qcloud.tim.uikit.utils;

import android.os.Build;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class IMFunc {

    private static final char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public IMFunc() {
    }

    public static byte[] getHmacSHA1(byte[] binaryData, String strKey) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec secretKey = new SecretKeySpec(strKey.getBytes(), "HmacSHA1");
        mac.init(secretKey);
        byte[] HmacSha1Digest = null;

        try {
            HmacSha1Digest = mac.doFinal(binaryData);
        } catch (IllegalStateException var6) {
        }

        return HmacSha1Digest;
    }

    public static String base64Encode(byte[] binaryData) {
        return Base64.encodeToString(binaryData, 2);
    }

    public static byte[] gzCompress(String str) throws IOException {
        if (str != null && str.length() != 0) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes());
            gzip.close();
            return out.toByteArray();
        } else {
            return null;
        }
    }

    public static String byte2hex(byte[] buffer) {
        return byte2hexInternal(buffer);
    }

    public static String byte2hexInternal(byte[] buffer) {
        try {
            StringBuilder strBuilder = new StringBuilder();
            if (buffer != null && buffer.length != 0) {
                for(int i = 0; i < buffer.length; ++i) {
                    byte b = buffer[i];
                    char cl = digits[b & 15];
                    b = (byte)(b >>> 4);
                    char ch = digits[b & 15];
                    strBuilder.append(ch).append(cl);
                }

                return strBuilder.toString();
            }
        } catch (OutOfMemoryError var6) {
            System.gc();
        }

        return "b2h failed";
    }

    public static String calcSHA(byte[] binaryData) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(binaryData);
        return byte2hexInternal(md.digest()).replace(" ", "");
    }

    public static String getExceptionInfo(Throwable e) {
        if (e == null) {
            return "";
        } else {
            String info = e.toString();
            StackTraceElement[] elements = e.getStackTrace();
            if (elements != null) {
                StackTraceElement[] var3 = elements;
                int var4 = elements.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    StackTraceElement elem = var3[var5];
                    info = info + "\n\tat " + elem.toString();
                }
            }

            return info;
        }
    }

    public static boolean httpPostReq(String url, final byte[] req, final Map<String, String> proMaps, final com.tencent.imsdk.utils.IMFunc.RequestListener listener) {
        final HttpURLConnection http;
        try {
            http = (HttpURLConnection)(new URL(url)).openConnection();
        } catch (Throwable var6) {
            var6.printStackTrace();
            if (null != listener) {
                listener.onFail(getExceptionInfo(var6));
            }

            return false;
        }

        (new Thread(new Runnable() {
            public void run() {
                try {
                    http.setRequestMethod("POST");
                    http.setDoOutput(true);
                    http.setRequestProperty("Content-Length", String.valueOf(req.length));
                    http.setRequestProperty("connection", "close");
                    Iterator var1 = proMaps.entrySet().iterator();

                    while(var1.hasNext()) {
                        Map.Entry<String, String> entry = (Map.Entry)var1.next();
                        http.setRequestProperty((String)entry.getKey(), (String)entry.getValue());
                    }

                    http.getOutputStream().write(req);
                    InputStream in = new BufferedInputStream(http.getInputStream());
                    ByteArrayOutputStream bout = new ByteArrayOutputStream(10240);
                    byte[] buf = new byte[10240];

                    while(true) {
                        int n = in.read(buf);
                        if (n < 0) {
                            byte[] result = bout.toByteArray();
                            if (null != listener) {
                                listener.onSuccess(result);
                            }
                            break;
                        }

                        bout.write(buf, 0, n);
                    }
                } catch (Throwable var8) {
                    var8.printStackTrace();
                    String es = var8.toString();
                    if (null != listener) {
                        listener.onFail(es);
                    }
                } finally {
                    http.disconnect();
                }

            }
        })).start();
        return true;
    }

    public static byte[] getParamBytes(String boundary, String name, String filename, String value) {
        String param = "--" + boundary + "\r\nContent-Disposition: form-data; name=\"" + name + "\"; filename=\"" + filename + "\"\r\n\r\n" + value + "\r\n";
        return param.getBytes();
    }

    public static byte[] getParamBytes(String boundary, String name, String filename, byte[] binaryData) {
        String param = "--" + boundary + "\r\nContent-Disposition: form-data; name=\"" + name + "\"; filename=\"" + filename + "\"\r\n\r\n";
        byte[] paramByte = param.getBytes();
        byte[] bts = new byte[paramByte.length + binaryData.length + 2];
        System.arraycopy(paramByte, 0, bts, 0, paramByte.length);
        System.arraycopy(binaryData, 0, bts, paramByte.length, binaryData.length);
        System.arraycopy("\r\n".getBytes(), 0, bts, paramByte.length + binaryData.length, 2);
        return bts;
    }

    public static String appSignature(int appId, String secretId, String secretKey, long expired, String fileId, String bucketName) throws Exception {
        if (secretId != null && secretKey != null && fileId != null && bucketName != null) {
            long now = System.currentTimeMillis() / 1000L;
            int rdm = Math.abs((new Random()).nextInt());
            String plainText = "a=" + appId + "&k=" + secretId + "&e=" + (now + expired) + "&t=" + now + "&r=" + rdm + "&f=" + fileId + "&b=" + bucketName;
            byte[] hmacDigest = getHmacSHA1(plainText.getBytes(), secretKey);
            byte[] signContent = new byte[hmacDigest.length + plainText.getBytes().length];
            System.arraycopy(hmacDigest, 0, signContent, 0, hmacDigest.length);
            System.arraycopy(plainText.getBytes(), 0, signContent, hmacDigest.length, plainText.getBytes().length);
            return base64Encode(signContent);
        } else {
            return "-1";
        }
    }

    public static int getClientInstType() {
        String vendor = Build.MANUFACTURER;
        int vendorId = 2002;
        if (isBrandXiaoMi()) {
            vendorId = 2000;
        } else if (isBrandHuawei()) {
            vendorId = 2001;
        } else if (isBrandMeizu()) {
            vendorId = 2003;
        } else if (isBrandOppo()) {
            vendorId = 2004;
        } else if (isBrandVivo()) {
            vendorId = 2005;
        }

        return vendorId;
    }

    public static boolean isBrandXiaoMi() {
        return "xiaomi".equalsIgnoreCase(Build.BRAND) || "xiaomi".equalsIgnoreCase(Build.MANUFACTURER);
    }

    public static boolean isBrandHuawei() {
        return "huawei".equalsIgnoreCase(Build.BRAND) || "huawei".equalsIgnoreCase(Build.MANUFACTURER);
    }

    public static boolean isBrandMeizu() {
        return "meizu".equalsIgnoreCase(Build.BRAND) || "meizu".equalsIgnoreCase(Build.MANUFACTURER) || "22c4185e".equalsIgnoreCase(Build.BRAND);
    }

    public static boolean isBrandOppo() {
        return "oppo".equalsIgnoreCase(Build.BRAND) || "oppo".equalsIgnoreCase(Build.MANUFACTURER);
    }

    public static boolean isBrandVivo() {
        return "vivo".equalsIgnoreCase(Build.BRAND) || "vivo".equalsIgnoreCase(Build.MANUFACTURER);
    }

    public interface RequestListener {
        void onSuccess(byte[] var1);

        void onFail(String var1);
    }
}
