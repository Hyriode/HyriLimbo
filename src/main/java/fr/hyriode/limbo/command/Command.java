package fr.hyriode.limbo.command;

import fr.hyriode.limbo.player.PlayerSession;

/**
 * Created by AstFaster
 * on 08/01/2023 at 09:41
 */
public abstract class Command {

    protected final String name;
    protected final String[] aliases;

    public Command(String name, String... aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    public abstract void execute(PlayerSession player, String label, String[] args);

    public String getName() {
        return this.name;
    }

    public String[] getAliases() {
        return this.aliases;
    }

}
