package com.example.demo;

import com.example.demo.design.Ingredient;
import com.example.demo.design.Taco;
import com.example.demo.orders.Order;
import com.example.demo.users.User;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import java.nio.file.Paths;
import java.util.EnumSet;

public class ExportSchema {
    public static void main(String[] args) {
        final String outputFile = Paths.get("target/schema.sql").toAbsolutePath().toString();
        System.out.println("outputFile = " + outputFile);
        final StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
                .build();
        final Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(Ingredient.class)
                .addAnnotatedClass(Taco.class)
                .addAnnotatedClass(Order.class)
                .addAnnotatedClass(User.class)
                .buildMetadata();
        new SchemaExport()
                .setFormat(true)
                .setDelimiter(";")
                .setOutputFile(outputFile)
                .execute(EnumSet.of(TargetType.SCRIPT), SchemaExport.Action.CREATE, metadata);
    }
}
