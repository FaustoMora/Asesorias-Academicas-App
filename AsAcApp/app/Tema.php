<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Tema extends Model{
	protected $table = 'tema';
	protected $columns = array('id','nombre','descripcion','user_id');
	protected $fillable = [
        'nombre','descripcion',
    ];
    protected $hidden = ['user_id'];

	public function User(){
		return $this->belongsTo('App\User');
	}

}