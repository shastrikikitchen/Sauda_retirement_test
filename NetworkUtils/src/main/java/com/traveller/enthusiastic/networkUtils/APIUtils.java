package com.traveller.enthusiastic.networkUtils;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by sauda on 14/02/17.
 */

public class APIUtils {
    public static final String createJsonFromModel(Object response) {
        ObjectMapper mapper = ObjectMapperSingleton.getInstance().getJacksonObjectMapper();
        String result = null;
        try {
            result = mapper.writeValueAsString(response);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }



}
