package net.mariusgundersen.qcv.loader;

public interface Instancer<T> {
	public T create(Class<? extends T> classType);
}
