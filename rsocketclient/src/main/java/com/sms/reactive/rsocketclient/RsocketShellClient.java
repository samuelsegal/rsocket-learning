package com.sms.reactive.rsocketclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import com.sms.reactive.rsocketclient.model.Message;

import lombok.extern.slf4j.Slf4j;
import reactor.core.Disposable;

@ShellComponent
@Slf4j
public class RsocketShellClient {

	private final RSocketRequester rsocketRequester;
	
	static final String CLIENT = "Client";
	static final String REQUEST = "Request";
	static final String STREAM = "Stream";
	
	private static Disposable disposable;
	
	@Autowired
	public RsocketShellClient(RSocketRequester.Builder builder) {
		rsocketRequester = builder.tcp("localhost", 7000);
		//rsocketRequester = builder.connectTcp("localhost", 7000).block();
	}
	
	@ShellMethod("Send one request. One response will be printed.")
	public void requestResponse() throws InterruptedException {
		Message message = this.rsocketRequester
				.route("request-response")
				.data(Message.builder()
						.origin(CLIENT)
						.interaction(REQUEST)
						.build())
				.retrieveMono(Message.class)
				.block();
		log.info("Response {}", message);
			
	}
	
	@ShellMethod("Send a fire and forget request. No response will be received")
	public void fireAndForget() throws InterruptedException{
		rsocketRequester
		.route("fire-and-forget")
		.data(Message.builder()
				.origin(CLIENT)
				.interaction(REQUEST)
				.build())
		.send()
		.block();
		
	}
	
	@ShellMethod("Send one request for a stream of many messages. Use s command to stop the stream")
	public void stream() throws InterruptedException{
		disposable = rsocketRequester
				.route("stream")
				.data(Message.builder()
						.origin(CLIENT)
						.interaction(STREAM)
						.build())
				.retrieveFlux(Message.class)
				.subscribe(er -> log.info("Response received :: {}",  er));
	}
	
	@ShellMethod("Stop streaming messages from server")
	public void s() {
		if(disposable != null) {
			disposable.dispose();
		}
	}
}
