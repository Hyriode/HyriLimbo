package fr.hyriode.limbo.player.profile;

import java.security.*;
import java.util.Base64;

/**
 * Created by AstFaster
 * on 22/12/2022 at 09:49
 */
public record Property(String name, String value, String signature) {

    public boolean hasSignature() {
        return this.signature != null;
    }

    public boolean isSignatureValid(PublicKey key) {
        try {
            final Signature signature = Signature.getInstance("SHA1withRSA");

            signature.initVerify(key);
            signature.update(this.value.getBytes());

            return signature.verify(Base64.getDecoder().decode(this.signature));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return false;
    }

}
