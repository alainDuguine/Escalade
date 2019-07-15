package com.alain.metier;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class Utilities {

    private static final String CHAMP_PHOTO = "photo";
    private static final int TAILLE_TAMPON = 10240;
    private static final String CHEMIN_UPLOAD = "D:\\fichiers\\";
    private static final Long TAILLE_PHOTO_MAX = 2000000L; // 2,5Mo
    private static final Long TOTAL_PHOTO_MAX = 250000000L; // 25Mo

    public Map<String,String> erreurs = new HashMap<String, String>();

    public static boolean checkMail(String email){
        if ( email != null ) {
            if (email.matches("^[\\w._-]+@[\\w._-]+\\.[a-z]{2,4}$")) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkPassword(String password, String confirmation) {
        if (password != null && confirmation != null) {
            if (password.equals(confirmation)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(String string){
        if (string != null && !string.isEmpty()){
            return true;
        }
        return false;
    }

    public static String getValeurChamp(HttpServletRequest req, String champ) {
        String valeur = req.getParameter(champ);
        if ( valeur == null || valeur.isEmpty()){
            return null;
        }else{
            return valeur.trim();
        }
    }

    /**
     * Hashing password with SHA algorithm
     * @param password non crypted
     * @return password crypted
     */
    public static String getSecurePassword(String password, byte[] salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i=0; i< bytes.length; i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     * Add random data as additional input to hash password.
     * @return the randomly generated bytes
     */
    public static byte[] getSalt() {
        SecureRandom sr = null;
        try {
            sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

}
