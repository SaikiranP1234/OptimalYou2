document.addEventListener("DOMContentLoaded", async () => {
    const container = document.getElementById("workout-container");
    container.innerHTML = "<p>Generating your personalized plan...</p>";

    const fullData = JSON.parse(localStorage.getItem("fullDietData"));
    if (!fullData) {
        container.innerHTML = "<p>Error: No data found. Please submit your preferences again.</p>";
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/ai/generateDiet", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(fullData),
        });

        if (!response.ok) {
            throw new Error("Failed to fetch workout plan.");
        }

        const reader = response.body.getReader();
        const decoder = new TextDecoder();
        
        container.innerHTML = ""; // Clear previous loading message
        let lastParagraph = document.createElement("pre");
        lastParagraph.style.fontFamily = "-apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif";
        container.appendChild(lastParagraph); // Start with one paragraph

        while (true) {
            const { value, done } = await reader.read();
            if (done) break;
            
            const text = decoder.decode(value, { stream: true });
            lastParagraph.textContent += text; // Append text to the last paragraph
        }
    } catch (error) {
        container.innerHTML = `<p>Error: ${error.message}</p>`;
    }
});
