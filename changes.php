<?php


// get previous api data
$old_json = file_get_contents('worlds.json');


$json = json_decode(file_get_contents("https://api.guildwars2.com/v2/worlds?ids=all"), true);
$last_json = json_decode($old_json, true);
$difference = array_diff_assoc($json, $last_json);

// print_r($difference); 


foreach($last_json as $key=>$value){
    if($value['population'] != $json[$key]['population']){
        // value is changed
	echo "value has changed";
	echo "<br />";
	echo $json[$key]['name']." - ".$json[$key]['population'];
	echo "<br />";
	echo "Old value was: ".$last_json[$key]['population'];
	echo "<br />";
	echo "New value is: ".$json[$key]['population'];
	echo "<br /><br />";
    }else{
	echo "<br />";
        // value is not changed
	echo "value is the same";
	echo "<br /><br />";
    }
}


$json = file_get_contents('https://api.guildwars2.com/v2/worlds?ids=all');
$data = json_decode($json);
file_put_contents('worlds.json', json_encode($data));

?>