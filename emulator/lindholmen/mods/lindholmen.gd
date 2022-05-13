extends Reference

var mod_name: String = "Lindholmen"

func init(global) -> void:
	global.register_environment("Lindholmen", load("res://environments/lindholmen.tscn"))

#Input handler which checks what spawn point was selected
func _input(event):
	## Get the instance of the car and set it to the spawn point
	if event.is_action_pressed("spawn location 1"):
		print("Changing Spawn point...")
		#$VehicleSpawn.set(spawnLocation1.instance())
	else:
		print("No spawn location found")
