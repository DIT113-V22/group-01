extends Reference

var mod_name: String = "Lindholmen"

func init(global) -> void:
	global.register_environment("Lindholmen", load("res://environments/lindholmen.tscn"))
