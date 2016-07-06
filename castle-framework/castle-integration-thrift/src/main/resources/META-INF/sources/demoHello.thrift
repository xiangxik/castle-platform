namespace java com.whenling.castle.integration.thrift

service HelloWorldService {
	string sayHello(1:string username)
}