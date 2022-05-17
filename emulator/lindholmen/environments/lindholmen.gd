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

func get_spawn_position(hint: String) -> Transform:
	#$VehicleSpawn.global_transform = Transform(Basis(),Vector3(-942.974, 51.919, -263.916))
	return $VehicleSpawn.global_transform

func get_input():
	return json
