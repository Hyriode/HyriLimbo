package fr.hyriode.limbo.command;

import fr.hyriode.limbo.command.impl.LobbyCommand;
import fr.hyriode.limbo.command.impl.LoginCommand;
import fr.hyriode.limbo.command.impl.RegisterCommand;
import fr.hyriode.limbo.language.Message;
import fr.hyriode.limbo.player.PlayerSession;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AstFaster
 * on 08/01/2023 at 09:42
 */
public class CommandManager {

    private final Map<String, Command> commands = new HashMap<>();

    private final CommandParser parser;

    public CommandManager() {
        this.parser = new CommandParser();

        this.registerCommand(new LobbyCommand());
        this.registerCommand(new RegisterCommand());
        this.registerCommand(new LoginCommand());
    }

    public void process(PlayerSession player, String input) {
        this.executeCommand(player, this.parser.parse(input));
    }

    public void executeCommand(PlayerSession player, String[] args) {
        final String label = args[0].substring(1).toLowerCase();
        final Command command = this.getCommand(label);

        if (command == null) {
            player.sendMessage(Message.COMMAND_DOESNT_EXIST_MESSAGE.asString(player).replace("%command%", label));
            return;
        }

        command.execute(player, label, args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : new String[]{});
    }

    public void registerCommand(Command command) {
        this.commands.put(command.getName(), command);
    }

    public Command getCommand(String label) {
        final Command command = this.commands.get(label);

        return command == null ? this.commands.values().stream().filter(c -> Arrays.stream(c.getAliases()).allMatch(alias -> alias.equals(label))).findFirst().orElse(null) : command;
    }

}
