package com.example.exam19;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Products implements Serializable {
    public int id;
    public String title;
    public String description;
    public String price;
    public String discountPercentage;
    public String rating;
    public String stock;
    public String brand;
    public String category;
    public String thumbnail;
    public List<String> images;
}
