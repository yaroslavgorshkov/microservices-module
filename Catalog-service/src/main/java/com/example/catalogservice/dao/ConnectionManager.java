package com.example.catalogservice.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConnectionManager {
    private static final EntityManagerFactory entityManagerFactory;

    static {
        entityManagerFactory =
                Persistence.createEntityManagerFactory("my-persistence-unit");
    }
    public static EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }
}
