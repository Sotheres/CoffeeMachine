package com.chertov.coffeemachine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.chertov.coffeemachine.repository.CoffeeMachineRepository;
import com.chertov.coffeemachine.typesofcoffee.CoffeeType;
import com.chertov.coffeemachine.dto.CoffeeDto;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class CoffeeControllerIT {

    private static final String MAKE_COFFEE = "/coffee";
    private static final String PORTIONS_LEFT = "/service/getPortionsLeft";
    private static final String FILL_MACHINE = "/service/fillMachine";
    private static final String AVAILABLE_TYPES = "/types";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CoffeeMachineRepository coffeeMachineRepository;

    private final ObjectMapper mapper =
        JsonMapper.builder()
                  .findAndAddModules() // get support of Instance class
                  .disable(MapperFeature.USE_ANNOTATIONS) // disable rules to assert against dto
                  .build();

    @Container
    private static PostgreSQLContainer<?> testContainer =
        new PostgreSQLContainer<>("postgres:14.2");

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", testContainer::getJdbcUrl);
        registry.add("spring.datasource.username", testContainer::getUsername);
        registry.add("spring.datasource.password", testContainer::getPassword);
        registry.add("spring.datasource.database-name", testContainer::getDatabaseName);
    }

    @BeforeEach
    public void setInitialNumberOfCoffeePortions() {
        coffeeMachineRepository.setPortionsLeftById(1, 1L);
    }

    @Test
    void makeCoffee_enoughPortions_coffeeIsMade() throws Exception {
        CoffeeDto requestDto = new CoffeeDto();
        requestDto.setType(CoffeeType.ESPRESSO);

        MvcResult result = mockMvc.perform(post(MAKE_COFFEE)
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .content(mapper.writeValueAsString(requestDto)))
                                  .andExpect(status().isOk())
                                  .andReturn();

        CoffeeDto resultDto = mapper.readValue(result.getResponse().getContentAsString(),
                                               CoffeeDto.class);
        int portionsLeft = coffeeMachineRepository.getPortionsLeft(1L);

        assertThat(resultDto.getType()).isEqualTo(CoffeeType.ESPRESSO);
        assertThat(resultDto.getTimeOfBrewing()).isNotNull();
        assertThat(portionsLeft).isZero();
    }

    @Test
    void makeCoffee_notEnoughPortions_badRequest() throws Exception {
        CoffeeDto requestDto = new CoffeeDto();
        requestDto.setType(CoffeeType.AMERICANO);

        mockMvc.perform(post(MAKE_COFFEE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(requestDto)))
               .andExpect(status().isOk());

        mockMvc.perform(post(MAKE_COFFEE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(requestDto)))
               .andExpect(status().isBadRequest());
    }

    @Test
    void getPortionsLeft_actualNumberIsReceived() throws Exception {
        MvcResult result = mockMvc.perform(get(PORTIONS_LEFT))
                                  .andExpect(status().isOk())
                                  .andReturn();
        Integer portionsLeft = mapper.readValue(result.getResponse().getContentAsString(),
                                                Integer.class);

        assertThat(portionsLeft).isEqualTo(1);
    }

    @Test
    void fillMachine_portionsAreAdded() throws Exception {
        mockMvc.perform(post(FILL_MACHINE)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(2)))
               .andExpect(status().isOk());

        int portionsLeft = coffeeMachineRepository.getPortionsLeft(1L);

        assertThat(portionsLeft).isEqualTo(3);
    }

    @Test
    void getTypesOfCoffee() throws Exception {
        MvcResult result = mockMvc.perform(get(AVAILABLE_TYPES))
                                  .andExpect(status().isOk())
                                  .andReturn();
        CoffeeType[] coffeeTypes = mapper.readValue(result.getResponse().getContentAsString(),
                                                    CoffeeType[].class);

        assertThat(coffeeTypes).isEqualTo(CoffeeType.values());
    }
}
