<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>ListUsers</title>
</head>
<style>
    form {
        display: flex;
    }

    input {
        margin: 5px;
    }

    .Users {
        display: flex;
        flex-direction: column;
        width: 80%;
        margin: 0 auto;
        align-items: center;
        justify-content: center;
    }

    .row {
        display: flex;
        justify-content: space-between;
        align-items: center;
        width: 100%;
        height: 70px;
    }

    .col {
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        width: 20%;
        height: 60px;
        border: 1px black solid;
    }

    .col button {
        width: 70%;
        margin: 3px;
    }

    #modal {
        position: absolute;
        width: 100%;
        height: 100%;
        opacity: .5;
        top: 0;
        left: 0;
        background: rgba(0, 0, 0, 0);
        pointer-events: none;
        transition: 0.5s;
    }

    #modal.on {
        background: rgba(0, 0, 0, 0.5);
        transition: 0.5s;
        pointer-events: auto;
    }

    #close {
        position: absolute;
        z-index: 3;
        top: -100%;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        width: 30%;
        background: white;
        border: 1px solid;
        box-shadow: 8px 7px rgba(0, 0, 0, 0.4);
        height: 40%;
        transition: 0.5s;
        margin: 0 auto;
        left: 0;
        right: 0;
    }

    .col form {
        width: 100%;
        display: flex;
        justify-content: center;
        margin-bottom: 0px;
        align-items: center;
    }

    #close.on {
        top: 20%;
        transition: 0.5s;

    }

    #id {
        opacity: 0;
    }

    #close form {
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        background: #fff;
    }

    #invisibleForm {
        width: 0;
        height: 0;
        opacity: 0;
        pointer-events: none;
    }

    #close form .inp {
        width: 50%;
        margin: 15px;
    }

    .combobox {
        margin: 4px 7px;

    }
</style>
<script>
    var ar;

    function editFunction(val) {
        ar = val.value;

        var el = document.getElementById("modal");
        var el2 = document.getElementsByClassName(ar)[0];
        el.classList.toggle("on");
        el2.classList.toggle("on");

    }

    function modalOff() {
        var el = document.getElementById("modal");
        var el2 = document.getElementsByClassName(ar)[0];
        el.classList.toggle("on");
        el2.classList.toggle("on");
    }

    function f(e) {
        var a = e.id;
        var f = document.getElementById('id');
        f.setAttribute('value', a);

    }

</script>
<body>
<form action="addUser" method="post">
    <input type="text" name="name" placeholder="Name">
    <input type="text" name="lastName" placeholder="Second Name">
    <input type="text" name="password" placeholder="Password">
    <select name="combobox" class="combobox">
        <option name="user">User</option>
        <option name="admin">Admin</option>
    </select>
    <button type="submit" name="Add">Add</button>
</form>
<div class="Users">
    <div class="row">
        <div class="col">id</div>
        <div class="col">name</div>
        <div class="col">lastName</div>
        <div class="col">password</div>
        <div class="col">role</div>
        <div class="col">
            buttons
        </div>
    </div>

    <c:forEach var="user" items="${users}">
        <div class="row">
            <div class="col"><c:out value="${user.id}"/></div>
            <div class="col"><c:out value="${user.name}"/></div>
            <div class="col"><c:out value="${user.lastName}"/></div>
            <div class="col"><c:out value="${user.password}"/></div>
            <div class="col"><c:out value="${user.role}"/></div>
            <div class="col">
                <button id=${user.id} name="edit" value="${user.id}" onclick="editFunction(this); javascript:f(this);">
                    Edit
                </button>
                <form action="deleteUser" method="post">
                    <button name="Delete" type="submit" value=${user.id}>Delete</button>
                </form>
            </div>
            <div id="close" class="${user.id}">
                <form action="editUser" method="post">
                    <input type="text" name="id" value="${user.id}" id="id">
                    <input type="text" placeholder="New name" class="inp" name="name" value="${user.name}">
                    <input type="text" placeholder="New lastName" class="inp" name="lastName" value="${user.lastName}">
                    <input type="text" placeholder="New password" class="inp" name="password" value="${user.password}">
                    <select name="combobox" class="combobox inp">
                        <option name="user">User</option>
                        <option name="admin">Admin</option>
                    </select>
                    <button type="submit" onclick="modalOff()" class="inp" name="Edit">Edit</button>
                </form>
            </div>
        </div>

    </c:forEach>


    <div id="modal" onclick="modalOff()">

    </div>

</div>
</body>
</html>