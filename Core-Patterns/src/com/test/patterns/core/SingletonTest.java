package com.test.patterns.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Constructor;

public class SingletonTest {

	public static void main(String[] args) throws Exception {
		// Eager
		System.out.println("1  same - " + Singleton.getEagerInstance().hashCode());
		System.out.println("11 same - " + Singleton.getEagerInstance().hashCode());

		// Lazy
		System.out.println("2  same - " + Singleton.getLazyInstance().hashCode());
		System.out.println("22 same - " + Singleton.getLazyInstance().hashCode());

		// Reflection issue
		System.out.println("3  diff - " + Singleton.getEagerInstance().hashCode());
		Class clazz = Class.forName("com.test.patterns.core.Singleton");
		Constructor<Singleton> constructor = clazz.getDeclaredConstructor(null);
		constructor.setAccessible(true);
		Singleton s3 = constructor.newInstance(null);
		System.out.println("33 diff - " + s3.hashCode());

		// Serialization issue
		System.out.println("4  diff - " + Singleton.getEagerInstance().hashCode());
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("/s4.ser"));
		out.writeObject(Singleton.getEagerInstance());
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("/s4.ser"));
		Singleton s4 = (Singleton) in.readObject();
		System.out.println("44 diff - " + s4.hashCode());

		// Clone
		System.out.println("5  diff - " + Singleton.getEagerInstance().hashCode());
		System.out.println("55 diff - " + Singleton.getEagerInstance().clone().hashCode());

		// Multithreading
		// Multiple class loaders
		// Garbage collection
	}
}

class Singleton implements Cloneable, Serializable {
	private static Singleton eagerSingleton = new Singleton();
	private static volatile Singleton lazySingleton;

	// private constructor
	private Singleton() {
		if (lazySingleton != null) {
			//throw new RuntimeException("Can't create instance. Use getEagerInstance instead");
		}		
	}

	static class Holder {
		static final Singleton holderInstance = new Singleton();
	}

	public static Singleton getLazyHolderInstance() {
		return Holder.holderInstance;
	}

	// Eager instatation
	public static Singleton getEagerInstance() {		
		return eagerSingleton;
	}

	public static Singleton getLazyInstance() {
		if (lazySingleton == null) { // check 1
			synchronized (Singleton.class) {
				if (lazySingleton == null) { // check 2
					lazySingleton = new Singleton();
				}
			}
		}
		return lazySingleton;
	}

	private Object readResolve() throws ObjectStreamException {
		System.out.println("read resolve ");
		return eagerSingleton;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// throw new CloneNotSupportedException("Clone is not supported for Singleton
		// class");
		return super.clone();
	}
}

// Issues with Singleton
// - Reflection - check instance null check and throw new RuntimeException
// - Serialization - implement readResolve method
// - Clone - throw clone not supported exception
// - Multithreading - Use volatile variable and Double check pattern or use
// holder class or use ENUM
// - Multi class loaders -
// - Garbage collection

// Singleton class benefits over helper static class - subclassing, controlled
// access with access modifier, can control no of instances, 
