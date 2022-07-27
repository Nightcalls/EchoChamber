package echochamber.channel.api.grpc;

import echochamber.channel.Channel;
import echochamber.channel.api.grpc.ChannelApi.GetChannelResponse;
import echochamber.channel.api.grpc.ChannelApi.GetChannelsResponse;
import echochamber.channel.service.ChannelSearchService;
import echochamber.util.ProtoTypeConverters;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChannelApiGrpcImpl extends ChannelApiServiceGrpc.ChannelApiServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(ChannelApiGrpcImpl.class);

    private final ChannelSearchService channelSearchService;

    public ChannelApiGrpcImpl(ChannelSearchService channelSearchService) {
        this.channelSearchService = channelSearchService;
    }

    @Override
    public void getChannel(ChannelApi.GetChannelRequest request,
                           StreamObserver<GetChannelResponse> responseObserver) {
        try {
            GetChannelResponse response = channelSearchService.getChannel(request.getChannelId())
                    .map(ChannelApiGrpcImpl::toProtoChannel)
                    .map(it -> GetChannelResponse.newBuilder().setChannel(it).build())
                    .orElse(GetChannelResponse.getDefaultInstance());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new RuntimeException("Channel search failed for channel id "
                    + request.getChannelId(), e));
            log.error("Channel search failed for channel id {}", request.getChannelId(), e);
        }

    }

    @Override
    public void getChannels(ChannelApi.GetChannelsRequest request,
                            StreamObserver<GetChannelsResponse> responseObserver) {
        try {
            List<ChannelApi.Channel> channels = channelSearchService.getChannels(request.getChannelIdList())
                    .stream()
                    .map(ChannelApiGrpcImpl::toProtoChannel)
                    .collect(Collectors.toList());
            GetChannelsResponse response = GetChannelsResponse.newBuilder().addAllChannel(channels).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(new RuntimeException("Channel search failed for channel ids "
                    + request.getChannelIdList(), e));
            log.error("Channel search failed for channel ids {}", request.getChannelIdList(), e);
        }
    }

    private static ChannelApi.Channel toProtoChannel(Channel channel) {
        return ChannelApi.Channel.newBuilder()
                .setId(channel.getId())
                .setName(channel.getName().getName())
                .setCreatedTs(ProtoTypeConverters.offsetDateTimeToTimestamp(channel.getCreatedTs()))
                .setUpdatedTs(ProtoTypeConverters.offsetDateTimeToTimestamp(channel.getUpdatedTs()))
                .setDeleted(channel.isDeleted())
                .build();
    }
}
