<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Tema extends Model{
	protected $table = 'tema';
	protected $columns = array('id','nombre','descripcion','materia_id');
	protected $fillable = [
        'nombre','descripcion',
    ];

    protected $hidden = ['materia_id'];


	public function Materia(){
		return $this->belongsTo('App\Materia');
	}

	public function Preguntas(){
		return $this->hasMany('App\Pregunta', 'tema_id', 'id');
	}

}