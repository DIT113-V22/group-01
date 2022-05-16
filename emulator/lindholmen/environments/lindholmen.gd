extends Spatial

var json

func _ready():
	$HTTPRequest.connect("request_completed", self, "_on_request_completed")
	
func _on_request_completed(result, response_code, headers, body):
	if(response_code > 399):
		print(response_code)
		return
	json = JSON.parse(body.get_string_from_utf8())
	print(json.result)

func init_cam_pos() -> Transform:
	return $CamPosition.global_transform
	
## Here instead of $VehicleSpawn.global_transform --> spawnLocation1.instance()
#### For this to work: couple the spawnLocation1 node to the env
func get_spawn_position(hint: String) -> Transform:
	return $VehicleSpawn.global_transform

func get_input():
	return json
	
func get_spawn1():
	return $SpawnLoc1.position
