document.addEventListener("DOMContentLoaded", () => {
    document.querySelector(".user-form").addEventListener("submit", (event) => {
        event.preventDefault(); // Prevent default navigation

        // Collect form1 data
        const formData = {
            name: document.getElementById("name").value,
            age: document.getElementById("age").value,
            gender: document.getElementById("gender").value,
            fitnessLevel: document.getElementById("fitness").value,
            fitnessGoals: Array.from(document.querySelectorAll('input[name="fitnessGoals"]:checked'))
                                .map(el => el.value)
        };

        // Store in localStorage
        localStorage.setItem("fitnessform1Data", JSON.stringify(formData));

        // Redirect to form2.html
        window.location.href = "form2.html";
    });
});
