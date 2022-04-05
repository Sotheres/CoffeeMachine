package com.chertov.coffeemachine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.chertov.coffeemachine.entity.Coffee;
import com.chertov.coffeemachine.repository.CoffeeRepository;
import com.chertov.coffeemachine.service.CoffeeMachineService;
import com.chertov.coffeemachine.typesofcoffee.CoffeeType;
import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CoffeeMachineServiceTest {

    @InjectMocks
    private CoffeeMachineService coffeeMachineService;

    @Mock
    private CoffeeRepository coffeeRepository;

    @Test
    void getCoffeeStatistics() {
        List<Coffee> persistedCoffees = Arrays.asList(
            new Coffee(1L, CoffeeType.AMERICANO, Instant.now()),
            new Coffee(2L, CoffeeType.AMERICANO, Instant.now()),
            new Coffee(3L, CoffeeType.AMERICANO, Instant.now()),
            new Coffee(4L, CoffeeType.ESPRESSO, Instant.now()),
            new Coffee(5L, CoffeeType.ESPRESSO, Instant.now()),
            new Coffee(6L, CoffeeType.CAPPUCCINO, Instant.now())
        );
        Map<CoffeeType, Long> expectedResult = new LinkedHashMap<>();
        expectedResult.put(CoffeeType.AMERICANO, 3L);
        expectedResult.put(CoffeeType.ESPRESSO, 2L);
        expectedResult.put(CoffeeType.CAPPUCCINO, 1L);

        when(coffeeRepository.findAll()).thenReturn(persistedCoffees);

        Map<CoffeeType, Long> actualResult =
            coffeeMachineService.getCoffeeStatisticsByTypeSortedByAmount();

        assertThat(actualResult).isEqualTo(expectedResult);
    }

}
