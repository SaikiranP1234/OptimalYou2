const jwtToken = localStorage.getItem('jwtToken');
const username = localStorage.getItem('username');

if (!jwtToken || !username) {
    alert('Unauthorized! Please log in.');
    window.location.href = 'index.html';
}

const apiBase = 'http://localhost:8080/mundane';

// Fetch tasks based on category
async function fetchTasks(endpoint) {
    try {
        const response = await fetch(`${apiBase}/${endpoint}/${username}`, {
            headers: {
                Authorization: `Bearer ${jwtToken}`,
            },
        });
        if (!response.ok) throw new Error('Failed to fetch tasks');
        const tasks = await response.json();
        renderTasks(tasks);
    } catch (error) {
        console.error(error);
        alert('Error fetching tasks.');
    }
}

// Render tasks to the UI
function renderTasks(tasks) {
    const container = document.getElementById('tasks-container');
    container.innerHTML = '';
    tasks.forEach((task) => {
        const taskDiv = document.createElement('div');
        taskDiv.className = 'task-item';
        taskDiv.innerHTML = `
            <div>
                <h4>${task.taskTitle} (${['Low', 'Medium', 'High'][task.priority - 1]})</h4>
                <p>${task.taskDescription}</p>
                <p>Deadline: ${new Date(task.deadline).toLocaleDateString()}</p>
            </div>
            <div class="task-actions">
                <button onclick="markCompleted(${task.id})">Complete</button>
                <button class="edit-btn" onclick="editTask(${task.id})">Edit</button>
                <button onclick="deleteTask(${task.id})">Delete</button>
            </div>
        `;
        container.appendChild(taskDiv);
    });
}

// Create a new task
document.getElementById('task-form').addEventListener('submit', async (event) => {
    event.preventDefault();

    const task = {
        username,
        taskTitle: document.getElementById('task-title').value,
        taskDescription: document.getElementById('task-description').value,
        priority: document.getElementById('task-priority').value,
        deadline: document.getElementById('task-deadline').value,
    };

    try {
        const response = await fetch(`${apiBase}/create`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${jwtToken}`,
            },
            body: JSON.stringify(task),
        });
        if (!response.ok) throw new Error('Failed to create task');
        alert('Task created successfully!');
        fetchTasks('all');
    } catch (error) {
        console.error(error);
        alert('Error creating task.');
    }
});

// Edit an existing task
async function editTask(taskId) {
    const taskTitle = prompt('Enter new task title:');
    const taskDescription = prompt('Enter new task description:');
    const taskPriority = prompt('Enter new priority (1: Low, 2: Medium, 3: High):');
    const taskDeadline = prompt('Enter new deadline (YYYY-MM-DD):');

    if (!taskTitle || !taskDescription || !taskPriority || !taskDeadline) {
        alert('All fields are required for editing.');
        return;
    }

    const updatedTask = {
        id: taskId,
        username,
        taskTitle,
        taskDescription,
        priority: parseInt(taskPriority),
        deadline: new Date(taskDeadline),
    };

    try {
        const response = await fetch(`${apiBase}/edit`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${jwtToken}`,
            },
            body: JSON.stringify(updatedTask),
        });
        if (!response.ok) throw new Error('Failed to edit task');
        alert('Task updated successfully!');
        fetchTasks('all');
    } catch (error) {
        console.error(error);
        alert('Error updating task.');
    }
}

// Search tasks
document.getElementById('search-btn').addEventListener('click', async () => {
    const searchKey = document.getElementById('search-bar').value.trim();
    if (!searchKey) {
        alert('Please enter a keyword to search.');
        return;
    }

    try {
        const response = await fetch(`${apiBase}/search/${username}/${searchKey}`, {
            headers: {
                Authorization: `Bearer ${jwtToken}`,
            },
        });
        if (!response.ok) throw new Error('Failed to search tasks');
        const tasks = await response.json();
        renderTasks(tasks);
    } catch (error) {
        console.error(error);
        alert('Error searching tasks.');
    }
});

// Mark task as completed
async function markCompleted(taskId) {
    try {
        const response = await fetch(`${apiBase}/completed/${taskId}`, {
            method: 'POST',
            headers: {
                Authorization: `Bearer ${jwtToken}`,
            },
        });
        if (!response.ok) throw new Error('Failed to mark task as completed');
        alert('Task marked as completed.');
        fetchTasks('all');
    } catch (error) {
        console.error(error);
        alert('Error completing task.');
    }
}

// Delete a task
async function deleteTask(taskId) {
    try {
        const response = await fetch(`${apiBase}/delete/${taskId}`, {
            method: 'DELETE',
            headers: {
                Authorization: `Bearer ${jwtToken}`,
            },
        });
        if (!response.ok) throw new Error('Failed to delete task');
        alert('Task deleted successfully.');
        fetchTasks('all');
    } catch (error) {
        console.error(error);
        alert('Error deleting task.');
    }
}

// Event listeners for navigation buttons
document.getElementById('btn-all').addEventListener('click', () => fetchTasks('all'));
document.getElementById('btn-progressing').addEventListener('click', () => fetchTasks('progressing'));
document.getElementById('btn-missed').addEventListener('click', () => fetchTasks('missed'));
document.getElementById('btn-completed').addEventListener('click', () => fetchTasks('completed'));

// Initial fetch
fetchTasks('all');