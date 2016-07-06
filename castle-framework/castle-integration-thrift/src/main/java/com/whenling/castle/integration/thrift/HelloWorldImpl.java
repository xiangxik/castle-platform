package com.whenling.castle.integration.thrift;

import org.apache.thrift.TException;

public class HelloWorldImpl implements HelloWorldService.Iface {

	public HelloWorldImpl() {
	}

	@Override
	public String sayHello(String username) throws TException {
		return "print1333: " + username;
	}

}
