// This file is required by the index.html file and will
// be executed in the renderer process for that window.
// All of the Node.js APIs are available in this process.
var $ = require('jQuery');
const {dialog} = require('electron').remote
download.addEventListener("click", function(){// add event for 'download'
dialog.showMessageBox({// show a dialog message when click on the download button
  type: 'info',
    title: 'Download',
    message: "Do you want to download?",
    buttons: ['YES', 'NO']
  },(res,checked) =>{
  if (res === 0){
    //Yes button pressed
    var usernameha = localStorage.getItem("username");
    // console.log(usernameha);
    var fileName = window.parent.str;// pass the value of the dialog message to the parent page
    console.log(fileName);
    const http = require('http');
    const fs = require('fs');
    const file = fs.createWriteStream(fileName);
    const request = http.get("http://teamparamount.cn:8080/Paramount/download?username=" + usernameha + "&url=" + fileName, function(response) {
      response.pipe(file);// send request to server and get the response of the file
    });
    console.log("works");
   }
  })
});

DeleteFile.addEventListener("click", function(){
  //set MessageBox
    dialog.showMessageBox({
        type: 'info',
        title: 'DeleteFile',
        message: "Do you want to delete?",
        buttons: ['YES', 'NO']
    },(res,checked) =>{
        if (res === 0){
            //Yes button pressed
            //start delete function

            //then, pick items which be checked
            var items = document.getElementsByClassName('checkbox');
            //set searching max length
            var len = items.length;
            //for loop search
            for(var i = len-1; i >= 0; i--){
              //pick items which be checked
                var is_checked = items[i].checked;
                // delete item which checkbox has already be checked
                if (is_checked){
                  //get lines which need to be deleted
                    var divItem = items[i].parentNode.parentNode;
                    //delete
                    divItem.parentNode.removeChild(divItem);
                }
            }
        }
    })

    var fileName = window.parent.str;
    //get deleted file's name
    const http = require('http');
    var usernameha = localStorage.getItem("username");
    http.get(
        "http://teamparamount.cn:8080/Paramount/delete?username=" + usernameha + "&url=" + fileName, (resp) =>{
            let data = '';
            // A chunk of data has been recieved.
            resp.on('data', (chunk) =>{
                data += chunk;
            });
            // The whole response has been received. Print out the result.
            resp.on('end', () =>{
                var hhh = JSON.parse(data);
                var xxx = JSON.parse(data).info;
            });
        }).on("error", (err) => {
        console.log("Error: " + err.message);
    });

});

// Listen for the event of refresh button
refresh.addEventListener("click", function(){
  const http = require('http');
  //retrieve username from localStorage
  var usernameha = localStorage.getItem("username");
  console.log(usernameha);
  http.get(
    'http://teamparamount.cn:8080/Paramount/filesroot?username=' + usernameha, (resp) =>{
      let data = '';
      // A chunk of data has been recieved.
      resp.on('data', (chunk) =>{
        data += chunk;
      });
      // The whole response has been received. Print out the result.
      resp.on('end', () =>{
        //parse JSON data sent from server and assign it to different variables
        var hhh = JSON.parse(data);
        var xxx = JSON.parse(data).info;
        var heh = JSON.parse(hhh.info);
        console.log(hhh);
        console.log(xxx);
        // Dynamic stitching table
        var trStr = '';
        for (var i = 0; i < heh.length; i++) {//Loop through each data in the json object and display it in the corresponding td
                   trStr += '<tr class="example">';//Tabular specification
                   trStr += '<td id="chebox0" ><input class="checkbox" type="checkbox" onclick="test(this);"></td>';
                   trStr += '<td contenteditable="true">' + heh[i].url + '</td>';//File name corresponding to the array element
                   trStr += '<td>' + heh[i].size + '</td>';//File size corresponding to the array element
                   trStr += '<td>' + heh[i].time + '</td>';//File creation date for the corresponding array element
                   trStr += '</tr>';
        }
        //Insert the form generated after the traversal into the HTML file
        $("#document_table").html(trStr);
      });
    }).on("error", (err) => {
      console.log("Error: " + err.message);
    });
});

