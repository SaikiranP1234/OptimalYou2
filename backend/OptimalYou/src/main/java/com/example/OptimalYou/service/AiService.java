package com.example.OptimalYou.service;

import com.example.OptimalYou.model.ai.FinancialAdviceRequest;
import com.example.OptimalYou.model.ai.UserDietPreferences;
import com.example.OptimalYou.model.ai.UserWorkoutPreferences;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    public String generateWorkout(UserWorkoutPreferences preferences) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("I want a personalized workout planner for me based on my fitness level, goals, preferences, and available equipment. ");
        prompt.append("Here's my profile:\n\n");

        // Personal Information
        prompt.append("1. Personal Information:\n");
        prompt.append("   - Name: ").append(preferences.getName() != null ? preferences.getName() : "Not provided").append("\n");
        prompt.append("   - Age: ").append(preferences.getAge() > 0 ? preferences.getAge() : "Not provided").append("\n");
        prompt.append("   - Gender: ").append(preferences.getGender() != null ? preferences.getGender() : "Not provided").append("\n\n");

        // Fitness Goals
        prompt.append("2. Fitness Goals: ").append(
                preferences.getFitnessGoals() != null && !preferences.getFitnessGoals().isEmpty()
                        ? String.join(", ", preferences.getFitnessGoals())
                        : "Not provided"
        ).append("\n\n");

        // Current Fitness Level
        prompt.append("3. Current Fitness Level: ").append(
                preferences.getFitnessLevel() != null ? preferences.getFitnessLevel() : "Not provided"
        ).append("\n\n");

        // Workout Preferences
        prompt.append("4. Workout Preferences:\n");
        prompt.append("   - Types of workouts: ").append(
                preferences.getWorkoutTypes() != null && !preferences.getWorkoutTypes().isEmpty()
                        ? String.join(", ", preferences.getWorkoutTypes())
                        : "Not provided"
        ).append("\n");
        prompt.append("   - Workout duration: ").append(preferences.getWorkoutDuration() > 0
                ? preferences.getWorkoutDuration() + " minutes"
                : "Not provided"
        ).append("\n");
        prompt.append("   - Preferred workout days: ").append(
                preferences.getPreferredWorkoutDays() != null && !preferences.getPreferredWorkoutDays().isEmpty()
                        ? String.join(", ", preferences.getPreferredWorkoutDays())
                        : "Not provided"
        ).append("\n");
        prompt.append("   - Preferred time of day: ").append(
                preferences.getPreferredTimeOfDay() != null ? preferences.getPreferredTimeOfDay() : "Not provided"
        ).append("\n\n");

        // Health Considerations
        prompt.append("5. Health Considerations:\n");
        prompt.append("   - Health conditions: ").append(
                preferences.getHealthConditions() != null && !preferences.getHealthConditions().isEmpty()
                        ? String.join(", ", preferences.getHealthConditions())
                        : "None"
        ).append("\n");
        prompt.append("   - Injuries: ").append(
                preferences.getInjuries() != null && !preferences.getInjuries().isEmpty()
                        ? String.join(", ", preferences.getInjuries())
                        : "None"
        ).append("\n\n");

        // Available Equipment and Location
        prompt.append("6. Available Equipment and Location:\n");
        prompt.append("   - Equipment: ").append(
                preferences.getAvailableEquipment() != null && !preferences.getAvailableEquipment().isEmpty()
                        ? String.join(", ", preferences.getAvailableEquipment())
                        : "None"
        ).append("\n");
        prompt.append("   - Workout location: ").append(
                preferences.getWorkoutLocation() != null ? preferences.getWorkoutLocation() : "Not provided"
        ).append("\n\n");

        // Additional Preferences
        prompt.append("7. Additional Preferences:\n");
        prompt.append("   - Intensity level: ").append(
                preferences.getIntensityLevel() != null ? preferences.getIntensityLevel() : "Not provided"
        ).append("\n");
        prompt.append("   - Calorie burn target: ").append(preferences.getCalorieBurnTarget() > 0
                ? preferences.getCalorieBurnTarget() + " calories"
                : "Not provided"
        ).append("\n\n");

        prompt.append("Based on this information, please design a weekly workout plan tailored for me. ");
        prompt.append("Include warm-up and cool-down suggestions, exercise names, repetitions/sets (if applicable), and any special tips or recommendations.");
        return prompt.toString();

    }

    public String generateDiet(UserDietPreferences preferences) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("I am creating a personalized diet plan for me based on my health conditions, dietary preferences, and calorie goals. ");
        prompt.append("Here's my's profile:\n\n");

        // Personal Information
        prompt.append("1. Personal Information:\n");
        prompt.append("   - Name: ").append(preferences.getName() != null ? preferences.getName() : "Not provided").append("\n");
        prompt.append("   - Age: ").append(preferences.getAge() > 0 ? preferences.getAge() : "Not provided").append("\n");
        prompt.append("   - Gender: ").append(preferences.getGender() != null ? preferences.getGender() : "Not provided").append("\n\n");

        // Health and Dietary Conditions
        prompt.append("2. Health and Dietary Conditions:\n");
        prompt.append("   - Health conditions: ").append(
                preferences.getHealthConditions() != null
                        ?  preferences.getHealthConditions()
                        : "None"
        ).append("\n");
        prompt.append("   - Allergies: ").append(
                preferences.getAllergies() != null
                        ?  preferences.getAllergies()
                        : "None"
        ).append("\n");
        prompt.append("   - Dietary restrictions: ").append(
                preferences.getDietaryRestrictions() != null
                        ? preferences.getDietaryRestrictions()
                        : "None"
        ).append("\n\n");

        // Calorie and Nutrient Goals
        prompt.append("3. Calorie and Nutrient Goals:\n");
        prompt.append("   - Daily Calorie Goal: ").append(preferences.getDailyCalorieGoal() > 0 ? preferences.getDailyCalorieGoal() + " kcal" : "Not provided").append("\n");
        prompt.append("   - Macronutrient Ratio: ").append(
                preferences.getMacronutrientRatio() != null
                        ?  preferences.getMacronutrientRatio()
                        : "Not provided"
        ).append("\n\n");

        // Meal Preferences
        prompt.append("4. Meal Preferences:\n");
        prompt.append("   - Preferred Cuisines: ").append(
                preferences.getPreferredCuisines() != null
                        ? preferences.getPreferredCuisines()
                        : "Not provided"
        ).append("\n");
        prompt.append("   - Preferred Ingredients: ").append(
                preferences.getPreferredIngredients() != null
                        ? preferences.getPreferredIngredients()
                        : "Not provided"
        ).append("\n");
        prompt.append("   - Avoided Ingredients: ").append(
                preferences.getAvoidedIngredients() != null
                        ? preferences.getAvoidedIngredients()
                        : "Not provided"
        ).append("\n\n");

        // Meal Frequency
        prompt.append("5. Meal Frequency:\n");
        prompt.append("   - Meals per Day: ").append(preferences.getMealsPerDay() != null ? preferences.getMealsPerDay() : "Not provided").append("\n");
        prompt.append("   - Preferred Meal Times: ").append(
                preferences.getPreferredMealTimes() != null
                        ? preferences.getPreferredMealTimes()
                        : "Not provided"
        ).append("\n\n");

        // Grocery List
        prompt.append("6. Grocery List:\n");
        prompt.append("   - Items: ").append(
                preferences.getGroceryList() != null
                        ? preferences.getGroceryList()
                        : "Not provided"
        ).append("\n\n");

        // Special Requirements
        prompt.append("7. Special Requirements:\n");
        prompt.append("   - Include recipes ");
        prompt.append("   - Include shopping list ");

        prompt.append("Based on this information, please design a weekly diet plan tailored to my needs. ");
        prompt.append("Include meal suggestions, grocery items, recipes, and a shopping list (if applicable).");

        return prompt.toString();
    }

    public String generateAdvice(FinancialAdviceRequest far) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Provide financial advice based on the following details:\n\n");

        prompt.append("1. Topics of Interest: ")
                .append(far.getAdviceTopics() != null ? far.getAdviceTopics() : "Not provided")
                .append(".\n");

        prompt.append("2. Budget/Amount involved: ")
                .append(far.getBudgetAmount() != null ? far.getBudgetAmount() + "Rupees" : "Not specified")
                .append(".\n");

        prompt.append("3. Time Frame: ")
                .append(far.getTimeFrame() != null ? far.getTimeFrame() : "Not specified")
                .append(".\n");

        prompt.append("4. Risk Tolerance: ")
                .append(far.getRiskTolerance() != null ? far.getRiskTolerance() : "Not specified")
                .append(".\n");

        prompt.append("5. Additional Concerns: ")
                .append(far.getAdditionalConcerns() != null ? far.getAdditionalConcerns() : "None")
                .append(".\n");

        return prompt.toString();
    }
}
