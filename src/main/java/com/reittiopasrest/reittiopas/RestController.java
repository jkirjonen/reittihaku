package com.reittiopasrest.reittiopas;

import org.apache.logging.log4j.core.util.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileReader;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	private String lahto;
	private String loppu;

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

	@GetMapping("/reittiopas.json")
	public String haejson() throws Exception {
		String json = new Jiisoni().jiisoni;
		return json;
	}

	@GetMapping("/testi")
	public String testi() throws Exception{
		lahto="a";
		loppu="k";
		String jsonArray = new Reittiopas().reittiopas(lahto,loppu);
		return jsonArray;
	}
}
