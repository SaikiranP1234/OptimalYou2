package com.example.OptimalYou.model.ai;

import org.springframework.stereotype.Component;

@Component
public class FinancialAdviceRequest {
    private String adviceTopics; // E.g., "Saving & Investing", "Stock Market & Crypto"
    private Double budgetAmount; // Can be null if "Not sure"
    private String timeFrame; // "Immediate", "Short-term", "Long-term"
    private String riskTolerance; // "Low", "Medium", "High"
    private String additionalConcerns;

    public String getAdditionalConcerns() {
        return additionalConcerns;
    }

    public void setAdditionalConcerns(String additionalConcerns) {
        this.additionalConcerns = additionalConcerns;
    }

    public String getAdviceTopics() {
        return adviceTopics;
    }

    public void setAdviceTopics(String adviceTopics) {
        this.adviceTopics = adviceTopics;
    }

    public Double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(Double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public String getRiskTolerance() {
        return riskTolerance;
    }

    public void setRiskTolerance(String riskTolerance) {
        this.riskTolerance = riskTolerance;
    }

    public String getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(String timeFrame) {
        this.timeFrame = timeFrame;
    }

    @Override
    public String toString() {
        return "FinancialAdviceRequest{" +
                "additionalConcerns='" + additionalConcerns + '\'' +
                ", adviceTopics='" + adviceTopics + '\'' +
                ", budgetAmount=" + budgetAmount +
                ", timeFrame='" + timeFrame + '\'' +
                ", riskTolerance='" + riskTolerance + '\'' +
                '}';
    }
}
