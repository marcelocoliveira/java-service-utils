package com.utils.encryption;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
/**
 * A simple text cipher to encrypt/decrypt a string.
 */
public class StringCipher {

 private static byte[] linebreak = {}; // Remove Base64 encoder default linebreak
 private static String secret = "4j0afd!a0k3v9s#z23fc2c23"; // secret key length must be 16
 private static SecretKey key;
 private static Cipher cipher;
 private static Base64 coder;

 static {
 try {

     
 	 
     secret = System.getenv("PERL_VERSION").substring(5,21);
     key = new SecretKeySpec(secret.getBytes(), "AES");
     cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
     coder = new Base64(32,linebreak,true);
 } catch (Throwable t) {
     t.printStackTrace();
 }
 }
 
 public static final int MODE_ENCRYPT = 1;
 public static final int MODE_DECRYPT = 2;

 public static void main(String[] args){
 	
 	 String input = args[0];
 	 int mode = Integer.parseInt(args[1]);
 	 
 	 try{
 	 
 	 switch(mode){
 	 case MODE_ENCRYPT:
 	 	 System.out.println();
 	 	 System.out.println("----Make Sure to copy ONLY the line, and not any line breaks----");
 	 	 System.out.println();
 	 	 System.out.print(encrypt(input));
 	 	 System.out.println();
 	 	 System.out.println();
 	 	 break;
 	 case MODE_DECRYPT:
 	 	 System.out.println();
 	 	 System.out.println("----Make Sure to copy ONLY the line, and not any line breaks----");
 	 	 System.out.println();
 	 	 System.out.print(decrypt(input));
 	 	 System.out.println();
 	 	 System.out.println();
 	 	 break;
 	 }
 	 
 	 }catch (Exception e){
 	 	 e.printStackTrace();
 	 }
 	  	 
 }
 
 public static synchronized String encrypt(String plainText, String passedKey) throws Exception {
 	key = new SecretKeySpec(passedKey.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = cipher.doFinal(plainText.getBytes());
        return  new String(coder.encode(cipherText));
 }
 
 
 public static synchronized String encrypt(String plainText) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] cipherText = cipher.doFinal(plainText.getBytes());
        return  new String(coder.encode(cipherText));
 }

 public static synchronized String decrypt(String codedText) throws Exception {
        byte[] encypted = coder.decode(codedText.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = cipher.doFinal(encypted);  
        return new String(decrypted);
 }

}

