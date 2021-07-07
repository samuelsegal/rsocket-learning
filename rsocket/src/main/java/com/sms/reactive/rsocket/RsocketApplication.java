package com.sms.reactive.rsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import io.rsocket.core.RSocketServer;
import io.rsocket.transport.netty.server.CloseableChannel;
import io.rsocket.transport.netty.server.WebsocketServerTransport;
import io.rsocket.util.ByteBufPayload;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@SpringBootApplication
@Slf4j
public class RsocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(RsocketApplication.class, args);
//		CloseableChannel channel = RSocketServer.create(SocketAcceptor.with(new RSocket() {
//
//			@Override
//			public Flux<Payload> requestStream(Payload payload) {
//				log.info("Request Stream {}", payload);
//				return Flux.range(0,100).map( i -> ByteBufPayload.create("Howdy: " + i));
//			}
//			
//		})).bindNow(WebsocketServerTransport.create("localhost", 8080));
//		channel.onClose().block();
	}

}
