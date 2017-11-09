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
/* GET users listing. */
router.get('/', function(req, res, next) {
  // res.send('respond with a resource');
  connection.query('select id, email, name, mobileNo, age from user',
  	function(err, results, fields){
  		if(err){
  			res.send(JSON.stringify(err));
  		}else{
  			res.send(JSON.stringify(results));
  		}
  	});
});
// specific usr list request:   GET    - /users/id
router.get('/:id', function(req, res, next){
	connection.query('select id, email, name, mobileNo, age from user where id=?',
		[req.params.id],function(err, results, fields){
			if (err){
				res.send(JSON.stringify(err));
			} else {
				if( results.length > 0){
					res.send(JSON.stringify(results));
				} else {
					res.send(JSON.stringify({}));
				}
				
			}	
		});
});
// Insert user  : POST   - /users
var crypto = require('crypto');
router.post('/', function(req, res,next){
	var email = req.body.email;
	var password = req.body.password;
	var name = req.body.name;
	var mobileNo = req.body.mobileNo;
	var age = req.body.age;
	console.log(email+ ', ' +password+', '+name+', '+mobileNo+', '+age);
	var hash = crypto.createHash("sha512").update(password).digest('base64');
	console.log(hash);
	connection.query(
		'insert into user(email,password,name,mobileNo, age) values(?,?,?,?,?)',
		[email, hash, name, mobileNo, age ],
		function(err, result){
			if (err){
				res.send(JSON.stringify(err));
			} else {
				res.send(JSON.stringify(result));
			}	
		});
	// res.send(JSON.stringify({email:email, password:hash, name:name, age:age}));
});

// update user  :       PUT    - /users/id
router.put('/:id', function(req,res,next){
	var email = req.body.email;
	var password = req.body.password;
	var name = req.body.name;
	var mobileNo = req.body.mobileNo;
	var age = req.body.age;
	console.log(email+ ', ' +password+', '+name+', '+mobileNo+', '+age);

	var query = 'update user set ';
	var conditions = [];
	if( email != undefined){
		query += "email=?,";	conditions.push(email);
	}
	if( password != undefined){
		var hash = crypto.createHash("sha512").update(password).digest('base64');
		query += "password=?,"; conditions.push(hash);
	}
	if(name != undefined){
		query += "name=?,"; 	conditions.push(name);
	}
	if(mobileNo != undefined){
		query += "mobileNo=?"; 		conditions.push(mobileNo); 
	}
	if(age != undefined){
		query += "age=?"; 		conditions.push(age); 
	}
	if( query[query.length-1] == ',') 
		query = query.substring(0, query.length-1);
	query += " where id=?";
	console.log(query);
	console.log(conditions);

	conditions.push(req.params.id);
	connection.query(query, conditions,
	// connection.query(
	// 	'update user set email=?,password=?,name=?,age=? where id=?',
	// 	[email, hash, name, age, req.params.id ],
		function(err, result){
			if(err){
				res.send(JSON.stringify(err));
			}else{
				res.send(JSON.stringify(result));
			}
		})
	// res.send(JSON.stringify({id:req.params.id}));

});
// Delete user :       DELETE - /users/id
router.delete('/:id', function(req,res,next){
	connection.query('delete from user where id=?',
		[req.params.id], function(err, result){
			if(err){
				res.send(JSON.stringify(err));
			}else{
				res.send(JSON.stringify(result));
			}
		});
	// res.send(JSON.stringify({id:req.params.id}));
});

module.exports = router;
