package com.chertov.coffeemachine.validator;

import com.chertov.coffeemachine.exception.OutOfCoffeeException;
import com.chertov.coffeemachine.service.CoffeeMachineService;
import org.springframework.stereotype.Component;

@Component
public class CoffeeValidator {

    private final CoffeeMachineService coffeeMachineService;

    public CoffeeValidator(CoffeeMachineService coffeeMachineService) {
        this.coffeeMachineService = coffeeMachineService;
    }

    public void validate() {
        int portionsLeft = coffeeMachineService.getPortionsLeft();
        if (portionsLeft == 0) {
            throw new OutOfCoffeeException("Run out of coffee! Please, fill the machine.");
        }
    }
}
