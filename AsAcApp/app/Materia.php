<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Materia extends Model{
	protected $table = 'materia';
	protected $columns = array('id','nombre_materia','icono_materia','user_id');
	protected $fillable = [
        'nombre_materia','icono_materia',

    ];
    protected $hidden = ['user_id'];

	public function User(){
		return $this->belongsTo('App\User');
	}

	public function Temas(){
		return $this->hasMany('App\Tema', 'materia_id', 'id');
	}

}