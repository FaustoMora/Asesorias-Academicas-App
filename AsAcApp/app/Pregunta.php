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

	public function PreguntaImagen(){
		return $this->hasOne('App\Imagen', 'id', 'fk_pregunta_imagen');
	}

	public function SolucionImagen(){
		return $this->hasOne('App\Imagen', 'id', 'fk_solucion_imagen');
	}

}