extends Spatial

func init_cam_pos() -> Transform:
	return $CamPosition.global_transform
	
## Here instead of $VehicleSpawn.global_transform --> spawnLocation1.instance()
#### For this to work: couple the spawnLocation1 node to the env
func get_spawn_position(hint: String) -> Transform:
	return $VehicleSpawn.global_transform
