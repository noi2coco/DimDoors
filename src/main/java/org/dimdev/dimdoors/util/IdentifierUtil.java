package org.dimdev.dimdoors.util;

import net.minecraft.util.Identifier;

public class IdentifierUtil {
	public static Identifier create(String key) {
		return new Identifier("dimdoors", key);
	}
}
