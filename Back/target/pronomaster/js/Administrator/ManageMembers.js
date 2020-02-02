window.onload = function () {
    listMembers();
    if (document.getElementById("attribut").value==""){
        document.getElementById("instantSearch").hidden=true;
    }
    document.getElementById("attribut").onkeyup = function(){
        //fonction de recherche
        let instantSearchParameters = document.getElementById("attribut").value ;
        if (instantSearchParameters==""){
            document.getElementById("instantSearch").hidden=true;
            listMembers();
        }else{
            document.getElementById("instantSearch").hidden=false;
            instantSearch(instantSearchParameters);
            //modifySearchBar();
        }
    };
    document.getElementById("search").onclick = function(){
        document.getElementById("instantSearch").hidden=true;
        let searchParameter = document.getElementById("attribut").value ;
        if (searchParameter==""){
            listMembers();
        }else{
            searchUser(searchParameter);
        }
    };
    document.getElementById("gerermembres").onclick = function (){
        document.getElementById("instantSearch").hidden=true;
    };

    document.getElementById("cJoueur").onclick = function(){
        searchUserWithRole(0);
    };
    document.getElementById("cAdmin").onclick = function(){
        searchUserWithRole(1);
    };
    document.getElementById("cSuperAdmin").onclick = function(){
        searchUserWithRole(2);
    };
};


let deleteUser = function (userName) {

    if (confirm("Etes vous sur de vouloir supprimer " + userName + "?")) {
        let deleteRequest = new XMLHttpRequest();
        deleteRequest.open("DELETE", "/ws/members/" + userName, true);

        deleteRequest.onload = function () {
            listMembers();
        };
        deleteRequest.send();
    }
};

let changeRole = function(userName){
    let changeRoleRequest = new XMLHttpRequest();
    let url="/ws/members/"+userName;
    changeRoleRequest.open("PATCH", url, true);
    changeRoleRequest.onload = function () {
        listMembers();
    };
    changeRoleRequest.send();
};

let listMembers = function () {
    let membersRequest = new XMLHttpRequest();
    let url = "/ws/members";
    membersRequest.open("GET", url, true);
    membersRequest.responseType = "json";

    membersRequest.onload = function () {
        let members = this.response;
        refreshMembersList(members);
    };

    membersRequest.send();
};

let searchUser = function(keyboard){
    let membersRequest = new XMLHttpRequest();
    let url = "/ws/members/search/name/"+keyboard;
    console.log(url);
    membersRequest.open("GET", url, true);
    membersRequest.responseType = "json";

    membersRequest.onload = function () {
        let members = this.response;
        refreshMembersList(members);
    };
    membersRequest.send();
};

let instantSearch = function(instantParameter){
    let membersRequest = new XMLHttpRequest();
    let url = "/ws/members/search/name/"+instantParameter;
    console.log(url);
    membersRequest.open("GET", url, true);
    membersRequest.responseType = "json";

    membersRequest.onload = function () {
        let members = this.response;
        refreshInstantSearchList(members);
    };
    membersRequest.send();
};

/*let modifySearchBar = function(){
    document.getElementById("attribut"). = 'none';
};*/

let searchUserWithRole = function(role){
    let membersRequest = new XMLHttpRequest();
    let url = "/ws/members/search/role/"+role;
    console.log(url);
    membersRequest.open("GET", url, true);
    membersRequest.responseType = "json";

    membersRequest.onload = function () {
        let members = this.response;
        refreshMembersList(members);
    };
    membersRequest.send();
};
