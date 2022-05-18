extends Spatial

func init_cam_pos() -> Transform:
	return $CamPosition.global_transform

func get_spawn_position(hint: String) -> Transform:
	#$VehicleSpawn.global_transform = Transform(Basis(),Vector3(-942.974, 51.919, -263.916))
	return $VehicleSpawn.global_transform
