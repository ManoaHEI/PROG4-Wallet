package com.prog4.wallet.IntegrationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WithdrawalIntegrationTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSuccessfulWithdrawal() throws Exception {
        String requestBody = "{\"accountId\": \"123456\", \"amount\": 100.00, \"date\": \"2024-04-02T10:00:00\"}";
        mockMvc.perform(post("/api/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Withdrawal successful"));
    }

    @Test
    public void testInsufficientBalanceWithdrawal() throws Exception {
        String requestBody = "{\"accountId\": \"123456\", \"amount\": 100000.00, \"date\": \"2024-04-02T10:00:00\"}";
        mockMvc.perform(post("/api/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Insufficient balance"));
    }

    @Test
    public void testInvalidAccountWithdrawal() throws Exception {
        String requestBody = "{\"accountId\": \"999999\", \"amount\": 100.00, \"date\": \"2024-04-02T10:00:00\"}";
        mockMvc.perform(post("/api/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }


}
