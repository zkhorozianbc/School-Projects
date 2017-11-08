// db.js
const mongoose = require('mongoose');

const movies = new mongoose.Schema({
	title: String,
	director: String,
	year: Number
});

mongoose.model('movies', movies);
mongoose.connect('mongodb://localhost/hw05',{ useMongoClient: true });
