package echochamber.channel.api;

import echochamber.user.api.grpc.UserApi;

public class UserForChannelDto {
    private final long id;

    public UserForChannelDto(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "UserForChannelDto{" +
                "id=" + id +
                '}';
    }

//    public static UserForChannelDto fromUser(UserApi user) {
//        return new UserForChannelDto(
//                UserApi.User.
//
//        );
//    }
}
