function handleRequest(action) {
	var table = document.getElementById("creationTable");

	var inputs = table.getElementsByTagName("input");

	data = {};

	for (var i = 0; i < inputs.length; i++) {
		data[inputs[i].name] = inputs[i].value;
	}

	load(action, function(xhr) {
		if (action == 'SearchIdentity')
			showSearchResult(xhr);
		else
			showMessageSucess(xhr);
	});
}


function handleEdit() {

	data = document.querySelector('input[name="selection"]:checked').id;
	load('UpdateIdentity', function(xhr) {
		
		window.location.href = "UpdateIdentity?id=" + data;
	});
}

function showSearchResult(xhr) {
	data = JSON.parse(xhr.responseText);

	var table = document.getElementById("resultsBody");
	table.innerHTML = '';

	if (data[0].status == '408') {
		window.location.href = "index.html";
	} else if (data[0].status == '200') {

		for (var i = 1; i < data.length; i++) {

			var tr = document.createElement("tr");
			
			var td0 = document.createElement("td");
			td0.innerHTML = "<input name=\"selection\" id=\"" +  data[i].id + "\"  type=\"radio\"/>"

			var td1 = document.createElement("td");
			td1.innerHTML = data[i].fname;
			var td2 = document.createElement("td");
			td2.innerHTML = data[i].lname;
			var td3 = document.createElement("td");
			td3.innerHTML = data[i].email;
			var td4 = document.createElement("td");
			td4.innerHTML = data[i].birthdate;

			tr.appendChild(td0);
			tr.appendChild(td1);
			tr.appendChild(td2);
			tr.appendChild(td3);
			tr.appendChild(td4);

			table.appendChild(tr);
		}
	}
}

function showMessageSucess(xhr) {

	myData = JSON.parse(xhr.responseText);

	if (myData.status == '408') {
		window.location.href = "home.html";
	} else if (myData.status == '200') {
		var msg = document.getElementById("pageTitle");

		var h3 = document.createElement("h3");
		h3.innerHTML = "Identity created :)";

		msg.appendChild(h3);
	}
}

function load(url, callback) {
	var xhr;

	if (typeof XMLHttpRequest !== 'undefined')
		xhr = new XMLHttpRequest();
	else {
		var versions = [ "MSXML2.XmlHttp.5.0", "MSXML2.XmlHttp.4.0",
				"MSXML2.XmlHttp.3.0", "MSXML2.XmlHttp.2.0", "Microsoft.XmlHttp" ]

		for (var i = 0, len = versions.length; i < len; i++) {
			try {
				xhr = new ActiveXObject(versions[i]);
				break;
			} catch (e) {
			}
		} // end for
	}

	xhr.onreadystatechange = ensureReadiness;

	function ensureReadiness() {
		if (xhr.readyState < 4) {
			return;
		}

		if (xhr.status !== 200) {
			return;
		}

		// all is well
		if (xhr.readyState === 4) {
			callback(xhr);
		}
	}

	xhr.open('POST', url, true);
	xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	xhr.send("data=" + JSON.stringify(data));
}

function login(action) {

	var user = document.getElementById("inputEmail");
	var password = document.getElementById("inputPassword");

	data = {};

	data["user"] = user.value;
	data["password"] = password.value;

	load(action, function(xhr) {
		myData = JSON.parse(xhr.responseText);

		if (myData.status == '200') {
			window.location.href = "home.html";
		}
	});
}
