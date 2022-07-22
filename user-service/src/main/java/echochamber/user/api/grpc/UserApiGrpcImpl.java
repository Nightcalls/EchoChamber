package echochamber.user.api.grpc;

import echochamber.user.User;
import echochamber.user.api.grpc.UserApi.GetUserRequest;
import echochamber.user.api.grpc.UserApi.GetUserResponse;
import echochamber.user.service.UserSearchService;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserApiGrpcImpl extends UserApiServiceGrpc.UserApiServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(UserApiGrpcImpl.class);

    private final UserSearchService userSearchService;

    public UserApiGrpcImpl(UserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    @Override
    public void getUser(GetUserRequest request, StreamObserver<GetUserResponse> responseObserver) {
        try {
            GetUserResponse response = userSearchService.getUser(request.getUserId())
                    .map(UserApiGrpcImpl::toProtoUser)
                    .map(it -> GetUserResponse.newBuilder().setUser(it).build())
                    .orElse(GetUserResponse.getDefaultInstance());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new RuntimeException("User search failed for user id " + request.getUserId(), e));
            log.error("User search failed for user id {}", request.getUserId(), e);
        }
    }

    @Override
    public void getUsers(UserApi.GetUsersRequest request, StreamObserver<UserApi.GetUsersResponse> responseObserver) {
        super.getUsers(request, responseObserver);
    }

    private static UserApi.User toProtoUser(User user) {
        return UserApi.User.newBuilder()
                .setId(user.getId())
                .setName(user.getName().getName())
//                .setCreatedTs(user.getCreatedTs())
//                .setUpdatedTs(user.getUpdatedTs())
                .setDeleted(user.isDeleted())
                .build();
    }
}
