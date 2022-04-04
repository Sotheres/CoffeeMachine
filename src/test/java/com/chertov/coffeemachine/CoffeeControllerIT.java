package com.chertov.coffeemachine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.chertov.coffeemachine.typesofcoffee.CoffeeType;
import com.chertov.coffeemachine.dto.CoffeeDto;
import com.chertov.coffeemachine.testcontainers.PostgreSqlTestContainer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class CoffeeControllerIT {

    @Container
    private PostgreSqlTestContainer testContainer = PostgreSqlTestContainer.getInstance();

    @Autowired
    private MockMvc mockMvc;

    private JsonMapper mapper = JsonMapper.builder().disable(MapperFeature.USE_ANNOTATIONS).build();

    @Test
    void makeCoffee() throws Exception {
        CoffeeDto requestDto = new CoffeeDto();
        requestDto.setType(CoffeeType.ESPRESSO);

        mockMvc.perform(post("/service/fillMachine")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(1)))
               .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(post("/coffee")
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .content(mapper.writeValueAsString(requestDto)))
                                  .andExpect(status().isOk())
                                  .andReturn();
        CoffeeDto resultDto = mapper.readValue(result.getResponse().getContentAsString(),
                                               CoffeeDto.class);

        assertThat(resultDto.getType()).isEqualTo(CoffeeType.ESPRESSO);
        assertThat(resultDto.getTimeOfBrewing()).isNotNull();
    }
}
