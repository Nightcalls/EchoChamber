package echochamber.member;

public class MemberRole {
    public enum Roles {
        ADMIN(Names.ADMIN),
        USER(Names.USER),
        BANNED(Names.BANNED);

        public static class Names {
            public static final String ADMIN = "Admin";
            public static final String USER = "User";
            public static final String BANNED = "Banned";
        }

        private final String role;

        private Roles(String role) {
            this.role = role;
        }

        public String toString() {
            return this.role;
        }
    }
}
