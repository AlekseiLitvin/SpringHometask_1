package by.epam.aliaksei.litvin.service.impl;

import by.epam.aliaksei.litvin.domain.Event;
import by.epam.aliaksei.litvin.domain.User;
import by.epam.aliaksei.litvin.service.DiscountService;
import by.epam.aliaksei.litvin.strategies.DiscountStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;

public class DiscountServiceImpl implements DiscountService {

    private List<DiscountStrategy> discountStrategies;

    public DiscountServiceImpl(List<DiscountStrategy> discountStrategies) {
        this.discountStrategies = discountStrategies;
    }

    @Override
    public double getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        return discountStrategies.stream()
                .map(discountStrategy -> discountStrategy.getDiscountValue(user, event, airDateTime, numberOfTickets))
                .max(Double::compare)
                .orElse(0.0);

    }
}
