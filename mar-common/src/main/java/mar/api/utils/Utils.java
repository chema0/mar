package mar.api.utils;

import javax.annotation.CheckForNull;

public class Utils {

	public static boolean isNullOrEmpty(@CheckForNull String s) {
		return s == null || s.isEmpty();
	}
	
}
