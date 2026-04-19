package com.radi.demo7;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDb {

    public static List<Product> getProductList() throws ClassNotFoundException, SQLException {

        Jedis jedis = new Jedis("localhost", 6379);

        // per user (better than global)
        String key = "rate:" + "global";

        int count = 0;

        String value = jedis.get(key);

        if (value != null) {
            count = Integer.parseInt(value);
        }
        // block if too many requests
        if (count >= 5) {
            throw new RuntimeException("Too many requests");
        }

        // increase counter
        jedis.incr(key);       // reset after 10 seconds windo
        jedis.expire(key, 10);


        Gson gson = new Gson();

        String cached = jedis.get("products");

        if (cached != null) {
            System.out.println(">>> FROM REDIS CACHE");

            Type type = new TypeToken<ArrayList<Product>>() {
            }.getType();

            return gson.fromJson(cached, type);
        }


        System.out.println("Hitting the db..........");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        String url = "jdbc:mysql://localhost:3306/products?useSSL=false";
        String user = "root";
        String password = "123#";

        ArrayList<Product> products = new ArrayList<>();

        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection(url, user, password);

             Statement statement = connection.createStatement();

             ResultSet resultSet = statement.executeQuery("SELECT * FROM product_cards")) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("item");
                float price = resultSet.getFloat("price");

                products.add(new Product(id, price, name));
            }
        }


        String json = gson.toJson(products);
        jedis.setex("products", 60, json);


        return products;


    }
}
