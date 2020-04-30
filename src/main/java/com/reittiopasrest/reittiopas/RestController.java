package com.reittiopasrest.reittiopas;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	private final AtomicLong counter = new AtomicLong();
	private static final String template = "Hello, %s!";
	private final AtomicLong counter1 = new AtomicLong();
	private String lahto;
	private String loppu;

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter1.incrementAndGet(), String.format(template, name));
	}

	@GetMapping("/lahto")
	public String asetaLahto(@RequestParam String lahto){
		this.lahto = lahto;
		return "Lahto asetettu.";
	}

	@GetMapping("/loppu")
	public String asetaLoppu(@RequestParam String loppu){
	this.loppu = loppu;
		return "Loppu asetettu";
	}

	@GetMapping("/reittihaku")
	public String reittihaku() throws Exception{
		String jsonArray = new Reittiopas().reittiopas(lahto,loppu);
		return jsonArray;
	}


}
