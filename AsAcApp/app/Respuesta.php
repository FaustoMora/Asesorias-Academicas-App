<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Respuesta extends Model{
	protected $table = 'respuesta';
	protected $fillable = [
        'detalle','es_correcta',
    ];

	public function Pregunta(){
		return $this->hasOne('App\Pregunta', 'id', 'pregunta_id');
	}

	public function Imagen(){
		return $this->hasOne('App\Imagen', 'id', 'imagen_id');
	}
}