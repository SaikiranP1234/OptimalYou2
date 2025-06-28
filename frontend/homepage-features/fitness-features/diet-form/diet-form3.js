document.addEventListener("DOMContentLoaded", () => {
    document.querySelector(".user-form").addEventListener("submit", (event) => {
        event.preventDefault();

        const form3Data = {
            preferredIngredients: document.getElementById("preferred").value,
            avoidedIngredients: document.getElementById("avoided").value,
            mealsPerDay: document.getElementById("meals").value,
            preferredMealTimes: document.getElementById("mealtimes").value
        };

        localStorage.setItem("dietForm3Data", JSON.stringify(form3Data));
        window.location.href = "diet-form4.html"; // Navigate to next form
    });
});
