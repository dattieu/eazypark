package prototype.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonMapper {
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	public static final String convertObjectToJson(Object obj) throws JsonProcessingException{
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);	
	}
	
	public static final void convertObjectToJsonInFile(Object obj, String filePath) 
			throws JsonGenerationException, JsonMappingException, IOException{
		mapper.writeValue(new File(filePath), obj);
	}
	
	@SuppressWarnings("unchecked")
	public static final Object convertJsonToObject(String jsonString, @SuppressWarnings("rawtypes") Class entity) 
						throws JsonParseException, JsonMappingException, IOException{
		return mapper.readValue(jsonString, entity);
	}
	
}
