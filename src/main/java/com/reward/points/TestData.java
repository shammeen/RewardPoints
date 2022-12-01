package com.reward.points;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TestData {

    public static void main(String[] args) throws JsonProcessingException {

        LocalDate date =  LocalDate.now().minus(Period.ofDays((new Random().nextInt(80))));
        System.out.println(date.toString());
        System.out.println(LocalDate.now().minusMonths(3).with(TemporalAdjusters.firstDayOfMonth()).toString());
        System.out.println(LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth()).toString());
        List<Map> transactionList = new ArrayList<>();

        for (int i=0; i<100; i++) {
            Map<String, Object> themap = new HashMap<>();

            themap.put("customerId", new Random().nextInt(10) + 1);
            themap.put("transactionAmount", new Random().nextInt(100) + 50);
            themap.put("transactionDate", LocalDate.now().minus(Period.ofDays((new Random().nextInt(80)))).toString());

            transactionList.add(themap);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        String json = objectMapper.writeValueAsString(transactionList);

        System.out.println(json);

    }
}
