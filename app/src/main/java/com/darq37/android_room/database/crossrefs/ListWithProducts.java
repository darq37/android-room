package com.darq37.android_room.database.crossrefs;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.darq37.android_room.entity.Product;
import com.darq37.android_room.entity.ShoppingList;

import java.util.List;


public class ListWithProducts {

    @Embedded
    private ShoppingList shoppingList;

    @Relation(
            parentColumn = "list_id",
            entityColumn = "product_id"
    )
    private List<Product> products;

    public ListWithProducts(ShoppingList shoppingList, List<Product> products) {
        this.shoppingList = shoppingList;
        this.products = products;
    }
}
