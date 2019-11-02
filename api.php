<?php

// listen on api and push notification to FCM

$messageText = "Automated FCM Message";
/* $world is the string that holds the id of the server that we send to. Same as topic name */
function send_notification($world)
{
	/* this line is not important, it just prints some text */ 
	echo 'World '.$world.' has changed';
	/* this line defines the access key to use FCM*/
define( 'API_ACCESS_KEY', 'AAAA1O0HZKE:APA91bE3hazO-2rTgEzbIE4j2ManIGygi1H5AyIzOIgG56W1TjouhMerTO12lWbWG7aJ2UdkEqmBzqz4XtzGsEdIzuZXM6580UZ62fDXDKFGm0Nh4-ouJcr6-YcK6d_6TWh6sQ6on9iv');

/* prepare the bundle */

     $msg = array
		(
			'body' 	=> 'Automated FCM Message',
			'title'	=> 'Push Data Every Hour',
		);
	$fields = array
		(	
			'to' => '/topics/'.$world.'', /* /topics/... is destination address in FCM Service */
			'data' => array(
			'message' => $messageText,
			'contents' => 'Push Data Automaticly'
			)	
		);
	$headers = array
		(
			'Authorization: key=' . API_ACCESS_KEY,
			'Content-Type: application/json'
		);
		
/* Send Reponse To FireBase Server */
/* don't bother changing this, all of this is standard for FCM */
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
/* end of send_notification function */


/* now lets listen for changes on the servers/worlds */

// get previous api data that we saved last time, that is one minute ago, or less...
$old_json = file_get_contents('worlds.json');

/* get the new data from Guild Wars 2 api service */
$json = json_decode(file_get_contents("https://api.guildwars2.com/v2/worlds?ids=all"), true);
$last_json = json_decode($old_json, true);
$difference = array_diff_assoc($json, $last_json);


/* now lets iterate with a foreach loop, through each of the worlds, one by one and compare the old value with the new value */
foreach($last_json as $key=>$value){
    if($value['population'] != $json[$key]['population']){
        // value is changed
		
		$world_ID = $json[$key]['id'];
		
		/* previous data on this peticular world */
		$world_before = $value['population'];
		/* new data on the same world */
		$world_now = $json[$key]['population'];

		/* if the world has gone from full to something other than full, e.g. VeryHigh or High, 
		then we call the function to order FCM Service to send notifications to all users who have set an alarm for this world */
		if($world_before == "Full" && $world_now != "Full"){
		// call function to send notification for this topic/server/world
		send_notification($world_ID);
		}else{
		/* if there is some other kind of change. e.g. this world has gone from High to Medium then dont nofify users of this change
		in other words, don't send notification to servers that was not changed from full to something less  */
		}
		/* all of this echo stuff is just for testing to see if we have changes on the worlds, while calling this file in browser */
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


/* put the new api data from Guild Wars 2 into the same file and write over the old data */
$json = file_get_contents('https://api.guildwars2.com/v2/worlds?ids=all');
$data = json_decode($json);
file_put_contents('worlds.json', json_encode($data));

/* call the function once again for the the notification test, that users can activate in Settings in the app.
This will run each time this file runs(cron jobs set to 30 seconds) to give users the option to 
know what it's like when the alarm goes off for a world that they listen to */
send_notification("alarmtest");


?>