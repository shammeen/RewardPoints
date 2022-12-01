package com.reward.points.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.reward.points.model.Transaction;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RewardPointController {

    private static final String TOTAL = "Total";

	@PostMapping("/report")
    public Map getReportOfRewardPoints(@RequestBody(required = true) String transactionData) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        List<Transaction> transactionList =  mapper.readValue(transactionData, new TypeReference<List<Transaction>>(){});
        LocalDate startDate1 = LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endData1 = LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());

        LocalDate startDate2 = LocalDate.now().minusMonths(2).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endData2 = LocalDate.now().minusMonths(2).with(TemporalAdjusters.lastDayOfMonth());

        LocalDate startDate3 = LocalDate.now().minusMonths(3).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endData3 = LocalDate.now().minusMonths(3).with(TemporalAdjusters.lastDayOfMonth());

        Map reportMap = new HashMap();
        for (Transaction transaction: transactionList) {
            Object object = reportMap.get(transaction.getCustomerId());
            Map customerReportMap = null;
            if (object == null) {
                customerReportMap = new HashMap();
                customerReportMap.put(startDate1.getMonth(), 0);
                customerReportMap.put(startDate2.getMonth(), 0);
                customerReportMap.put(startDate3.getMonth(), 0);
                customerReportMap.put(TOTAL, 0);
                reportMap.put(transaction.getCustomerId(), customerReportMap);
            }
            else {
                customerReportMap = (Map) object;
            }
            
            if (updateReportList(transaction, startDate1, endData1, startDate2, endData2, startDate3, endData3, customerReportMap)) {
                customerReportMap.put(TOTAL, (int) customerReportMap.get(TOTAL) + transaction.getRewardPoints());
            }

        }

        return reportMap;
    }

    public boolean updateReportList(Transaction transaction, LocalDate startDate1, LocalDate endData1,
                                 LocalDate startDate2, LocalDate endData2,
                                 LocalDate startDate3, LocalDate endData3,
                                 Map customerReportMap) {
        if (! transaction.getTransactionDate().isBefore(startDate1) && transaction.getTransactionDate().isAfter(endData1))
        {
            customerReportMap.put(startDate1.getMonth(), (int) customerReportMap.get(startDate1.getMonth()) + transaction.getRewardPoints());
            return true;
        }

        if (! transaction.getTransactionDate().isBefore(startDate2) && transaction.getTransactionDate().isAfter(endData2))
        {
            customerReportMap.put(startDate2.getMonth(), (int) customerReportMap.get(startDate2.getMonth()) + transaction.getRewardPoints());
            return true;
        }

        if (! transaction.getTransactionDate().isBefore(startDate3) && transaction.getTransactionDate().isAfter(endData3))
        {
            customerReportMap.put(startDate3.getMonth(), (int) customerReportMap.get(startDate3.getMonth()) + transaction.getRewardPoints());
            return true;
        }

        return false;
    }
}
