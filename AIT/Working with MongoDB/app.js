require('./db.js');
const express = require('express');
const app = express();
const mongoose = require('mongoose');
const ImagePost = mongoose.model('ImagePost');
const bodyParser = require('body-parser');
const path = require('path');

app.set('view engine', 'hbs');
app.set('view options','layout');

const publicPath = path.resolve(__dirname,"views");
app.use(express.static(publicPath));
app.use(bodyParser.urlencoded({extended:false}));



app.get('/', function(req,res) {
	res.redirect('image-posts');
});


app.get('/image-posts', function(req,res) {
	const query = {};

	ImagePost.find(query, function(err,data) {
		res.render('image-posts', {"posts": data});
	});
});


app.get('/image-post/:slug', function(req,res) {
	const query = {'slug': req.params.slug};

	ImagePost.findOne(query,(err,data) => {
		if (err) {
			res.status(404).send(err);
		}
		else {
			res.render('image-post', {"posts": data});
		}
		
	});
});


app.post('/image-post/:slug', function(req,res) {
	const query = {'slug': req.params.slug};

	let update = {};
	if ('add' in req.body) {
		update = {$push: {'images': {'caption': req.body['caption'], 'url': req.body['url']}}};
	}
	else {
		const toRemove = [];
		for (const k in req.body) {
			if (k !== "delete") {
				toRemove.push(k);
			}
		}
		update = {$pull: {'images': {'_id':{$in: toRemove}}}};
	}
	
	ImagePost.findOneAndUpdate(query, update, function(err) {
		if (err) {
			res.status(404).send(err);
		} else {
			res.redirect('/image-post/' + req.params.slug);
		}
	});
});


app.post('/image-posts', function(req,res) {
	const title = req.body.title;
	const images = [];
	for (let i = 1; i <= 3; i++) {
		if (req.body['url'+i].length !== 0) {
			images.push({'caption': req.body['caption' + i], 'url': req.body['url'+i]});
		}
	}
	const post = new ImagePost({'title': title, 'images': images});
	post.save(function(err) {
		if (err) {
			res.status(400).send(err);
		}
		else {
			res.redirect('/image-posts');
		}
	});
	
	
});



app.listen(3000);