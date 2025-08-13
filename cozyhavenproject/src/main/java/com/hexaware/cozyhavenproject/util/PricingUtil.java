package com.hexaware.cozyhavenproject.util;

import java.math.BigDecimal;

import com.hexaware.cozyhavenproject.entities.BedType;
import com.hexaware.cozyhavenproject.entities.Room;

public final class PricingUtil {

	private PricingUtil() {}

    // adults = count considered >14, children = count considered <=14
    public static BigDecimal calculatePerNight(Room room, int adults, int children) {
        BigDecimal base = room.getBaseFare(); // BigDecimal recommended in entity
        int maxByBed = capacityByBedType(room.getBedType()); // 2/4/6
        int included = includedByBedType(room.getBedType()); // 1 for SINGLE, 2 for DOUBLE, 4 for KING (per spec)
        int total = adults + children;

        if (total <= included) {
            return base;
        }

        int extra = total - included;
        // charge extra per person: adult 40%, child 20% of base
        int chargeableAdults = Math.min(adults, extra); // prioritize adults in extras
        int chargeableChildren = Math.max(0, extra - chargeableAdults);

        BigDecimal adultExtra = base.multiply(BigDecimal.valueOf(0.40)).multiply(BigDecimal.valueOf(chargeableAdults));
        BigDecimal childExtra = base.multiply(BigDecimal.valueOf(0.20)).multiply(BigDecimal.valueOf(chargeableChildren));

        // If total exceeds maximum capacity, caller should block booking; here we just return price
        return base.add(adultExtra).add(childExtra);
    }

    public static int capacityByBedType(BedType bedType) {
        return switch (bedType) {
            case SINGLE -> 2;
            case DOUBLE -> 4;
            case KING -> 6;
		default -> throw new IllegalArgumentException("Unexpected value: " + bedType);
        };
    }

    public static int includedByBedType(BedType bedType) {
        // From PDF: SINGLE -> charges start from 2nd person, DOUBLE -> from 3rd, KING -> from 5th
        return switch (bedType) {
            case SINGLE -> 1;
            case DOUBLE -> 2;
            case KING -> 4;
		default -> throw new IllegalArgumentException("Unexpected value: " + bedType);
        };
    }
}
