document.addEventListener('DOMContentLoaded', () => {
    const jwtToken = localStorage.getItem('jwtToken');
    const username = localStorage.getItem('username');
    const notesContainer = document.getElementById('notes-container');
    const modal = document.getElementById('note-modal');
    const modalTitle = document.getElementById('modal-title');
    const noteTitleInput = document.getElementById('note-title');
    const noteContentInput = document.getElementById('note-content');
    const saveNoteButton = document.getElementById('save-note');
    const closeModalButton = document.getElementById('close-modal');

    let editingNoteId = null;

    const fetchNotes = async () => {
        const response = await fetch(`http://localhost:8080/notes/get/${username}`, {
            headers: { Authorization: `Bearer ${jwtToken}` },
        });
        const notes = await response.json();
        notesContainer.innerHTML = notes.map(noteToHTML).join('');
    };

    const noteToHTML = (note) => `
        <div class="note">
            <h2>${note.title}</h2>
            <p>${note.note}</p>
            <small>created on: ${new Date(note.issuedDate).toLocaleDateString()}</small>
            <button onclick="openModal('edit', ${note.id}, '${note.title}', '${note.note}')">Edit</button>
            <button onclick="deleteNote(${note.id})">Delete</button>
        </div>
    `;

    fetchNotes();

    document.getElementById('search-notes').addEventListener('input', async (e) => {
        const key = e.target.value;
        const response = await fetch(`http://localhost:8080/notes/search/${username}/${key}`, {
            headers: { Authorization: `Bearer ${jwtToken}` },
        });
        const notes = await response.json();
        notesContainer.innerHTML = notes.map(noteToHTML).join('');
    });

    document.getElementById('create-note').addEventListener('click', () => {
        openModal('create');
    });

    // Ensure the openModal function is available globally
    window.openModal = (action, id = null, title = '', content = '') => {
        editingNoteId = action === 'edit' ? id : null;
        modalTitle.textContent = action === 'edit' ? 'Edit Note' : 'Create Note';
        noteTitleInput.value = title;
        noteContentInput.value = content;
        modal.style.display = 'flex';
    };

    const closeModal = () => {
        modal.style.display = 'none';
        noteTitleInput.value = '';
        noteContentInput.value = '';
    };

    saveNoteButton.addEventListener('click', async () => {
        const title = noteTitleInput.value.trim();
        const content = noteContentInput.value.trim();

        if (!title || !content) {
            alert('Both fields are required.');
            return;
        }

        if (editingNoteId) {
            // Edit note
            await fetch(`http://localhost:8080/notes/edit`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${jwtToken}`,
                },
                body: JSON.stringify({ id: editingNoteId, title, note: content, username }),
            });
        } else {
            // Create note
            await fetch(`http://localhost:8080/notes/create`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${jwtToken}`,
                },
                body: JSON.stringify({ title, note: content, username }),
            });
        }

        fetchNotes();
        closeModal();
    });

    closeModalButton.addEventListener('click', closeModal);

    window.deleteNote = async (id) => {
        const confirmDelete = confirm('Are you sure you want to delete this note?');
        if (confirmDelete) {
            await fetch(`http://localhost:8080/notes/${username}/${id}`, {
                method: 'DELETE',
                headers: { Authorization: `Bearer ${jwtToken}` },
            });
            fetchNotes();
        }
    };
});
