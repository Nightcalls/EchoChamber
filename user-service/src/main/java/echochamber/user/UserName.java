package echochamber.user;

import java.util.Set;

public class UserName {
    static final int MIN_NAME_LENGTH = 4;
    static final int MAX_NAME_LENGTH = 32;
    static final Set<Character> ILLEGAL_CHARACTERS = Set.of('\u0000', ' ');

    private final String name;

    public UserName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Username can't be null");
        }
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Username length should be between "
                    + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " characters (" + name.length() + ")");
        }
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (ILLEGAL_CHARACTERS.contains(c)) {
                throw new IllegalArgumentException("Username contains illegal character '"
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
        UserName userName = (UserName) o;
        return name.equals(userName.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
