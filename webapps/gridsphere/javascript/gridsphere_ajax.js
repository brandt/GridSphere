
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

var GridSphereAjaxHandler2 = {

    handleSuccess:function(o) {
        var name = o.argument[0];
        //alert(name);
        if(o.responseText != undefined) {
            //alert(o.responseText);

            // check to see if entire HTML has been transferred back

            var fragment = o.responseText;

            // check for a redirect!
            if (fragment.substring(0,4) == "http") {
                window.location = fragment;
                return;
            }

            // create a temporary div element to store the responseText..
            var tmpDiv = document.createElement("div");
            // ... and store the responseText inside it

            tmpDiv.innerHTML = fragment;

            var frame = YAHOO.util.Dom.getElementsByClassName('gridsphere-window-content', 'div', tmpDiv);
            var titlebar = YAHOO.util.Dom.getElementsByClassName('gridsphere-window-title', 'div', tmpDiv);
            var panel = document.getElementById(name);
            var title = YAHOO.util.Dom.getElementsByClassName('hd', 'div', panel);
            var body = YAHOO.util.Dom.getElementsByClassName('bd', 'div', panel);
            body[0].innerHTML = frame[0].innerHTML;
            title[0].innerHTML = titlebar[0].innerHTML; // + "<a style=\"position:absolute; top:5px; right:4px; z-index:6; height:12px; width:12px; margin:0px; padding:0px; background-repeat:no-repeat; cursor:pointer; visibility:inherit;\" href=\"#\" onclick=\"YAHOO." + name + ".panel.hide();\">close</a>";

            //alert(o.responseText);
            // create a collection of all the div elements returned
            //var tempPageElements = serverResponse.getElementsByTagName("div");
            // replace existing component with new one
            //document.getElementById(name).innerHTML = tempPageElements[0].innerHTML;
            //window.eval("YAHOO." + name + ".panel.show()");
        }
    },

    handleFailure:function(o) {
        var cid = o.argument[0];
        if(o.responseText != undefined) {
            // replace existing component with new one           
            //alert("failure! " + o.statusText);
            //alert(o.getResponseHeader());

            document.getElementById(name).innerHTML = "Unable to retrieve portlet fragment!!! ";
        }
    },

    startRequest:function(portlet, name, action) {

        var pageElements = document.getElementById(name);
        var formElements = pageElements.getElementsByTagName("form");

        YAHOO.util.Connect.setForm(formElements[0]);

        var extra =  YAHOO.util.Connect._sFormData;
       
        if (action != null) {
            extra += action;
        }
        if (extra == null) {
            extra = "";
        } else {
            extra = "&" + extra;
        }

        var sUrl = "/gridsphere/gs" + "?ajax=true&compname=" + name + "&portlet=" + encodeURIComponent(portlet) + extra;

        var postData = null;

        var transaction = YAHOO.util.Connect.asyncRequest('POST', sUrl,
            {success:this.handleSuccess, failure:this.handleFailure, argument:[ name ], scope:this},
                postData);
        //Abort the transaction if it isn't completed in ten seconds.
        //setTimeout("YAHOO.util.Connect.abort(transaction)",10000);
    }

};
