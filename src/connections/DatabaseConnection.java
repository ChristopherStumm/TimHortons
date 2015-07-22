package connections;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import model.ERPData;
import model.Product;

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
	
	public static void  getAllProducts(){
		
		DB db = DatabaseConnection.getConnection();
		DBCollection table = db.getCollection("bigdata");
		DBCursor cursor = table.find();
		while(cursor.hasNext()) {
			Gson gson = new Gson();
			Product product = gson.fromJson(cursor.next().toString(), Product.class);
			
			
		    System.out.println(cursor.next());
		};
		
	}
	
	public static void main(String[] args) {
		getAllProducts();
	}
	public static void saveProductInformation(int customerNumber, String order){
		//DB db = DatabaseConnection.getConnection();
		//DBCollection table = db.getCollection("bigdata");
		System.out.println("Upload order: "+ order);
		
		//DBObject orderObject = (DBObject) JSON.parse(order);
		//table.save(orderObject);	

	}

}
