/**
 * Handle the requests from the HTML/JSP pages
 * @param action Page coming from
 */
function handleRequest(action) {
	var table = document.getElementById("creationTable");

	var inputs = table.getElementsByTagName("input");
	
	var f = document.getElementById('errorMsg');
	f.hidden = true;

	data = {};
	
	var allFieldsFilled = true;
	
	for (var i = 0; i < inputs.length; i++) {
		
		allFieldsFilled &= inputs[i].value != "";			
		
		data[inputs[i].name] = inputs[i].value;
	}
	
	if (action == 'CreateIdentity' && allFieldsFilled == false){
		showStatusMsg("All fields are required!");
		return;
	}

	// list all: sets ID to -1 for listing everything
	if(action == 'ListAll'){
		action = 'SearchIdentity';
		data.id = "-1";
	}
	
	load(action, function(xhr) {
		if (action == 'SearchIdentity')
			showSearchResult(xhr);
		else
			showMessageSucess(xhr);
	});
}

/**
 * Handles only the Edit/Update Identity request
 * @param action the Update Identity request
 */
function handleEdit(action) {

	//get identity selected
	selected = document.querySelector('input[name="selection"]:checked');
	if(selected){
		data = selected.id;
		
		if(action == "UpdateIdentity")
			window.location.href = action + "?id=" + data;
		else{
			load(action, function(xhr) {
				var table = document.getElementById("resultsBody");
				table.innerHTML = '';
				showMessageSucess(xhr);
			});
		}
	}else{
		showStatusMsg("Selected one option!");
	}
}

/**
 * Populates the a table showing the search result
 * 
 * @param xhr XML Http Response
 */
function showSearchResult(xhr) {
	data = JSON.parse(xhr.responseText);

	var table = document.getElementById("resultsBody");
	table.innerHTML = '';

	if (data[0].status == '408') {
		window.location.href = "index.html";
	} else if (data[0].status == '400') { 
		showStatusMsg(data[0].msg);
	}else if (data[0].status == '200') {

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

/**
 * Shows the success or failure message on below the page title
 * 
 * @param xhr XML Http Response
 */
function showMessageSucess(xhr) {

	myData = JSON.parse(xhr.responseText);

	if (myData.status == '408') {
		window.location.href = "home.html";
	} else {
		showStatusMsg(myData.msg);
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

/**
 * Handles the login request
 * 
 * @param action The login action
 */
function login(action) {

	var user = document.getElementById("inputEmail");
	var password = document.getElementById("inputPassword");

	data = {};

	//validate fields:
	if(user.value == "" || password.value == ""){
		var element = document.getElementById('errorDiv');
		element.hidden = false;
		element.innerHTML = "All fields required!!";
	}else{
	
		data["user"] = user.value;
		data["password"] = password.value;
	
		load(action, function(xhr) {
			myData = JSON.parse(xhr.responseText);
	
			if (myData.status == '200') {
				window.location.href = "home.html";
			}else {
				var element = document.getElementById('errorDiv');
				element.hidden = false;
				element.innerHTML = "User or login invalid!!";
			}
		});
	}
}

function showStatusMsg(msg){
	var f = document.getElementById('errorMsg');
	f.hidden = false;
	f.innerHTML = msg;
	window.scrollTo(0, 0);
}
