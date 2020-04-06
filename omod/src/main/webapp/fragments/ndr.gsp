<%
def id = config.id
%>
<%= ui.resourceLinks() %>

<script>
    jq = jQuery;
    jq('#wait').hide();
    /*var popupDialog = emr.setupConfirmationDialog({
    selector: '#wait'
    });*/
    jq(function() {
      jq('#${ id }_button').click(function() {
        var startDate = jq("#startDate").val();
        var endDate = jq("#endDate").val();
        if(startDate == "")
        {
            alert("Select start date");
        }
        else if(endDate == "")
        {
            alert("Select end date");
        }
        else{
            jq('#wait').show();
            
             jq.getJSON('${ ui.actionLink("generateNDRFile") }', {startDate:startDate, endDate:endDate})
            .success(function(filename) {
                //Old implementation
                if(filename == "no new patient record found"){
                    jq('#wait').hide();
                    alert("no updated patient record found")
                }
              else{
                    window.location = filename;
              }
                /* if(filename.startsWith("Files Exported successfully")){
                 //export was successful
                 alert(filename);
                 }
                 else{
                 //export was partially successful
                 //this may require directing the user to an erro summary page.
                 alert(filename);
                 }*/
                jq('#wait').hide();
              }).error(function(xhr, status, err) {
                 console.log("err", err);
                 console.log("xhr", xhr);
                 console.log("status", status);
                 alert('There was an error generating all NDR files, check generated files at downloads directory in the application root folder ' + err);
                 //popupDialog.close();
                 jq('#wait').hide();
               })
            //});

        }
    //popupDialog.show();
         
    });
    
    jq(".date").datepicker({dateFormat:"yy-mm-dd", changeMonth:true, changeYear:true});
    
});



</script>
<div>
    <table>
        <tr>
            <td>Start Date</td><td><input type="text" name="startDate" id="startDate" class="date"/></td>
        </tr>
        <tr>
            <td>End Date</td><td><input type="text" name="endDate" id="endDate" class="date"/></td>
        </tr>
    </table>
</div>
<a id="${ id }_button"  class="button app big" style="font-size:12px;min-height: 10px;">
    <i class="icon-download"></i>
    <br/>
    <p>Generate NDR</p>
</a>

<a id="${ id }_button_loc_export"  class="button app big" style="font-size:12px;min-height: 10px;" href="ndrexport.page">
    <i class="icon-lock"></i>
    <br/>
    <p>NDR Export By Location</p>
</a>

<a id="${ id }_button_manage_location"  class="button app big" style="font-size:12px;min-height: 10px;" href="FacilityLocation.page">
    <i class="icon-file-alt"></i>
    <br/>
    <p>Manage Facility Location</p>
</a>



