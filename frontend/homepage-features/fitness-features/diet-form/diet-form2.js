document.addEventListener("DOMContentLoaded", () => {
    document.querySelector(".user-form").addEventListener("submit", (event) => {
        event.preventDefault();

        const form2Data = {
            allergies: document.getElementById("allergies").value,
            dietaryRestrictions: document.getElementById("restrictions").value,
            macronutrientRatio: document.getElementById("macro").value,
            preferredCuisines: document.getElementById("cuisines").value
        };

        localStorage.setItem("dietForm2Data", JSON.stringify(form2Data));
        window.location.href = "diet-form3.html"; // Navigate to next form
    });
});
