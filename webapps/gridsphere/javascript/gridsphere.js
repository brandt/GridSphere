  /**************************************************************************
   * GridSphere Object Functions
   **************************************************************************/

  /**
   * Checks if an object with given name exists in form
   */
  function GridSphere_Object_existsInForm(form, name) {

    // alert("Testing whether object [" + name + "] exists in form [" + form.name + "]");

    for (var i = 0; i < form.elements.length; ++i) {

      if (form.elements[i].name == name) {

        // alert("Object " + name + " exists");

        return true;
      }
    }

    // alert("Object does not exist");

    return false;
  }

  /**************************************************************************
   * GridSphere Form Functions
   **************************************************************************/

  /**
   * Submits the given action for the given form
   */
  function GridSphere_Form_submitAction(form, action) {
    form.action=action;
    form.submit();
  }

  /**************************************************************************
   * GridSpehre Check Box List Functions
   **************************************************************************/

  function GridSphere_CheckBoxList_checkAll(list) {

      if (list[0].checked) {

        // alert("GridSphere CheckBoxList Check All True");

        for (i = 1; i < list.length; i++) {

          list[i].checked = true;
        }

        // Select first list value if none selected yet
        if (list[0].value == "") {

          if (list.length > 1) {

            list[0].value = list[1].value;
          }
        }

      } else {

        // alert("GridSphere CheckBoxList Check All False");

        GridSphere_CheckBoxList_clear(list);
      }
  }

  function GridSphere_CheckBoxList_clear(list) {

    // alert("GridSphere CheckBoxList Clear");

      for (i = 0; i < list.length; i++) {

        list[i].checked = false;
      }

      // Clear selected value
      list[0].value = "";
  }

  function GridSphere_CheckBoxList_checkOne(list)
  {
    // alert("GridSphere CheckBoxList Check One");

      // Uncheck "all" option
      list[0].checked = false;

      // Uncheck those that don't match selection
      for (i = 1; i < list.length; i++) {

        if (list[i].value != list[0].value) {

          list[i].checked = false;
        }
      }
  }

  function GridSphere_CheckBoxList_onClick(list, newSelection)
  {
    // alert("GridSphere CheckBoxList On Click");

      if (newSelection.checked) {

        // Save selection only if none made yet
        if (list[0].value == "") {

          list[0].value = newSelection.value;
        }

      } else {

        // If saved selection was this one
        if (list[0].value == newSelection.value) {

          var found = false;

          // Set selection to first checked item other than this
          for (i = 1; i < list.length && !found; i++) {

            if (list[i].checked) {

              if (list[i].value != item.value) {

                list[0].value = list[i].value;

                found = true;
              }
            }
          }

          // If we didn't find a checked value
          if (!found) {

            // Set selection to none
            list[0].value = "";
          }
        }
      }

      // alert("GridSphere CheckBoxList new selection: " + selection.value);
  }

  function GridSphere_CheckBoxList_validateCheckOneOrMore(list)
  {
    // alert("GridSphere CheckBoxList Validate Check One Or More");

      // alert("Performing validate check one or more on " + list.name);

      return (list[0].value != "");
  }

 function GridSphere_SelectSubmit( aform ) {
    aform.submit();
 }


 function GridSphere_popup(mylink, windowname) {
    if (! window.focus)return true;
    var href;
    if (typeof(mylink) == 'string')
        href=mylink;
    else
        href=mylink.href;
        window.open(href, windowname, 'width=800,height=600,scrollbars=yes,resizable=yes');
    return false;
 }


// This code made publicly available from 
// http://www.kryogenix.org/code/browser/sorttable/sorttable.js 
// under MIT license - JN Mar 14 2004
/* Copyright (c) <year> <copyright holders>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

/* Modification of sorttable.js
 * http://www.kryogenix.org/code/browser/sorttable/
 * Original code by Stuart Langridge, November 2003
 * Modified by Andy Edmonds, December 2003
 *  Added alternateRowColors to color alternating rows
 */

addEvent(window, "load", sortables_init);

var SORT_COLUMN_INDEX;

function sortables_init() {
    // Find all tables with class sortable and make them sortable
    if (!document.getElementsByTagName) return;
    tbls = document.getElementsByTagName("table");
    for (ti=0;ti<tbls.length;ti++) {
        thisTbl = tbls[ti];
        if (((' '+thisTbl.className+' ').indexOf("sortable") != -1) && (thisTbl.id)) {
            //initTable(thisTbl.id);
            ts_makeSortable(thisTbl);
        }
    }
    alternateRowColors();
}

