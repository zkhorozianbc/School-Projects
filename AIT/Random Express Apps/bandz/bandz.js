// bandz.js
const express = require('express');
const path = require('path');
const bodyParser = require('body-parser');

const app = express();
const HOST = '127.0.0.1';
const PORT = 3000;


app.set('view engine','hbs');
app.set('view options','layout');
const publicPath = path.resolve(__dirname,"public");
app.use(express.static(publicPath));
app.use(bodyParser.urlencoded({extended:false}));

const bands = [];

function populateBands() {
	bands.push({"name": "Lil Yachty", "genre": "Hip-Hop", "location": "Atlanta, GA", "description": "Not really a boat"});
	bands.push({"name": "Tata Simonyan", "genre": "Pop", "location": "Glendale, CA", "description": "Armenian Pop Star"});
	bands.push({"name": "DJ FiveStar", "genre": "Electronic", "location": "Austin, TX", "description": "Post-Post-Modern"});
	bands.push({"name": "Osufsen", "genre": "Metal", "location": "Southampton, NY", "description": "Indie Bougie"});

}
populateBands();

function updateBands(bandAttrs) {
	if (Object.keys(bandAttrs).length !== 4) {
		return;
	}
	bands.push(bandAttrs);
}

function filterBands(filterGenre) {
	const requestedGenre = filterGenre["filterGenre"];
	if (Object.keys(filterGenre).length === 0) {
		return bands;
	}
	return bands.filter((band) => {
		return band["genre"] === requestedGenre;
	});
}


app.get('/', function(req,res) {
	const bandsFiltered = filterBands(req.query);
	res.render('recs.hbs', {"items": bandsFiltered});
});

app.post('/', function(req,res) {
	updateBands(req.body);
	res.redirect(301,'/');
});





app.listen(PORT,HOST);
