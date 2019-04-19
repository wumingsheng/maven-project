package com.home.example.grpcclient;

import java.util.concurrent.TimeUnit;

import com.home.example.hello.GreeterGrpc;
import com.home.example.hello.Helloworld.HelloReply;
import com.home.example.hello.Helloworld.HelloRequest;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloWorldClient {

	
	private final ManagedChannel channel;

	private final GreeterGrpc.GreeterBlockingStub blockingStub;

	public HelloWorldClient(String host, int port) {
		this(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
	}

	HelloWorldClient(ManagedChannel channel) {
		this.channel = channel;
		blockingStub = GreeterGrpc.newBlockingStub(channel);
	}

	public void shutdown() throws InterruptedException {
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}

	/** Say hello to server. */
	public void greet(String name) {
		
		HelloRequest request = HelloRequest.newBuilder().setName(name).build();
		HelloReply response;
		try {
			response = blockingStub.sayHello(request);
		} catch (StatusRuntimeException e) {
			log.warn("RPC failed: {0}", e.getStatus());
			return;
		}
		log.info("Greeting: " + response.getMessage());
	}


	public static void main(String[] args) throws Exception {
		HelloWorldClient client = new HelloWorldClient("localhost", 50051);
		try {
			client.greet("world");
		} finally {
			client.shutdown();
		}
	}
}
