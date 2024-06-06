

async function getCsrfToken() {
    const response = await fetch('http://localhost:8080/api/csrf-token', {
        method: 'GET',
    });
    const token = await response.json();

    console.log('token.token', token.token);
    return token.token;
}


async function getNotes() {

    const csrfToken = await getCsrfToken();
    const response = await fetch('http://localhost:8080/api/notes', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken,
            'Authorization': '123123231'
        },
    });
    const notes = await response.json();
    console.log('notes', notes);
    return notes;
}

async function sendPostRequest(data) {
    const csrfToken = await getCsrfToken();
    
    const response = await fetch('http://localhost:8080/api/notes', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(data),
    });

    if (response.ok) {
        const result = await response.json();
        console.log('Success:', result);
    } else {
        console.error('Error:', response.statusText);
    }
}


async function handleSubmit(event) {
    event.preventDefault();

    const formData = {
        title: document.getElementById('title').value,
        content: document.getElementById('content').value
    };

    await sendPostRequest(formData);
}

window.onload = () => {
    getNotes();
}