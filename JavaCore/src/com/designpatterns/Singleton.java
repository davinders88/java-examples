package com.designpatterns;

/**
 * The Bill Pugh Singleton implementation
 * @author devgosain
 *
 */
public class Singleton {

	private Singleton(){}

	private static class SingletonHelper {
		private static final Singleton INST = new Singleton();
	}

	public static Singleton getInstance() {
		return SingletonHelper.INST;
	}
}

//enums are singleton
enum EnumSingleton {

    INSTANCE;
    
    public static void doSomething(){
        //do something
    }
}