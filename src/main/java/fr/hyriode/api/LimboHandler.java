package fr.hyriode.api;

import fr.hyriode.api.limbo.IHyriLimbo;
import fr.hyriode.hyggdrasil.api.limbo.HyggLimbo;
import fr.hyriode.hyggdrasil.api.protocol.data.HyggApplication;
import fr.hyriode.hyggdrasil.api.protocol.data.HyggData;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by AstFaster
 * on 05/01/2023 at 21:11
 */
public class LimboHandler implements IHyriLimbo {

    private final ReentrantReadWriteLock playersLock = new ReentrantReadWriteLock();

    private final String name;
    private HyggLimbo.Type type;
    private HyggLimbo.State state;

    private final HyggData data;

    private final Set<UUID> players;

    private final long startedTime;

    public LimboHandler(HyggApplication application) {
        if (application == null) {
            this.name = "dev-limbo";
            this.type = HyggLimbo.Type.LOGIN;
            this.state = HyggLimbo.State.STARTING;
            this.data = new HyggData();
            this.players = new HashSet<>();
            this.startedTime = System.currentTimeMillis();
            return;
        }

        final HyggLimbo handle = HyriAPI.get().getLimboManager().getLimbo(application.getName());

        this.name = Objects.requireNonNull(handle).getName();
        this.type = handle.getType();
        this.state = HyggLimbo.State.STARTING;
        this.data = handle.getData();
        this.players = handle.getPlayers();
        this.startedTime = handle.getStartedTime();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public @NotNull HyggLimbo.Type getType() {
        return this.type;
    }

    @Override
    public void setType(@NotNull HyggLimbo.Type type) {
        this.type = type;

        this.update();
    }

    @Override
    public HyggLimbo.State getState() {
        return this.state;
    }

    @Override
    public void setState(HyggLimbo.State state) {
        this.state = state;

        this.update();
    }

    @Override
    public @NotNull HyggData getData() {
        return this.data;
    }

    @Override
    public @NotNull Set<UUID> getPlayers() {
        this.playersLock.readLock().lock();

        try {
            return this.players;
        } finally {
            this.playersLock.readLock().unlock();
        }
    }

    @Override
    public void addPlayer(@NotNull UUID player) {
        this.playersLock.writeLock().lock();

        try {
            this.players.add(player);

            this.update();
        } finally {
            this.playersLock.writeLock().unlock();
        }
    }

    @Override
    public void removePlayer(@NotNull UUID player) {
        this.playersLock.writeLock().lock();

        try {
            this.players.remove(player);

            this.update();
        } finally {
            this.playersLock.writeLock().unlock();
        }
    }

    @Override
    public long getStartedTime() {
        return this.startedTime;
    }

    private void update() {
        HyriAPI.get().getHyggdrasilManager().sendData();
    }

}
