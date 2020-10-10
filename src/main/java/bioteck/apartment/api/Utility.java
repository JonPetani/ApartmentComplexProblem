package bioteck.apartment.api;

public class Utility{
public static boolean isNum(String str) {
if (str == null || str.isEmpty())
	return false;
return str.matches("^[\\d]*$");
}
}