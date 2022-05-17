extends Node

var mod_name: String = "Lindholmen"

const something = preload("res://environments/lindholmen.gd")
var http = something.new()

var json = http.get_input()

#north of lindholmen
var spawn1Coordinates = Transform(Basis(),Vector3(-942.974, 51.919, -263.916))
#lindholmen parking lot
var spawn2Coordinates = Transform(Basis(), Vector3(-845.957, 49.522, -503.148))
#infront of Patricia building
var spawn3Coordinates = Transform(Basis(), Vector3(-736.535, 49.522, -588.707))

func init(global) -> void:
	global.register_environment("Lindholmen", load("res://environments/lindholmen.tscn"))

#Input handler which checks what spawn point was selected

func _input(event):
	var spawn_location = json
	#do some handling with the parsed json
	#set spawn point
	match spawn_location:
		"spawn location 1":
			$VehicleSpawn.global_transform = spawn1Coordinates
			print("opening spawn 1")
		"spawn location 2":
			$VehicleSpawn.global_transform = spawn2Coordinates
			print("opening spawn 2")
		"spawn location 3":
			$VehicleSpawn.global_transform = spawn3Coordinates
			print("opening spawn 3")
