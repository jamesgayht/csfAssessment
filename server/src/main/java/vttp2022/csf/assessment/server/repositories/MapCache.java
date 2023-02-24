package vttp2022.csf.assessment.server.repositories;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Repository
public class MapCache {

    private static final String CHUK_URL = "http://map.chuklee.com/map";

	// TODO Task 4
	// Use this method to retrieve the map
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	public Optional<Byte[]> getMap(float lat, float lng) {
		// Implmementation in here
        
        final String url = UriComponentsBuilder
        .fromUriString(CHUK_URL)
        .queryParam("lat", lat)
        .queryParam("lng", lng)
        .toUriString();

        System.out.println("url >>>> " + url);

        RequestEntity req = RequestEntity.get(url).accept(MediaType.IMAGE_PNG).build();

        RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Byte[]> resp = restTemplate.exchange(req, Byte[].class);

        Byte[] image = resp.getBody();
        
        return Optional.of(image);
		
	}

	// You may add other methods to this class

        // Implmementation in here
        


}
