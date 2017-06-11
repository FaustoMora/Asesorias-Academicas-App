<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Pregunta extends Model{
	protected $table = 'pregunta';
	protected $columns = array('id','detalle','fk_test','fk_pregunta_imagen','fk_solucion_imagen');
	protected $fillable = [
        'detalle',
    ];

    protected $hidden = ['fk_pregunta_imagen','fk_solucion_imagen'];

	public function Test(){
		return $this->belongsTo('App\Test', 'fk_test', 'id');
	}


	public function PreguntaImagen(){
		return $this->hasOne('App\Imagen', 'id', 'fk_pregunta_imagen');
	}

	public function SolucionImagen(){
		return $this->hasOne('App\Imagen', 'id', 'fk_solucion_imagen');

	}

	public function Respuestas(){
		return $this->hasMany('App\Respuesta', 'fk_pregunta', 'id');
	}

}
