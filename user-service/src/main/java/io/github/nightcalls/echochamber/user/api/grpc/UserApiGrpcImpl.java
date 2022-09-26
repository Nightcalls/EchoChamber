package io.github.nightcalls.echochamber.user.api.grpc;

import io.github.nightcalls.echochamber.user.User;
import io.github.nightcalls.echochamber.user.api.grpc.UserApi.GetUserRequest;
import io.github.nightcalls.echochamber.user.api.grpc.UserApi.GetUserResponse;
import io.github.nightcalls.echochamber.user.api.grpc.UserApi.GetUsersRequest;
import io.github.nightcalls.echochamber.user.api.grpc.UserApi.GetUsersResponse;
import io.github.nightcalls.echochamber.user.service.UserSearchService;
import io.github.nightcalls.echochamber.util.ProtoTypeConverters;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public void getUsers(GetUsersRequest request, StreamObserver<GetUsersResponse> responseObserver) {
        try {
            List<UserApi.User> users = userSearchService.getUsers(request.getUserIdList())
                    .stream()
                    .map(UserApiGrpcImpl::toProtoUser)
                    .collect(Collectors.toList());
            GetUsersResponse response = GetUsersResponse.newBuilder().addAllUser(users).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new RuntimeException("User search failed for user ids " +
                    request.getUserIdList(), e));
            log.error("User search failed for user ids {}", request.getUserIdList(), e);
        }
    }

    private static UserApi.User toProtoUser(User user) {
        return UserApi.User.newBuilder()
                .setId(user.getId())
                .setName(user.getName().getName())
                .setCreatedTs(ProtoTypeConverters.offsetDateTimeToTimestamp(user.getCreatedTs()))
                .setUpdatedTs(ProtoTypeConverters.offsetDateTimeToTimestamp(user.getUpdatedTs()))
                .setDeleted(user.isDeleted())
                .build();
    }
}
