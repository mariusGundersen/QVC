package qvc.exceptions;

import java.lang.reflect.Type;

import com.google.gson.*;

public class ExceptionSerializer implements JsonSerializer<Throwable> {
	@Override
	public JsonElement serialize(Throwable src, Type typeOfSrc,	JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.add("cause",  new JsonPrimitive(String.valueOf(src.getCause())));
		if(src.getMessage() != null)
			jsonObject.add("message", new JsonPrimitive(src.getMessage()));
		jsonObject.add("stackTrace", stackTrace(src));
		jsonObject.add("exception", new JsonPrimitive(src.getClass().getName()));
		return jsonObject;
	}
	
	private JsonArray stackTrace(Throwable e){
		JsonArray array = new JsonArray();
		StackTraceElement[] stackTrace = e.getStackTrace();
		for(StackTraceElement element: stackTrace){
			array.add(stackTraceElement(element));
		}
		return array;
	}

	private JsonObject stackTraceElement(StackTraceElement element) {
		JsonObject object = new JsonObject();
		if(element.getClassName() != null)
			object.add("className", new JsonPrimitive(element.getClassName()));
		if(element.getFileName() != null)
			object.add("fileName", new JsonPrimitive(element.getFileName()));
		if(element.getMethodName() != null)
			object.add("methodName", new JsonPrimitive(element.getMethodName()));
		if(element.getLineNumber() >= 0)
			object.add("lineNumber", new JsonPrimitive(element.getLineNumber()));
		return object;
	}
}
