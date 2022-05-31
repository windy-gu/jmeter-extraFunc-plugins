package org.tester.jmeter.functions.core;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.KeyFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class RsaEncryptUtils {
    /**
     * RSA 公钥加密/解密工具
     * @author Windy.Gu
     * @date 2022-2-10 13:15
     */
    private static final Logger log = LoggerFactory.getLogger(RsaEncryptUtils.class);
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        return keyPairGenerator.generateKeyPair();
    }

    public static String decryptString(String encryptString, String privateKey)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] bytes = Base64.decodeBase64(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        PrivateKey privateKey1 = keyFactory.generatePrivate(keySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey1);
        byte[] bytes1 = cipher.doFinal(Base64.decodeBase64(encryptString));
        return new String(bytes1, StandardCharsets.UTF_8);
    }

    public static String encryptString(String plaintext, String publicKey)
            throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException {
        log.debug("plaintext={}", plaintext);
        log.debug("publicKey={}", publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey1 = keyFactory.generatePublic(keySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey1);
        byte[] bytes1 = cipher.doFinal(plaintext.getBytes());
        return new String(Base64.encodeBase64(bytes1), StandardCharsets.UTF_8);
    }


    public static void main(String[] args) throws Exception {

        String tempEncryptString = encryptString("test", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCjblMlMerHC2Dx2+mbrW2jjmBDgDaSolsXZuC3e2oc94xBHPn0C4Dyna1xRkBPPWW1I4GoBWRL32LDQ8xqfugv8UxcAiOQdTXVJ5XbHSXCBfA+aySRg9tjfVONyxp1cwUhon23NkdY9Jy/XE7VKUzjCvhwasfbjg83oQY8dyQefQIDAQAB");
        System.out.println("tempEncryptString data:" + tempEncryptString);
        String tempDecryptString = decryptString("k+rFOIFAvqaB3TSTXMH9xQO2+STWRPNOu4HYvRtjSHrgKo/tI9x1a0qI0p0bXhHvbTVMRXIze/o1Xo3BWW7Xm26tOxd1BFPgg/WiERObTHejeDeAMCbl+CFB+kFHjaYrk6zu/DGT98QI36xahVm3dfnO9pUbh5y9U9XVjCZOZxU=", "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKNuUyUx6scLYPHb6ZutbaOOYEOANpKiWxdm4Ld7ahz3jEEc+fQLgPKdrXFGQE89ZbUjgagFZEvfYsNDzGp+6C/xTFwCI5B1NdUnldsdJcIF8D5rJJGD22N9U43LGnVzBSGifbc2R1j0nL9cTtUpTOMK+HBqx9uODzehBjx3JB59AgMBAAECgYEAkiymL1lzTS5vOkPgsP4wVx6MGTO/G+4dHVRF37aB1YDQMJjzM4VqgxXmwK797CHVK2ujlx2jdH1Lv6yPVB1IG9D9BQyGUcyiKPcOfW1FNSlqlHPfRkUezL63ZWz1r90RXv0pIVrDPTNjLL8e26rDllBqyq4FaTvKaIyAnRswsX0CQQDmHoBbIpv0IxHqIDGyyE9fjqaDrS8Ih50Od3Eaj+9IgFuulwk3PpXZ3YbGiSsfNTOLKkDRtzjl0UgEDa6jUEd3AkEAtc/DfoewtvLq9cSRk0RG6sZZLJbeQS9Dym3YUmKhF35XBSfpi5giFAGrX/APEPT0xzpzoy3ok8uvX9usrvsuqwJBAM39Vba+102GKx4iLXq1Vl/aiPHsD+MZyhxODP/TAkYrKGazqrs6GdfJNkD4PpKUBAy6CQXxdkw6t7VxLffg/+cCQAOYaDVIWaZEh4D0sixHXV+nLHxFP3/qf1yxb2WxrZ3ZUb84WtX33DSpE8qB5MphDbYi4caIUeEaVg1/Opr7PKECQQDHxlWno6sEN/dTo0lM1HAH3imZQh+eZptaydkiVLeaMVV9Y2aS5MhpcC7t9k5d0nwVFL4zjUto4NnwXNaR5Ew1");
        System.out.println("tempDecryptString data:" + tempDecryptString);
    }
}
