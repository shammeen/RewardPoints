package com.reward.points.model;

import java.time.LocalDate;

public class Transaction {
    private int customerId;
    private double transactionAmount;
	private int rewardPoints;
    private LocalDate transactionDate;

    public Transaction() {
    	
    }
    
    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
        double amount = this.transactionAmount;

        if (amount > 100) {
            this.setRewardPoints((int) (amount - 100) * 2 + 50 * 1);
        }

        else if (amount <= 100 && amount >= 50) {
            this.setRewardPoints((int) (amount - 50) * 1);
        }
    }

    public double getTransactionAmount() {
		return transactionAmount;
	}
    
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getRewardPoints() {
		return rewardPoints;
	}

	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

}
