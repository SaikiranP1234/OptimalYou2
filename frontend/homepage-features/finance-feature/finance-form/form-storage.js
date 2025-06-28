document.addEventListener("DOMContentLoaded", () => {
    document.querySelector(".user-form").addEventListener("submit", (event) => {
        event.preventDefault(); // Prevent default navigation

        // Collect form1 data
        const formData = {
            adviceTopics: document.getElementById("adviceTopics").value,
            budgetAmount: document.getElementById("budgetAmount").value,
            timeFrame: document.getElementById("timeFrame").value,
            riskTolerance: document.getElementById("riskTolerance").value,
            additionalConcerns: document.getElementById("additionalConcerns").value
        };

        // Store in localStorage
        localStorage.setItem("finance-data", JSON.stringify(formData));

        // Redirect to form2.html
        window.location.href = "plan/plan.html";
    });
});
