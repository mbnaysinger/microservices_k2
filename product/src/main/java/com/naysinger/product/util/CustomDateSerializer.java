package com.naysinger.product.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
//https://stackoverflow.com/questions/6704992/contextualdeserializer-for-mapping-json-to-different-types-of-maps-with-jackson
@Component
public class CustomDateSerializer extends StdSerializer<Date> {
	private static final long serialVersionUID = 1L;
	
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
 
    public CustomDateSerializer() {
        this(null);
    }
 
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public CustomDateSerializer(Class t) {
        super(t);
    }
     
    @Override
    public void serialize (Date value, JsonGenerator gen, SerializerProvider arg2) throws IOException, JsonProcessingException {
        gen.writeString(formatter.format(value));
    }
}

