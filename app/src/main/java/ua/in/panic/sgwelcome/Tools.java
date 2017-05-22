package ua.in.panic.sgwelcome;

import android.app.Application;
import android.util.ArrayMap;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by PaNiC on 21.05.2017.
 */

public class Tools extends Application {
    private static Tools instance;

    public static Tools getInstance() {
        if (null == instance){
            instance = new Tools();
        }
        return instance;
    }

    /**
     * Hashes the password with MD5.
     * @param s
     * @return
     */
    String md5(String s) {
        try {

            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            return s;
        }
    }


}
