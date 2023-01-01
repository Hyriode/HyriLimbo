package fr.hyriode.limbo.player;

import fr.hyriode.api.HyriAPI;
import fr.hyriode.limbo.HyriLimbo;
import fr.hyriode.limbo.data.Title;
import fr.hyriode.limbo.network.pipeline.PacketCodec;
import fr.hyriode.limbo.player.profile.GameProfile;
import fr.hyriode.limbo.protocol.Protocol;
import fr.hyriode.limbo.protocol.ProtocolState;
import fr.hyriode.limbo.protocol.ProtocolVersion;
import fr.hyriode.limbo.protocol.packet.PacketOut;
import fr.hyriode.limbo.protocol.packet.impl.PacketOutDisconnect;
import fr.hyriode.limbo.protocol.packet.impl.login.PacketLoginOutSuccess;
import fr.hyriode.limbo.protocol.packet.impl.play.*;
import fr.hyriode.limbo.protocol.packet.impl.status.PacketStatusOutPong;
import fr.hyriode.limbo.protocol.packet.impl.status.PacketStatusOutResponse;
import fr.hyriode.limbo.world.Difficulty;
import fr.hyriode.limbo.world.Dimension;
import fr.hyriode.limbo.world.GameMode;
import io.netty.channel.Channel;

import java.util.UUID;

/**
 * Created by AstFaster
 * on 21/12/2022 at 21:01
 */
public class PlayerSession {

    private final Channel channel;

    private ProtocolState protocolState;
    private ProtocolVersion protocolVersion;
    private Protocol protocol;

    private GameProfile profile;

    private long lastKeepAlive = -1;
    private long keepAliveId = 0;

    public PlayerSession(Channel channel) {
        this.channel = channel;
        this.protocolVersion = ProtocolVersion.earliest();
        this.protocol = HyriLimbo.get().getProtocolRepository().getProtocol(this.protocolVersion);
        this.protocolState = ProtocolState.HANDSHAKE;
    }

    public void sendPacket(PacketOut packet) {
        this.channel.writeAndFlush(packet);
    }

    public void sendStatusResponse() {
        this.sendPacket(new PacketStatusOutResponse("HyriLimbo", this.protocolVersion.getId(), 1000, HyriLimbo.get().getPlayers().size(), "A lightweight Limbo server by Hyriode", ""));
    }

    public void sendStatusPong(long id) {
        this.sendPacket(new PacketStatusOutPong(id));
    }

    public void sendLoginSuccess() {
        this.sendPacket(new PacketLoginOutSuccess(this.profile.id(), this.profile.name()));
    }

    public void sendKeepAlive() {
        this.sendPacket(new PacketPlayOutKeepAlive(this.keepAliveId += 1));
    }

    public void sendJoinGame(GameMode gameMode, Dimension dimension, Difficulty difficulty, int maxSlots, String worldType, boolean reducedDebugInfo) {
        this.sendPacket(new PacketPlayOutJoinGame(0, gameMode, dimension, difficulty, maxSlots, worldType, reducedDebugInfo));
    }

    public void sendPosition(double x, double y, double z, float yaw, float pitch) {
        this.sendPacket(new PacketPlayOutPositionAndLook(x, y, z, yaw, pitch));
    }

    public void sendTitle(Title title) {
        this.sendPacket(new PacketPlayOutTitle(title.getFadeIn(), title.getStay(), title.getFadeOut()));

        if (title.getTitle() != null) {
            this.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.Action.TITLE, title.getTitle()));
        }

        if (title.getSubtitle() != null) {
            this.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.Action.SUBTITLE, title.getSubtitle()));
        }
    }

    public void sendMessage(String message) {
        this.sendPacket(new PacketPlayOutMessage(PacketPlayOutMessage.ChatPosition.CHAT, message));
    }

    public void sendActionBar(String actionBar) {
        this.sendPacket(new PacketPlayOutMessage(PacketPlayOutMessage.ChatPosition.ACTION_BAR, actionBar));
    }

    public void disconnect(String reason) {
        this.destroy();

        if (this.protocolState == ProtocolState.HANDSHAKE || this.protocolState == ProtocolState.STATUS) {
            return;
        }

        this.sendPacket(new PacketOutDisconnect(reason));
    }

    public void destroy() {
        if (this.profile != null) {
            System.out.println(this.getName() + " left the server.");

            HyriLimbo.get().removePlayer(this.profile.id());
        }

        if (this.channel.isActive()) {
            this.channel.close();
        }
    }

    public Channel getChannel() {
        return this.channel;
    }

    public ProtocolState getProtocolState() {
        return this.protocolState;
    }

    public void setProtocolState(ProtocolState protocolState) {
        this.protocolState = protocolState;
        this.channel.pipeline().get(PacketCodec.class).setProtocolState(this.protocolState);
    }

    public Protocol getProtocol() {
        return this.protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public ProtocolVersion getProtocolVersion() {
        return this.protocolVersion;
    }

    public void setProtocolVersion(ProtocolVersion protocolVersion) {
        this.protocolVersion = protocolVersion;
        this.channel.pipeline().get(PacketCodec.class).setProtocolVersion(this.protocolVersion);
    }

    public String getName() {
        return this.profile.name();
    }

    public UUID getUniqueId() {
        return this.profile.id();
    }

    public GameProfile getProfile() {
        return this.profile;
    }

    public void setProfile(GameProfile profile) {
        this.profile = profile;
    }

    public long getLastKeepAlive() {
        return this.lastKeepAlive;
    }

    public void setLastKeepAlive(long lastKeepAlive) {
        this.lastKeepAlive = lastKeepAlive;
    }

    public long getKeepAliveId() {
        return this.keepAliveId;
    }

}
