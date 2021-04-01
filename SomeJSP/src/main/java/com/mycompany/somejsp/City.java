/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.somejsp;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Кудин
 */
public class City {
    
    private Integer id;
    private String title;
    private Integer population;

    public City() {
    }
    
    private boolean hasAttribute(String attribute) {
        List<String> fields = Arrays.asList(City.class.getDeclaredFields()).stream().map(field -> {
            return field.getName().toLowerCase();
        }).collect(Collectors.toList());
        return fields.contains(attribute.trim().toLowerCase());
    }
    
    public void setAttribute(String attribute, Object value) throws IllegalArgumentException,NoSuchFieldException,IllegalAccessException {
        if (!this.hasAttribute(attribute)) {
            return;
        }
        Field[] fields = City.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().toLowerCase().equals(attribute.trim().toLowerCase())) {
                field.set(this, value);
                break;
            }
        }
    }
    
    public static List<City> getFromMapList (List<Map<String, Object>> values) {
        List<City> result = new ArrayList<>();
        try {
            for (Map<String, Object> item : values) {
                City city = new City();
                for (String key : item.keySet()) {
                    city.setAttribute(key, item.get(key));
                }
                result.add(city);
            }
            
        } catch (IllegalArgumentException | NoSuchFieldException | IllegalAccessException ex) {
            return null;
        }
        
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }  
    
}
