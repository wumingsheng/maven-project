package com.home.example.grpcserver;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelloWorldServer {
	
	 

	  private Server server;

	  private void start() throws IOException {
	    /* The port on which the server should run */
	    int port = 50051;
	    server = ServerBuilder.forPort(port)
	        .addService(new GreeterImpl())
	        .build()
	        .start();
	    log.info("Server started, listening on " + port);
	    Runtime.getRuntime().addShutdownHook(new Thread() {
	      @Override
	      public void run() {
	        // Use stderr here since the logger may have been reset by its JVM shutdown hook.
	        System.err.println("*** shutting down gRPC server since JVM is shutting down");
	        HelloWorldServer.this.stop();
	        System.err.println("*** server shut down");
	      }
	    });
	  }

	  private void stop() {
	    if (server != null) {
	      server.shutdown();
	    }
	  }

	  /**
	   * Await termination on the main thread since the grpc library uses daemon threads.
	   */
	  private void blockUntilShutdown() throws InterruptedException {
	    if (server != null) {
	      server.awaitTermination();
	    }
	  }

	  /**
	   * Main launches the server from the command line.
	   */
	  public static void main(String[] args) throws IOException, InterruptedException {
	    final HelloWorldServer server = new HelloWorldServer();
	    server.start();
	    server.blockUntilShutdown();
	  }



}
