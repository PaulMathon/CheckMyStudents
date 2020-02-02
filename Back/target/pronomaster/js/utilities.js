var refreshMembersList = function(members){
    let oldMembersListElement = document.getElementById("membres");
    let newMembersListElement = oldMembersListElement.cloneNode(false);
    for (const member of members) {
        newMembersListElement.appendChild(createMemberCase(member));
    }
    oldMembersListElement.parentNode.replaceChild(newMembersListElement, oldMembersListElement);
};

var createMemberCase = function (member) {
    //div principale
    let divElement = document.createElement("div");
    if (member.admin==0){
        divElement.setAttribute("class","player");
    }else if (member.admin==1){
        divElement.setAttribute("class","admin");
    }else {
        divElement.setAttribute("class","superAdmin");
    }
    //image profil
    let figureElement = document.createElement("figure");
    let imageElement = document.createElement("img");
    imageElement.setAttribute("src","../../../images/absence-photo.png");
    imageElement.setAttribute("alt","logo HTML 5");
    figureElement.appendChild(imageElement);
    figureElement.onclick = function(){
        figureElement.hidden=true;
        let scoreElement = document.createElement("article");
        let titleScoreElement = document.createElement("h2");
        titleScoreElement.innerText = "Score";
        scoreElement.appendChild(titleScoreElement);
        let scoreNumberElement = document.createElement("p");
        scoreNumberElement.innerText = member.score;
        scoreElement.appendChild(scoreNumberElement);
        scoreElement.onclick = function(){
            scoreElement.hidden = true;
            figureElement.hidden=false;
        };
        divElement.appendChild(scoreElement);
    };
    divElement.appendChild(figureElement);

    //liste détail joueur
    let ulElement = document.createElement("ul");
    ulElement.setAttribute("style","list-style-type:none");
    //nom de l'utilisateur
    let nomElement = document.createElement("li");
    nomElement.setAttribute("class","name");
    nomElement.innerText=member.name;
    ulElement.appendChild(nomElement);

    //Statu de l'utilisateur
    let statuElement = document.createElement("li");
    statuElement.setAttribute("class","statu");
    if (member.admin==0){
        statuElement.innerText="Joueur";
    }else if (member.admin==1){
        statuElement.innerText="Administrateur";
    }else {
        statuElement.innerText="Super utilisateur";
    }
    ulElement.appendChild(statuElement);

    //Action de gestion des utilisateur
    let gestionElement = document.createElement("li");
    let formElement = document.createElement("div");

    //boutton changerStatu
    let changeStatuElement = document.createElement("button");
    changeStatuElement.setAttribute("type","submit");
    changeStatuElement.setAttribute("name","changerStatu");
    changeStatuElement.setAttribute("value",member.name);
    if (member.admin==0){
        changeStatuElement.innerText="Nommer Admin";
    }else if (member.admin==1){
        changeStatuElement.innerText="Nommer Joueur";
    }
    changeStatuElement.onclick = function(){
        changeRole(member.name);
    };
    formElement.appendChild(changeStatuElement);

    //boutton banir
    let banirElement = document.createElement("button");
    banirElement.setAttribute("type","submit");
    banirElement.setAttribute("name","banir");
    banirElement.setAttribute("value",member.name);
    banirElement.setAttribute("id","banir "+member.name);
    banirElement.innerText="banir";
    banirElement.onclick = function(){
        deleteUser(member.name);
    };
    formElement.appendChild(banirElement);

    gestionElement.appendChild(formElement);
    ulElement.appendChild(gestionElement);
    divElement.appendChild(ulElement);
    return divElement;
};


var refreshInstantSearchList = function(members){
    let oldInstantSearchElement = document.getElementById("instantSearch");
    let newInstantSearchElement = oldInstantSearchElement.cloneNode(false);
    for (const member of members) {
        newInstantSearchElement.appendChild(createInstantSearchLine(member));
    }
    oldInstantSearchElement.parentNode.replaceChild(newInstantSearchElement, oldInstantSearchElement);
};

var createInstantSearchLine = function(member){
    let lineElement = document.createElement("li");
    lineElement.innerText=member.name;
    lineElement.setAttribute("class","instantSearchLine");
    lineElement.onclick = function(){
        document.getElementById("attribut").value = member.name;
    }
    return lineElement;
};


