package io.github.nightcalls.echochamber.user.service;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(long userId) {
        super("User with ID " + userId + " doesn't exist");
    }
}
