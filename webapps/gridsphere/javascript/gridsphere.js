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

      if (list[0].checked == true) {

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

    alert("GridSphere CheckBoxList Clear");

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

      if (newSelection.checked == true) {

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

            if (list[i].checked == true) {

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
