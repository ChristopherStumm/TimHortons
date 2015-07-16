package model;

public class StatusUpdate {

	private static String itemName;
	private static String status;
	private static String timestamp;
	private static String value;
	
	public static String getItemName() {
		return itemName;
	}
	public static void setItemName(String itemName) {
		StatusUpdate.itemName = itemName;
	}
	public static String getStatus() {
		return status;
	}
	public static void setStatus(String status) {
		StatusUpdate.status = status;
	}
	public static String getTimestamp() {
		return timestamp;
	}
	public static void setTimestamp(String timestamp) {
		StatusUpdate.timestamp = timestamp;
	}
	public static String getValue() {
		return value;
	}
	public static void setValue(String value) {
		StatusUpdate.value = value;
	}
	
}
