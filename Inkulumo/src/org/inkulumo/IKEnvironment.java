package org.inkulumo;

import java.util.Hashtable;

import javax.naming.Context;

@SuppressWarnings("serial")
public class IKEnvironment extends Hashtable<String, Object> {

	private static final IKEnvironment INSTANCE = new IKEnvironment();

	public static IKEnvironment instance() {
		return INSTANCE;
	}

	public IKEnvironment() {
		put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.rmi.registry.RegistryContextFactory");
		put(Context.PROVIDER_URL, "rmi://localhost:1099");
	}
}
