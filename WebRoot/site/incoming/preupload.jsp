<%@ page pageEncoding="utf-8"%>
<div style="font: 15px Verdana; background: #B0E2FF; color: #333">
<p><strong>通过excel文件上传</strong></p>

<div id="filelist">你的浏览器不支持Flash, Silverlight 或者 HTML5</div>
<br />
<div id="container"  style="font-family:'微软雅黑'">
    <a id="pickfiles" href="javascript:;" >[选择文件]</a> 
    <a id="uploadfiles" href="javascript:;" >[点击上传]</a>
</div>

<br />
<pre id="console" style="display:none"></pre>

<script type="text/javascript">
// Custom example logic

var uploader = new plupload.Uploader({
	runtimes : 'html5,html4,flash,silverlight',
	browse_button : 'pickfiles', // you can pass in id...
	container: document.getElementById('container'), // ... or DOM Element itself
	file_data_name : 'pointExcel',
	url : 'addpreexcel',
	
	filters : {
		max_file_size : '10mb',
		prevent_duplicates : true,
		mime_types: [
			{title : "Excel files", extensions : "xls"}	
		]
	},

	init: {
		PostInit: function() {
			document.getElementById('filelist').innerHTML = '';

			document.getElementById('uploadfiles').onclick = function() {
				uploader.start();
				return false;
			};
		},

		FilesAdded: function(up, files) {
			plupload.each(files, function(file) {
				document.getElementById('filelist').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';
			});
		},

		UploadProgress: function(up, file) {
			document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
		},

		Error: function(up, err) {
			document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
		}
	}
});
uploader.init();

</script>

</div>