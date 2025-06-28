document.addEventListener("DOMContentLoaded", () => {
    document.querySelector(".user-form").addEventListener("submit", (event) => {
        event.preventDefault();

        const form1Data = {
            name: document.getElementById("name").value,
            age: document.getElementById("age").value,
            gender: document.getElementById("gender").value,
            healthConditions: document.getElementById("health").value
        };

        localStorage.setItem("dietForm1Data", JSON.stringify(form1Data));
        window.location.href = "diet-form2.html"; // Navigate to next form
    });
});