function ts_makeSortable(table) {
    if (table.rows && table.rows.length > 0) {
        var firstRow = table.rows[0];
    }
    if (!firstRow) return;

    // We have a first row: assume it's the header, and make its contents clickable links
    for (var i=0;i<firstRow.cells.length;i++) {
        var cell = firstRow.cells[i];
        var txt = ts_getInnerText(cell);
        cell.innerHTML = '<a href="#" style="color: black; " class="sortheader" onclick="ts_resortTable(this);return false;">'+txt+'<span class="sortarrow">&nbsp;&nbsp;&nbsp;</span></a>';
    }
}

function ts_getInnerText(el) {
	if (typeof el == "string") return el;
	if (typeof el == "undefined") { return el };
	if (el.innerText) return el.innerText;	//Not needed but it is faster
	var str = "";

	var cs = el.childNodes;
	var l = cs.length;
	for (var i = 0; i < l; i++) {
		switch (cs[i].nodeType) {
			case 1: //ELEMENT_NODE
				str += ts_getInnerText(cs[i]);
				break;
			case 3:	//TEXT_NODE
				str += cs[i].nodeValue;
				break;
		}
	}
	return str;
}

function ts_resortTable(lnk) {
    // get the span
    var span;
    for (var ci=0;ci<lnk.childNodes.length;ci++) {
        if (lnk.childNodes[ci].tagName && lnk.childNodes[ci].tagName.toLowerCase() == 'span') span = lnk.childNodes[ci];
    }
    var spantext = ts_getInnerText(span);
    var td = lnk.parentNode;
    var column = td.cellIndex;
    var table = getParent(td,'TABLE');

    // Work out a type for the column
    if (table.rows.length <= 1) return;
    var itm = ts_getInnerText(table.rows[1].cells[column]);
    sortfn = ts_sort_caseinsensitive;
    if (itm.match(/^\d\d[\/-]\d\d[\/-]\d\d\d\d$/)) sortfn = ts_sort_date;
    if (itm.match(/^\d\d[\/-]\d\d[\/-]\d\d$/)) sortfn = ts_sort_date;
    if (itm.match(/^[�$]/)) sortfn = ts_sort_currency;
    if (itm.match(/^[\d\.]+$/)) sortfn = ts_sort_numeric;
    SORT_COLUMN_INDEX = column;
    var firstRow = new Array();
    var newRows = new Array();
    for (i=0;i<table.rows[0].length;i++) { firstRow[i] = table.rows[0][i]; }
    for (j=1;j<table.rows.length;j++) { newRows[j-1] = table.rows[j]; }

    newRows.sort(sortfn);

    if (span.getAttribute("sortdir") == 'down') {
        ARROW = '&nbsp;&nbsp;&uarr;';
        newRows.reverse();
        span.setAttribute('sortdir','up');
    } else {
        ARROW = '&nbsp;&nbsp;&darr;';
        span.setAttribute('sortdir','down');
    }

    // We appendChild rows that already exist to the tbody, so it moves them rather than creating new ones
    // don't do sortbottom rows
    for (i=0;i<newRows.length;i++) { if (!newRows[i].className || (newRows[i].className && (newRows[i].className.indexOf('sortbottom') == -1))) table.tBodies[0].appendChild(newRows[i]);}
    // do sortbottom rows only
    for (i=0;i<newRows.length;i++) { if (newRows[i].className && (newRows[i].className.indexOf('sortbottom') != -1)) table.tBodies[0].appendChild(newRows[i]);}

    // Delete any other arrows there may be showing
    var allspans = document.getElementsByTagName("span");
    for (var ci=0;ci<allspans.length;ci++) {
        if (allspans[ci].className == 'sortarrow') {
            if (getParent(allspans[ci],"table") == getParent(lnk,"table")) { // in the same table as us?
                allspans[ci].innerHTML = '&nbsp;&nbsp;&nbsp;';
            }
        }
    }

    span.innerHTML = ARROW;
		alternateRowColors();
}

