package com.chertov.coffeemachine.service;

import com.chertov.coffeemachine.dto.CoffeeDto;
import com.chertov.coffeemachine.entity.Coffee;
import com.chertov.coffeemachine.mapper.CoffeeMapper;
import com.chertov.coffeemachine.repository.CoffeeMachineRepository;
import com.chertov.coffeemachine.repository.CoffeeRepository;
import com.chertov.coffeemachine.typesofcoffee.CoffeeType;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoffeeMachineService {

    private final CoffeeMachineRepository coffeeMachineRepository;
    private final CoffeeRepository coffeeRepository;
    private final CoffeeMapper coffeeMapper;

    public CoffeeMachineService(CoffeeMachineRepository coffeeMachineRepository,
                                CoffeeRepository coffeeRepository,
                                CoffeeMapper coffeeMapper) {
        this.coffeeMachineRepository = coffeeMachineRepository;
        this.coffeeRepository = coffeeRepository;
        this.coffeeMapper = coffeeMapper;
    }

    @CacheEvict(value = "portionsLeft", allEntries = true)
    @Transactional
    public CoffeeDto makeCoffee(CoffeeDto coffeeDto) {
        Coffee coffee = coffeeMapper.convert(coffeeDto);
        coffee.setTimeOfBrewing(Instant.now());
        Coffee persistedCoffee = coffeeRepository.save(coffee);
        coffeeMachineRepository.decreasePortionsLeftById(1L);

        return coffeeMapper.convert(persistedCoffee);
    }

    @CacheEvict(value = "portionsLeft", allEntries = true)
    public void fillMachine(int numOfPortions) {
        coffeeMachineRepository.fillMachineById(numOfPortions, 1L);
    }

    @Cacheable("portionsLeft")
    public int getPortionsLeft() {
        return coffeeMachineRepository.getPortionsLeft(1L);
    }

    public Map<CoffeeType, Long> getCoffeeStatisticsByTypeSortedByAmount() {
        List<Coffee> coffees = coffeeRepository.findAll();
        return coffees.stream()
                      .collect(Collectors.groupingBy(Coffee::getType, Collectors.counting()))
                      .entrySet()
                      .stream()
                      .sorted(Map.Entry.<CoffeeType, Long>comparingByValue().reversed())
                      .collect(Collectors.toMap(Map.Entry::getKey,
                                                Map.Entry::getValue,
                                                (val1, val2) -> val1,
                                                LinkedHashMap::new));
    }
}
