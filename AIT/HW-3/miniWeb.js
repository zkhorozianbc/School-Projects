// miniWeb.js
// define your Request, Response and App objects here
const fs = require('fs');
const net = require('net');
class Request {

	constructor(httpRequest) {
		httpRequest = httpRequest.split("\r\n");
		
		const reqLine = httpRequest[0].split(" ");
		const method = reqLine[0];
		const path = reqLine[1];
		

		const headers = {};
		const endOfHeader = httpRequest.indexOf("");
	
		for (let field of httpRequest.slice(1,endOfHeader)) {
			field = field.split(": ");
			headers[field[0]] = field[1];
		}

		const body = httpRequest.slice(-1)[0];


		this.method = method;
		this.path = path;
		this.headers = headers;
		this.body = body;

	}

	toString() {
		let str = "";
		str += this.method + " " + this.path + " HTTP/1.1\r\n";
		for (let field in this.headers) {
			str += field + ": " + this.headers[field] + "\r\n";
		}
		str += "\r\n" + this.body;
		return str;
	}

}


class Response {

	constructor(socket) {
		this.sock = socket;
		this.headers = {};
		this.body = "";
		this.statusCode = "";
		this.dictOfStatusCodes = {200: "OK", 404: "Not Found", 500: "Internal Server Error",
								400:"Bad Request", 301: "Moved Permanently", 302: 
								"Found", 303: "See Other"};

		this.dictOfContentTypes = {"jpeg": "image/jpeg","jpg": "image/jpeg","png": "image/png",
						"gif": "image/gif","html": "text/html", 
						"css": "text/css", "txt": "text/plain"};

	}

	setHeader(name, value) {
		this.headers[name] = value;
	}

	write(data) {
		this.sock.write(data);
	}

	end(s) {
		this.sock.end(s);
	}

	send(statusCode,body) {
		let httpResponse = "";
		this.statusCode = statusCode;
		this.body = body;

		httpResponse += "HTTP/1.1 " + String(this.statusCode) + " " + this.dictOfStatusCodes[this.statusCode] + "\r\n";
		
		for (let field in this.headers) {
			httpResponse += field + ": " + this.headers[field] + "\r\n";
		}

		httpResponse += "\r\n" + this.body;

		this.end(httpResponse);
		
	}

	writeHead(statusCode) {
		let httpResponse = "";
		this.statusCode = statusCode;

		httpResponse += "HTTP/1.1 " + String(this.statusCode) + " " + this.dictOfStatusCodes[this.statusCode] + "\r\n";

		for (let field in this.headers) {
			httpResponse += field + ": " + this.headers[field] + "\r\n";
		}

		httpResponse += "\r\n";

		this.write(httpResponse);
	}

	redirect(statusCode,url) {
		if (arguments.length === 1) {
			url = arguments[0];
			statusCode = 301;
		}
		this.statusCode = statusCode;
		this.headers["Location"] = url;
	

		this.send(this.statusCode,this.body);

	}



	toString() {
		let httpResponse = "";

		httpResponse += "HTTP/1.1 " + String(this.statusCode) + " " + this.dictOfStatusCodes[this.statusCode] + "\r\n";
		
		for (let field in this.headers) {
			httpResponse += field + ": " + this.headers[field] + "\r\n";
		}

		httpResponse += "\r\n" + this.body;

		return httpResponse;
	}

	sendFile(fileName) {

		let filePath = "";
		let fileExt = fileName.split(".")[1];
		let contentType = this.dictOfContentTypes[fileExt];
		let fileEncoding = {};
		
		if (contentType.indexOf("text") >= 0) {
			fileEncoding["encoding"] = 'utf8';
			if (contentType.indexOf("css") >= 0) {
				filePath = "/Users/zachkhorozian/Documents/zak261-homework03/public" + "/css"+ fileName;
			}
			else {
				filePath = "/Users/zachkhorozian/Documents/zak261-homework03/public" + "/html"+ fileName;
			}
		}
		else {
			filePath = "/Users/zachkhorozian/Documents/zak261-homework03/public" + "/img"+ fileName;
		}
		
		
		this.setHeader("Content-Type",contentType);

	
		fs.readFile(filePath,fileEncoding,(err,data) => {this.handleRead(err,data);});
	}

	handleRead(err,data) {
		if (err) {
			this.send(500,"");
		}
		else {

			this.writeHead(200);
			this.write(data);
			this.end();
		}
	}


}




class App {
	constructor() {
		this.server = net.createServer(this.handleConnection.bind(this));
		this.routes = {};
	}

	get(path,cb) {
		this.routes[path] = cb;
	}

	listen(port,host) {
		this.server.listen(port,host);
	}

	handleConnection(sock) {
		sock.on('data',this.handleRequestData.bind(this,sock));
	}

	handleRequestData(sock, binaryData) {
		let reqString = "" + binaryData;
		let req = new Request(reqString);
		let res = new Response(sock);

		if (!("Host" in req.headers)) {
			res.statusCode = 400;
			res.setHeader("Content-Type","text/plain");
			res.body = "Bad bad bad request";
			res.send(res.statusCode,res.body);
		}

		else if (req.path in this.routes || req.path.slice(0,-1) in this.routes) {
			if (req.path.charAt(req.path.length-1) === "/" && req.path.length > 1) {
				console.log(req.path);
				req.path = req.path.slice(0,-1);
			}
			this.routes[req.path](req,res);
			
		}

		else {
			res.statusCode = 404;
			res.body = "uh oh... 404 page not found!";
			res.setHeader("Content-Type","text/plain");
			res.send(res.statusCode,res.body);
		}


		sock.on('close', this.logResponse.bind(this, req, res));

	}


	logResponse(req,res) {
		console.log(req.method,req.path,res.statusCode, res.dictOfStatusCodes[res.statusCode]);
		
	}





}


module.exports = {Request: Request, Response: Response, App: App}




