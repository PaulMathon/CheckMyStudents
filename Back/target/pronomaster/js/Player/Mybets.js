window.onload = function(){
    let userName = document.getElementById("userName").innerText;
    console.log(userName);
    listUserBets(userName);
};

let deleteBet = function(userName,idBet){
    let deleteBetRequest = new XMLHttpRequest();
    let url = "/ws/bets/"+userName+"/"+idBet;
    deleteBetRequest.open("DELETE", url, true);
    console.log(url);
    deleteBetRequest.onload = function () {
        listUserBets(userName);
    };
    deleteBetRequest.send();
};

let listUserBets = function(userName){
    let membersRequest = new XMLHttpRequest();
    let url = "/ws/bets/"+userName;
    membersRequest.open("GET", url, true);
    membersRequest.responseType = "json";

    membersRequest.onload = function () {
        let bets = this.response;
        console.log(bets);
        if (bets.length == 0){
            document.getElementById("noBetMessage").hidden = false;
            document.getElementById("tableauParis").hidden = true;
        }else{
            document.getElementById("noBetMessage").hidden = true;
            document.getElementById("tableauParis").hidden = false;
            refreshBetsList(userName,bets);
        }
    };

    membersRequest.send();
};

let refreshBetsList = function(userName,bets){
    let oldBetsListElement = document.getElementById("betsContents");
    let newBetslistElement = oldBetsListElement.cloneNode(false);
    for (const bet of bets) {
        newBetslistElement.appendChild(createBetLine(userName,bet));
    }
    oldBetsListElement.parentNode.replaceChild(newBetslistElement, oldBetsListElement);
};

let createBetLine = function(userName,bet){
    let betLineElement = document.createElement("tr");
    //Nom de l'équipeIn
    let teamInElement = document.createElement("td");
    teamInElement.innerText = bet.match.teamIn.name;
    betLineElement.appendChild(teamInElement);

    //But de l'équipeIn
    let butInElement = document.createElement("td");
    butInElement.innerText = bet.butIn;
    butInElement.setAttribute("class","result");
    betLineElement.appendChild(butInElement);

    //But de l'équipeOut
    let butOutElement = document.createElement("td");
    butOutElement.innerText = bet.butOut;
    butOutElement.setAttribute("class","result");
    betLineElement.appendChild(butOutElement);

    //Nom de l'équipe Out
    let teamOutElement = document.createElement("td");
    teamOutElement.innerText = bet.match.teamOut.name;
    betLineElement.appendChild(teamOutElement);

    //Date
    let dateElement = document.createElement("td");
    dateElement.innerText = bet.match.date.year+"-"+bet.match.date.monthValue+"-"+bet.match.date.dayOfMonth;
    betLineElement.appendChild(dateElement);

    //Boutton supprimer
    let deleteCaseElement = document.createElement("td");
    let deleteButtonElement = document.createElement("button");
    deleteButtonElement.innerText = "Annuler";
    deleteButtonElement.onclick = function(){
        if (confirm("Êtes-vous sur de vouloir annuler ce paris?")){
            deleteBet(userName,bet.idPari);
        }
    };
    deleteCaseElement.appendChild(deleteButtonElement);
    betLineElement.appendChild(deleteCaseElement);

    return betLineElement;
};

/*<tr th:each="pari : ${parislist}" align="center" class="myBets">
    <td th:text="${pari.match.getTeamIn().getName()}">[[match.team1]]</td>
    <td th:text="${pari.butIn}" class= "result">[[match.team1Score]]</td>
    <td th:text="${pari.butOut}" class= "result">[[match.team2Score]]</td >
    <td th:text="${pari.match.getTeamOut().getName()}">[[match.team2]]</td>
    <td th:text="${pari.match.date}"></td>
</tr >*/