var express = require('express');  
var app = express();
var io = require('socket.io')(server);
var r = require('rethinkdb');
var path = require('path');
var http = require('http');

app.use(express.static('app'));
app.set('port', process.env.PORT || 3001);

app.get('/', function(req, res) {  
    res.sendFile(__dirname + 'index.html');
});


var server = http.createServer(app).listen(app.get('port'), function(){
  console.log("Express server listening on port " + app.get('port'));
});
 
var io = require('socket.io').listen(server);

io.on('connection', function(socket) {       
    r.connect({ host: 'rethinkdb', port: 28015 }, function(err, conn) {
		if(err) throw err;
		
		/*all tweets*/
		r.db('tweets').table('tweet').run(conn, function(err, res) {
			if(err) throw err;
			console.log(res);
			res.toArray(function(err, results) {
	            if(err) throw err;
	            io.emit('/tweets', results);
	        })
		});

		/*new tweets*/
		/*r.db('tweets').table('tweet').changes().run(conn, function(err, cursor) {
			console.log('changes');
			if(err) throw err;
		  	cursor.each(function(err, results) {
	            if(err) throw err;
	        	io.emit('/tweets_new', results.new_val);
	        });
					});*/

	});

});




io.on('error', function(err) {
  console.log("Error: " + err);
});
