package br.com.lupus;

import java.util.Arrays;
import java.util.List;

public interface Model {
	
	default String getKey() {
		return this.getClass().getName();
	};
	
	default void setAttributes(String... attributes) {
		Attributes.list.put(this.getKey(), Arrays.asList(attributes));
	}
	
	default List<String> getAttributes() {
		return Attributes.list.get(getKey());
	}
}
