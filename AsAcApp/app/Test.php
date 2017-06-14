<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Test extends Model{
	protected $table = 'test';
	protected $columns = array('id','nombre','active','fk_tema');
	protected $fillable = [
        'nombre'
    ];

    //protected $hidden = ['fk_tema'];


	public function Tema(){
		return $this->belongsTo('App\Tema','fk_tema','id');
	}

	public function Preguntas(){
		return $this->hasMany('App\Pregunta', 'fk_test', 'id');
	}

}
