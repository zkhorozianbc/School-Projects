const uuid = require('node-uuid');
const store = {};

// manageSession and parseCookies
function parseCookies(req,res,next) {
	req.hwCookies = {};
	const cookies = req.get("Cookie");
	if (cookies !== undefined) {
		cookies.split("&").forEach(function(c) {
			const item = c.split("=");
			req.hwCookies[item[0]] = item[1];
		});
	}

	next();
}

function manageSession(req,res,next) {
	let sessionid = req.hwCookies["sessionid"];
	req.hwSession = {};
	if (sessionid !== undefined && sessionid in store) {
		req.hwSession = store[sessionid];
		console.log("session already exists: " + sessionid);
	}
	else {
		sessionid = uuid.v4();
		store[sessionid] = {};
		res.append("Set-Cookie","sessionid=" + sessionid + "; Expires=Wed, 21 Oct 2018 07:28:00 GMT; HttpOnly");
		console.log("session generated: " + sessionid);
	}
	req.hwSession["sessionid"] = sessionid;
	next();
}

module.exports = {parseCookies: parseCookies,
					manageSession: manageSession};