const apiBase = 'http://localhost:8080/notebucket';
const username = localStorage.getItem('username');
const jwtToken = localStorage.getItem('jwtToken');

if (!username || !jwtToken) {
    alert('User not authenticated. Please log in.');
    window.location.href = '/login.html';
}

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    loadBuckets();

    // Event listeners
    document.getElementById('search-button').addEventListener('click', handleSearch);
    document.getElementById('create-bucket-button').addEventListener('click', showCreateBucketModal);
    document.getElementById('modal-close').addEventListener('click', closeModal);
});

// Fetch and display buckets
const loadBuckets = async () => {
    try {
        const response = await fetch(`${apiBase}/get/${username}`, {
            headers: { Authorization: `Bearer ${jwtToken}` },
        });
        if (!response.ok) throw new Error('Failed to load buckets.');

        const buckets = await response.json();
        renderBuckets(buckets);
    } catch (error) {
        console.error(error);
        alert('Error loading buckets.');
    }
};

// Render buckets
const renderBuckets = (buckets) => {
    const container = document.getElementById('buckets-container');
    if (!buckets.length) {
        container.innerHTML = '<p>No buckets found. Create a new one!</p>';
        return;
    }

    container.innerHTML = buckets.map(bucketHTML).join('');
};

// HTML for each bucket
const bucketHTML = (bucket) => `
    <div class="bucket">
        <h3>${bucket.title}</h3>
        <button onclick="editBucket(${bucket.id}, '${bucket.title}')">Edit</button>
        <button onclick="deleteBucket(${bucket.id})">Delete</button>
        <button onclick="showManageNotesModal(${bucket.id}, 'add')">Add Notes</button>
        <button onclick="showManageNotesModal(${bucket.id}, 'remove')">Remove Notes</button>
        <ul>${bucket.notes.map(note => `<li onclick="viewNoteContent(${note.id})">${note.title}</li>`).join('')}</ul>
    </div>
`;

// View note content in a modal popup
const viewNoteContent = async (noteId) => {
    try {
        const response = await fetch(`http://localhost:8080/notes/get/${username}/${noteId}`, {
            headers: { Authorization: `Bearer ${jwtToken}` },
        });

        if (!response.ok) throw new Error('Failed to fetch note content.');

        const note = await response.json();
        showModal(note.title, `<p>${note.note}</p>`);
    } catch (error) {
        console.error(error);
        alert('Error fetching note content.');
    }
};

// Handle bucket creation
const showCreateBucketModal = () => {
    showModal('Create New Bucket', `
        <input type="text" id="bucket-title" placeholder="Enter bucket title">
    `, createBucket);
};

