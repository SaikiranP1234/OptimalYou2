document.addEventListener("DOMContentLoaded", () => {
    document.querySelector(".user-form").addEventListener("submit", (event) => {
        event.preventDefault();

        const formData = {
            healthConditions: Array.from(document.getElementById("health").selectedOptions)
                                   .map(el => el.value),
            goals: document.getElementById("goals").value,
            workoutTypes: Array.from(document.querySelectorAll('input[name="workoutTypes"]:checked'))
                               .map(el => el.value),
            workoutDuration: document.getElementById("duration").value,
            preferredWorkoutDays: Array.from(document.querySelectorAll('input[name="preferredWorkoutDays"]:checked'))
                                       .map(el => el.value),
            preferredTimeOfDay: document.getElementById("preferredTime").value
        };

        // Store in localStorage
        localStorage.setItem("fitnessform2Data", JSON.stringify(formData));

        // Redirect to form3.html
        window.location.href = "form3.html";
    });
});
