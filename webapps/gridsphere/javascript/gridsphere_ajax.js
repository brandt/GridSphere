
var GridSphereAjaxHandler = {

    handleSuccess:function(o) {
        var cid = o.argument[0];
        //alert(cid);
        if(o.responseText != undefined){
            //alert(o.responseText);
            // create a temporary div element to store the responseText...
            var serverResponse = document.createElement("div");
            // ... and store the responseText inside it
            serverResponse.innerHTML = o.responseText;
            //alert(o.responseText);
            // create a collection of all the div elements returned
            var tempPageElements = serverResponse.getElementsByTagName("div");
            // replace existing component with new one
            document.getElementById(cid).innerHTML = tempPageElements[0].innerHTML;
        }
    },

    handleFailure:function(o) {
        var cid = o.argument[0];
        if(o.responseText != undefined) {
            // replace existing component with new one
            alert(o.responseText);
            document.getElementById(cid).innerHTML = "Unable to retrieve portlet fragment!!! ";
        }
    },

    startRequest:function(mycid, action) {
        // argument formId can be the id or name attribute value of the
        // HTML form, or an HTML form object.
        var pageElements = document.getElementById(mycid);
        var formElements = pageElements.getElementsByTagName("form");

        YAHOO.util.Connect.setForm(formElements[0]);

        var sUrl = "/gridsphere/gridsphere" + "?ajax=true&cid=" + encodeURIComponent(mycid) + "&" + YAHOO.util.Connect._sFormData;

        var postData = null;

        var transaction = YAHOO.util.Connect.asyncRequest('POST', sUrl,
            {success:this.handleSuccess, failure:this.handleFailure, argument:[ mycid ], scope:this},
                postData);
        //Abort the transaction if it isn't completed in ten seconds.
        //setTimeout("YAHOO.util.Connect.abort(transaction)",10000);
    },

    returnPortlet:function(portlet) {
        // argument formId can be the id or name attribute value of the
        // HTML form, or an HTML form object.
        var pageElements = document.getElementById("portlet#" + portlet);
        var formElements = pageElements.getElementsByTagName("form");
        if (formElements) YAHOO.util.Connect.setForm(formElements[0]);

        var sUrl = "/gridsphere/gridsphere" + "?ajax=true&portlet=" + encodeURIComponent(portlet) + "&" + YAHOO.util.Connect._sFormData;

        var postData = null;

        var transaction = YAHOO.util.Connect.asyncRequest('POST', sUrl,
            {success:this.handleSuccess, failure:this.handleFailure, argument:[ "portlet#" + portlet ], scope:this},
                postData);

         //Abort the transaction if it isn't completed in ten seconds.
        //setTimeout("YAHOO.util.Connect.abort(transaction)",10000);
    },

    loadPortlets:function() {
        var portlets = [];
        var j = 0;
        var divElements = document.getElementsByTagName("div");
        for (var i = 0; i < divElements.length; i++) {
            var anid = divElements[i].id;
            var startIdx = anid.indexOf("portlet");
            var lastIdx = anid.indexOf('#', startIdx);
            if (startIdx >= 0) {
                var portlet = anid.substring(lastIdx+1);
                var webIdx = anid.indexOf('#', lastIdx);
                this.returnPortlet(portlet);
            }
        }
    }

};
