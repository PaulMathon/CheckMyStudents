function checkIfBothPasswordAreEquals() {
    var table = document.getElementsByClassName("newpassword");
    var tableValues = new Array();

    for (var i = 0; i < table.length; i++) {
        var valeur = table[i].value;
        tableValues.push(valeur);
    };

    var textAlert = ""
    if (!(tableValues[0] == "") && !(tableValues[1] == "")) {
        if (!(tableValues[0] == tableValues[1])) {
            textAlert = textAlert + " Les mots de passes ne sont pas identiques \n";
        }

        if (tableValues[0] == tableValues[1]) {
            textAlert = "Votre mot de passe a été modifié";
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
};

window.onload = function () {

    let changePassword=document.getElementById("changePassword");
    changePassword.onsubmit=function(){
        return checkIfBothPasswordAreEquals();
    };
    let changePicture = document.getElementById("changePicture");
    let picture = document.getElementById("picture");
    changePicture.hidden = true;
    picture.onmouseover = function () {
        changePicture.hidden = false;
        picture.hidden = true;
    };
    changePicture.onmouseout = function () {
        changePicture.hidden = true;
        picture.hidden = false;
    }
};

