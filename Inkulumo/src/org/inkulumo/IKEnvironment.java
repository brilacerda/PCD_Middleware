package org.inkulumo;

import java.util.Hashtable;

import javax.naming.Context;

@SuppressWarnings("serial")
public class IKEnvironment extends Hashtable<String, String> {

	private static final IKEnvironment INSTANCE = new IKEnvironment();

	public static final String ROOMS_TOPIC_KEY = "ROOMS";

	public static IKEnvironment instance() {
		return INSTANCE;
	}

	public IKEnvironment() {
		put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
		put(Context.PROVIDER_URL, "rmi://localhost:1099");
		put(ROOMS_TOPIC_KEY, "Rooms");
	}
}
