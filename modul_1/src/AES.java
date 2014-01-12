import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

import javax.crypto.Cipher;

/**
 * Klasa AES odpowiedzialna za utajnianie tresci testamentu za pomoca
 * szyfrowania AES.
 */
public class AES {
	
	static String IV = "LoveMyLittlePony";
	
	static String decrypted = new String();
	
	static String added = "7526841457218985643178021036504875960236520145541012518565148898747412366985630891287524586247522671102877002546010257870486522450211982";
  
	/**
	 * Metoda odpowiadajaca za przebieg szyfrowania AES.
	 * @param blocks
	 * Lista lancuchow znakowych stanowiacych bloki testamentu przeznaczone do zaszyfrowania.
	 * @param encryptionKey
	 * 16-znakowy klucz wprowadzany przez uzytkownika przeznaczony do szyfrowania AES.
	 * @param Filepath
	 * Sciezka prowadzaca do pliku tekstowego zawierajacego tresc testamentu wprowadzona przez uzytkownika.
	 */
	public void AES(List<String> blocks, String encryptionKey, String Filepath) {
    try {

      System.out.println("==JAVA==");
    String s = new String();
    
    byte[] cipher = new byte[] {};
    
    BigInteger BI = new BigInteger("250");
    for (int j = 0; j < blocks.size(); j++)
    {
    	cipher = encrypt(blocks.get(j), encryptionKey);
        for (int i=0; i<cipher.length; i++){
          s= s + (BI.add((new BigInteger(""+cipher[i])).add(new BigInteger("250")).multiply(new BigInteger(""+added))).toString(16) +" ");
        }
    	
    }
      PrintWriter ciphered = new PrintWriter(Filepath);
      ciphered.println(s);
      ciphered.close();
      
      System.out.print("cipher:  " + s);
      
      System.out.println(s);
      
      

     PrintWriter deciphered = new PrintWriter("testament_odszyfrowany.txt");
     deciphered.println(decrypted);
     deciphered.close();
     
  	  ClientConfig.ciphered = true;
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }

  /**
   * Metoda szyfrujaca.
   * @param plainText
   * Lancuch znakowy stanowiacy tekst jawny testamentu.
   * @param encryptionKey
   * 16-znakowy klucz wprowadzany przez uzytkownika przeznaczony do szyfrowania AES.
   * @return
   * Metoda zwraca zaszyfrowana tresc testamentu (sekret).
   */
  public static byte[] encrypt(String plainText, String encryptionKey) throws Exception {
    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
    SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
    cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
    return cipher.doFinal(plainText.getBytes("UTF-8"));
  }

}




