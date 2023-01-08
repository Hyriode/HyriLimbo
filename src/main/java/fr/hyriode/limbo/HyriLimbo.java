package fr.hyriode.limbo;

import fr.hyriode.api.HyriAPI;
import fr.hyriode.api.HyriAPIImpl;
import fr.hyriode.api.language.HyriLanguage;
import fr.hyriode.hyggdrasil.api.limbo.HyggLimbo;
import fr.hyriode.limbo.command.CommandManager;
import fr.hyriode.limbo.config.LimboConfig;
import fr.hyriode.limbo.network.Server;
import fr.hyriode.limbo.player.PlayerSession;
import fr.hyriode.limbo.player.listener.PlayerListener;
import fr.hyriode.limbo.protocol.ProtocolRepository;
import fr.hyriode.limbo.util.IOUtil;
import fr.hyriode.limbo.util.References;
import fr.hyriode.limbo.util.logger.ColoredLogger;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by AstFaster
 * on 21/12/2022 at 11:51
 */
public class HyriLimbo {

    private static final Path LANG_FOLDERS = Paths.get("lang");

    private static HyriLimbo instance;

    private ColoredLogger logger;
    private LimboConfig config;

    private HyriAPIImpl hyriAPI;

    private ProtocolRepository protocolRepository;
    private CommandManager commandManager;

    private final ReentrantReadWriteLock playersLock = new ReentrantReadWriteLock();
    private final Map<UUID, PlayerSession> players = new ConcurrentHashMap<>();

    private Server server;

    public void start() {
        instance = this;

        ColoredLogger.printHeaderMessage();
        IOUtil.createDirectory(References.LOG_FOLDER);

        this.logger = new ColoredLogger(References.NAME, References.LOG_FILE);

        System.out.println("Starting " + References.NAME + "...");

        this.config = LimboConfig.load();
        this.hyriAPI = new HyriAPIImpl(this.config.hyriAPI());
        this.loadLanguages();
        this.protocolRepository = new ProtocolRepository();
        this.commandManager = new CommandManager();

        this.hyriAPI.getEventBus().register(new PlayerListener());
        this.hyriAPI.getLanguageManager().registerAdapter(PlayerSession.class, (message, player) -> message.getValue(player.getUniqueId()));
        this.hyriAPI.getLimbo().setState(HyggLimbo.State.READY);

        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));

        this.server = new Server(25565);
        this.server.start();
    }

    public void stop() {
        System.out.println("Stopping " + References.NAME + "...");

        for (PlayerSession player : this.getPlayers()) {
            player.destroy();
        }

        this.hyriAPI.getLimbo().setState(HyggLimbo.State.SHUTDOWN);
        this.hyriAPI.stop();
        this.server.stop();
    }

    private void loadLanguages() {
        try {
            for (HyriLanguage language : HyriLanguage.values()) {
                final String path = "/lang/" + language.getCode() + ".json";

                try (final InputStream inputStream = HyriLimbo.class.getResourceAsStream(path)) {
                    if (inputStream == null) {
                        System.err.println("Cannot get resource from '" + path + "'!");
                        continue;
                    }

                    final Path langFile = Paths.get(LANG_FOLDERS.toString(), language.getCode() + ".json");
                    final boolean exists = Files.exists(langFile);

                    if (!exists) {
                        FileUtils.copyInputStreamToFile(inputStream, langFile.toFile());
                        continue;
                    }

                    try (final InputStream existingInput = Files.newInputStream(langFile); final InputStream clonedInput = HyriLimbo.class.getResourceAsStream(path)) {
                        if (IOUtil.toMD5(clonedInput).equals(IOUtil.toMD5(existingInput))) {
                            continue;
                        }

                        Files.delete(langFile);
                        FileUtils.copyInputStreamToFile(inputStream, langFile.toFile());
                    }
                }
            }

            HyriAPI.get().getLanguageManager().loadLanguagesMessages(LANG_FOLDERS.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static HyriLimbo get() {
        return instance;
    }

    public ColoredLogger getLogger() {
        return this.logger;
    }

    public LimboConfig getConfig() {
        return this.config;
    }

    public HyriAPIImpl getHyriAPI() {
        return this.hyriAPI;
    }

    public ProtocolRepository getProtocolRepository() {
        return this.protocolRepository;
    }

    public CommandManager getCommandManager() {
        return this.commandManager;
    }

    public void addPlayer(PlayerSession player) {
        this.playersLock.writeLock().lock();

        try {
            this.players.put(player.getUniqueId(), player);
        } finally {
            this.playersLock.writeLock().unlock();
        }
    }

    public void removePlayer(UUID playerId) {
        this.playersLock.writeLock().lock();

        try {
            this.players.remove(playerId);
        } finally {
            this.playersLock.writeLock().unlock();
        }
    }

    public PlayerSession getPlayer(UUID playerId) {
        this.playersLock.readLock().lock();

        try {
            return this.players.get(playerId);
        } finally {
            this.playersLock.readLock().unlock();
        }
    }

    public Collection<PlayerSession> getPlayers() {
        this.playersLock.readLock().lock();

        try {
            return this.players.values();
        } finally {
            this.playersLock.readLock().unlock();
        }
    }

    public Server getServer() {
        return this.server;
    }

}
