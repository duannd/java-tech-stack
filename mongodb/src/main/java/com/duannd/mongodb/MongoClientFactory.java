package com.duannd.mongodb;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Created By duan.nguyen
 */
public class MongoClientFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoClientFactory.class);
    private static volatile MongoClientFactory instance;
    private static final Object MUTEX = new Object();
    private static final String DB_NAME = "sb-game";

    private MongoClient mongoClient;

    private MongoClientFactory(){
        try {
            LOGGER.info("Initial Mongo Collection & create collection");
            this.mongoClient = initMongoClient();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private MongoClient initMongoClient() throws UnsupportedEncodingException {
        String user = "game";
        String pass = URLEncoder.encode("Q1w2e3r4t5", StandardCharsets.UTF_8);
        String servers = "10.1.1.118:27017,10.1.1.119:27018,10.1.1.119:27019";

        String connection = "mongodb://" + user + ":" + pass + "@" + servers + "/?authSource=" + DB_NAME;
        LOGGER.info("Mongodb connection string: {}", connection);

        MongoClient mongoClient = new MongoClient(
                new MongoClientURI(connection, MongoClientOptions.builder().alwaysUseMBeans(false)));
        LOGGER.info("Connected to Mongo " + user + " and database " + DB_NAME + " successfully.");
        return mongoClient;
    }

    public static MongoClientFactory getInstance() {
        MongoClientFactory result = instance;
        if (result == null) {
            synchronized (MUTEX) {
                result = instance;
                if (result == null) {
                    instance = result = new MongoClientFactory();
                }
            }
        }
        return result;
    }

    public void log() {
        LOGGER.info("MongoClient Factory is existed.");
    }

    public MongoCollection<Document> getCollection(String collection) {
        return this.getDatabase().getCollection(collection);
    }

    public MongoDatabase getDatabase() {
        return this.mongoClient.getDatabase(DB_NAME);
    }

}
