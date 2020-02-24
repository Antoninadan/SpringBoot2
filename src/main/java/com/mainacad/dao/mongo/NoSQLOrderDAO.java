package com.mainacad.dao.mongo;

import com.mainacad.model.Order;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NoSQLOrderDAO {
    private final MongoDatabase mongoDatabase;

    public void save(Order order){
        MongoCollection collection = mongoDatabase.getCollection("orders");
        Document dbObject = new Document();

        // test TODO
        dbObject.put("item", order.getItem());

        collection.insertOne(dbObject);
    }

//    public List<Order> getAll(){
//        MongoCollection collection = mongoDatabase.getCollection("orders");
//
//        List<Document> documents = (List<Document>) collection.find().into(new ArrayList<Document>());
//        List <Order> orders = new ArrayList<>();
//        for (Document document : documents) {
//            Order order = new Order();
//            order.setCode(document.getString("code"));
//            order.setName(document.getString("name"));
//            order.setPrice((Integer) document.get("price"));
//            orders.add(order);
//        }
//        return orders;
//    }

}

