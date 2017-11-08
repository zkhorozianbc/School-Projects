const fs = require('fs');
function readHexNames() {
	const fileName = __dirname + "/colors.txt";
	const dict = {};
	fs.readFile(fileName,'utf8', (err,data) => {
		if (err) {
			console.log(err);
		}
		else {
			data = data.split("\n");
			data.forEach((line) => {
				line = line.split(",");
				dict[line[1]] = line[0];
			});
		}
	});
	return dict;
	
}
const hexCodeToName = readHexNames();

class Color {

	constructor(req) {
		this.r = parseInt(req.query.red,10);
		this.g = parseInt(req.query.green,10);
		this.b = parseInt(req.query.blue,10);
		this.total = parseInt(req.query.total,10);
		this.templateData = [];
		this.badInputMessage = "\"Hey, \"Red\", \"Green\", and \"Blue\" should be from 0 through 255,\n and the \"How Many?\" should be between 2 and 10!\"";
		this.isValid = true;
		

		if (Object.keys(req.query).length !== 0) {
			if (this.getInputValidity()) {
				this.createColorAttributes(this.r,this.g,this.b);
				this.generateRandomHex().forEach((rgb) => {
					this.createColorAttributes(...rgb);
				});
			}
			else {
				this.templateData = {"badInputMessage": this.badInputMessage};
				this.isValid = false;
			}
		}


	}

	generateRandomHex() {
		const randomColors = [];
		for (let i = 1; i < this.total; i++) {
			const rgb = [];
			for (let j = 0; j < 3; j++) {
				const randomDecimal = Math.floor(Math.random() * 256);
				rgb.push(randomDecimal);
			}
			randomColors.push(rgb);

		}
		return randomColors;
		
	}
 
	createColorAttributes(red,green,blue) {
		const colorObj = {};

		const hexCode = this.rgbToHex(red,green,blue);
		let hexName = "";
		if (hexCode in hexCodeToName) {
			hexName = hexCodeToName[hexCode];
		}
		const rgbString = "(" + red + "," + green + "," + blue + ")";

		colorObj["r"] = red;
		colorObj["g"] = green;
		colorObj["b"] = blue;
		colorObj["ir"] = 255;
		colorObj["ig"] = 255;
		colorObj["ib"] = 255;
		if (red + green + blue > (128*3)) {
			colorObj["ir"] = 0;
			colorObj["ig"] = 0;
			colorObj["ib"] = 0;
		}

		colorObj["hexCode"] = hexCode;
		colorObj["hexName"] = hexName;
		colorObj["rgbString"] = rgbString;

		this.templateData.push(colorObj);

	}

	rgbToHex(red, green, blue) {
		const rgb = "#" + ((1 << 24) + (red << 16) + (green << 8) + blue).toString(16).slice(1).toUpperCase();
		return rgb;
	}


	getInputValidity() {
		return (this.r >=0 && this.r <= 255) && 
				(this.g >=0 && this.g <= 255) &&
				(this.b >=0 && this.b <= 255) &&
				(this.total >=2 && this.total <= 10);
	}





}




module.exports = {Color: Color, hexCodeToName: hexCodeToName};