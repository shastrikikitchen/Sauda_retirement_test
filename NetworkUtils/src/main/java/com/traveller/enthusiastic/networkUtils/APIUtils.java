package com.traveller.enthusiastic.networkUtils;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.RequestBody;

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

    public static String createParamBody(Object r) {
        if (r instanceof FormBody) {
            FormBody requestBody = (FormBody) r;
            StringBuilder requestParam = new StringBuilder("");
            for (int i = 0; i < requestBody.size(); i++) {
                requestParam.append(requestBody.encodedName(i)+"="+requestBody.encodedValue(i));
                requestParam.append("&");
            }
        return requestParam.toString();
        }else {
            return createJsonFromModel(r);
        }

    }
}
































































































































