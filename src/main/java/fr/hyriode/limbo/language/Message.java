package fr.hyriode.limbo.language;

import fr.hyriode.api.language.HyriLanguageMessage;
import fr.hyriode.api.player.IHyriPlayer;
import fr.hyriode.limbo.player.PlayerSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Created by AstFaster
 * on 23/07/2022 at 09:51
 */
public enum Message {

    COMMAND_DOESNT_EXIST_MESSAGE("command.doesnt-exist.message"),
    COMMAND_NO_PERMISSION_MESSAGE("command.no-permission.message"),
    COMMAND_BAD_USAGE_MESSAGE("command.bad-usage.message"),

    LOGIN_PREFIX("login.prefix"),

    LOGIN_INVALID_PASSWORD_MESSAGE("login.invalid-password.message", LOGIN_PREFIX),
    LOGIN_SUCCESS_MESSAGE("login.success.message", LOGIN_PREFIX),

    REGISTER_PASSWORDS_DONT_MATCH_MESSAGE("register.passwords-dont-match.message", LOGIN_PREFIX),
    REGISTER_INVALID_PASSWORD_MESSAGE("register.invalid-password.message", LOGIN_PREFIX),
    REGISTER_SUCCESS_MESSAGE("register.success.message", LOGIN_PREFIX),

    AFK_TITLE("afk.title"),
    AFK_SUBTITLE("afk.subtitle"),

    LOGIN_TITLE("login.title"),
    LOGIN_LOGIN_SUBTITLE("login.login-subtitle"),
    LOGIN_REGISTER_SUBTITLE("login.register-subtitle"),

    ;

    private HyriLanguageMessage languageMessage;

    private final String key;
    private final BiFunction<IHyriPlayer, String, String> formatter;

    Message(String key, BiFunction<IHyriPlayer, String, String> formatter) {
        this.key = key;
        this.formatter = formatter;
    }

    Message(String key, Message prefix) {
        this.key = key;
        this.formatter = (target, input) -> prefix.asString(target) + input;
    }

    Message(String key) {
        this(key, (target, input) -> input);
    }

    public HyriLanguageMessage asLang() {
        return this.languageMessage == null ? this.languageMessage = HyriLanguageMessage.get(this.key) : this.languageMessage;
    }

    public String asString(IHyriPlayer account) {
        return this.formatter.apply(account, this.asLang().getValue(account));
    }

    public String asString(PlayerSession player) {
        return this.asString(IHyriPlayer.get(player.getUniqueId()));
    }

    public List<String> asList(IHyriPlayer account) {
        return new ArrayList<>(Arrays.asList(this.asString(account).split("\n")));
    }

    public List<String> asList(PlayerSession player) {
        return this.asList(IHyriPlayer.get(player.getUniqueId()));
    }

}
