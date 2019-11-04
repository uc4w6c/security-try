package com.example.demo.grpcservice;

import com.example.demo.proto.GreeterGrpc;
import com.example.demo.proto.GreeterOuterClass;
import io.grpc.stub.StreamObserver;

/**
 * gRPCのサーバ実装テスト
 */
public class GreeterGRpcService extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(GreeterOuterClass.HelloRequest req,
                         StreamObserver<GreeterOuterClass.HelloReply> responseObserver) {
        GreeterOuterClass.HelloReply reply = GreeterOuterClass.HelloReply
                                                    .newBuilder()
                                                    .setMessage("Hello " + req.getName())
                                                    .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