const createBucket = async () => {
    const title = document.getElementById('bucket-title').value.trim();
    if (!title) {
        alert('Please enter a title for the bucket.');
        return;
    }

    try {
        const response = await fetch(`${apiBase}/create`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${jwtToken}`,
            },
            body: JSON.stringify({ title, username }),
        });

        if (!response.ok) throw new Error('Failed to create bucket.');

        closeModal();
        loadBuckets();
        alert('Bucket created successfully!');
    } catch (error) {
        console.error(error);
        alert('Error creating bucket.');
    }
};

// Handle bucket deletion
const deleteBucket = async (bucketId) => {
    if (!confirm('Are you sure you want to delete this bucket?')) {
        return;
    }

    try {
        const response = await fetch(`${apiBase}/delete/${username}/${bucketId}`, {
            method: 'DELETE',
            headers: { Authorization: `Bearer ${jwtToken}` },
        });

        if (!response.ok) throw new Error('Failed to delete bucket.');

        loadBuckets();
        alert('Bucket deleted successfully!');
    } catch (error) {
        console.error(error);
        alert('Error deleting bucket.');
    }
};

// Edit bucket title
const editBucket = (bucketId, currentTitle) => {
    showModal('Edit Bucket Title', `
        <input type="text" id="bucket-title" value="${currentTitle}" placeholder="Enter new bucket title">
    `, () => submitEditBucket(bucketId));
};

const submitEditBucket = async (bucketId) => {
    const title = document.getElementById('bucket-title').value.trim();
    if (!title) {
        alert('Please enter a new title.');
        return;
    }

    try {
        const response = await fetch(`${apiBase}/edit/${bucketId}/${encodeURIComponent(title)}`, {
            method: 'POST',
            headers: { Authorization: `Bearer ${jwtToken}` },
        });

        if (!response.ok) throw new Error('Failed to edit bucket.');

        closeModal();
        loadBuckets();
        alert('Bucket updated successfully!');
    } catch (error) {
        console.error(error);
        alert('Error updating bucket.');
    }
};

// Show a modal for managing notes (add/remove)
const showManageNotesModal = async (bucketId, action) => {
    try {
        // Fetch notes based on action
        const endpoint = action === 'add' ? `notebucket/not-in-bucket/${bucketId}` : `notebucket/in-bucket/${bucketId}`;
        const response = await fetch(`http://localhost:8080/${endpoint}`, {
            headers: { Authorization: `Bearer ${jwtToken}` },
        });

        if (!response.ok) throw new Error(`Failed to load notes for ${action}.`);

        const notes = await response.json();
        if (!notes.length) {
            showModal(`${action === 'add' ? 'Add' : 'Remove'} Notes`, '<p>No notes available.</p>');
            return;
        }

        // Render notes as a checklist
        const notesList = notes.map(note => `
            <li>
                <input type="checkbox" id="note-${note.id}" value="${note.id}">
                <label for="note-${note.id}">${note.title}</label>
            </li>
        `).join('');

        showModal(`${action === 'add' ? 'Add' : 'Remove'} Notes`, `
            <ul>${notesList}</ul>
        `, () => manageNotes(bucketId, action));
    } catch (error) {
        console.error(error);
        alert(`Error loading notes for ${action}.`);
    }
};

// Handle adding or removing notes from a bucket
const manageNotes = async (bucketId, action) => {
    const selectedNoteIds = Array.from(document.querySelectorAll('input[type="checkbox"]:checked'))
        .map(input => input.value);

    if (!selectedNoteIds.length) {
        alert('Please select at least one note.');
        return;
    }

    try {
        const endpoint = action === 'add' ? 'add' : 'remove';

        // Process each note one by one
        for (const noteId of selectedNoteIds) {
            const response = await fetch(`${apiBase}/${endpoint}/${noteId}/${bucketId}`, {
                method: action === 'add' ? 'POST' : 'DELETE',
                headers: { Authorization: `Bearer ${jwtToken}` },
            });

            if (!response.ok) {
                throw new Error(`Failed to ${action} note with ID: ${noteId}`);
            }
        }

        closeModal();
        loadBuckets();
        alert(`${action === 'add' ? 'Added' : 'Removed'} notes successfully!`);
    } catch (error) {
        console.error(error);
        alert(`Error ${action === 'add' ? 'adding' : 'removing'} notes.`);
    }
};


// Modal utilities
const showModal = (title, body, actionHandler = null) => {
    document.getElementById('modal-title').textContent = title;
    document.getElementById('modal-body').innerHTML = body;

    const actionButton = document.getElementById('modal-action-button');
    if (actionHandler) {
        actionButton.style.display = 'block';
        actionButton.onclick = actionHandler;
    } else {
        actionButton.style.display = 'none';
    }

    document.getElementById('modal').style.display = 'block';
};

const closeModal = () => {
    document.getElementById('modal').style.display = 'none';
};

// Handle search
const handleSearch = async () => {
    const query = document.getElementById('search-bar').value.trim();
    if (!query) {
        alert('Enter a keyword to search.');
        return;
    }
    try {
        const response = await fetch(`${apiBase}/search/${username}/${encodeURIComponent(query)}`, {
            headers: { Authorization: `Bearer ${jwtToken}` },
        });

        if (!response.ok) throw new Error('Failed to search buckets.');

        const buckets = await response.json();
        renderBuckets(buckets);
    } catch (error) {
        console.error(error);
        alert('Error searching buckets.');
    }
};
