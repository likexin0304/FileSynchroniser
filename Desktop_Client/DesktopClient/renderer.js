// This file is required by the index.html file and will
// be executed in the renderer process for that window.
// All of the Node.js APIs are available in this process.
const {dialog} = require('electron').remote
download.addEventListener("click", function(){
dialog.showMessageBox({
  type: 'info',
    title: 'Download',
    message: "Do you want to download?",
    buttons: ['YES', 'NO']
  },(res,checked) =>{
  if (res === 0){
    //Yes button pressed
    var fileName = window.parent.str;
    console.log(fileName);
    const http = require('http');
    const fs = require('fs');
    const file = fs.createWriteStream(fileName);
    const request = http.get("http://teamparamount.cn:8080/Paramount/download?username=test1&url="+fileName, function(response) {
      response.pipe(file);
    });
    console.log("works");
   }
  })
});


// var FormData = require('form-data');
// var http = require('http');
//
// var form = new FormData();
//
// http.request('http://teamparamount.cn:8080/Paramount/download', function(response) {
//   form.append('username', 'admin');
//   form.append('url','1.pptx');
//   form.append('my_buffer', new Buffer(2048));
//   form.append('my', response);
// });



    // dialog.showMessageBox({
    //     //默认路径
    //     defaultPath :'../Desktop',
    //     //选择操作，此处是打开文件夹
    //     properties: [
    //         'openDirectory',
    //     ],
    //     //过滤条件
    //     filters: [
    //         { name: 'All', extensions: ['*'] },
    //     ]
    // })
