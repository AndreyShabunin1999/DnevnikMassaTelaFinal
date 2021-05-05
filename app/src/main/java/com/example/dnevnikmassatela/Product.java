package com.example.dnevnikmassatela;

public class Product {
    private String name;
    private String description;

    Product(String name, String description){
        this.name = name;
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String name){
        this.name = name;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
