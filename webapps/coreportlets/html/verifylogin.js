<script language="JavaScript">

// generic check to see if a text input is null
function isFileValid( form ) {
    var fname = form.filename.value;
    if ((fname == null) || (fname == "")) {
	alert("Please enter a valid filename");
        return false;
    }
    return true;
}

// client side validation to insure name and password have been entered
function isLoginValid( form ) {
    var header = "Please provide your ";
    var namemsg = " username ";
    var passmsg = " password ";
    var err = 0;	
    var msg = "";
   
    // Check name
    var name = form.username.value;
    if ((name == null) || (name == "")) {
	msg += header + namemsg;
	err = 1;
    } 
    var pass = form.password.value;
    if ((pass == null) || (pass == "")) {
	if (err == 1) {
	    msg += " and " + passmsg + ".\n";
	} else {
	    msg += header + passmsg;
	}
    } else if (pass.length < 6) {
	msg += "The passphrase entered is too short" + "\n";
    }

    if (msg != "") {
	alert(header + msg);
	return false;
    }  
    return true;
}

</script>
