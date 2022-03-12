let userInfo = $('#tableAllUsers')
let getAllUser = []
const allRoles = ['USER', 'ADMIN']

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
    let innerDepth = ''
    userInfo.append(
        '<tr>' +
        '<td>' + user.id + '</td>' +
        '<td>' + user.name + '</td>' +
        '<td>' + user.lastName + '</td>' +
        '<td>' + user.age + '</td>' +
        //'<td>' + user.roles.map(roleUser => roleUser.role) + '</td>' +
        '<td>' + user.roles.forEach(role => {
            innerDepth += (role.role === 'ROLE_USER' ? 'USER' : 'ADMIN')}) + '</td>' +
        '<td>' +
        '<button onclick="editUserById(' + user.id + ')" class="btn btn-info edit-btn" data-toggle="modal" data-target="#edit"' +
        '>Edit</button></td>' +
        '<td>' +
        '<button onclick="deleteUserById(' + user.id + ')" class="btn btn-danger" data-toggle="modal" data-target="#delete"' +
        '>Delete</button></td>' +
        '</tr>'
    )
}

function createNewProfile() {
    let name = document.getElementById('newName').value;
    let lastName = document.getElementById('newLastname').value;
    let age = document.getElementById('newAge').value;
    let password = document.getElementById('newPassword').value;

    fetch("api/users", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json; charset=UTF-8',
            'Accept': 'application/json'
        },
        body: JSON.stringify({
            name: name,
            lastName: lastName,
            age: age,
            password: password
        })
    })
        .then(() => {
            getUsers();
            document.getElementById("newUserForm").reset();
        })
}


function deleteUserById(id) {
    fetch("api/users/" + id, {
        method: "GET",
        dataType: 'json'})
        .then(res => {
            res.json().then(user =>{
                $("#deleteId").val(user.id);
                $("#deleteName").val(user.name);
                $("#deleteLastname").val(user.lastName);
                $("#deleteAge").val(user.age);
                $("#deleteRole").val(user.role);
            })
        })
}

function deleteItem() {
    fetch("api/users/" + ($("#deleteId").val()), {
        method: "DELETE",
    })
        //.then(response => response.json())
        .then(() => {
            location.reload();
            closeForm();
            userInfo().empty();
            getUsers();
        })


}

// function _displayCount(itemCount) {
//     const name = (itemCount === 1) ? 'to-do' : 'to-dos';
//
//     document.getElementById('counter').innerText = `${itemCount} ${name}`;
// }



function closeForm() {
    $("#edit .close").click();
    document.getElementById("editUserForm").reset();
    $("#delete .close").click();
    document.getElementById("deleteUserForm").reset();
    $('#deleteRole > option').remove();
}



async function showAllEditRoles() {
    const allRoles = await getRoles();
    allRoles.forEach(items => {
        let role = items.role;
        let selectElem = document.getElementById('editRoles');
        let element = document.createElement('option');
        element.innerText = role;
        selectElem.append(element);
    })}
// /*
// в модалке айди выставляешь соответственно. потом просто вызываешь функцию, она подтянет роли
//  */

function editUserById(id) {
    fetch("api/users/" + id, {
        method: "GET",
        dataType: 'json'})
        .then(res => {
            res.json().then(user =>{
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
            password: password
        })
    })
        .then(() => {
            userInfo.empty();
            getUsers();
            closeForm();
        })
}
