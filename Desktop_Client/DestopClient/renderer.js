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
