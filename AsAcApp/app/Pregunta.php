<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Pregunta extends Model{
	protected $table = 'pregunta';
	protected $columns = array('id','detalle','tema_id','user_id','fk_pregunta_imagen','fk_solucion_imagen');
	protected $fillable = [
        'detalle',
    ];

    protected $hidden = ['fk_pregunta_imagen','fk_solucion_imagen'];

	public function Tema(){
		return $this->hasOne('App\Tema', 'id', 'tema_id');
	}


	public function PreguntaImagen(){
		return $this->hasOne('App\Imagen', 'id', 'fk_pregunta_imagen');
	}

	public function SolucionImagen(){
		return $this->hasOne('App\Imagen', 'id', 'fk_solucion_imagen');

	}

	public function Respuestas(){
		return $this->hasMany('App\Respuesta', 'pregunta_id', 'id');
	}

}