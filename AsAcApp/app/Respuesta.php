<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Respuesta extends Model{
	protected $table = 'respuesta';
	protected $columns = array('id','detalle','es_correcta','fk_pregunta','fk_imagen');
	protected $fillable = [
        'detalle','es_correcta',
    ];

	public function Pregunta(){
		return $this->belongsTo('App\Pregunta', 'fk_pregunta', 'id');
	}

	public function Imagen(){
		return $this->hasOne('App\Imagen', 'id', 'fk_imagen');
	}
}
