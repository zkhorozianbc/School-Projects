// app.js
require('./db.js');
const mongoose = require('mongoose');
const movies = mongoose.model('movies');
const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const path = require('path');
const session = require('express-session');
const sessionOptions = { 
	secret: 'secret for signing session id', 
	saveUninitialized: false, 
	resave: false 
};

app.use(session(sessionOptions));

app.set('view engine', 'hbs');
app.set('view options','layout');

const publicPath = path.resolve(__dirname,"views");
app.use(express.static(publicPath));
app.use(bodyParser.urlencoded({extended:false}));

session.addedMovies = [];

app.get('/movies', function(req,res) {
	movies.find((err,data) => {
		let filteredMovies = [];
		if (req.query.field !== undefined && req.query.value !== undefined && req.query.field !== "" && req.query.value !== "")  {
			data.forEach((movie) => {
				if (movie[req.query.field] == req.query.value) {
					filteredMovies.push(movie);
				}
			});
		}
		else {
			filteredMovies = data;
		}
		res.render('movs', {'movies': filteredMovies});
	});
});

app.get('/mymovies', function(req,res) {
	res.render('mymovies', {'movies': session.addedMovies});
})

app.get('/', function(req,res) {
	res.redirect('movies');
});

app.get('/add', function(req,res) {
	res.render('add');
});

app.post('/add', function(req,res) {
	session.addedMovies.push(req.body);
	movies.create(req.body);
	res.redirect('/movies');
});



app.listen(3000);