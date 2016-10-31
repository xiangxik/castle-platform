package com.whenling.castle.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GreetingController {

	@Autowired
	private SimpMessagingTemplate messageTemplate;

	@MessageMapping("/hello")
	@SendTo("/topic/gmapws")
	public String handle(String gg) {
		return "[" + System.currentTimeMillis() + ":" + gg;
	}

	@RequestMapping("/greetingsend/{gg}")
	public void send(@PathVariable("gg") String gg) {
		messageTemplate.convertAndSend("/topic/gmapws", gg);
		// messageTemplate.send("/topic/greeting", new GenericMessage<>(gg));
	}
}
