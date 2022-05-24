extends Spatial

var gravity = 9.8
var capncrunch = Vector3()


# Declare member variables here. Examples:
# var a = 2
# var b = "text"


# Called when the node enters the scene tree for the first time.
func _ready():
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	if not is_on_floor():
		capncrunch.y -= gravity * delta
		
move_and_slide(capncrunch, Vector3.UP)

