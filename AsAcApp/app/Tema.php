<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Tema extends Model{
	protected $table = 'tema';
	protected $columns = array('id','nombre','descripcion','formulas','fk_materia');
	protected $fillable = [
        'nombre','descripcion','formulas',
    ];

    protected $hidden = ['fk_materia'];


	public function Materia(){
		return $this->belongsTo('App\Materia','fk_materia','id');
	}

	public function Tests(){
		return $this->hasMany('App\Test', 'fk_tema', 'id');
	}

}
