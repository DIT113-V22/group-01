extends Reference

var mod_name: String = "Lindholmen"

func init(global) -> void:
	Mqtt.connect_to_server()
	Mqtt.subscribe("/smartcar/heartbeat")
	Mqtt.subscribe("/smartcar/spawnLocation")
	print(Mqtt.check_msg())
	global.register_environment("Lindholmen", load("res://environments/lindholmen.tscn"))

#Input handler which checks what spawn point was selected
func _input(event):
	var spawn_location = Mqtt.check_msg()
	#set spawn point
	match spawn_location:
		"spawn location 1":
			#set vehicle spawn
			print("opening spawn 1")
		"spawn location 2":
			#set vehicle spawn
			print("opening spawn 2")
		"spawn location 3":
			#set vehicle spawn
			print("opening spawn 3")
		_:
			print("error")
	
	
	## Get the instance of the car and set it to the spawn point
	
	#if event.is_action_pressed("spawn location 1"):
	#	print("Changing Spawn point...")
	#	#$VehicleSpawn.set(spawnLocation1.instance())
	#else:
	#	print("No spawn location found")
