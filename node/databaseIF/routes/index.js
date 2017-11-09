var express = require('express');
var router = express.Router();

var mysql      = require('mysql');
var connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'root',
  password : 'test1234',
  database : 'ha_iot'
});
connection.connect();

/* GET home page. */
router.get('/', function(req, res, next) {
  connection.query('select * from channel',
 		function(err, results, fields){
 			if(err){
 				res.send(JSON.stringify(err));
 			} else{
 				res.send(JSON.stringify(results));
 			}
 		});
});

module.exports = router;
