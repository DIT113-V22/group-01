extends Spatial

# Declare member variables here. Examples:
# var a = 2
# var b = "text"

# Called when the node enters the scene tree for the first time.
func _ready():
	get_viewport().get_camera().far = 2500

# Called every frame. 'delta' is the elapsed time since the previous frame.
#func _process(delta):
#	pass

func init_cam_pos() -> Basis:
	return $CamPosition.global_transform

func get_spawn_position(hint: String) -> Transform:
	return $VehicleSpawn.global_transform
