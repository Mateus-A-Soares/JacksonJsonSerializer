package br.com.lupus;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class Serializer extends JsonSerializer<Model> {

	@Override
	public void serialize(Model model, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException {
		jsonGenerator.writeStartObject();
		List<String> attributes = model.getAttributes();
		attributes.forEach(attribute -> {
			try {
				FieldSerializer[] annotations = model.getClass().getDeclaredField(attribute)
						.getDeclaredAnnotationsByType(FieldSerializer.class);
				String getter = "get" + attribute.substring(0, 1).toUpperCase().concat(attribute.substring(1));
				Object value = model.getClass().getMethod(getter).invoke(model);
				if (annotations.length != 0) {
					Class<? extends FieldJsonSerializer<?, ?>> c = annotations[0].serializer();
					FieldJsonSerializer serializer = c.getConstructor().newInstance();
					jsonGenerator.writeObjectField(attribute, serializer.serialize((value)));
				} else
					jsonGenerator.writeObjectField(attribute, value);
			} catch (Exception e) {
				System.err.println("Unable to serialize the field.");				
				e.printStackTrace();
			}
		});
		jsonGenerator.writeEndObject();
	}
}
