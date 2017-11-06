// hoffy.js
const fs = require('fs');

function sum(num1, num2,...numn) {
	if (arguments.length === 0) {
		return 0;
	}
	if (numn.length > 0) {
		return num1 + num2 + sum(...numn);
	}
	else if (!isNaN(num2)) {
		return num1 + num2;
	}
	else {
		return num1;
	}
	

}

function repeatCall(fn, n, arg) {
	if (n > 0) {
		fn(arg);
		repeatCall(fn,n-1,arg);
	}
}

function repeatCallAllArgs(fn, n, args1, ...argsn) {
	if (n > 0) {
		argsn.unshift(args1);
		fn(...argsn);
		repeatCallAllArgs(fn,n-1,argsn[0],...argsn.slice(1));
	}
}



function maybe(fn) {
	return function(...args) {
		const m = function(...as) {
			if (as.length === 0) {
				return true;
			}
			return as[0] !== null && as[0] !== undefined && m(...as.slice(1));
		}; 

		if (m(...args)) {
			return fn(...args);
		}
		else {
			return undefined;
		}
	};
}



function constrainDecorator(fn, min, max) {
	const ha = arguments.length;
	return function(arg) {
		const fnVal = fn(arg);
		if (ha === 1) {
			return fnVal;
		}
		else if (fnVal < min) {
			return min;
		}
		else if (fnVal > max) {
			return max;
		}
		else {
			return fnVal;
		}
	};

}


function limitCallsDecorator(fn, n) {

	return function(...args) {
		n -= 1;
		
		if (n >= 0) {
			return fn(...args);
		}
		else {
			return undefined;
		}
		
	};
}


function filterWith(fn) {
	const l = [];
	return function(args) {
		const f = function(as) {
			if (as.length === 0) {
				return l;
			}
			if (fn(as[0])) {
				l.push(as[0]);
			}
			return f(as.slice(1));
		};
		return f(args);
	};
}

function simpleINIParse(s) {
	s = s.split("\n");
	const sob = {};

	const f = function(k) {
		if (k.length === 0) {
			return sob;
		}
		let pair = k[0];
 
		if (pair.indexOf("=") >= 0) {
			pair = pair.split("=");
			sob[pair[0]] = pair[1];		
		} 

		return f(k.slice(1));
	};
	return f(s);
}



function readFileWith(fn) {
	return function(fileName,callback) {		
		return fs.readFile(fileName,'utf8',function(err,data) {
			if (err) {
				callback(err,undefined);
			}
			else {
				callback(err,fn(data));
			}
		});
	};
}


module.exports = {
	sum:sum,
	repeatCall: repeatCall,
	repeatCallAllArgs: repeatCallAllArgs,
	maybe: maybe,
	constrainDecorator: constrainDecorator,
	limitCallsDecorator: limitCallsDecorator,
	filterWith: filterWith,
	simpleINIParse: simpleINIParse,
	readFileWith: readFileWith
};



