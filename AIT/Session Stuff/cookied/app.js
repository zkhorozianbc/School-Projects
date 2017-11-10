const express = require('express');
const cookied = require('./cookied.js');
const app = express();
const bodyParser = require('body-parser');
const colorOptions = require('./colors.js');
const path = require('path');

app.set('view engine', 'hbs');
app.set('view options','layout');

const publicPath = path.resolve(__dirname,"views");
app.use(express.static(publicPath));
app.use(bodyParser.urlencoded({extended:false}));


app.use(cookied.parseCookies);
app.use(cookied.manageSession);



app.get('/', function(req, res) {
    const favColor = req.hwSession.favColor || '#fff';
    res.render('index', {favColor:favColor, 'sessionData':JSON.stringify(req.hwSession, null, 2)});
});

app.get('/preferences', function(req, res) {
    const favColor = req.hwSession.favColor || '#fff';
    const options = colorOptions.map(function(c) {
        c.selected = c.hex === favColor; 
        return c;
    });
    res.render('preferences.hbs', {favColor:favColor, options: options});
});

app.post('/preferences', function(req, res) {
    req.hwSession.favColor = req.body.favColor;
    res.redirect('/preferences');
});

app.listen(3000);
