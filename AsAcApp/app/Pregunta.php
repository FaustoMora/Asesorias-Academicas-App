<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Pregunta extends Model{
	protected $table = 'pregunta';
	protected $fillable = [
        'detalle',
    ];

	public function Tema(){
		return $this->hasOne('App\Tema', 'id', 'tema_id');
	}

	public function Imagen(){
		return $this->hasOne('App\Imagen', 'id', 'imagen_id');
	}
}