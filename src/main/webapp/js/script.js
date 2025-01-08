document.addEventListener('DOMContentLoaded', function() {
    loadPackages();
    setupFormHandler();
});

function loadPackages() {
    fetch('/api/packages')
        .then(response => response.json())
        .then(packages => {
            const packageList = document.getElementById('package-list');
            packageList.innerHTML = '';
            
            packages.forEach(package => {
                const card = createPackageCard(package);
                packageList.appendChild(card);
            });
        })
        .catch(error => {
            console.error('Error loading packages:', error);
            showError('Failed to load travel packages');
        });
}

function createPackageCard(package) {
    const card = document.createElement('div');
    card.className = 'package-card';
    
    const startDate = new Date(package.startDate).toLocaleDateString();
    const endDate = new Date(package.endDate).toLocaleDateString();
    
    card.innerHTML = `
        <h3>${package.packageName}</h3>
        <p><strong>Destination:</strong> ${package.destination}</p>
        <p><strong>Price:</strong> $${package.price}</p>
        <p><strong>Dates:</strong> ${startDate} - ${endDate}</p>
        <p>${package.description}</p>
        <p><strong>Available Spots:</strong> ${package.maxCapacity}</p>
        <div class="card-actions">
            <button onclick="editPackage(${package.id})">Edit</button>
            <button onclick="deletePackage(${package.id})">Delete</button>
        </div>
    `;
    
    return card;
}

function setupFormHandler() {
    const form = document.getElementById('package-form');
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const formData = {
            packageName: form.packageName.value,
            destination: form.destination.value,
            price: parseFloat(form.price.value),
            startDate: form.startDate.value,
            endDate: form.endDate.value,
            description: form.description.value,
            maxCapacity: parseInt(form.maxCapacity.value)
        };
        
        fetch('/api/packages', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to add package');
            }
            return response.json();
        })
        .then(() => {
            form.reset();
            loadPackages();
            showSuccess('Package added successfully');
        })
        .catch(error => {
            console.error('Error adding package:', error);
            showError('Failed to add package');
        });
    });
}

function editPackage(id) {
    // Implementation for editing a package
    fetch(`/api/packages/${id}`)
        .then(response => response.json())
        .then(package => {
            // Fill the form with package data
            const form = document.getElementById('package-form');
            form.packageName.value = package.packageName;
            form.destination.value = package.destination;
            form.price.value = package.price;
            form.startDate.value = package.startDate.split('T')[0];
            form.endDate.value = package.endDate.split('T')[0];
            form.description.value = package.description;
            form.maxCapacity.value = package.maxCapacity;
            
            // Change form submission to update instead of create
            form.dataset.editId = id;
        })
        .catch(error => {
            console.error('Error loading package details:', error);
            showError('Failed to load package details');
        });
}

function deletePackage(id) {
    if (confirm('Are you sure you want to delete this package?')) {
        fetch(`/api/packages/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to delete package');
            }
            loadPackages();
            showSuccess('Package deleted successfully');
        })
        .catch(error => {
            console.error('Error deleting package:', error);
            showError('Failed to delete package');
        });
    }
}

function showSuccess(message) {
    alert(message); // Replace with a better notification system
}

function showError(message) {
    alert('Error: ' + message); // Replace with a better notification system
}
