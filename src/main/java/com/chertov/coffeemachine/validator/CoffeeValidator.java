package com.chertov.coffeemachine.validator;

import com.chertov.coffeemachine.exception.OutOfCoffeeException;
import com.chertov.coffeemachine.repository.CoffeeMachineRepository;
import org.springframework.stereotype.Component;

@Component
public class CoffeeValidator {

    private final CoffeeMachineRepository coffeeMachineRepository;

    public CoffeeValidator(CoffeeMachineRepository coffeeMachineRepository) {
        this.coffeeMachineRepository = coffeeMachineRepository;
    }

    public void validate() {
        int portionsLeft = coffeeMachineRepository.getPortionsLeft(1L);
        if (portionsLeft == 0) {
            throw new OutOfCoffeeException("Run out of coffee! Please, fill the machine.");
        }
    }
}
