package com.whenling.castle.integration.thrift;

import java.io.IOException;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Client {

	public static final String SERVER_IP = "localhost";
	public static final int SERVER_PORT = 8090;
	public static final int TIMEOUT = 30000;

	public void start() {
		TTransport transport = new TFramedTransport(new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT));
		TProtocol protocol = new TCompactProtocol(transport);
		HelloWorldService.Client client = new HelloWorldService.Client(protocol);
		try {
			transport.open();
			while(true) {
				
				String result = client.sayHello("haha");
				System.out.println("Thrify client result =: " + result);
				
				Thread.sleep(2000);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Client().start();
	}
}
