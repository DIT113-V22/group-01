extends Node

var mod_name: String = "Lindholmen"

#var onReady = load("res://addons/plugins/mqtt.gd")
#var mqtt = onReady._init()

const DefaultManager = preload("res://addons/plugins/mqtt.gd") # Relative path
onready var mqtt = DefaultManager.new()

func init(global) -> void:
	mqtt.connect_to_server()
	mqtt.subscribe("/smartcar/heartbeat")
	mqtt.subscribe("/smartcar/spawnLocation")
	print(mqtt.check_msg())
	global.register_environment("Lindholmen", load("res://environments/lindholmen.tscn"))

#Input handler which checks what spawn point was selected
func _input(event):
	#var spawn1 = $SpawnLoc1.position
	var spawn_location = mqtt.check_msg()
	#set spawn point
	match spawn_location:
		"spawn location 1":
			#lolol
			#$VehicleSpawn.set("spawn 1", spawn1)
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
