document.addEventListener("DOMContentLoaded", () => {
    document.querySelector(".user-form").addEventListener("submit", async (event) => {
        event.preventDefault();

        const form1Data = JSON.parse(localStorage.getItem("fitnessform1Data")) || {};
        const form2Data = JSON.parse(localStorage.getItem("fitnessform2Data")) || {};

        const form3Data = {
            injuries: Array.from(document.getElementById("injuries").selectedOptions)
                           .map(el => el.value),
            availableEquipment: Array.from(document.querySelectorAll('input[name="availableEquipment"]:checked'))
                                     .map(el => el.value),
            workoutLocation: document.getElementById("location").value,
            intensityLevel: document.getElementById("intensity").value,
            calorieBurnTarget: document.getElementById("calories").value
        };

        // Merge all form data
        const fullfitnessData = { ...form1Data, ...form2Data, ...form3Data };
        localStorage.setItem("fullfitnessData", JSON.stringify(fullfitnessData));
        window.location.href = "plan/plan.html";
    });
});
