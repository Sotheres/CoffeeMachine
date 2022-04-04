package com.chertov.coffeemachine.service;

import com.chertov.coffeemachine.dto.CoffeeDto;
import com.chertov.coffeemachine.entity.Coffee;
import com.chertov.coffeemachine.mapper.CoffeeMapper;
import com.chertov.coffeemachine.repository.CoffeeRepository;
import java.time.Instant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoffeeMachineService {

    private final CoffeeRepository coffeeRepository;
    private final CoffeeMapper coffeeMapper;

    public CoffeeMachineService(CoffeeRepository coffeeRepository,
                                CoffeeMapper coffeeMapper) {
        this.coffeeRepository = coffeeRepository;
        this.coffeeMapper = coffeeMapper;
    }

    @Transactional
    public CoffeeDto makeCoffee(CoffeeDto coffeeDto) {
        Coffee coffee = coffeeMapper.convert(coffeeDto);
        coffee.setTimeOfBrewing(Instant.now());
        Coffee persistedCoffee = coffeeRepository.save(coffee);
        coffeeRepository.decreasePortionsLeft();

        return coffeeMapper.convert(persistedCoffee);
    }

    public void fillMachine(int numOfPortions) {
        coffeeRepository.addCoffeeBeans(numOfPortions);
    }

    public int portionsLeft() {
        return coffeeRepository.getNumberOfPortionsLeft();
    }
}