function getParent(el, pTagName) {
	if (el == null) return null;
	else if (el.nodeType == 1 && el.tagName.toLowerCase() == pTagName.toLowerCase())	// Gecko bug, supposed to be uppercase
		return el;
	else
		return getParent(el.parentNode, pTagName);
}
function ts_sort_date(a,b) {
    // y2k notes: two digit years less than 50 are treated as 20XX, greater than 50 are treated as 19XX
    aa = ts_getInnerText(a.cells[SORT_COLUMN_INDEX]);
    bb = ts_getInnerText(b.cells[SORT_COLUMN_INDEX]);
    if (aa.length == 10) {
        dt1 = aa.substr(6,4)+aa.substr(3,2)+aa.substr(0,2);
    } else {
        yr = aa.substr(6,2);
        if (parseInt(yr) < 50) { yr = '20'+yr; } else { yr = '19'+yr; }
        dt1 = yr+aa.substr(3,2)+aa.substr(0,2);
    }
    if (bb.length == 10) {
        dt2 = bb.substr(6,4)+bb.substr(3,2)+bb.substr(0,2);
    } else {
        yr = bb.substr(6,2);
        if (parseInt(yr) < 50) { yr = '20'+yr; } else { yr = '19'+yr; }
        dt2 = yr+bb.substr(3,2)+bb.substr(0,2);
    }
    if (dt1==dt2) return 0;
    if (dt1<dt2) return -1;
    return 1;
}

function ts_sort_currency(a,b) {
    aa = ts_getInnerText(a.cells[SORT_COLUMN_INDEX]).replace(/[^0-9.]/g,'');
    bb = ts_getInnerText(b.cells[SORT_COLUMN_INDEX]).replace(/[^0-9.]/g,'');
    return parseFloat(aa) - parseFloat(bb);
}

function ts_sort_numeric(a,b) {
    aa = parseFloat(ts_getInnerText(a.cells[SORT_COLUMN_INDEX]));
    if (isNaN(aa)) aa = 0;
    bb = parseFloat(ts_getInnerText(b.cells[SORT_COLUMN_INDEX]));
    if (isNaN(bb)) bb = 0;
    return aa-bb;
}

function ts_sort_caseinsensitive(a,b) {
    aa = ts_getInnerText(a.cells[SORT_COLUMN_INDEX]).toLowerCase();
    bb = ts_getInnerText(b.cells[SORT_COLUMN_INDEX]).toLowerCase();
    if (aa==bb) return 0;
    if (aa<bb) return -1;
    return 1;
}

function ts_sort_default(a,b) {
    aa = ts_getInnerText(a.cells[SORT_COLUMN_INDEX]);
    bb = ts_getInnerText(b.cells[SORT_COLUMN_INDEX]);
    if (aa==bb) return 0;
    if (aa<bb) return -1;
    return 1;
}


function addEvent(elm, evType, fn, useCapture)
// addEvent and removeEvent
// cross-browser event handling for IE5+,  NS6 and Mozilla
// By Scott Andrew
{
  if (elm.addEventListener){
    elm.addEventListener(evType, fn, useCapture);
    return true;
  } else if (elm.attachEvent){
    var r = elm.attachEvent("on"+evType, fn);
    return r;
  } else {
    //alert("Handler could not be removed");
  }
}

function origalternateRowColors() {
	var className = 'sortable';
	var rowcolor = '#dddddd';
	var defaultrowcolor = '#ffffff';
	var rows, arow;
	var tables = document.getElementsByTagName("table");
	var rowCount = 0;
	for(var i=0;i<tables.length;i++) {
		//dump(tables.item(i).className + " " + tables.item(i).nodeName + "\n");
		if(tables.item(i).className == className) {
			atable = tables.item(i);
			rows = atable.getElementsByTagName("tr");
			for(var j=0;j<rows.length;j++) {
				arow = rows.item(j);
				if(arow.nodeName == "TR") {
					if(rowCount % 2) {
						arow.style.backgroundColor = rowcolor;
					} else {
						// default case
						arow.style.backgroundColor = defaultrowcolor;
					}
					rowCount++;
				}
			}
			rowCount = 0;
		}
	}
}


  function alternateRowColors() {
      var className = 'sortable';
      var rows, arow;
      var tables = document.getElementsByTagName("table");
      var rowCount = 0;
      for(var i=0;i<tables.length;i++) {
          //dump(tables.item(i).className + " " + tables.item(i).nodeName + "\n");
          if(tables.item(i).className == className) {
              atable = tables.item(i);
              rows = atable.getElementsByTagName("tr");
              for(var j=1;j<rows.length;j++) {
                  arow = rows.item(j);
                  if(arow.nodeName == "TR") {
                      if(rowCount % 2) {
                          arow.setAttribute('class', 'portlet-section-alternate');

                      } else {
                          // default case
                          arow.setAttribute('class', 'portlet-section-body');
                      }
                      rowCount++;
                  }
              }
              rowCount = 0;
          }
      }
  }


