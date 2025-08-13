package com.hexaware.cozyhavenproject.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.hexaware.cozyhavenproject.entities.BedType;
import com.hexaware.cozyhavenproject.entities.Room;
import com.hexaware.cozyhavenproject.util.PricingUtil;

class PricingUtilTest {


	@Test
    void calculatePerNight_shouldApplyExtraChargesForAdultsAndChildren() {
        Room room = new Room();
        room.setBedType(BedType.DOUBLE);
        room.setBaseFare(BigDecimal.valueOf(1000));

        BigDecimal price = PricingUtil.calculatePerNight(room, 3, 1);

        assertThat(price).isGreaterThan(BigDecimal.valueOf(1000));
    }
}
