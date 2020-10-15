package com.darq37.android_room.entity;

import java.util.Date;
import java.util.List;

public class ShoppingList {
    private long id;
    private String name;
    private User owner;
    private List<Product> products;
    private Date creationDate;
    private Date modificationDate;
}
