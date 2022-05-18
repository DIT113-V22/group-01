extends Spatial

var json
var packet

var server := UDPServer.new()

#north of lindholmen
var spawn1Coordinates = Transform(Basis(),Vector3(-942.974, 51.919, -263.916))
#lindholmen parking lot
var spawn2Coordinates = Transform(Basis(), Vector3(-845.957, 49.522, -503.148))
#infront of Patricia building
var spawn3Coordinates = Transform(Basis(), Vector3(-736.535, 49.522, -588.707))

func init_cam_pos() -> Transform:
	return $CamPosition.global_transform

func get_spawn_position(hint: String) -> Transform:
	#$VehicleSpawn.global_transform = Transform(Basis(),Vector3(-942.974, 51.919, -263.916))
	return $VehicleSpawn.global_transform

func _ready():
	server.listen(8080, "127.0.0.1")
	
func _process(delta):
	server.poll()
	if server.is_connection_available():
		var peer : PacketPeerUDP = server.take_connection()
		packet = peer.get_packet()
		
		print("Received data: %s" % [packet.get_string_from_utf8()])
		
		peer.put_packet(packet)

func _input(event):
	if(packet != null):
		var spawn_location = packet.get_string_from_utf8()
		#do some handling with the parsed json
		#set spawn point
		match spawn_location:
			"spawn1":
				$VehicleSpawn.global_transform = spawn1Coordinates
				print("opening spawn 1")
			"spawn2":
				$VehicleSpawn.global_transform = spawn2Coordinates
				print("opening spawn 2")
			"spawn3":
				$VehicleSpawn.global_transform = spawn3Coordinates
				print("opening spawn 3")
