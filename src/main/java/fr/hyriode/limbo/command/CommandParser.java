package fr.hyriode.limbo.command;

/**
 * Created by AstFaster
 * on 08/01/2023 at 09:42
 */
public class CommandParser {

    public String[] parse(String input) {
        return input.trim().replaceAll(" +", " ").split(" ");
    }

}