// Listen for the event of Today button
today.addEventListener("click", function(){
  const http = require('http');
  //retrieve username from localStorage
  var usernameha = localStorage.getItem("username");
  console.log(usernameha);
  http.get(
    'http://teamparamount.cn:8080/Paramount/filesroot?username=' + usernameha, (resp) =>{
      let data = '';
      // A chunk of data has been recieved.
      resp.on('data', (chunk) =>{
        data += chunk;
      });
      // The whole response has been received. Print out the result.
      resp.on('end', () =>{
        //parse JSON data sent from server and assign it to different variables
        var hhh = JSON.parse(data);
        var xxx = JSON.parse(data).info;
        var heh = JSON.parse(hhh.info);
        console.log(hhh);
        console.log(xxx);
        console.log(heh);
        var dtCur = new Date();
        var yearCur = dtCur.getFullYear();
        var monCur = dtCur.getMonth() + 1;
        var dayCur = dtCur.getDate();
        //Get the current time, accurate to the day
        timeCur = yearCur + "-" + (monCur < 10 ? "0" + monCur : monCur) + "-"
    				+ (dayCur < 10 ? "0" + dayCur : dayCur);
        console.log(timeCur);
        console.log(heh[0].time);
        console.log(heh[0].time.substring(0,10));
        var trStr = '';
        for (var i = 0; i < heh.length; i++) {//Loop through each data in the json object and display it in the corresponding td
          if (timeCur == heh[i].time.substring(0,10)) {
            trStr += '<tr class="example">';
            trStr += '<td id="chebox0" ><input class="checkbox" type="checkbox" onclick="test(this);"></td>';
            trStr += '<td contenteditable="true">' + heh[i].url + '</td>';//对应数组表的字段值
            trStr += '<td>' + heh[i].size + '</td>';
            trStr += '<td>' + heh[i].time + '</td>';
            trStr += '</tr>';
          }
        }
        // console.log(trStr);
        $("#document_table").html(trStr);
      });
    }).on("error", (err) => {
      console.log("Error: " + err.message);
    });
});

// Listen for the event of GO button
searchFuction.addEventListener("click", function(){
  const http = require('http');
  var usernameha = localStorage.getItem("username");
  http.get(
    'http://teamparamount.cn:8080/Paramount/filesroot?username=' + usernameha, (resp) =>{
      let data = '';
      // A chunk of data has been recieved.
      resp.on('data', (chunk) =>{
        data += chunk;
      });
      // The whole response has been received. Print out the result.
      resp.on('end', () =>{
        //parse JSON data sent from server
        var hhh = JSON.parse(data);
        var xxx = JSON.parse(data).info;
        var heh = JSON.parse(hhh.info);
        var trStr = '';//Dynamic stitching table
        //retrieve user's input
        var searchWord = $.trim($("#searchInput").val());
        //tranfer input into uppercase
        var upsearchWord = searchWord.toUpperCase();
        console.log(upsearchWord);
        for (var i = 0; i < heh.length; i++) {//Loop through each data in the json object and display it in the corresponding td
          var type = "." + heh[i].type;
          console.log(type);
          var wholefileName = searchWord + type;
          var fileName = heh[i].url.replace(type,"");
          var upfileName = fileName.toUpperCase();
          console.log(upfileName);
          //Traversal logic
          if (heh[i].url == searchWord || heh[i].url == wholefileName || upsearchWord == upfileName || searchWord == heh[i].type) {
            trStr += '<tr class="example">';//Tabular specification
            trStr += '<td id="chebox0" ><input class="checkbox" type="checkbox" onclick="test(this);"></td>';
            trStr += '<td contenteditable="true">' + heh[i].url + '</td>';//File name corresponding to the array element
            trStr += '<td>' + heh[i].size + '</td>';//File size corresponding to the array element
            trStr += '<td>' + heh[i].time + '</td>';//File creation date for the corresponding array element
            trStr += '</tr>';
          }else if (upfileName.search(upsearchWord) != -1) {
            trStr += '<tr class="example">';//Tabular specification
            trStr += '<td id="chebox0" ><input class="checkbox" type="checkbox" onclick="test(this);"></td>';
            trStr += '<td contenteditable="true">' + heh[i].url + '</td>';//File name corresponding to the array element
            trStr += '<td>' + heh[i].size + '</td>';//File size corresponding to the array element
            trStr += '<td>' + heh[i].time + '</td>';//File creation date for the corresponding array element
            trStr += '</tr>';
          }
        }
        //Insert the form generated after the traversal into the HTML file
        $("#document_table").html(trStr);
      });
    }).on("error", (err) => {
      console.log("Error: " + err.message);
    });
});

