<?php

// listen on api and push notification to FCM





/*
if(isset($_GET['send_notification'])){
   send_notification ();
}
*/


$messageText = "Automated FCM Message";

function send_notification($world)
{
	echo 'World '.$world.' has changed';
define( 'API_ACCESS_KEY', 'AAAA1O0HZKE:APA91bE3hazO-2rTgEzbIE4j2ManIGygi1H5AyIzOIgG56W1TjouhMerTO12lWbWG7aJ2UdkEqmBzqz4XtzGsEdIzuZXM6580UZ62fDXDKFGm0Nh4-ouJcr6-YcK6d_6TWh6sQ6on9iv');

#prep the bundle
$topic = "2007";
     $msg = array
          (

		'body' 	=> 'Automated FCM Message',
		'title'	=> 'Push Data Every Hour',
             	
          );
	$fields = array
			(
				'to'		=> '/topics/'.$world.'',
				// ex. 'to'		=> '/topics/2007',
		        	'data' => array(
		               	 "message" => $messageText,
					 "contents" => "Push Data Automaticly"
		       	 )	

			);
	
	$headers = array
			(
				'Authorization: key=' . API_ACCESS_KEY,
				'Content-Type: application/json'
			);
#Send Reponse To FireBase Server	
		$ch = curl_init();
		curl_setopt( $ch,CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
		curl_setopt( $ch,CURLOPT_POST, true );
		curl_setopt( $ch,CURLOPT_HTTPHEADER, $headers );
		curl_setopt( $ch,CURLOPT_RETURNTRANSFER, true );
		curl_setopt( $ch,CURLOPT_SSL_VERIFYPEER, false );
		curl_setopt( $ch,CURLOPT_POSTFIELDS, json_encode( $fields ) );
		$result = curl_exec($ch );
		echo $result;
		curl_close( $ch );
}
// end of send_notification function


// listen for changes on the servers

// get previous api data
$old_json = file_get_contents('worlds.json');


$json = json_decode(file_get_contents("https://api.guildwars2.com/v2/worlds?ids=all"), true);
$last_json = json_decode($old_json, true);
$difference = array_diff_assoc($json, $last_json);

// print_r($difference); 


foreach($last_json as $key=>$value){
    if($value['population'] != $json[$key]['population']){
        // value is changed
		
	$world_ID = $json[$key]['id'];
		
		$world_before = $value['population'];
		$world_now = $json[$key]['population'];

		
		if($world_before == "Full" && $world_now != "Full"){
		// call function to send notification for this topic/server/world
		send_notification($world_ID);
		}else{
		// don't send notification to servers that was not changed from full to something less
		}
				echo "<br /><br />";
				echo $world_ID;
				echo "<br />";
				echo "value has changed";
				echo "<br />";
				echo $json[$key]['name']." - ".$json[$key]['population'];
				echo "<br />";
				echo "Old value was: ".$last_json[$key]['population'];
				echo "<br />";
				echo "New value is: ".$json[$key]['population'];
				echo "<br /><br />";
    }else{
					// value is not changed
				echo "value is the same";
				echo "<br />";
    }
}

// store new value in file
$json = file_get_contents('https://api.guildwars2.com/v2/worlds?ids=all');
$data = json_decode($json);
file_put_contents('worlds.json', json_encode($data));

// call the function once again for the the notification test
send_notification("alarmtest");


?>