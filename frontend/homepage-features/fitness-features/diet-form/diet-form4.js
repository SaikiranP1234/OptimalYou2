document.addEventListener("DOMContentLoaded", () => {
    document.querySelector(".user-form").addEventListener("submit", (event) => {
        event.preventDefault();

        const form1Data = JSON.parse(localStorage.getItem("dietForm1Data")) || {};
        const form2Data = JSON.parse(localStorage.getItem("dietForm2Data")) || {};
        const form3Data = JSON.parse(localStorage.getItem("dietForm3Data")) || {};

        const form4Data = {
            groceryList: document.getElementById("grocery").value,
            recipes: document.getElementById("recipes").value,
            shoppingList: document.getElementById("shopping").value
        };

        const fullDietData = { ...form1Data, ...form2Data, ...form3Data, ...form4Data };

        localStorage.setItem("fullDietData", JSON.stringify(fullDietData));
        window.location.href = "plan/plan.html"; // Navigate to diet plan page
    });
});
