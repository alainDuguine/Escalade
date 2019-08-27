package com.alain.metier;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

public class Utilities {

    public static final String[] paramList = {"nomSpot", "officiel", "departement", "ville", "cotationMin", "cotationMax", "secteurMin", "secteurMax"};

    /**
     * Crée et trie une liste de départements à partir d'une liste d'objets SpotResearchDto
     * @param spotResearchDtoList liste des objets à partir desquels extraire et trier les departements
     * @return le treemap créé
     */
    public static TreeMap<String, String> getDepartementSortedFromList(List<SpotResearchDto> spotResearchDtoList){
        TreeMap<String, String> departementsMap = new TreeMap<>();
        // Injection de la liste dans un treeMap
        for (SpotResearchDto spot : spotResearchDtoList){
            if (!departementsMap.containsKey(spot.getDepartementCode())){
                departementsMap.put(spot.getDepartementCode(), spot.getDepartementNom());
            }
        }
        return  departementsMap;
    }

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

    /**
     * Vérifie si le string est vide
     * @param string
     * @return
     */
    public static boolean isEmpty(String string){
        return (string == null && string.isEmpty());
    }

    /**
     * retourne le champ auquel on a enlevé les espaces superflus
     * @param req
     * @param champ
     * @return
     */
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
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
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
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        byte[] salt = new byte[16];
        assert sr != null;
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * formate une variable LocalDateTime en français
     * @param date
     * @return
     */
    public static String dateStringFr(LocalDateTime date){
        String dateFormat;
        String[] moisFr = {"Janvier", "Février", "Mars", "Avril", "Mai","Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Décembre"};
        return dateFormat = date.getDayOfMonth() + " " + moisFr[date.getMonthValue()-1] + " " + date.getYear() + " - " + getFullHour(date) + ":" + getFullMinute(date);
    }

    private static String getFullHour(LocalDateTime date) {
        return (date.getHour()<10?"0":"") + date.getHour();
    }

    private static String getFullMinute(LocalDateTime date) {
        return (date.getMinute()<10?"0":"") + date.getMinute();
    }

    /**
     * Créé une map contenant les paramètres pour exécter requête dynamique, à partir de l'objet HttpServletRequest
     * depuis le formulaire de recherche rechercheSpot.jsp
     * @param req
     * @return une Map contenant le nom du paramètre et sa valeur
     */
    public static Map<String, Object> getParameterMapFromReq(HttpServletRequest req) {
        Map<String, Object> paramMap = new HashMap<>();
        for (String param : paramList) {
            if (param.contains("Min") || param.contains("Max")) {
                if (req.getParameter(param) != "") {
                    if(param.contains("cotation")) {
                        paramMap.put(param, Long.parseLong(req.getParameter(param)));
                    }else{
                        paramMap.put(param, Integer.parseInt(req.getParameter(param)));
                    }
                } else {
                    paramMap.put(param, null);
                }
            }else if (param.equals("officiel")) {
                if (req.getParameter(param) != null) {
                    paramMap.put(param, true);
                } else {
                    paramMap.put(param, false);
                }
            } else {
                paramMap.put(param, req.getParameter(param));
            }
        }
        return paramMap;
    }
}
