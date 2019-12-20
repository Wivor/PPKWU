async function find() {
    login = document.getElementById("login").value
    password = document.getElementById("password").value
    search = document.getElementById("search").value

    var url = "http://localhost:8080/vcard/" + search + "?login=" + login + "&password=" + password;

    var response = await fetch(encodeURI(url)).then((res) => res.text());

    console.log(response);

    document.getElementById("users").innerHTML = response;

}