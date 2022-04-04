package com.chertov.coffeemachine.mapper;

import com.chertov.coffeemachine.dto.CoffeeDto;
import com.chertov.coffeemachine.entity.Coffee;
import org.springframework.stereotype.Component;

@Component
public class CoffeeMapper {

    public Coffee convert(CoffeeDto coffeeDto) {
        if (coffeeDto == null) {
            return null;
        }
        Coffee coffee = new Coffee();
        coffee.setType(coffeeDto.getType());

        return coffee;
    }

    public CoffeeDto convert(Coffee coffee) {
        if (coffee == null) {
            return null;
        }
        CoffeeDto coffeeDto = new CoffeeDto();
        coffeeDto.setId(coffee.getId());
        coffeeDto.setType(coffee.getType());
        coffeeDto.setTimeOfBrewing(coffee.getTimeOfBrewing());

        return coffeeDto;
    }
}
