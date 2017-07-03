package prototype.util;

import org.hashids.Hashids;

public final class IdObfuscator {

	private static final String ID_HASH_SALT = "salt";
	private static final int OBFUSCATED_ID_MIN_LENGTH = 4;
	
	private static final Hashids obfuscator = new Hashids(ID_HASH_SALT, OBFUSCATED_ID_MIN_LENGTH);
	
	public static final String obfuscate(int id) {
		return obfuscator.encode(id);
	}
	
	public static final int deObfuscate(String idHash) {
		return (int) obfuscator.decode(idHash)[0];
	}
}
