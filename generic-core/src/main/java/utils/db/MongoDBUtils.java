package utils.db;

import java.util.*;

import com.mongodb.*;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.testng.Assert;

public class MongoDBUtils {

    private MongoClient client;

    public static void main(String arg[]){

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB database = mongoClient.getDB("log");
        DBCollection collection = database.getCollection("log");

        BasicDBObject document = new BasicDBObject();
        document.put("name", "Shubham");
        document.put("company", "Test");
        collection.insert(document);
        DBCursor cursor = collection.find();

        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    public static MongoDatabase getDB(String dbName, String dbServiceName, String dbUserName, String dbPassword, String dbHost, String dbPort) {
        MongoCredential credential = MongoCredential.createCredential(dbUserName, dbServiceName, dbPassword.toCharArray());
        MongoClient client = new MongoClient(new ServerAddress(dbHost, Integer.parseInt(dbPort)), Arrays.asList(credential));
        MongoDatabase db = client.getDatabase(dbName);
        return db;
    }

    public static MongoCollection<Document> getCollection(MongoDatabase database, String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        return collection;
    }

    public static void deleteManyRecords(MongoCollection<Document> collection, String key, String value) {
        try {
            collection.deleteMany(Filters.regex(key, value));
            FindIterable<Document> iterDoc = collection.find();
            Iterator it = iterDoc.iterator();
            while (it.hasNext()) {
                break;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteOneRecord(MongoCollection<Document> collection, String key, String value) {
        try {
            collection.deleteOne(Filters.regex(key, value));
            FindIterable<Document> iterDoc = collection.find();
            Iterator it = iterDoc.iterator();
            while (it.hasNext()) {
                break;
            }
        } catch(Exception e) {
            e.printStackTrace();
            Assert.fail("Deletion from DB failed !");
        }
    }

    public static void updateUsingRegex(MongoCollection<Document> collection, String key, String value) {
        try {
            collection.updateMany(new Document(), Filters.eq("$set", Filters.eq(key, value)));;
            FindIterable<Document> iterDoc = collection.find();
            Iterator it = iterDoc.iterator();
            while (it.hasNext()) {
                break;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static long count(MongoCollection<Document> collection, Document query) {
        long count = 0;
        try {
            count = collection.count(query);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static long count(MongoCollection<Document> collection, String key, String value) {
        long count = 0;
        try {
            count = collection.count(Filters.regex(key, value));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void updateCollection(MongoCollection<Document> collection, Document update, Document filter) {
        collection.updateOne(filter, update);
    }

    public static long validateDocuments(MongoCollection<Document> collection, String key, String value) {
        return collection.count(Filters.eq(key, value));
    }

    public static String queryRecord(MongoDatabase database, String key, String value) {
        Document command = database.runCommand(new Document(key, value));
        return command.toJson();
    }
}