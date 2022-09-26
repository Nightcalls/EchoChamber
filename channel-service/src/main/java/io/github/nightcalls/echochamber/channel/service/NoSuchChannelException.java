package io.github.nightcalls.echochamber.channel.service;

public class NoSuchChannelException extends RuntimeException {
    public NoSuchChannelException(long channelId) {
        super("Channel with ID " + channelId + " doesn't exist");
    }
}
