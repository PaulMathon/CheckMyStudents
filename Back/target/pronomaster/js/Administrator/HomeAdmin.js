window.onload = function () {
    let wording = document.getElementById("userName").innerText;
    let wordingTable = wording.split(" ");
    let userName = wordingTable[2];
    getUserRole(userName);
}

var getUserRole = function (userName) {
    let roleRequest = new XMLHttpRequest();
    roleRequest.open("GET", "/ws/members/"+userName, true);
    roleRequest.responseType = "json";
    roleRequest.onload = function () {
        let user = this.response;
        if (user.admin==1){
            document.getElementById("members").hidden = true;
            document.getElementById("membersDesc").hidden = true;
            console.log(document.getElementById("membersDesc").hidden);
        }
    };
    roleRequest.send();
};