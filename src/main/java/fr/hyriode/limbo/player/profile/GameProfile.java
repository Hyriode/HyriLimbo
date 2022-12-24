package fr.hyriode.limbo.player.profile;

import java.util.List;
import java.util.UUID;

/**
 * Created by AstFaster
 * on 22/12/2022 at 09:45
 */
public record GameProfile(UUID id, String name, List<Property> properties) {

}
