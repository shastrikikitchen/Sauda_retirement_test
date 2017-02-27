package com.traveller.enthusiastic.networkUtils;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by sauda on 14/02/17.
 */

public class ObjectMapperSingleton {
        private static ObjectMapperSingleton objectMapperSingleton = null;
        private ObjectMapper objectMapper;

        private ObjectMapperSingleton() {
            objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
        }

        public static ObjectMapperSingleton getInstance() {
            synchronized (ObjectMapperSingleton.class) {
                if (objectMapperSingleton == null) {
                    objectMapperSingleton = new ObjectMapperSingleton();
                }
            }
            return objectMapperSingleton;
        }

        public ObjectMapper getJacksonObjectMapper() {
            return objectMapper;
        }

    }

