package fr.hyriode.limbo;

import fr.hyriode.limbo.network.Server;
import fr.hyriode.limbo.player.PlayerSession;
import fr.hyriode.limbo.protocol.ProtocolRepository;
import fr.hyriode.limbo.util.IOUtil;
import fr.hyriode.limbo.util.References;
import fr.hyriode.limbo.util.logger.ColoredLogger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by AstFaster
 * on 21/12/2022 at 11:51
 */
public class HyriLimbo {

    private static HyriLimbo instance;

    private ColoredLogger logger;

    private ProtocolRepository protocolRepository;

    private final ReentrantReadWriteLock playersLock = new ReentrantReadWriteLock();
    private final Map<UUID, PlayerSession> players = new ConcurrentHashMap<>();

    private Server server;

    public void start() {
        instance = this;

        ColoredLogger.printHeaderMessage();
        IOUtil.createDirectory(References.LOG_FOLDER);

        this.logger = new ColoredLogger(References.NAME, References.LOG_FILE);

        System.out.println("Starting " + References.NAME + "...");

        this.protocolRepository = new ProtocolRepository();
        this.server = new Server(25565);
        this.server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    }

    public void stop() {
        System.out.println("Stopping " + References.NAME + "...");

        this.server.stop();
    }

    public static HyriLimbo get() {
        return instance;
    }

    public ColoredLogger getLogger() {
        return this.logger;
    }

    public ProtocolRepository getProtocolRepository() {
        return this.protocolRepository;
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
