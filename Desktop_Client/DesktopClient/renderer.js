// This file is required by the index.html file and will
// be executed in the renderer process for that window.
// All of the Node.js APIs are available in this process.
var $ = require('jQuery');
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
    const request = http.get("http://teamparamount.cn:8080/Paramount/download?username=test&url="+fileName, function(response) {
      response.pipe(file);
    });
    console.log("works");
   }
  })
});


DeleteFile.addEventListener("click", function(){
    dialog.showMessageBox({
        type: 'info',
        title: 'DeleteFile',
        message: "Do you want to delete?",
        buttons: ['YES', 'NO']
    },(res,checked) =>{
        if (res === 0){
            //Yes button pressed

            var items = document.getElementsByClassName('checkbox');
            var len = items.length;
            for(var i = len-1; i >= 0; i--){
                var is_checked = items[i].checked;
                if (is_checked){
                    var divItem = items[i].parentNode.parentNode;
                    //get lines which need to be deleted
                    
                    // var tds = divItem.cells;
                    // var n = 1;
                    // str = tds[n].innerHTML;
                    // var fileName = str;
                    divItem.parentNode.removeChild(divItem);
                }
            }

        }
    })

    var fileName = window.parent.str;
    //get deleted file's name

    //console.log(fileName);

    const http = require('http');
    http.get(
        'http://teamparamount.cn:8080/Paramount/delete?username=test&url='+fileName, (resp) =>{
            let data = '';
            // A chunk of data has been recieved.
            resp.on('data', (chunk) =>{
                data += chunk;
            });
            // The whole response has been received. Print out the result.
            resp.on('end', () =>{

                var hhh = JSON.parse(data);
                var xxx = JSON.parse(data).info;
                // console.log(hhh);
                // console.log(xxx);

            });
        }).on("error", (err) => {
        console.log("Error: " + err.message);
    });

});

refresh.addEventListener("click", function(){
  const http = require('http');
  http.get(
    'http://teamparamount.cn:8080/Paramount/filesroot?username=test', (resp) =>{
      let data = '';
      // A chunk of data has been recieved.
      resp.on('data', (chunk) =>{
        data += chunk;
      });
      // The whole response has been received. Print out the result.
      resp.on('end', () =>{
        // console.log(JSON.parse(data).info);
        // var obj = JSON.stringify(data);

        // var bobo1 = JSON.parse(data, function(key,value){
        //   if(key =="status") {
        //      return undefined;
        //    }
        //      console.log(value);
        //      // return value;
        // });

       // var bobo2 = JSON.parse(bobo1, function(key,value){
       //   console.log(key);
       //   console.log(value);
       // });

       // var bobo3 = [{\"size\":\"10105\",\"time\":\"2019-03-03 20:12:43\",\"type\":\"xlsx\",\"url\":\"Book1.xlsx\"}, {\"size\":\"10179\",\"time\":\"2019-03-03 20:12:56\",\"type\":\"docx\",\"url\":\"groupproject.docx\"}];
       // var bobo4 = JSON.parse(bobo3, function(key,value){
       //   console.log(key);
       //   console.log(value);
       //      // return value;
       // });

        var hhh = JSON.parse(data);
        var xxx = JSON.parse(data).info;
        var heh = JSON.parse(hhh.info);
        // console.log(hhh);
        // console.log(xxx);
        // console.log(heh);
        // console.log(heh.length);
        // console.log(heh[0].size,heh[1].time,heh[4]);
        // var mainTable = document.getElementById("document_table");

        // var info = JSON.parse(data.info);
        // data.info = info;
        // console.log(data);

        // // alert(typeof obj);
        // // console.log(hhh.length);
        // // console.log(obj);
        // console.log(data);
        // console.log(hhh.status);


        var trStr = '';//动态拼接table
        for (var i = 0; i < heh.length; i++) {//循环遍历出json对象中的每一个数据并显示在对应的td中
                   trStr += '<tr class="example">';//拼接处规范的表格形式
                   trStr += '<td id="chebox0" ><input class="checkbox" type="checkbox" onclick="test(this);"></td>';
                   // trStr += '<td width="15%" style="display:none" id="user">' + obj[i].NVFID + '</td>';//数据表的主键值
                   trStr += '<td>' + heh[i].url + '</td>';//对应数组表的字段值
                   trStr += '<td>' + heh[i].size + '</td>';
                   trStr += '<td>' + heh[i].time + '</td>';
                   // trStr += '<td>' + obj[i].PHONEIMEI + '</td>';
                   // trStr += '<td>' + obj[i].BMMC + '</td>';
                   /*经典之处，要将主键对应的值以json的形式进行传递，才能在后台使用*/
                   // trStr += "<td><a href='#'style='text-decoration:none' onclick='Delete(\"" + obj[i].NVFID + "\")'>删除</a><td>";
                   trStr += '</tr>';
        }
        // console.log(trStr);
        $("#document_table").html(trStr);


        // console.log(hhh.info);
        // console.log(hhh.info[1].time);
        // console.log(hhh.info.length);
        // console.log(hhh.info[408]);
        //
        //
        // // console.log(obj.info[1]);
        //
        // // console.log(obj.status);
        // // console.log(obj.status.length);
        // function getJsonLth(obj){
        //   var index = 0;
        //   for(var i=0;i<obj.length;i++){
        //     if (obj[i] == ':') {
        //       index++;
        //     }
        //     return index;
        //     // alert(json1.abc[i].name);
        //   }
        // };
        // console.log(getJsonLth(xxx));
      });
    }).on("error", (err) => {
      console.log("Error: " + err.message);
    });
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