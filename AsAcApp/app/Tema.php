<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Tema extends Model{
	protected $table = 'tema';
	protected $fillable = [
        'nombre','descripcion',
    ];

	public function User(){
		return $this->belongsTo('App\User');
	}
}