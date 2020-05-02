<%--
  Created by IntelliJ IDEA.
  User: 100nout.by
  Date: 28.04.2020
  Time: 3:34
  To change this template use File | Settings | File Templates.
--%>
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
        top: -100%;
        display: flex;
        justify-content: center;
        align-items: center;
        width: 30%;
        height: 30%;
        transition: 0.5s;
        margin: 0 auto;
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
        margin: 15px;
    }
</style>
<script>
    function editFunction() {
        var el = document.getElementById("modal");
        var el2 = document.getElementById("close");
        el.classList.toggle("on");
        el2.classList.toggle("on");
    }

    function modalOff() {
        var el = document.getElementById("modal");
        var el2 = document.getElementById("close");
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
    <input type="number" name="age" placeholder="Age">
    <button type="submit" name="Add">Add</button>
</form>
<div class="Users">
    <div class="row">
        <div class="col">id</div>
        <div class="col">name</div>
        <div class="col">lastName</div>
        <div class="col">age</div>
        <div class="col">
            buttons
        </div>
    </div>

    <c:forEach var="user" items="${users}">
        <div class="row">
            <div class="col"><c:out value="${user.id}"/></div>
            <div class="col"><c:out value="${user.name}"/></div>
            <div class="col"><c:out value="${user.lastName}"/></div>
            <div class="col"><c:out value="${user.age}"/></div>
            <div class="col">
                <button id=${user.id} name=${user.id} onclick="editFunction(); javascript:f(this);">Edit</button>
                <form action="deleteUser" method="post">
                    <button name="Delete" type="submit" value=${user.id}>Delete</button>
                </form>
            </div>
        </div>
    </c:forEach>


    <div id="modal" onclick="modalOff()">

    </div>
    <div id="close">
        <form action="editUser" method="get">
            <input type="text" name="id" value="" id="id">
            <input type="text" placeholder="New name" class="inp" name="name">
            <input type="text" placeholder="New lastName" class="inp" name="lastName">
            <input type="number" placeholder="New age" class="inp" name="age">
            <button type="submit" onclick="modalOff()" class="inp" name="Edit">Edit</button>
        </form>
    </div>
</div>
</body>
</html>