// Listen for the event of revert button
revert.addEventListener("click", function(){
  //set MessageBox
    dialog.showMessageBox({
        type: 'info',
        title: 'Revert File',
        message: "Do you want to revert?",
        buttons: ['YES', 'NO']
    },(res,checked) =>{
        if (res === 0){
            //Yes button pressed
            var fileName = window.parent.str;
            //get file's name
            console.log(fileName);
            const http = require('http');
            //retrieve username from localStorage
            var usernameha = localStorage.getItem("username");
            http.get(
                'http://teamparamount.cn:8080/Paramount/revert?username=' + usernameha + '&url=' + fileName, (resp) =>{
                    let data = '';
                    // A chunk of data has been recieved.
                    resp.on('data', (chunk) =>{
                        data += chunk;
                    });
                    // The whole response has been received. Print out the result.
                    resp.on('end', () =>{
                        //parse JSON data set from server
                        var hhh = JSON.parse(data);
                        if (hhh.status == "success") {
                          //when the status sent from server is success then allert succeed message
                          alert("Revert Succeed!");
                        } else {
                          //When the user fails to revert, the application will respond to the failure information sent by the server.
                          alert(hhh.info);
                        }
                        console.log(hhh.status);
                        console.log(hhh.info);
                    });
                }).on("error", (err) => {
                console.log("Error: " + err.message);
            });
        }
    })
});


// Listen for the event of rename button
rename.addEventListener("click", function(){
  //set MessageBox
    dialog.showMessageBox({
        type: 'info',
        title: 'Rename File',
        message: "Do you want to rename?",
        buttons: ['YES', 'NO']
    },(res,checked) =>{
        if (res === 0){
            //retrieve user selected file's filename
            var fileName1 = window.parent.str;
            console.log(fileName1);
            //get the changed name
            var $check = $("#document_table").find(":checkbox").filter(":checked");
            var $tr = $check.eq(0).parent().parent();
            var $td = $tr.find("td").eq(1).text();
            var fileName2 = $td;
            console.log(fileName2);
            const http = require('http');
            var usernameha = localStorage.getItem("username");
            http.get(
                'http://teamparamount.cn:8080/Paramount/rename?username=' + usernameha + '&url=' + fileName1 + '&newUrl=' + fileName2, (resp) =>{
                    let data = '';
                    // A chunk of data has been recieved.
                    resp.on('data', (chunk) =>{
                        data += chunk;
                    });
                    // The whole response has been received. Print out the result.
                    resp.on('end', () =>{
                        var hhh = JSON.parse(data);
                        if (hhh.status == "success") {
                          //when the status sent from server is success then allert succeed message
                          alert("Rename Succeed!");
                        } else {
                          //When the user fails to rename, the application will respond to the failure information sent by the server.
                          alert(hhh.info);
                        }
                        console.log(hhh);
                    });
                }).on("error", (err) => {
                console.log("Error: " + err.message);
            });
        }
    })
});
