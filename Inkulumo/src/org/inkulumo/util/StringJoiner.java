package org.inkulumo.util;

import java.util.List;

public class StringJoiner {

	public static String join(List<String> list, String sep) {
		StringBuilder builder = new StringBuilder();
		final int size = list.size();
		for (int i = 0; i < size; ++i) {
			builder.append(list.get(i));
			if (i != size - 1)
				builder.append(sep);
		}
		
		return 	builder.toString();
	}
}
