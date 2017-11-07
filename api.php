<?php

if(isset($_GET['send_notification'])){
   send_notification ();
}

// my old server key
// AAAACRYuojw:APA91bHFhU-eZnL17lualkiJZ2e6DjxgJ82cN1hEPiV4aTfMLFF0WYV1HG5IpbdXG9urfCeVhHE5zRu84uK_awqrRhFvUbtXLCkNU_GxgIH99eBJq13OH57QKjsGcegeIN7CGw7njRPX');
// Justins server key
// AAAA1O0HZKE:APA91bE3hazO-2rTgEzbIE4j2ManIGygi1H5AyIzOIgG56W1TjouhMerTO12lWbWG7aJ2UdkEqmBzqz4XtzGsEdIzuZXM6580UZ62fDXDKFGm0Nh4-ouJcr6-YcK6d_6TWh6sQ6on9iv
function send_notification()
{
	echo 'Hello there Justin';
define( 'API_ACCESS_KEY', 'AAAA1O0HZKE:APA91bE3hazO-2rTgEzbIE4j2ManIGygi1H5AyIzOIgG56W1TjouhMerTO12lWbWG7aJ2UdkEqmBzqz4XtzGsEdIzuZXM6580UZ62fDXDKFGm0Nh4-ouJcr6-YcK6d_6TWh6sQ6on9iv');
 //   $registrationIds = ;
#prep the bundle
     $msg = array
          (
		'body' 	=> 'Yo Justin! Wassup!',
		'title'	=> 'New Push Notification',
             	
          );
	$fields = array
			(
				'to'		=> $_REQUEST['token'],
				'notification'	=> $msg
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
?>