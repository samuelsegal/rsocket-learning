package com.sms.reactive.rsocket.controller;

import java.time.Duration;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.sms.reactive.rsocket.model.Message;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Controller
@Slf4j
public class RSocketController {

	static final String SERVER = "Server";
	static final String RESPONSE = "Response";
	static final String STREAM = "Stream";
	
	@MessageMapping("request-response")
	Message requestResponse(Message request) {
		log.info("Request :: {}", request);
		return new Message(SERVER, RESPONSE);
	}
	
	@MessageMapping("fire-and-forget")
	void fireAndForget(Message requestMessage) {
		log.info("You are fired and forgotten ;( {}", requestMessage );
	}
	
	@MessageMapping("stream")
	Flux<Message> stream(Message requestMessage){
		log.info("Sending a stream of messages :: {}", requestMessage);
		return Flux
				.interval(Duration.ofSeconds(1))
				.map(index -> Message.builder()
						.origin(SERVER)
						.interaction(STREAM)
						.index(index)
						.build())
				.log();				
	}
}
