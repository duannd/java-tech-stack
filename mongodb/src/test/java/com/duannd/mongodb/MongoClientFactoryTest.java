package com.duannd.mongodb;

import com.google.gson.JsonObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

class MongoClientFactoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoClientFactoryTest.class);

    @Test
    public void createMongoClient_Success() {
        MongoClientFactory.getInstance().log();

        System.out.println("Get & create collection running.......");
        String collectionName = "z_audit_log_2";
        System.out.println(collectionName);
        MongoCollection<Document> mongoCollection = MongoClientFactory.getInstance().getCollection(collectionName);
        Assertions.assertNotNull(mongoCollection);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", "Duan");
        mongoCollection.insertOne(Document.parse(jsonObject.toString()));

        long start = System.currentTimeMillis();
        MongoDatabase database = MongoClientFactory.getInstance().getDatabase();
        List<String> collections = database.listCollectionNames().into(new ArrayList<>());
        boolean isExisted = collections.contains(collectionName);
        long end = System.currentTimeMillis();
        LOGGER.info("Check existed collection time: {}, isExisted: {}", (end - start), isExisted);

        Assertions.assertTrue(isExisted);
    }

}