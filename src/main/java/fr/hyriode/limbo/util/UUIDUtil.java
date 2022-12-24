package fr.hyriode.limbo.util;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Created by AstFaster
 * on 24/12/2022 at 10:09
 */
public class UUIDUtil {

    public static UUID getOffline(String username) {
        return UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(StandardCharsets.UTF_8));
    }

    public static UUID fromString(String input) {
        return UUID.fromString(input.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
    }

}
