var downloadUrl = "http://localhost:8888/bbs/vcityResourcesAction.do?action=toDownClient";
function startClient() {
	try {
		var state = document.getElementById("VCITY_OCX").GetState;
		if ( state == 1) {
			// 有安装客户端
			var ret = document.getElementById("VCITY_OCX").StartClient;
				if ( ret != 1) {
					// 启动失败
					alert(ret);
				}
		} else {
				// 没有安装客户端，处理下载
			alert("没有安装客户端，请先下载!");
		window.location.href = downloadUrl;
		}
	} catch (e) {
				// 没有安装客户端，处理下载
		alert("没有安装客户端，请先下载!");
		window.location.href = downloadUrl;
	}
}

function installApplication(appID) {
	try {
		var state = document.getElementById("VCITY_OCX").GetState;
		if ( state == 1) {
			// 有安装客户端
			var ret = document.getElementById("VCITY_OCX").InstallApplication(appID);
			if ( ret != 1) {
					// 安装成功
					alert("安装成功");
			}
		} else {
			// 没有安装客户端，处理下载
			alert("没有安装客户端，请先下载!");
			window.location.href = downloadUrl;
		
		}
	} catch (e) {
			// 没有安装客户端，处理下载
			alert("没有安装客户端，请先下载!");
			window.location.href = downloadUrl;
	}
}

function installMobileModel( mobileID) {
	try {
		var state = document.getElementById("VCITY_OCX").GetState;
		if ( state == 1) {
			// 有安装客户端
			var ret = document.getElementById("VCITY_OCX").InstallMobileModel( mobileID);
			if ( ret != 1) {
				// 安装成功
			}
		} else {
		alert("没有安装客户端，请先下载!");
			window.location.href = downloadUrl;
		}
	} catch (e) {
	alert("没有安装客户端，请先下载!");
			window.location.href = downloadUrl;
	}
}