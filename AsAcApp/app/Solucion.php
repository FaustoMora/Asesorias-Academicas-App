<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Solucion extends Model
{
    protected $table = 'solucion';

	public function Pregunta(){
		return $this->hasOne('App\Pregunta', 'id', 'pregunta_id');
	}

	public function Imagen(){
		return $this->hasOne('App\Imagen', 'id', 'imagen_id');
	}
}
