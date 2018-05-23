/**
 * 
 */
function goBack() {
	window.history.back();
}
function dateSetting() {

	var date = document.getElementById("date1");

	var m = new Date(date.value);
	if (parseInt(m.getDay()) == 1) {
		var num2 = new Date(m.getTime() + 86400000);
		var num3 = new Date(m.getTime() + 86400000 * 2);
		var num4 = new Date(m.getTime() + 86400000 * 3);
		var num5 = new Date(m.getTime() + 86400000 * 4);
		var stringDate2 = num2.getFullYear() + "." + (num2.getMonth() + 1)
				+ "." + num2.getDate();
		var stringDate3 = num3.getFullYear() + "." + (num3.getMonth() + 1)
				+ "." + num3.getDate();
		var stringDate4 = num4.getFullYear() + "." + (num4.getMonth() + 1)
				+ "." + num4.getDate();
		var stringDate5 = num5.getFullYear() + "." + (num5.getMonth() + 1)
				+ "." + num5.getDate();

		document.getElementById("date2").innerHTML = stringDate2;
		document.getElementById("date3").innerHTML = stringDate3;
		document.getElementById("date4").innerHTML = stringDate4;
		document.getElementById("date5").innerHTML = stringDate5;
		document.getElementById("date10").innerHTML = date;
	} else {
		alert("Nem hétfőt adtál meg");
		document.getElementById("date2").innerHTML = "Keddi dátum";
		document.getElementById("date3").innerHTML = "Szerdai dátum";
		document.getElementById("date4").innerHTML = "Csütörtöki dátum";
		document.getElementById("date5").innerHTML = "Pénteki dátum";
	}

}
function lathatovaTetel(ID) {
	// változtatott menü alatt lévő az id2
	var id2 = parseInt(ID) + 10000;
	// változatott menü alatt lévő time az id3
	var id3 = parseInt(ID) / 10 + 2000;
	var id4 = parseInt(ID) / 10 + 2000 + 1;
	var id5 = parseInt(ID) / 10 + 2000 - 1;

	if (document.getElementById(ID).selectedIndex == "0") {
		document.getElementById(id2).style.visibility = "hidden";
	} else {
		document.getElementById(id2).style.visibility = "visible";
		document.getElementById(id3).style.visibility = "visible";
		if (id3 % 10 == 1) {
			document.getElementById(id4).style.visibility = "visible";
		} else {
			document.getElementById(id5).style.visibility = "visible";
		}
	}
	// } 111110 11111 11112 12111 12112 13111 13112
	if (parseInt(ID) % 100 == 10 && ID.charAt(1) == 1) {
		if (document.getElementById(ID).selectedIndex == "0"
				&& document.getElementById(parseInt(ID) + 10).selectedIndex == "0") {
			document.getElementById(id3).style.visibility = "hidden";
			document.getElementById(id4).style.visibility = "hidden";
		}
		szinezz(ID);
	}
}
function szinezdVissza(ID) {
	var iDSelect = document.getElementById(ID);
	textOfSelected = iDSelect.options[iDSelect.selectedIndex].value;
	var dHSzin = "white";
	if (ID.charAt(1) == "1" && ID.charAt(4) == "1") {
		if (textOfSelected.includes("ID")) {
		} else {
			document.getElementById(parseInt(ID) / 10).style.backgroundColor = dHSzin;
			document.getElementById(parseInt(ID) / 10 + 1).style.backgroundColor = dHSzin;
			document.getElementById(parseInt(ID) / 10 + 1000).style.backgroundColor = dHSzin;
			document.getElementById(parseInt(ID) / 10 + 1000 + 1).style.backgroundColor = dHSzin;
			document.getElementById(parseInt(ID) / 10 + 2000).style.backgroundColor = dHSzin;
			document.getElementById(parseInt(ID) / 10 + 2000 + 1).style.backgroundColor = dHSzin;
		}
	}
}
function szinezz(ID) {
	szinezzModify(ID);
}
function setDefault() {

	for (i = 1; i < 7; i++) {
		var j = 1;
		for (j = 1; j < 3; j++) {
			var k = 1;
			for (k = 1; k < 6; k++) {
				var l = 1;
				for (l = 1; l < 3; l++) {
					var idForSet = i.toString() + j + k + l + "10";
					document.getElementById(idForSet).selectedIndex = "0";
					document.getElementById(parseInt(idForSet) + 10).selectedIndex = "0";
					if (j == 1) {
						szinezdVissza(idForSet);

					}
					if (j == 2) {
						document.getElementById(idForSet).style.visibility = "hidden";
						document.getElementById(parseInt(idForSet) + 10).style.visibility = "hidden";
						document.getElementById(parseInt(idForSet) + 10000).style.visibility = "hidden";
						document
								.getElementById(parseInt(idForSet) + 10000 + 10).style.visibility = "hidden";
					}
					if (k == 5) {
						break;
					}
				}
			}
		}
	}
	countHetiMuszakszam();
	document.getElementById("alert11").innerHTML = "";
	document.getElementById("alert12").innerHTML = "";
	document.getElementById("alert21").innerHTML = "";
	document.getElementById("alert22").innerHTML = "";
	document.getElementById("alert31").innerHTML = "";
	document.getElementById("alert32").innerHTML = "";
	document.getElementById("alert41").innerHTML = "";
	document.getElementById("alert42").innerHTML = "";
	document.getElementById("alert51").innerHTML = "";
	checkHosszu("111110");
	checkHosszu("112110");
	checkHosszu("113110");
	checkHosszu("114110");
	checkHosszu("115110");

}
function onChange(ID) {
	var idForSet = ID;
	var id2;
	var id3;
	var id4;
	var id5;
	// rendelő
	var i = ID.charAt(0);
	// sor
	var j = ID.charAt(1);
	// nap
	var k = ID.charAt(2);
	// napszak
	var l = ID.charAt(3);
	// orvos / asszisztens
	var m = ID.charAt(4);
	// Hibaüzenet, ha egy napszakban többször van beírva
	checkMultipleAssistant(ID);
	checkHosszu(ID);
	console.log(ID);
	// Első sor
	if (j == 1) {
		console.log(selectedValue(idForSet));
		if (selectedValue(idForSet) != 0) {
			// alatta lévőt láthatóvá teszi
			id2 = parseInt(idForSet) + 10000;

			document.getElementById(id2).style.visibility = "visible";
		} else { // alatta lévőt láthatatlanná teszi
			id2 = parseInt(idForSet) + 10000;
			if (selectedValue(id2) == 0) {
				document.getElementById(id2).style.visibility = "hidden";
			}
		}
		id4 = parseInt(idForSet) + 20000;
		// első sor orvos
		if (m == 1) {

			szinezzModify(idForSet);
			// id3 első sor asszisztens
			id3 = parseInt(idForSet) + 10;
			id5 = parseInt(idForSet) + 20010;

		} else if (m == 2) {
			// id3 első sor asszisztens
			id3 = parseInt(idForSet) - 10;
			id5 = parseInt(idForSet) + 20000 - 10;

		}
		if (selectedValue(idForSet) != 0 || selectedValue(id3) != 0) {
			// id4 orvos idő
			id4 = parseInt(idForSet) + 20000;
			document.getElementById(id4).style.visibility = "visible";
			// id5 asszisztens idő
			document.getElementById(id5).style.visibility = "visible";
		} else {
			document.getElementById(id4).style.visibility = "hidden";
			document.getElementById(id5).style.visibility = "hidden"
		}
	}
	countHetiMuszakszam();
	countHaviMuszakszam();
	

}
function countHetiMuszakszam() {

	var tablaOszlopszam = document.getElementById('hiddenTable').rows.length + 1;
	var hetiMuszakSzam = [];
	hetiMuszakSzam.length = document.getElementById('hiddenTable').rows.length + 1;
	hetiMuszakSzam.fill(0);
	var hetiOraSzam = [];
	hetiOraSzam.length = document.getElementById('hiddenTable').rows.length + 1;
	hetiOraSzam.fill("00:00");
	var index = 0;
	var idForSet = 0;
	var startTime;
	var endTime;
	var timeDiff;
	for (i = 1; i < 7; i++) {
		var j = 1;
		for (j = 1; j < 3; j++) {
			var k = 1;
			for (k = 1; k < 6; k++) {
				var l = 1;
				for (l = 1; l < 3; l++) {
					idForSet = i.toString() + j + k + l + "20";

					idForStartTime = i.toString() + "3" + k + l + "10";
					idForEndTime = i.toString() + "3" + k + l + "20";
					startTime = document.getElementById(idForStartTime).value;
					endTime = document.getElementById(idForEndTime).value;

					index = selectedValue(idForSet);
					hetiMuszakSzam[index] = hetiMuszakSzam[index] + 1;
					hetiOraSzam[index] = timeCalcAdd(hetiOraSzam[index],
							timeCalcSub(startTime, endTime))
					// document.getElementById("date5").innerHTML =
					// hetiMuszakSzam[0];

					// penteken nincs delutan
					if (k == 5) {
						break;
					}
				}
			}
		}
	}
	for (i = 1; i < 7; i++) {
		var j = 1;
		for (j = 1; j < 3; j++) {
			var k = 1;
			for (k = 1; k < 6; k++) {
				var l = 1;
				for (l = 1; l < 3; l++) {
					idForSet = i.toString() + j + k + l + "10";

					idForStartTime = i.toString() + "3" + k + l + "10";
					idForEndTime = i.toString() + "3" + k + l + "20";
					startTime = document.getElementById(idForStartTime).value;
					endTime = document.getElementById(idForEndTime).value;

					index = selectedValue(idForSet);
					hetiMuszakSzam[index] = hetiMuszakSzam[index] + 1;
					hetiOraSzam[index] = timeCalcAdd(hetiOraSzam[index],
							timeCalcSub(startTime, endTime))
					// document.getElementById("date5").innerHTML =
					// hetiMuszakSzam[0];

					// penteken nincs delutan
					if (k == 5) {
						break;
					}
				}
			}
		}
	}
	var elementExists;
	var id = "";
	for (var m = 0; m < tablaOszlopszam; m++) {
		id = "hetiMuszakszam" + m;
		if (document.getElementById(id)) {
			document.getElementById(id).innerHTML = hetiMuszakSzam[m];
		}

	}
	var id = "";
	for (var m = 0; m < tablaOszlopszam; m++) {
		id = "hetiIdo" + m;
		if (document.getElementById(id)) {
			document.getElementById(id).innerHTML = hetiOraSzam[m];

		}
	}
}
function countHaviMuszakszam() {

	var tablaOszlopszam = document.getElementById('hiddenTable').rows.length + 1;
	var haviMuszakSzam = [];
	var haviOraSzam = [];
	haviOraSzam.length = document.getElementById('hiddenTable').rows.length + 1;
	haviOraSzam.fill("00:00");

	var id = "";
	for (var m = 0; m < tablaOszlopszam; m++) {
		id = "haviMuszakSzam" + m;
		id2 = "hetiMuszakszam" + m;
		idStorage="haviMuszakszamStorage"+m;
		if (document.getElementById(id)) {
			document.getElementById(id).innerHTML = parseInt(sessionStorage.getItem(idStorage))
					+ parseInt(document.getElementById(id2).innerHTML);
		}

	}
	
}
function storeMonthlyData() {
	var tablaOszlopszam = document.getElementById('hiddenTable').rows.length;
	var haviMuszakSzam = [];
	var haviMuszakSzamStorage = [];
	haviMuszakSzam.length = document.getElementById('hiddenTable').rows.length + 1;
	haviMuszakSzam.fill(0);
	var haviOraSzam = [];
	haviOraSzam.length = document.getElementById('hiddenTable').rows.length + 1;
	haviOraSzam.fill("00:00");
	var id = "";
	var subID;
	for (var m = 0; m < tablaOszlopszam; m++) {
		id = "haviMuszakSzam" + m;
		id2 = "hetiMuszakszam" + m;
		id3 = "haviMuszakszamStorage" + m;
		if (document.getElementById(id)) {
			subID = document.getElementById(id).innerHTML
					- document.getElementById(id2).innerHTML;
			sessionStorage.setItem(id3, subID);
			document.getElementById(id).innerHTML = sessionStorage.getItem(id3);
		}

	}

}
function timeCalcSub(startTime, endTime) {
	var startTimeHour;
	var startTimeMin;
	var endTimeHour;
	var endTimeMin;
	var resultHour;
	var resultMin;
	var result
	startTimeHour = startTime.split(":")[0];
	startTimeMin = startTime.split(":")[1];
	endTimeHour = endTime.split(":")[0];
	endTimeMin = endTime.split(":")[1];

	if (endTimeMin >= startTimeMin) {
		resultHour = parseInt(endTimeHour) - parseInt(startTimeHour);
		resultMin = parseInt(endTimeMin) - parseInt(startTimeMin);
	} else {
		resultHour = parseInt(endTimeHour) - parseInt(startTimeHour) - 1;
		resultMin = parseInt(endTimeMin) + (60 - parseInt(startTimeMin));
	}
	if (resultMin < 10) {
		resultMin = "0" + resultMin;
	}
	if (resultHour < 10) {
		resultHour = "0" + resultHour;
	}
	result = resultHour + ":" + resultMin;
	return result;
}
function timeCalcAdd(basicTime, addTime) {
	var basicHour;
	var basicMin;
	var addHour;
	var addMin;
	var resultHour;
	var resultMin;
	var result;
	basicHour = basicTime.split(":")[0];
	basicMin = basicTime.split(":")[1];
	addHour = addTime.split(":")[0];
	addMin = addTime.split(":")[1];
	var addedMin = parseInt(basicMin) + parseInt(addMin);
	if (addedMin > 59) {
		resultHour = parseInt(basicHour) + parseInt(addHour) + 1;
		resultMin = parseInt(basicMin) + parseInt(addMin) - 60;
	} else {
		resultHour = parseInt(basicHour) + parseInt(addHour);
		resultMin = parseInt(basicMin) + parseInt(addMin);
	}
	if (resultMin < 10) {
		resultMin = "0" + resultMin;
	}
	if (resultHour < 10) {
		resultHour = "0" + resultHour;
	}
	result = resultHour + ":" + resultMin;
	return result;
}
function setEverything() {
	storeMonthlyData();
	var idForSet;
	var id2;
	var id3;
	var id4;
	var id5;
	// rendelő
	for (var i = 1; i < 7; i++) {
		// sor
		for (var j = 1; j < 4; j++) {
			// nap
			for (var k = 1; k < 6; k++) {
				// napszak
				for (var l = 1; l < 3; l++) {
					if (k == 5 && l == 2) {
						break;
					}
					// orvos/asszisztens
					for (var m = 1; m < 3; m++) {
						idForSet = i.toString() + j + k + l + m + "0";
						// Első sor
						if (j == 1) {
							if (selectedValue(idForSet) != 0) {
								// alatta lévőt láthatóvá teszi
								id2 = parseInt(idForSet) + 10000;

								document.getElementById(id2).style.visibility = "visible";
							} else { // alatta lévőt láthatatlanná teszi
								id2 = parseInt(idForSet) + 10000;

								document.getElementById(id2).style.visibility = "hidden"
							}

							// első sor orvos
							if (m == 1) {
								szinezzModify(idForSet);
								// id3 első sor asszisztens
								id3 = parseInt(idForSet) + 10;
								if (selectedValue(idForSet) != 0
										|| selectedValue(id3) != 0) {
									// id4 orvos idő
									id4 = parseInt(idForSet) + 20000;
									document.getElementById(id4).style.visibility = "visible";
									// id5 asszisztens idő
									id5 = parseInt(idForSet) + 20010;
									document.getElementById(id5).style.visibility = "visible";
								} else {// id4 orvos idő
									id4 = parseInt(idForSet) + 20000;
									document.getElementById(id4).style.visibility = "hidden";
									// id5 asszisztens idő
									id5 = parseInt(idForSet) + 20010;
									document.getElementById(id5).style.visibility = "hidden"
								}
							}
						}
						countHetiMuszakszam();

					}
				}
			}
		}

	}

}
function szinezzModify(ID) {
	var iDSelect = document.getElementById(ID);
	valueOfSelected = iDSelect.options[iDSelect.selectedIndex].value;
	textOfSelected = iDSelect.options[iDSelect.selectedIndex].text;
	var idOfData = "adat" + valueOfSelected;
	var valueOfSelected2 = document.getElementById(idOfData).textContent;
	var szajsebSzin = "green";
	var fogszabiSzin = "orange";
	var DHSzin = "gold";
	var alapszin = "white";
	// feltételrendszer
	if (textOfSelected.includes("DH")) {
		document.getElementById(parseInt(ID) / 10).style.backgroundColor = DHSzin;
		document.getElementById(parseInt(ID) / 10 + 1).style.backgroundColor = DHSzin;
		document.getElementById(parseInt(ID) / 10 + 1000).style.backgroundColor = DHSzin;
		document.getElementById(parseInt(ID) / 10 + 1000 + 1).style.backgroundColor = DHSzin;
		document.getElementById(parseInt(ID) / 10 + 2000).style.backgroundColor = DHSzin;
		document.getElementById(parseInt(ID) / 10 + 2000 + 1).style.backgroundColor = DHSzin;
	} else if (valueOfSelected2.includes("fogszabi1")) {
		document.getElementById(parseInt(ID) / 10).style.backgroundColor = fogszabiSzin;
		document.getElementById(parseInt(ID) / 10 + 1).style.backgroundColor = fogszabiSzin;
		document.getElementById(parseInt(ID) / 10 + 1000).style.backgroundColor = fogszabiSzin;
		document.getElementById(parseInt(ID) / 10 + 1000 + 1).style.backgroundColor = fogszabiSzin;
		document.getElementById(parseInt(ID) / 10 + 2000).style.backgroundColor = fogszabiSzin;
		document.getElementById(parseInt(ID) / 10 + 2000 + 1).style.backgroundColor = fogszabiSzin;
	} else if (valueOfSelected2.includes("szajseb1")) {
		document.getElementById(parseInt(ID) / 10).style.backgroundColor = szajsebSzin;
		document.getElementById(parseInt(ID) / 10 + 1).style.backgroundColor = szajsebSzin;
		document.getElementById(parseInt(ID) / 10 + 1000).style.backgroundColor = szajsebSzin;
		document.getElementById(parseInt(ID) / 10 + 1000 + 1).style.backgroundColor = szajsebSzin;
		document.getElementById(parseInt(ID) / 10 + 2000).style.backgroundColor = szajsebSzin;
		document.getElementById(parseInt(ID) / 10 + 2000 + 1).style.backgroundColor = szajsebSzin;
	} else if (valueOfSelected2.includes("")) {
		document.getElementById(parseInt(ID) / 10).style.backgroundColor = alapszin;
		document.getElementById(parseInt(ID) / 10 + 1).style.backgroundColor = alapszin;
		document.getElementById(parseInt(ID) / 10 + 1000).style.backgroundColor = alapszin;
		document.getElementById(parseInt(ID) / 10 + 1000 + 1).style.backgroundColor = alapszin;
		document.getElementById(parseInt(ID) / 10 + 2000).style.backgroundColor = alapszin;
		document.getElementById(parseInt(ID) / 10 + 2000 + 1).style.backgroundColor = alapszin;
	}
}
function selectedValue(ID) {
	var iDSelect = document.getElementById(ID);
	valueOfSelected = iDSelect.options[iDSelect.selectedIndex].value;
	return valueOfSelected;
}
function checkMultipleAssistant(ID) {
	var value = selectedValue(ID);
	var idCount = [];
	idCount.length = document.getElementById('hiddenTable').rows.length + 1;
	idCount.fill(0);
	// rendelő
	var i = ID.charAt(0);
	// sor
	var j = ID.charAt(1);
	// nap
	var k = ID.charAt(2);
	// napszak
	var l = ID.charAt(3);
	;
	// orvos / asszisztens
	var m = ID.charAt(4);
	var idForCheck;
	var valueIdForCheck;
	var count = 0;
	for (n = 1; n < 7; n++) {
		for (o = 1; o < 3; o++) {
			for (m = 1; m < 3; m++) {
				idForCheck = n.toString() + o + k + l + m + "0";
				idCount[selectedValue(idForCheck)]++;
				valueIdForCheck = selectedValue(idForCheck);
			}
		}
	}
	// orvos / asszisztens
	var m = ID.charAt(4);
	var alert = "alert" + k + l;
	document.getElementById(alert).innerHTML = "";
	for (var b = 1; b < idCount.length; b++) {
		if (idCount[b] > 1) {
			document.getElementById(alert).innerHTML = document
					.getElementById(alert).innerHTML
					+ "!" + idToName(b) + " " + idCount[b] + "x!<br>";
		}
	}
}
function idToName(ID) {
	// EmployeeID-ra kiadja a hozzá tartozó nevet

	var nameSor = "name" + ID;
	return document.getElementById(nameSor).textContent;
}
function nameToID(name) {
	var id = "";

	for (i = 0; i < (document.getElementById('hiddenTable').rows.length + 1); i++) {

		var nameTable = "name" + i;
		if (nameTable == name) {
			id = id + i;
			return document.getElementById(id).textContent;
		}
	}
}
function checkHosszu(ID) {
	var value = selectedValue(ID);
	var idCount = [];
	idCount.length = document.getElementById('hiddenTable').rows.length + 1;
	idCount.fill(false);
	var idCount2 = [];
	idCount2.length = document.getElementById('hiddenTable').rows.length + 1;
	idCount2.fill(false);
	// rendelő
	var i = ID.charAt(0);
	// sor
	var j = ID.charAt(1);
	// nap
	var k = ID.charAt(2);
	// napszak
	var l = ID.charAt(3);
	// orvos / asszisztens
	var m = ID.charAt(4);
	var idForCheck;
	var valueIdForCheck;
	var count = 0;
	if (k != 5) {
		for (n = 1; n < 7; n++) {
			for (o = 1; o < 3; o++) {
				for (l = 1; l < 3; l++) {
					for (m = 1; m < 3; m++) {
						if (l == 1) {
							idForCheck = n.toString() + o + k + l + m + "0";
							idCount[selectedValue(idForCheck)] = true;
							valueIdForCheck = selectedValue(idForCheck);
						} else {
							idForCheck = n.toString() + o + k + l + m + "0";
							idCount2[selectedValue(idForCheck)] = true;
							valueIdForCheck = selectedValue(idForCheck);
						}
					}
				}
			}
		}
		// orvos / asszisztens
		var m = ID.charAt(4);
		var alert = "help" + k;
		document.getElementById(alert).innerHTML = "";
		for (var b = 1; b < idCount.length; b++) {
			if (idCount[b] && idCount2[b]) {
				document.getElementById(alert).innerHTML = document
						.getElementById(alert).innerHTML
						+ "!" + idToName(b) + " hosszú!<br>";
			}
		}
	}
}