package com.jgbpedro.teste.resources;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jgbpedro.teste.entities.PrestadorSaude;

@SpringBootApplication
@ComponentScan("com.jgbpedro.teste.config")
@RestController
public class PrestadorSaudeResource {
	@Autowired
	RestTemplate restTemp;
	
	@Value("${google.api-key}")
	private String API_KEY;
	
	@RequestMapping(value = "prestadores", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PrestadorSaude>> obterPrestadoresSaude(@RequestParam double longitude, double latitude, String especialidade) {
		
		String uri = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
				+ "location="+latitude+","+longitude+"&"
				+ "radius=1500&"
				+ "type=health&"
				+ "keyword="+especialidade+"&"
				+ "key="+API_KEY;
		
		ResponseEntity<String> responseEntity = restTemp.exchange(uri, HttpMethod.GET, null, String.class);
		
		JSONObject rootData = new JSONObject(responseEntity.getBody());
		JSONArray results = rootData.getJSONArray("results");
				
		List<PrestadorSaude> listaDePrestadores = new ArrayList<PrestadorSaude>();
		for(Object r : results){
			
			String nome = ((JSONObject) r).getString("name");
			String endereco = ((JSONObject) r).getString("vicinity");
			double latitudePrestadorServico = ((JSONObject) r).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
			double longitudePrestadorServico = ((JSONObject) r).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
					
			PrestadorSaude ps = new PrestadorSaude(
					nome, 
					endereco,
					latitude,
					latitudePrestadorServico,
					longitude,
					longitudePrestadorServico
			);
			
			listaDePrestadores.add(ps);
		}
		
		return ResponseEntity.ok().body(listaDePrestadores);
	}
}