package qvc.validation;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GroupSerializer  implements JsonSerializer<Class<?>> {

	@Override
	public JsonElement serialize(Class<?> src, Type typeOfSrc,
			JsonSerializationContext context) {
		return new JsonPrimitive(src.getSimpleName());
	}

}
