package echochamber.member;

import echochamber.channel.ChannelName;

import java.util.Set;

public class MemberName {
    static final int MIN_NAME_LENGTH = 4;
    static final int MAX_NAME_LENGTH = 32;
    static final Set<Character> ILLEGAL_CHARACTERS = Set.of('\u0000', ' ');

    private final String name;

    public MemberName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Member name can't be null");
        }
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Member name length should be be between "
                    + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters (" + name.length() + ")");
        }
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (ILLEGAL_CHARACTERS.contains(c)) {
                throw new IllegalArgumentException("Channel name contains illegal character '"
                        + c + "' (" + ((int) c) + ") at pos " + i);
            }
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberName memberName = (MemberName) o;
        return name.equals(memberName.name);
    }

    @Override
    public int hashCode() {return name.hashCode();}
}
