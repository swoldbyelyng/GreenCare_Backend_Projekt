package api.database;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.security.SecureRandom;
import java.util.Base64;

public class Passwords {

    public String createHash(char[] password) {
        Argon2 argon2 = Argon2Factory.create();
        String hash = argon2.hash(10, 65536, 1, password);
        argon2.wipeArray(password);
        return hash;
    }

    public String createSalt(){
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[28];
        random.nextBytes(bytes);
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        String salt = encoder.encodeToString(bytes);
        return salt;
    }
}
