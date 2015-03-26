package com.yazuo.erp.base;

import net.sf.json.JSONObject;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.*;
import org.codehaus.jackson.map.module.SimpleModule;

import java.io.IOException;
import java.util.Date;

/**
 * 将数据格式化成unix-timestamp格式
 *
 * @author congshuanglong
 */
public class DateObjectMapper extends ObjectMapper {
    public DateObjectMapper() {
    	//null转空串处理
    	this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
					JsonProcessingException {
				jgen.writeString("");
			}
		});
    	//日期处理
        SimpleModule dateModule = new SimpleModule("date",Version.unknownVersion());
        dateModule.addSerializer(Date.class, new JsonSerializer<Date>() {
            @Override
            public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
                Long result = value.getTime() / 1000;
                jgen.writeNumber(result);
            }
        });
    }

    @Override
    public void writeValue(JsonGenerator jgen, Object value) throws IOException, JsonGenerationException, JsonMappingException {
        SerializationConfig config = copySerializationConfig();
        if(value != null && (value.getClass() == String.class||value instanceof JSONObject)){
            jgen.writeRaw(value.toString());
            if (config.isEnabled(SerializationConfig.Feature.FLUSH_AFTER_WRITE_VALUE)) {
                jgen.flush();
                jgen.close();
            }
        }else {
            super.writeValue(jgen, value);
        }
    }
}

