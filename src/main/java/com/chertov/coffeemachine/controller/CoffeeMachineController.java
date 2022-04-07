package com.chertov.coffeemachine.controller;

import com.chertov.coffeemachine.typesofcoffee.CoffeeType;
import com.chertov.coffeemachine.dto.CoffeeDto;
import com.chertov.coffeemachine.service.CoffeeMachineService;
import com.chertov.coffeemachine.validator.CoffeeValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Manage coffee making")
public class CoffeeMachineController {

    private final CoffeeMachineService coffeeMachineService;
    private final CoffeeValidator coffeeValidator;

    public CoffeeMachineController(CoffeeMachineService coffeeMachineService, CoffeeValidator coffeeValidator) {
        this.coffeeValidator = coffeeValidator;
        this.coffeeMachineService = coffeeMachineService;
    }

    @Operation(summary = "Make coffee of desired type")
    @ApiResponse(code = 400, message = "Wrong type of coffee")
    @PostMapping("/coffee")
    public CoffeeDto makeCoffee(@RequestBody CoffeeDto coffeeDto) {
        coffeeValidator.validate();
        return coffeeMachineService.makeCoffee(coffeeDto);
    }

    @Operation(summary = "Get available types of coffee")
    @GetMapping("/types")
    public CoffeeType[] getTypesOfCoffee() {
        return CoffeeType.values();
    }

    @Operation(summary = "Fill the machine with specified number of portions of coffee")
    @PostMapping("/service/fillMachine")
    public int fillMachine(@RequestParam int numOfPortions) {
        return coffeeMachineService.fillMachine(numOfPortions);
    }

    @Operation(summary = "Get number of portions left")
    @GetMapping("/service/getPortionsLeft")
    public int getPortionsLeft() {
        return coffeeMachineService.getPortionsLeft();
    }

    @Operation(summary = "Get number of coffees made by type sorted by amount desc")
    @GetMapping("/statistics")
    public Map<CoffeeType, Long> getCoffeeStatistic() {
        return coffeeMachineService.getCoffeeStatisticsByTypeSortedByAmount();
    }
}
