package com.chertov.coffeemachine.validator;

import com.chertov.coffeemachine.exception.OutOfCoffeeException;
import com.chertov.coffeemachine.repository.CoffeeRepository;
import org.springframework.stereotype.Component;

@Component
public class CoffeeValidator {

    private final CoffeeRepository coffeeRepository;

    public CoffeeValidator(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    public void validate() {
        int portionsLeft = coffeeRepository.getNumberOfPortionsLeft();
        if (portionsLeft == 0) {
            throw new OutOfCoffeeException("Run out of coffee! Please, fill the machine.");
        }
    }
}
