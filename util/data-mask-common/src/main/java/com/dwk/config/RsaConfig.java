package com.dwk.config;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.KeyPair;

/**
 * @author duweikun
 * @date 2023/7/18  15:04
 * @description  加解密配置
 */
@Slf4j
public class RsaConfig {

    public static String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALemt8fHKTcOjdKl5NoWM22Q5KmtIaB32VoOKN9SJ0Uk7MPGVFSGIRYlb3dABvwG2dLf5WrWUy+z5+E/h2/+Kx1Qk5EDskzbfJMajE2/QiouR0uk/Kkc+ANPbywQPohU3jsaVrMw1FXwWMeZ0QAcoWS+KEmpM00o05nvz9NSRrBlAgMBAAECgYATUfQqja6tzpen7fDd6pD9eU089mw/s+wHN88f80YTVZec3uv3OmcXq9eGnZM+hz/6GbjKXQLCVXRS83p9kykYzjDAInwiWLRS6nF+mFJO8OQJsAmk4UHd38vYXypQuBkaKR6iqmc/ZZZT/5Q/xxqgyUXV4C3fFO2P/e8on6OrzwJBAOaEVeJAoLt8PmBla2KKDbzMR1qVvjOeY0Abh09AnbDOqWMPlseANl5Z2KCWNj7Vx/FYnUhb1LAXaRMP7pFfo7sCQQDL9BN8CKdJD9jnXL3LgNTwp7g5kcBYLVdxrcEfIXSMfT6lQhs0B7vOKIkgZMoAde/KpzHrZ9OPwEJ7KeHKYOpfAkBh7FWPh2TAQEc/K9YyRfMQALdPA82IoAAJe6g/QnO6aBAMxNKIbS6rZf9gOG9Swf3tyHcIBfqT014PevUdTEOBAkEAxFKdUt7NkKHFi+gU9kh8MW9xAVGQkhCLRO0ZogrFMtr4q4E3yFzNZkRUwy7h15GvEibhAZX+6zgJhp8pLnMeBwJBAKkRGhzYXIDRCux3ogpshasB3HU2RU/BsTtVhfUfsmiCBXeOb0RaLVMjBung42gAj1Vl+rM8gI6r13H9Dmka4RE=";
    public static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3prfHxyk3Do3SpeTaFjNtkOSprSGgd9laDijfUidFJOzDxlRUhiEWJW93QAb8BtnS3+Vq1lMvs+fhP4dv/isdUJORA7JM23yTGoxNv0IqLkdLpPypHPgDT28sED6IVN47GlazMNRV8FjHmdEAHKFkvihJqTNNKNOZ78/TUkawZQIDAQAB";

    /**
     *
     * @param args
     * @throws UnsupportedEncodingException
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        pair.getPrivate();
        PRIVATE_KEY = Base64.encode(pair.getPrivate().getEncoded());
        PUBLIC_KEY = Base64.encode(pair.getPublic().getEncoded());
        log.info("私钥："+PRIVATE_KEY);
        log.info("公钥："+PUBLIC_KEY);
    }

}
