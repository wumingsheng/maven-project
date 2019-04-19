package com.home.example.grpcserver;

import com.home.example.hello.GreeterGrpc;
import com.home.example.hello.Helloworld.HelloReply;
import com.home.example.hello.Helloworld.HelloRequest;

import io.grpc.stub.StreamObserver;

public class GreeterImpl extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
      HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
      responseObserver.onNext(reply);
      responseObserver.onCompleted();
    }
  }
