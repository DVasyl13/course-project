package com.lab.util;

import java.util.HashMap;

public final class TaxCalculator {
    private static final HashMap<String, Float> taxes = new HashMap<>();

    static {
        taxes.put("income", 8.0f);
        taxes.put("prize", 18.0f);
        taxes.put("sale", 5.0f);
        taxes.put("transfer", 1.0f);
        taxes.put("perk", 3.0f);
    }

    public static int calculate(String taxType, int value) {
        if (value > 0 && taxes.containsKey(taxType)) {
            return (int) (taxes.get(taxType) * value / 100);
        }
        return 0;
    }

    public static boolean isValid(String taxName) {
        return taxes.containsKey(taxName);
    }

    public static void allAvailableTaxes() {
        for (String name : taxes.keySet()) {
            System.out.println(name);
        }
    }
}
