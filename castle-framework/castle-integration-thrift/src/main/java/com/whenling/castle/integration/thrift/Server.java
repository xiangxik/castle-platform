package com.whenling.castle.integration.thrift;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;

public class Server {

	public static final int SERVER_PORT = 8090;

	public void start() {
		System.out.println("server starting...");

		try {
			TProcessor processor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldImpl());

			TNonblockingServerSocket nonblockingServerSocket = new TNonblockingServerSocket(SERVER_PORT);
			TNonblockingServer.Args args = new TNonblockingServer.Args(nonblockingServerSocket);
			args.processor(processor);
			args.transportFactory(new TFramedTransport.Factory());
			args.protocolFactory(new TCompactProtocol.Factory());

			TServer server = new TNonblockingServer(args);
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new Server().start();
	}
}
