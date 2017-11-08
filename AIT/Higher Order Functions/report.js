const fs = require('fs');
const rev = require("./yelpfunc.js");
const request = require('request');



function parseBusiness(data) {
	data = data.trim();
	const restaurants = [];
	data = data.split("\n");
	
	const addRestuarants = function(datum) {
		if (datum.length === 0) {
			return;
		}
		if (datum.length === 1) {
			if (typeof JSON.parse(datum[0])["nextFile"] !== "undefined") {
				let nextFileName = JSON.parse(datum[0])["nextFile"];
				getURLData(baseURL + nextFileName);
				return;
			}
		}


		const r = JSON.parse(datum[0]);
		restaurants.push(r);
		return addRestuarants(datum.slice(1));
	};
	addRestuarants(data);

	console.log(rev.processYelpData(restaurants));


}

function getURLData(url) {

	request(url, function(err,response,body) {
		if (err) {
			console.log(err);
		}
		else {
			console.log("\n==========\nurl:\thttps://" + baseURL + url + "\n==========\n");
			parseBusiness(body);
		}
	});
}
var baseURL = "https://foureyes.github.io/csci-ua.0480-fall2017-003/homework/02/";
var firstFileName = "086e27c89913c5c2dde62b6cdd5a27d2.json";
getURLData(baseURL + firstFileName);
// function readBusiness() {
// 	const filePathBusiness = "/Users/zachkhorozian/Documents/business.json";
// 	fs.readFile(filePathBusiness,'utf8',function(err,data) {
// 		if (err) {
// 			console.log(err);
// 		}
// 		else {
// 			parseBusiness(data);
// 		}
// 	});
// }
// readBusiness();