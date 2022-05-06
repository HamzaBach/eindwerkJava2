package com.example.eindwerkJava2.wrappers;

import com.example.eindwerkJava2.model.City;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CitySuccess extends SuccessObject{
    private City city;
    private List<City> cities;
}
