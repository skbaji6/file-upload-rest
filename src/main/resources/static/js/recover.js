'use strict';

var singleFileUploadError = document.querySelector('#singleFileUploadError');
var singleFileUploadSuccess = document.querySelector('#singleFileUploadSuccess');

var modalBody = document.querySelector('#modalBody');

function recoverFile(fileId) {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/recoverFile/"+fileId);

    xhr.onload = function() {
        console.log(xhr.responseText);
        var response = JSON.parse(xhr.responseText);
        singleFileUploadSuccess.innerHTML = getContent(response);
        singleFileUploadSuccess.style.display = "block";
    }
    xhr.send();
}

function getAllDeletedFiles() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/getAllDeletedFiles");

    xhr.onload = function() {
        console.log(xhr.responseText);
        var response = JSON.parse(xhr.responseText);
        singleFileUploadSuccess.innerHTML = getContent(response);
        singleFileUploadSuccess.style.display = "block";
    }
    xhr.send();
}
function getContent(response) {
	var content='<table>';
	
	for(var i = 0; i < response.length; i++) { 
		content += "<tr>";
		if(response[i].fileType == 'Image'){
			content += "<td><img src='"+response[i].fileDownloadUri+"/download' alt='Forest' style='width:80px;height:80px'></td>";			
		}else if(response[i].fileType == 'Pdf'){
			content +="<td><img src='/images/pdf.png' alt='"+response[i].fileName+"' style='width:80px;height:80px'></td>" ;
		}
		content +=	"<td>"+response[i].fileName+"</td>" +
					"<td><a class='button button-primary' onclick='recoverFile("+response[i].id+");'>Recover</a></td>" +
					"</tr>";
	}
	content+='</table>';
	return content;
}

window.onload = function WindowLoad(event) {
	getAllDeletedFiles();
}

