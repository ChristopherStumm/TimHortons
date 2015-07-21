package connections;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import model.ERPData;

import com.google.gson.Gson;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

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
	
	public static void saveProductInformation(int customerNumber, String order){
		DB db = DatabaseConnection.getConnection();
		DBCollection table = db.getCollection("bigdata");
		System.out.println(order);
		
		JSON orderAsJson = new JSON();
		orderAsJson = (JSON) orderAsJson.parse(order);
		table.save((DBObject) orderAsJson);			
		

	}

}
