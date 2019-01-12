package br.com.lupus;

public interface FieldJsonSerializer<A,R> {
	
	public R serialize(A attribute);
}
