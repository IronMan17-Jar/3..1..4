let userInfo = $('#tableUsers')
let getAllUser = []
const xhr = new XMLHttpRequest();

getUsers()

function getUsers() {
    fetch("/api/users").then((response) => {
        response.json().then((users) => {
            users.forEach((user) => {
                addUserForTable(user)
                getAllUser.push(user)
            });
        });
    });
}

function addUserForTable(user) {
    userInfo.append(
        '<tr>' +
        '<td>' + user.id + '</td>' +
        '<td>' + user.name + '</td>' +
        '<td>' + user.lastName + '</td>' +
        '<td>' + user.age + '</td>' +
        '<td>' + user.roles.map(roleUser => roleUser.role) + '</td>' +
        '<td>' +
        '<button onclick="editUserById(' + user.id + ')" class="btn btn-info edit-btn" data-toggle="modal" data-target="#edit"' +
        '>Edit</button></td>' +
        '<td>' +
        '<button onclick="deleteUserById(' + user.id + ')" class="btn btn-danger" data-toggle="modal" data-target="#delete"' +
        '>Delete</button></td>' +
        '</tr>'
    )
}

const addUserForm = document.querySelector('.add-user-form')
addUserForm.addEventListener('submit', (e) => {
    e.preventDefault();

    fetch("api/users", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json; charset=UTF-8',
            'Accept': 'application/json'
        },
        body: JSON.stringify({
            name: document.getElementById('newName').value,
            lastName: document.getElementById('newLastname').value,
            age: document.getElementById('newAge').value,
            password: document.getElementById('newPassword').value,
            roles: getRol(Array.from(document.getElementById('newRole').selectedOptions).map(role => role.value))
        })
    })
        .then(() => {
            document.getElementById('newName').value = ''
            document.getElementById('newLastname').value = ''
            document.getElementById('newAge').value = ''
            document.getElementById('newPassword').value = ''
            document.getElementById('newRole').value = ''
            getUsers();
            document.getElementById("newUserForm").reset();
            document.location.replace('/admin')
        })
})


function deleteUserById(id) {

    fetch("api/users/" + id, {
        method: "GET",
        dataType: 'json'
    })
        .then(res => {
            res.json().then(user => {
                $("#deleteId").val(user.id);
                $("#deleteName").val(user.name);
                $("#deleteLastname").val(user.lastName);
                $("#deleteAge").val(user.age);
                user.roles.map(role => {
                    $("#deleteRole").append('<option>' + role.role + '</option>')
                })
            })
        })
}

function deleteItem() {
    fetch("api/users/" + ($("#deleteId").val()), {
        method: "DELETE",
    })
        .then(() => {
            location.reload();
            closeForm();
            userInfo().empty();
            getUsers();
        })
}

function closeForm() {
    $("#edit .close").click();
    document.getElementById("editUserForm").reset();
    $("#delete .close").click();
    document.getElementById("deleteUserForm").reset();
    $('#deleteRole > option').remove();
}

function editUserById(id) {
    fetch("api/users/" + id, {
        method: "GET",
        dataType: 'json'
    })
        .then(res => {
            res.json().then(user => {
                $("#editId").val(user.id);
                $("#editName").val(user.name);
                $("#editLastname").val(user.lastName);
                $("#editAge").val(user.age);
                $("#editPassword").val(user.password);
            })
        })
}

function updateItem() {
    let id = document.getElementById('editId').value;
    let name = document.getElementById('editName').value;
    let lastName = document.getElementById('editLastname').value;
    let age = document.getElementById('editAge').value;
    let password = document.getElementById('editPassword').value;
    let roles = getRol(Array.from(document.getElementById('editRole').selectedOptions).map(role => role.value));

    fetch("api/users/" + id, {
        method: "PATCH",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify({
            id: id,
            name: name,
            lastName: lastName,
            age: age,
            password: password,
            roles: roles
        })
    })
        .then(() => {
            userInfo.empty();
            getUsers();
            closeForm();
        })
}

function getRol(list) {
    let roles = [];
    if (list.indexOf("ADMIN") >= 0) {
        roles.push({"id": 1});
    }
    if (list.indexOf("USER") >= 0) {
        roles.push({"id": 2});
    }
    return roles;
}

