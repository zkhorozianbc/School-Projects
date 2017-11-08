// colors.js
const express = require('express');
const path = require('path');
const bodyParser = require('body-parser');
const Color = require('./colorlib.js').Color;

const app = express();
const HOST = '127.0.0.1';
const PORT = 3000;


app.set('view engine','hbs');
app.set('view options','layout');
const publicPath = path.resolve(__dirname,"public");
app.use(express.static(publicPath));
app.use(bodyParser.urlencoded({extended:false}));


app.get('/',function(req,res) {
	res.redirect(301,'/colors');
});

app.get('/colors', function(req,res) {
	const color = new Color(req);
	res.render('home.hbs', {"items": {"colors": color.templateData, "validity": color.isValid}});

});

app.get('/about', function(req,res) {
	res.render('about.hbs');
});

app.listen(PORT,HOST);




