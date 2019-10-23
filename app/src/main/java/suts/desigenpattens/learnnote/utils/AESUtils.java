package suts.desigenpattens.learnnote.utils;

import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密方式 AES DES 使用的时候注意区分
 * <p>
 * 调用 API 时需要对请求参数进行签名,服务器端会验证请求参数是否合法。 加密规则
 * <p>
 * 1. 对 request 参数值进行对称加密,再进行 base64 编码
 * 对称加密,使用的算法:DESede/CBC/PKCS5Padding, iv 向量位 8 字节的 16 进制 0
 * 密钥为 appsecret(base64 编码)
 * <p>
 * 2. 所有请求参数(除 sign 外)按照字母先后顺序排列
 * 例如:将 method, registerid,request,timestamp,v 排序为,method,registerid, request ,timestamp,v
 * <p>
 * 3. 把所有参数名和参数值进行拼装
 * 例如: methodxxxxxxtimestampxxxxxxvx
 * <p>
 * 4. 使用 rsa-sha1 算法进行签名后,再进行 base64 编码
 */
public class AESUtils {

    private final static String HEX = "0123456789ABCDEF";
    private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";// AES是加密方式
    // CBC是工作模式
    // PKCS5Padding是填充模式
    private static final String AES = "AES";// AES 加密
    private static final String SHA1PRNG = "SHA1PRNG";//// SHA1PRNG 强随机种子算法,
    private static final String DES_CBC_PKCS5_PADDING = "DES/CBC/PKCS5Padding";// 加密常量参数
    //// 要区别4.2以上版本的调用方法

    private static String getKey() {
        return "pS_2017+synj0nes";
    }

    // 对密钥进行处理
    private static Key getRawKey(String key) throws Exception {
        DESKeySpec dks = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        return keyFactory.generateSecret(dks);
    }

    public static String encrypt(String strData) {
        if (strData == null || strData.length() == 0)
            return null;
        return encrypt(strData.getBytes());
    }

    public static String encrypt(byte[] data) {

        String baseKey = getKey();

        try {
            Cipher cipher = Cipher.getInstance(DES_CBC_PKCS5_PADDING);
            IvParameterSpec iv = new IvParameterSpec(baseKey.substring(0, 8).getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, getRawKey(baseKey.substring(0, 8)), iv);
            byte[] bytes = cipher.doFinal(data);
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            return null;
        }
    }

    public static String decrypt(String data) {
        return decode(Base64.decode(data, Base64.DEFAULT));
    }

    public static String DecryptDoNet(String message) throws Exception {

        String key = "SYNJONES";

        byte[] bytesrc = Base64.decode(message.getBytes(), Base64.DEFAULT);
        Cipher cipher = Cipher.getInstance(DES_CBC_PKCS5_PADDING);
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte);

    }

    public static String decryptUrl(String data) {
        String baseKey = "SYNJONES";
        try {
            Cipher cipher = Cipher.getInstance(DES_CBC_PKCS5_PADDING);
            IvParameterSpec iv = new IvParameterSpec(baseKey.substring(0, 8).getBytes());
            cipher.init(Cipher.DECRYPT_MODE, getRawKey(baseKey.substring(0, 8)), iv);
            byte[] original = cipher.doFinal(Base64.decode(data, Base64.DEFAULT));
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @return 解密后的字节数组
     */
    private static String decode(byte[] data) {
        String baseKey = getKey();
        try {
            Cipher cipher = Cipher.getInstance(DES_CBC_PKCS5_PADDING);
            IvParameterSpec iv = new IvParameterSpec(baseKey.substring(0, 8).getBytes());
            cipher.init(Cipher.DECRYPT_MODE, getRawKey(baseKey.substring(0, 8)), iv);
            byte[] original = cipher.doFinal(data);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            return null;
        }
    }

    // 二进制转字符
    private static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    // 对密钥进行处理
    private static byte[] getRawKey(byte[] seed) throws NoSuchAlgorithmException, NoSuchProviderException {
        // return Base64.encodeToString(seed, Base64.DEFAULT).getBytes();
        KeyGenerator kgen = KeyGenerator.getInstance(AES);
        // for android
        SecureRandom sr = null;
        // 在4.2以上版本中，SecureRandom获取方式发生了改变

        /*
         * 7.0系统兼容NoSuchProviderException: no such provider: Crypto
         * 目前项目最低sdk构建版本为19，去除原先17的兼容
         */
        if (Build.VERSION.SDK_INT >= 23) {
            sr = SecureRandom.getInstance("SHA1PRNG", new CryptoProvider());
        } else {
            sr = SecureRandom.getInstance(SHA1PRNG);
        }
        // for Java
        // secureRandom = SecureRandom.getInstance(SHA1PRNG);
        sr.setSeed(seed);
        kgen.init(128, sr); // 256 bits or 128 bits,192bits
        // AES中128位密钥版本有10个加密循环，192比特密钥版本有12个加密循环，256比特密钥版本则有14个加密循环。
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    /*
     * 加密
     */
    public static String encrypt(String key, String cleartext) {
        if (TextUtils.isEmpty(cleartext)) {
            return cleartext;
        }
        try {
            byte[] result = encrypt(key, cleartext.getBytes());
            return Base64.encodeToString(result, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 加密
     */
    private static byte[] encrypt(String key, byte[] clear) throws Exception {
        byte[] raw = getRawKey(key.getBytes());
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
        Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    /*
     * 解密
     */
    public static String decrypt(String key, String encrypted) {
        if (TextUtils.isEmpty(encrypted)) {
            return encrypted;
        }
        try {
            byte[] enc = Base64.decode(encrypted, Base64.DEFAULT);
            byte[] result = decrypt(key, enc);
            return new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 解密
     */
    private static byte[] decrypt(String key, byte[] encrypted) throws Exception {
        byte[] raw = getRawKey(key.getBytes());
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
        Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }


    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位 （历史写法使用  “SYNJONES”  ）
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws Exception 异常
     */
    public static String desEncode(String key, String data) throws Exception {
        return desEncode(key, data.getBytes());
    }

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @param key  加密私钥，长度不能够小于8位
     * @return 加密后的字节数组，一般结合Base64编码使用
     * @throws Exception 异常
     */
    private static String desEncode(String key, byte[] data) throws Exception {
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(DES_CBC_PKCS5_PADDING);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes());
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(data);
            return Base64.encodeToString(bytes, 0);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public static final class CryptoProvider extends Provider {
        private static final long serialVersionUID = 7991202868423459598L;

        /**
         * Creates a Provider and puts parameters
         */
        public CryptoProvider() {
            super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
            put("SecureRandom.SHA1PRNG", "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
    }

}
