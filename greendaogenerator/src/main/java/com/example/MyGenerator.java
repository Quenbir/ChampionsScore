package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;

public class MyGenerator {

    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.example.pawel.championsscore.dao"); // Your app package name and the (.db) is the folder where the DAO files will be generated into.
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            new DaoGenerator().generateAll(schema, "./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
        addCompetitionEntities(schema);
        // addPhonesEntities(schema);
    }

    // This is use to describe the colums of your table
    private static Entity addCompetitionEntities(final Schema schema) {
        Entity competition = schema.addEntity("Competition");
        competition.addIdProperty().primaryKey();
        competition.addStringProperty("name");
        competition.addStringProperty("flagUrl");
        return competition;
    }

   /* private static Entity addRoundEntities(final Schema schema) {
        Entity round = schema.addEntity("Round");
        round.addIdProperty().primaryKey();
        round.addStringProperty("name");
        Property competitionIdProperty = round.addLongProperty("competitionId").getProperty();
        round.addToOne(, competitionIdProperty);
        return competition;
    }*/

}
