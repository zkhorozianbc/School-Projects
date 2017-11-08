const mongoose = require('mongoose');
const URLSlugs = require('mongoose-url-slugs');

const ImagePost = new mongoose.Schema({
	title: String,
	images: [Image]
});

ImagePost.plugin(URLSlugs('title'));

mongoose.model('ImagePost',ImagePost);
mongoose.Promise = global.Promise;
mongoose.connect('mongodb://localhost/hw06',{ useMongoClient: true });