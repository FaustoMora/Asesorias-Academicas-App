<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Materia extends Model{
	protected $table = 'materia';
	protected $columns = array('id','nombre','icono','fk_user');
	protected $fillable = [
        'nombre','icono',

    ];
    protected $hidden = ['fk_user'];

	public function User(){
		return $this->belongsTo('App\User');
	}

	public function Temas(){
		return $this->hasMany('App\Tema', 'fk_materia', 'id');
	}

}
