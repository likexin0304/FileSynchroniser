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

refresh.addEventListener("click", function(){
  const http = require('http');
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
        var hhh = JSON.parse(data);
        var xxx = JSON.parse(data).info;
        var heh = JSON.parse(hhh.info);
        console.log(hhh);
        console.log(xxx);
        var trStr = '';//动态拼接table
        for (var i = 0; i < heh.length; i++) {//循环遍历出json对象中的每一个数据并显示在对应的td中
                   trStr += '<tr class="example">';//拼接处规范的表格形式
                   trStr += '<td id="chebox0" ><input class="checkbox" type="checkbox" onclick="test(this);"></td>';
                   trStr += '<td contenteditable="true">' + heh[i].url + '</td>';//对应数组表的字段值
                   trStr += '<td>' + heh[i].size + '</td>';
                   trStr += '<td>' + heh[i].time + '</td>';
                   trStr += '</tr>';
        }
        // console.log(trStr);
        $("#document_table").html(trStr);
      });
    }).on("error", (err) => {
      console.log("Error: " + err.message);
    });
});

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
        var hhh = JSON.parse(data);
        var xxx = JSON.parse(data).info;
        var heh = JSON.parse(hhh.info);
        var trStr = '';//动态拼接table
        var searchWord = $.trim($("#searchInput").val());
        var upsearchWord = searchWord.toUpperCase();
        console.log(upsearchWord);
        for (var i = 0; i < heh.length; i++) {//循环遍历出json对象中的每一个数据并显示在对应的td中
          var type = "." + heh[i].type;
          console.log(type);
          var wholefileName = searchWord + type;
          var fileName = heh[i].url.replace(type,"");
          var upfileName = fileName.toUpperCase();
          console.log(upfileName);
          if (heh[i].url == searchWord || heh[i].url == wholefileName || upsearchWord == upfileName || searchWord == heh[i].type) {
            trStr += '<tr class="example">';//拼接处规范的表格形式
            trStr += '<td id="chebox0" ><input class="checkbox" type="checkbox" onclick="test(this);"></td>';
            trStr += '<td contenteditable="true">' + heh[i].url + '</td>';//对应数组表的字段值
            trStr += '<td>' + heh[i].size + '</td>';
            trStr += '<td>' + heh[i].time + '</td>';
            trStr += '</tr>';
          }else if (upfileName.search(upsearchWord) != -1) {
            trStr += '<tr class="example">';//拼接处规范的表格形式
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
            //get deleted file's name
            console.log(fileName);
            const http = require('http');
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

                        var hhh = JSON.parse(data);
                        if (hhh.status == "success") {
                          alert("Revert Succeed!");
                        } else {
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

rename.addEventListener("click", function(){
  //set MessageBox
    dialog.showMessageBox({
        type: 'info',
        title: 'Rename File',
        message: "Do you want to rename?",
        buttons: ['YES', 'NO']
    },(res,checked) =>{
        if (res === 0){
            var fileName1 = window.parent.str;
            console.log(fileName1);
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
                          alert("Rename Succeed!");
                        } else {
                          alert(hhh.info);
                        }
                        console.log(hhh);
                        // console.log(hhh.status);
                        // console.log(hhh.info);
                    });
                }).on("error", (err) => {
                console.log("Error: " + err.message);
            });
        }
    })
});

addnewfolder.addEventListener("click", function(){
   function getCurrentTime(){
  		var dtCur = new Date();
  		var yearCur = dtCur.getFullYear();
  		var monCur = dtCur.getMonth() + 1;
  		var dayCur = dtCur.getDate();
  		var hCur = dtCur.getHours();
  		var mCur = dtCur.getMinutes();
  		var sCur = dtCur.getSeconds();
  		timeCur = yearCur + "-" + (monCur < 10 ? "0" + monCur : monCur) + "-"
  				+ (dayCur < 10 ? "0" + dayCur : dayCur) + " " + (hCur < 10 ? "0" + hCur : hCur)
  				+ ":" + (mCur < 10 ? "0" + mCur : mCur) + ":" + (sCur < 10 ? "0" + sCur : sCur);
  		//alert(timeCur);// 输出时间
  		return timeCur;
    }

  function getNewFolderName(){


  }
  $("#document_table").append(createNewRow("New folder", 0, getCurrentTime()));
  // 根据填充数据创建表格行DOM元素，获取表格行DOM元素
  function createNewRow(cellName,cellSize,cellTime){
  	var str= "<tr><td>" + '<input class="checkbox" type="checkbox" onclick="test(this);"></td>'+ "<td>" + cellName + "</td><td>" + cellSize + "</td><td>"
  	+ cellTime + "</td></tr>";
  	return str;
}
});
