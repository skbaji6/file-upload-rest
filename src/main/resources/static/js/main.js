'use strict';

var singleUploadForm = document.querySelector('#singleUploadForm');
var singleFileUploadInput = document.querySelector('#singleFileUploadInput');
var singleFileUploadError = document.querySelector('#singleFileUploadError');
var singleFileUploadSuccess = document.querySelector('#singleFileUploadSuccess');

var modalBody = document.querySelector('#modalBody');

function uploadSingleFile(file) {
    var formData = new FormData();
    formData.append("file", file);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/uploadFile");

    xhr.onload = function() {
        //console.log(xhr.responseText);
        var response = JSON.parse(xhr.responseText);
        if(xhr.status == 200) {
            singleFileUploadError.style.display = "none";
            singleFileUploadInput.value='';
            var content = "<p>File Uploaded Successfully.</p>";
            content+=getContent(response);
            singleFileUploadSuccess.innerHTML = content;
            singleFileUploadSuccess.style.display = "block";
        } else if(xhr.status == 400){
        	singleFileUploadSuccess.style.display = "none";
            singleFileUploadError.innerHTML = (response && response.message) || "Fileformat not supported. Supported formats are jpg,jpeg,png,pdf ";
            getAllFiles();
        } else {
            singleFileUploadSuccess.style.display = "none";
            singleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
        }
    }

    xhr.send(formData);
}

singleUploadForm.addEventListener('submit', function(event){
    var files = singleFileUploadInput.files;
    if(files.length === 0) {
        singleFileUploadError.innerHTML = "Please select a file";
        singleFileUploadError.style.display = "block";
    }
    uploadSingleFile(files[0]);
    event.preventDefault();
}, true);

function openModal(fileProp){
	var modalHtml=
	'<p> File Name : '+fileProp.fileName+'</p>'+
	'<p> File Download : <a href="' + fileProp.fileDownloadUri +'/download" target="_blank">Download</a></p>'+
	'<p> File Type : '+fileProp.fileType+'</p>'+
	'<p> File Size : '+bytesToSize(fileProp.size)+'</p>'+
	'<p> Creation Date : '+fileProp.creationDate+'</p>'+
	'<p> Modification Date : '+fileProp.modificationDate+'</p>';
	if(fileProp.fileType == 'Image'){
		modalHtml +='<p> Image Resolution : '+fileProp.imageResolution+'</p>'+
		'<p> Image Mean Of Red : '+fileProp.meanOfRed+'</p>'+
		'<p> Image Mean Of Green : '+fileProp.meanOfGreen+'</p>'+
		'<p> Image Mean Of Blue : '+fileProp.meanOfBlue+'</p>'+
		'<p> Image Mean Of RGB : '+fileProp.meanOfRGB+'</p>';
		
	}else if (fileProp.fileType == 'Pdf'){
		modalHtml+='<p> Paper Size : '+fileProp.paperSize+'</p>'+
		'<p> Total No of Lines : '+fileProp.totalNoOfLines+'</p>'+
		'<p> Total No of Words : '+fileProp.totalNoOfWords+'</p>';
		'<p> '+fileProp.docStatus+'</p>';
	}
	modalBody.innerHTML =modalHtml;
	overlay.classList.remove("is-hidden");
}

function closeModal(){
	overlay.classList.add("is-hidden");
}

function deleteFile(fileId) {
    var xhr = new XMLHttpRequest();
    xhr.open("DELETE", "/deleteFile/"+fileId);

    xhr.onload = function() {
        //console.log(xhr.responseText);
        var response = JSON.parse(xhr.responseText);
        singleFileUploadSuccess.innerHTML = getContent(response);
        singleFileUploadSuccess.style.display = "block";
    }
    xhr.send();
}

function getAllFiles() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/getAllUploadedFiles");

    xhr.onload = function() {
        //console.log(xhr.responseText);
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
			content += "<td><a href='" + response[i].fileDownloadUri + "/download' target='_blank'><img src='"+response[i].fileDownloadUri+"/download' alt='Forest' style='width:80px;height:80px'></a></td>";			
		}else if(response[i].fileType == 'Pdf'){
			content +="<td><a href='" + response[i].fileDownloadUri + "/download' target='_blank'><img src='/images/pdf.png' alt='"+response[i].fileName+"' style='width:80px;height:80px'></td>" ;
		}
		content +=	"<td><a href='javascript:void(0)' onclick='loadPdf(\""+response[i].fileDownloadUri+"\")'>View</a></td>"+
					"<td><a class='button button-primary' onclick='openModal("+JSON.stringify(response[i])+");'>Show Properties</a></td>" +
					"<td><a class='button button-primary' onclick='deleteFile("+response[i].id+");'>Delete</a></td>" +
					"</tr>";
	}
	content+='</table>';
	return content;
}

function bytesToSize(bytes) {
	   var sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
	   if (bytes == 0) return '0 Byte';
	   var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
	   return Math.round(bytes / Math.pow(1024, i), 2) + ' ' + sizes[i];
	};

window.onload = function WindowLoad(event) {
	getAllFiles();
}

function loadPdf(url){
	var newWin = window.open("","_blank","width=400,height=300");

    /* The html you want to write in the new window. */
    var html = "<body>" +
    		"<script>function resizeIframe(obj)" +
    		" { obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';}" +
    		"</script>" +
    		"<iframe frameBorder='0' src='"+url+"/view' scrolling='auto' onload='resizeIframe(this)' width='1080' height='600'/>" +
    				"</body>";

  /* Get the window's document and write the html content. */
  newWin.document.write(html);
	
}


