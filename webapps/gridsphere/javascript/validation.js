// ************************************
// A variety of validation routines
//
//  All routines return true or false
//
// checkDate -- checks if valid date
// checkDigit -- checks if character provided is a single digit
// checkNotEmpty  -- checks if empty
// checkEmail -- check if valid email
// checkNumber -- check if number
// checkNumberLessThan -- check if number is less than a provided number
// checkNumberGreaterThan -- check if number is greater than a provided number
// checkPhone -- checks if a 10 digit phone number
// checkCreditCardNumber -- checks if valid card number using Luhn's formula
// checkCreditCardType -- checks if the credit card type is valid based on the credit card number
//                         Master Card, Visa, American Express
// ************************************



/*
    Checks if the string is a valid date formatted as...
    mm dd yyyy, mm/dd/yyyy, mm.dd.yyyy, mm-dd-yyyy

    http://codylindley.com/blogstuff/js/validation/
*/
function checkDate(str){
	var re = /^(\d{1,2})[\s\.\/-](\d{1,2})[\s\.\/-](\d{4})$/
	if (!re.test(str)) return false;
	var result = str.match(re);
	var m = parseInt(result[1]);
	var d = parseInt(result[2]);
	var y = parseInt(result[3]);
	if(m < 1 || m > 12 || y < 1900 || y > 2100) return false;
	if(m == 2){
		var days = ((y % 4) == 0) ? 29 : 28;
	}else if(m == 4 || m == 6 || m == 9 || m == 11){
		var days = 30;
	}else{
		var days = 31;
	}
	return (d >= 1 && d <= days);
}

/*
 Check if character supplied is a digit
*/
function checkDigit(c) {
    if (c.length != 1) return false;
    var strAllowed = "1234567890";
    return (strAllowed.indexOf (c) != -1);
}

/*
 Check for empty string, return true if empty, false otherwise
*/
function checkNotEmpty(strng) {
    return (strng != "");
}

/*
 Email checking-- comes from Apple's example validation code
 http://developer.apple.com/internet/webcontent/examples/validate_source.html
*/
function checkEmail(strng) {
    if (strng == "") {
        return false;
    }
    // test email for an @ followed by characters and a .
    var emailFilter=/^.+@.+\..{2,3}$/;
    if (!(emailFilter.test(strng))) {
        return false;
    }
    else {
        //test email for illegal characters
        var illegalChars= /[\(\)\<\>\,\;\:\\\"\[\]]/
        if (strng.match(illegalChars)) {
            return false;
        }
    }
    return true;
}

/*
 Check if this is a number
 */
function checkNumber(num) {
    return (!(isNaN(parseInt(num))));
}

function checkNumberGreaterThan(num, compareNum) {
    if (!checkNumber(num)) return false;
    return (num > compareNum);
}

function checkNumberLessThan(num, compareNum) {
    if (!checkNumber(num)) return false;
    return (num < compareNum);
}

/*
 phone number - strip out delimiters and check for 10 digits -- also from Apple
*/
function checkPhone(strng) {
    if (strng == "") {
        return false;
    }
    // check that phone number doesn't contain illegal chars
    var stripped = strng.replace(/[\(\)\.\-\ ]/g, ''); //strip out acceptable non-numeric characters
    if (isNaN(parseInt(stripped))) {
        return false;
    }
    // check length of phone number
    if (!(stripped.length == 10)) {
        return false;
    }
    return true;
}

/*
 Check if this credit card number is valid
*/
function checkCreditCardNumber (strNum) {
    var nCheck = 0;
    var bEven = false;

    for (n = strNum.length - 1; n >= 0; n--) {
        var cDigit = strNum.charAt (n);
        if (isDigit (cDigit)) {
            var nDigit = parseInt(cDigit, 10);
            if (bEven) {
                if ((nDigit *= 2) > 9)
                    nDigit -= 9;
            }
            nCheck += nDigit;
            bEven = ! bEven;
        } else if (cDigit != ' ' && cDigit != '.' && cDigit != '-') {
            return false;
        }
    }
    return (nCheck % 10) == 0;
}

/*
 Check if this credit card type is valid
*/
function checkCreditCardType(strNum, type) {
    var nLen = 0;
    for (n = 0; n < strNum.length; n++) {
        if (isDigit (strNum.substring (n,n+1)))
            ++nLen;
    }
    if (type == 'Visa')
        return ((strNum.substring(0,1) == '4') && (nLen == 13 || nLen == 16));
    else if (type == 'Amex')
        return ((strNum.substring(0,2) == '34' || strNum.substring(0,2) == '37') && (nLen == 15));
    else if (type == 'Master Card')
        return ((strNum.substring(0,2) == '51' || strNum.substring(0,2) == '52'
                || strNum.substring(0,2) == '53' || strNum.substring(0,2) == '54'
                || strNum.substring(0,2) == '55') && (nLen == 16));
    else
        return false;

}

function validate( myform ) {
    var inputFields = document.getElementById(myform.id).getElementsByTagName("input");
    var valid = true;
    var checkFunc = "";
    var message = "";
    for (i = 0; i < inputFields.length; i++) {
        var checkFuncStr = inputFields[i].className;
        var startIdx = 0;
        var endIdx = checkFuncStr.length;
        var done = false;
        do {
            startIdx = checkFuncStr.indexOf('check', startIdx);
            if (startIdx >= 0) {
                var lastIdx = checkFuncStr.indexOf('#', startIdx);
                if (lastIdx > 0) {
                    checkFunc = checkFuncStr.substring(startIdx, lastIdx);
                    startIdx = lastIdx;
                    var myfunc = checkFunc + "('" + inputFields[i].value + "')";
                    valid = eval(myfunc);
                    if (!valid) {
                        var validateName = "val#" + inputFields[i].name + "#" + checkFunc;
                        var hiddenField = document.getElementById(myform.id).elements[validateName];
                        message = hiddenField.value;
                        displayError( myform, message);
                        return false;
                    }
                }
            } else {
                done = true;
            }
            if (startIdx >= endIdx - 5) done = true;
        } while (!done)
    }
}

function displayError( myform, message) {
    var msgDiv = null;
    var form = document.getElementById(myform.id);
    var divs = form.getElementsByTagName("div");
    for (i = 0; i < divs.length; i++) {
        var errorDiv = divs[i].className;
        if (errorDiv == "ui-messagebox-portlet-msg-error") {
            msgDiv = divs[i];
            msgDiv.childNodes[0].textContent = message;
        }
    }
    if (!msgDiv) {
        msgDiv = document.createElement("div");
        msgDiv.className = "ui-messagebox-portlet-msg-error";
        var msgSpan = document.createElement("span");
        msgSpan.className = "portlet-msg-error";
        var txt = document.createTextNode(message);
        msgSpan.appendChild(txt);
        msgDiv.appendChild(msgSpan);
        form.insertBefore(msgDiv, form.childNodes[0]);
    }
}