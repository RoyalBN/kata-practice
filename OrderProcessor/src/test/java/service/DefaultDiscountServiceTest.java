package service;

import org.example.service.DefaultDiscountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultDiscountServiceTest {

    private DefaultDiscountService defaultDiscountService;
    private double total;
    private boolean isDiscounted;

    @BeforeEach
    void setUp() {
        defaultDiscountService = new DefaultDiscountService();
        total = 0.0;
    }

    @Test
    @DisplayName("If true --> Apply discount")
    void should_apply_discount_if_boolean_condition_is_true() {
        // Arrange
        total = 20.0;
        isDiscounted = true;

        // Act
        double totalWithDiscount = defaultDiscountService.applyDiscount(total, isDiscounted);

        // Verify
        assertThat(totalWithDiscount).isNotNull();
        assertThat(totalWithDiscount).isEqualTo(18.0);

    }

    @Test
    @DisplayName("If false --> Don't apply discount")
    void should_not_apply_discount_if_boolean_condition_is_false() {
        // Arrange
        total = 25.0;
        isDiscounted = false;

        // Act
        double totalWithoutDiscount = defaultDiscountService.applyDiscount(total, isDiscounted);

        // Verify
        assertThat(totalWithoutDiscount).isNotNull();
        assertThat(totalWithoutDiscount).isEqualTo(25.0);
    }
}