package utils;

import model.ERPData;
import model.OPCDataItem;

public class Output {

	public static void showERP(ERPData erpData) {
		System.err.println("-------------");
		System.err.println("New Order incoming...");
		System.out.println("Customer:\t" + erpData.getCustomerNumber());
		System.out.println("Material:\t" + erpData.getMaterialNumber());
		System.out.println("Order: \t\t" + erpData.getOrderNumber());
		System.out.println("Time: \t\t" + erpData.getTimeStamp());
	}

	public static void showStatusUpdate(
			@SuppressWarnings("rawtypes") OPCDataItem tempStatus) {
		System.err.println("-------------");
		System.out.println("Station: \t" + tempStatus.getItemName() + "\n"
				+ "Time: \t\t" + tempStatus.getTimestamp() + "\n"
				+ "Status: \t" + tempStatus.getStatus() + "\n" + "Value: \t\t"
				+ tempStatus.getValue().toString());
	}

}
