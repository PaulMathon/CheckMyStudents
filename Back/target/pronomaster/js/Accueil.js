function checkIfFormIsCompletedCorrectly() {
    var table = document.getElementsByClassName("newchamp");
    var tableValues = new Array();

    for (var i = 0; i < table.length; i++) {
        var valeur = table[i].value;
        tableValues.push(valeur);
    }
    ;

    var textAlert = ""
    if (!(tableValues[0] == "") && !(tableValues[1] == "") && !(tableValues[2] == "") && !(tableValues[3] == "") && !(tableValues[4] == "")) {
        if (!(tableValues[1] == tableValues[2])) {
            textAlert = textAlert + " Les mots de passes ne sont pas identiques \n";

        }

        if (!(tableValues[3] == tableValues[4])) {
            textAlert = textAlert + " Les e-mails ne sont pas identiques \n";
        }

        if (tableValues[1] == tableValues[2] && tableValues[3] == tableValues[4]) {
            textAlert = "Vous etes desormais inscrit";
            window.alert(textAlert);
            return true
        }

        window.alert(textAlert);
        return false;
    } else {
        window.alert("Un champ n'a pas été rempli correctement");
        return false;
    }
    return true;
}
window.onload = function () {

    var formInscription=document.getElementById("formulaireInscription");
    formInscription.onsubmit=function(){
       return checkIfFormIsCompletedCorrectly();
    };
};
