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

// 전체 디바이스 목록 조회
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

// 측정 채널 정보 조회ㅣ
router.get('/:id', function(req, res, next){
	connection.query('select * from channel where id=?', 
		[req.params.id], function(err, results, fields){
			if(err)
				res.send(JSON.stringify(err));
				else{
					if( results.length > 0){
						res.send(JSON.stringify(results[0]));
					}else{
						res.send(JSON.stringify({}));
					}
				}
		});
});

// 채널 등록
router.post('/', function(req, res, next){
	// var channel_id = req.body.channel_id;
	var channelId = req.body.channel_id;
	var channelName = req.body.channelName;
	var channelDesc = req.body.channelDesc;
	var latitude = req.body.latitude;
	var longitude = req.body.longitude;
	var field1 = req.body.field1;
	var field2 = req.body.field2;
	var ReadApiKey = req.body.ReadApiKey;
	var WriteApiKey = req.body.WriteApiKey;
	var isPublic = req.body.isPublic;
	var saveInterval = req.body.saveInterval;
	// var update_at = Date.toLocaleString();
	// var create_at = Date.toLocaleString();
	
	// var sql = "\'insert into channel\("+
	//           'channel_id'+','  +'channelName'+',' +'channelDesc'+',' +'latitude'+','    +'longitude'+',' + 
	//           'field1'+','      +'field2'+','      +'ReadApiKey' +',' +'WriteApiKey'+',' +'isPublic'+',' +
	//           'saveInterval';

// 			  'saveInterval'+','+ Date.toLocaleString()+','+ Date.toLocaleString();

	// res.send(JSON.stringify(sql));	
	// console.log(sql);	  
	connection.query(
		// 'insert into channel(`\
		// channel_id, channelName, channelDesc, latitude, longitude `\
		// field1, field2, ReadApiKey, WriteApiKey, isPublic,`\
		// saveInterval, update_at, create_at) `\
		// 'insert into channel("sql") values(?,?,?,?,?,?,?,?,?,?,?,?,?)',
		'insert into channel(channelId, channelName, channelDesc, latitude, longitude,field1, field2, ReadApiKey, WriteApiKey, isPublic, saveInterval) values(?,?,?,?,?,?,?,?,?,?,?)',
		[
		channelId, channelName, channelDesc, latitude,    longitude,
		field1,     field2,      ReadApiKey,  WriteApiKey, isPublic,
		saveInterval
		], function(err, result){
			if(err)
				res.send(JSON.stringify(err));
			else
				res.send(JSON.stringify(result));
		});

});

router.delete('/:id', function(req,res,next){
	connection.query('delete from channel where id=?',
		[req.params.id], function(err, result){
			if(err)
				res.send(JSON.stringify(err));
			else
				res.send(JSON.stringify(result));
		});
})
module.exports = router;
