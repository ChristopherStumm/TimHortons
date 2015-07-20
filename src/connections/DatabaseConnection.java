package connections;

import java.util.Date;
import java.util.Set;

import model.ERPData;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class DatabaseConnection {	
	
	private static DB db=null;
	private static final String URL = "personalchef.ddns.net";
	private static final int PORT = 80;
	
	
	public static DB getConnection(){
		if(db == null){
			MongoClient mongo = new MongoClient(URL, PORT);
			db = mongo.getDB("bigdata");
		}		
		return db;
	}
	
	public static void saveProductInformation(String customerNumber, String order){
		DB db = DatabaseConnection.getConnection();
		DBCollection table = db.getCollection("bigdata");
		String orderString = order.toString();
		
		//Check if object already exists
		BasicDBObject searchQuery = new BasicDBObject().append("customerNumber", customerNumber);
		DBCursor cursor = table.find(searchQuery);
		
		//Create new Object on DB
		if(!cursor.hasNext()){
			BasicDBObject document = new BasicDBObject();
			document.put("customerNumber", customerNumber);
			document.put("orders", orderString);
			table.insert(document);
		}else{
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.append("$addToSet", new BasicDBObject().append("orders", orderString));
			table.update(searchQuery, newDocument);			
		}

	}
//	public static void main(String[] args) {
//		DB db = DatabaseConnection.getConnection();
//		DBCollection table = db.getCollection("bigdata");
//		
////		BasicDBObject query = new BasicDBObject();
////		query.put("customerNumber", "mkyong");
////		
////		
////		ERPData erp = new ERPData();
////		erp.setCustomerNumber(23332);
////		erp.setMaterialNumber(6566);
////		erp.setOrderNumber("sdsdd5656kkkd");
////		erp.setTimeStamp(new Date());
////		
////		Gson test = new Gson();
////		String order = test.toJson(erp);
////		
////		BasicDBObject document = new BasicDBObject();
////		document.put("customerNumber", 56656);
////		document.put("order", order);
////		table.insert(document);
//		
//	
//		BasicDBObject searchQuery = new BasicDBObject().append("customerNumber", "77");
//		
//		BasicDBObject newDocument = new BasicDBObject();
//		newDocument.append("$addToSet", new BasicDBObject().append("orders", 110));
//
//		
//		table.update(searchQuery, newDocument);
//		
//		DBCursor cursor = table.find(searchQuery);
//	 
//		if(cursor.hasNext()==false) {
//			System.out.println("dalse");
//		}
//		while (cursor.hasNext()) {
//			System.out.println(cursor.next());
//		}
//	}

}
