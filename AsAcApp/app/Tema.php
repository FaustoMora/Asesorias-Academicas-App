<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Tema extends Model{
	protected $table = 'tema';
	protected $fillable = [
        'nombre','descripcion',
    ];

	public function User(){
		return $this->hasOne('App\User', 'id', 'user_id');
	}
}