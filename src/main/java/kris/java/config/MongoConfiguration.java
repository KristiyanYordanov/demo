//package kris.java.config;
//
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//
//@Configuration
//@EnableMongoRepositories
//@ComponentScan(basePackageClasses = {MongoDBApp.class})
//@PropertySource("classpath:application.properties")
//public class MongoConfiguration extends AbstractMongoConfiguration
//{
//    @Override
//    protected String getDatabaseName() {
//        return "demo";
//    }
//
//    @Override
//    public Mongo mongo() throws Exception {
//        return new Mongo();
//    }
//
//    @Override
//    protected String getMappingBasePackage() {
//        return "com.johnathanmarksmith.mongodb.example.domain";
//    }
//
//}