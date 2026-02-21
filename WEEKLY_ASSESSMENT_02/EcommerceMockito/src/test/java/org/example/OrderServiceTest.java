package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private PaymentGateway paymentGateway;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void testSuccessfulPayment() {
        // Arrange
        double amount = 2000;
        when(paymentGateway.processPayment(amount)).thenReturn(true);

        // Act
        String result = orderService.placeOrder(amount);

        // Assert
        assertEquals("Order Confirmed", result);
        verify(paymentGateway, times(1)).processPayment(amount);
    }

    @Test
    public void testPaymentFailure() {
        // Arrange
        double amount = 1500;
        when(paymentGateway.processPayment(amount)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.placeOrder(amount);
        });
        assertEquals("Payment Failed", exception.getMessage());
        verify(paymentGateway, times(1)).processPayment(amount);
    }

    @Test
    public void testInvalidOrderAmount() {
        // Arrange
        double amount = 0;

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.placeOrder(amount);
        });
        assertEquals("Invalid Order Amount", exception.getMessage());
        verify(paymentGateway, never()).processPayment(anyDouble());
    }
}
