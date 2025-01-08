<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Packages - Travel Management</title>
    <link rel="stylesheet" href="../css/style.css">
    <link rel="stylesheet" href="../css/admin.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="admin-container">
        <!-- Include Sidebar -->
        <div class="admin-sidebar">
            <!-- Same sidebar as dashboard.jsp -->
        </div>

        <div class="admin-content">
            <div class="admin-header">
                <h2>Manage Packages</h2>
                <button class="btn btn-primary" onclick="showAddPackageModal()">
                    <i class="fas fa-plus"></i> Add New Package
                </button>
            </div>

            <div class="package-filters mb-4">
                <div class="row">
                    <div class="col-md-3">
                        <select class="form-control" id="destinationFilter">
                            <option value="">All Destinations</option>
                            <c:forEach items="${destinations}" var="dest">
                                <option value="${dest}">${dest}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <select class="form-control" id="priceFilter">
                            <option value="">Price Range</option>
                            <option value="0-500">$0 - $500</option>
                            <option value="501-1000">$501 - $1000</option>
                            <option value="1001+">$1001+</option>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <input type="date" class="form-control" id="dateFilter" placeholder="Travel Date">
                    </div>
                    <div class="col-md-3">
                        <button class="btn btn-secondary" onclick="applyFilters()">
                            Apply Filters
                        </button>
                    </div>
                </div>
            </div>

            <div class="package-list">
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Package Name</th>
                            <th>Destination</th>
                            <th>Price</th>
                            <th>Duration</th>
                            <th>Capacity</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${packages}" var="pkg">
                            <tr>
                                <td>${pkg.id}</td>
                                <td>${pkg.packageName}</td>
                                <td>${pkg.destination}</td>
                                <td>$${pkg.price}</td>
                                <td>${pkg.duration} days</td>
                                <td>${pkg.maxCapacity}</td>
                                <td>
                                    <span class="status-badge ${pkg.active ? 'active' : 'inactive'}">
                                        ${pkg.active ? 'Active' : 'Inactive'}
                                    </span>
                                </td>
                                <td>
                                    <button class="btn btn-sm btn-info" onclick="editPackage(${pkg.id})">
                                        <i class="fas fa-edit"></i>
                                    </button>
                                    <button class="btn btn-sm btn-danger" onclick="deletePackage(${pkg.id})">
                                        <i class="fas fa-trash"></i>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Add/Edit Package Modal -->
    <div class="modal fade" id="packageModal" tabindex="-1">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="modalTitle">Add New Package</h5>
                    <button type="button" class="close" data-dismiss="modal">
                        <span>&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="packageForm" enctype="multipart/form-data">
                        <input type="hidden" id="packageId" name="id">
                        
                        <div class="form-group">
                            <label>Package Name</label>
                            <input type="text" class="form-control" name="packageName" required>
                        </div>
                        
                        <div class="form-group">
                            <label>Destination</label>
                            <input type="text" class="form-control" name="destination" required>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Price</label>
                                    <input type="number" class="form-control" name="price" required>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Max Capacity</label>
                                    <input type="number" class="form-control" name="maxCapacity" required>
                                </div>
                            </div>
                        </div>
                        
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>Start Date</label>
                                    <input type="date" class="form-control" name="startDate" required>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label>End Date</label>
                                    <input type="date" class="form-control" name="endDate" required>
                                </div>
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label>Description</label>
                            <textarea class="form-control" name="description" rows="4" required></textarea>
                        </div>
                        
                        <div class="form-group">
                            <label>Package Images</label>
                            <input type="file" class="form-control" name="images" multiple accept="image/*">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="savePackage()">Save Package</button>
                </div>
            </div>
        </div>
    </div>

    <script>
    function showAddPackageModal() {
        document.getElementById('modalTitle').textContent = 'Add New Package';
        document.getElementById('packageForm').reset();
        $('#packageModal').modal('show');
    }

    function editPackage(id) {
        document.getElementById('modalTitle').textContent = 'Edit Package';
        // Fetch package details and populate form
        fetch(`/admin/package/${id}`)
            .then(response => response.json())
            .then(data => {
                const form = document.getElementById('packageForm');
                form.id.value = data.id;
                form.packageName.value = data.packageName;
                form.destination.value = data.destination;
                form.price.value = data.price;
                form.maxCapacity.value = data.maxCapacity;
                form.startDate.value = data.startDate;
                form.endDate.value = data.endDate;
                form.description.value = data.description;
                $('#packageModal').modal('show');
            });
    }

    function savePackage() {
        const form = document.getElementById('packageForm');
        const formData = new FormData(form);

        fetch('/admin/package/save', {
            method: 'POST',
            body: formData
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                $('#packageModal').modal('hide');
                location.reload();
            } else {
                alert('Error saving package: ' + data.message);
            }
        });
    }

    function deletePackage(id) {
        if (confirm('Are you sure you want to delete this package?')) {
            fetch(`/admin/package/delete/${id}`, {
                method: 'POST'
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    location.reload();
                } else {
                    alert('Error deleting package: ' + data.message);
                }
            });
        }
    }

    function applyFilters() {
        const destination = document.getElementById('destinationFilter').value;
        const price = document.getElementById('priceFilter').value;
        const date = document.getElementById('dateFilter').value;

        const params = new URLSearchParams();
        if (destination) params.append('destination', destination);
        if (price) params.append('price', price);
        if (date) params.append('date', date);

        window.location.href = `packages.jsp?${params.toString()}`;
    }
    </script>
</body>
</html>
