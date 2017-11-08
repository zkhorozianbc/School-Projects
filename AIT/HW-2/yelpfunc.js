// yelpfunc.js
function processYelpData(restaurants) {
	let analysis = "";

	const stars = restaurants.map((x) => x["stars"]);
	const avgStars = stars.reduce((a,b) => a+b,0) / stars.length;

	analysis += "* Average Rating of the dataset: " + String(avgStars) + "\n\n";
	



	const nvPizzaJoints = restaurants.filter(function(r) {
		return r["city"] === "Las Vegas" && r["state"] === "NV" && r["categories"].indexOf("Pizza") >= 0;
	});
	let nvPizzaStars = "* All restaurants in Las Vegas, NV that serve pizza\n";
	nvPizzaJoints.forEach(function(r) {
		nvPizzaStars += "\t* " + r["name"] + " (* " + String(r["stars"]) + " stars *)\n";
	});

	analysis += nvPizzaStars + "\n";




	const mexicanSpots = restaurants.filter(function(r) {
		return r["categories"].indexOf("Mexican") >= 0;
	});
	const topTwoMexicanSpots = mexicanSpots.sort((a,b) => a["review_count"] - b["review_count"]).slice(-2);
	
	const goldMexican = "\t* " + topTwoMexicanSpots[1]["name"] + ", " + topTwoMexicanSpots[1]["city"] + " (" + topTwoMexicanSpots[1]["state"] + "), " + String(topTwoMexicanSpots[1]["review_count"]) + "reviews (* " + String(topTwoMexicanSpots[1]["stars"]) + " stars *)\n";
	const silverMexican = "\t* " + topTwoMexicanSpots[0]["name"] + ", " + topTwoMexicanSpots[0]["city"] + " (" + topTwoMexicanSpots[0]["state"] + "), " + String(topTwoMexicanSpots[0]["review_count"]) + "reviews (* " + String(topTwoMexicanSpots[0]["stars"]) + " stars *)\n";

	analysis += "* The two highest reviewed Mexican serving restaurants are:\n" + goldMexican + silverMexican + "\n";
	


	const nameCounts = {};

	restaurants.forEach(function(r) {
		if (r["name"] in nameCounts) { 
			nameCounts[r["name"]] += 1;
		}
		else {
			nameCounts[r["name"]] = 1;
		}
	});

	let maxName = "";
	let maxNameCount = "";
	const getMaxNameCount = function(names) {
		if (names.length === 0) {
			return;
		}
		const k = names[0];
		if (nameCounts[k] > maxNameCount) {
			maxName = k;
			maxNameCount = nameCounts[k];
		}
		getMaxNameCount(names.slice(1));
	};
	getMaxNameCount(Object.keys(nameCounts));

	// * The most common name in the dataset:
 //    * Starbucks is the most common business and appears 8 times in the dataset
 analysis += "* The most common name in the dataset:\n" + "\t* " + maxName + " is the most common business and appears " + String(maxNameCount) + " times in the dataset\n\n";


	
	const stateCounts = {};

	restaurants.forEach(function(r) {
		if (r["state"] in stateCounts) {
			stateCounts[r["state"]] += 1;
		}
		else {
			stateCounts[r["state"]] = 1;
		}
	});

	const stateSortedByName = Object.keys(stateCounts).sort();

 	let stateStr = "* Restaurant count by state\n";
 	stateSortedByName.forEach(function(s) {
 		stateStr += "\t* " + s + ": " + String(stateCounts[s]) + "\n";
 	});
 	analysis += stateStr.slice(0,-1);


	
	return analysis;
	




}

module.exports = {processYelpData: processYelpData};