// fansite.js
// create your own fansite using your miniWeb framework
const App = require('./miniWeb.js').App;
const Request = require('./miniWeb.js').Request;
const Response = require('./miniWeb.js').Response;
const HOST = '127.0.0.1';
const PORT = 8080;
const app = new App();

app.get("/", function(req,res) {
	res.sendFile("/home.html");
});


app.get("/about", function(req,res) {
	res.sendFile("/about.html");
});

app.get("/css/about.css", function(req,res) {
	res.sendFile("/about.css");
});

app.get("/css/base.css", function(req,res) {
	res.sendFile("/base.css");
});

app.get("/rando", function(req,res) {
	res.statusCode = 200;
	res.body = "<img src=\"https://www.theflagshop.co.uk/media/catalog/product/cache/1/thumbnail/9df78eab33525d08d6e5fb8d27136e95/a/l/albania.jpg\"/>";
	res.setHeader("Content-Type","text/html");
	res.send(res.statusCode,res.body);
});

app.get("/image1.jpg", function(req,res) {
	res.sendFile(req.path);
});

app.get("/image2.png", function(req,res) {
	res.sendFile(req.path);
});

app.get("/image3.gif", function(req,res) {
	res.sendFile(req.path);
});

app.get("/home", function(req,res) {
	res.redirect(301, "/");
});


app.listen(PORT, HOST);






