package com.test.patterns.core;

import java.lang.reflect.Constructor;

public class SingletonTest {

	public static void main(String[] args) throws Exception {
		// Eager
		System.out.println(Singleton.getEagerInstance().hashCode());
		System.out.println(Singleton.getEagerInstance().hashCode());

		// Lazy
		System.out.println(Singleton.getLazyInstance().hashCode());
		System.out.println(Singleton.getLazyInstance().hashCode());

		// Reflection issue
		System.out.println(Singleton.getEagerInstance().hashCode());
		Class clazz = Class.forName("com.test.patterns.core.Singleton");
		Constructor<Singleton> constructor = clazz.getDeclaredConstructor(null);
		constructor.setAccessible(true);
		Singleton s3 = constructor.newInstance(null);
	}
}

class Singleton {
	private static Singleton eagerSingleton = new Singleton();
	private static Singleton lazySingleton;

	// private constructor
	private Singleton() {
	}

	// Eager instatation
	public static Singleton getEagerInstance() {
		return eagerSingleton;
	}

	public static Singleton getLazyInstance() {
		if (lazySingleton == null) {
			synchronized (Singleton.class) {
				if (lazySingleton == null) {
					lazySingleton = new Singleton();
				}
			}
		}
		return lazySingleton;
	}

}

// Issues with Singleton
// - Reflection
// - Serialization
// - Clone
// - Multithreading
// - Multi class loaders
// - Garbage collection
