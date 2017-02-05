<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Imagen extends Model{
	protected $table = 'imagen';
	protected $fillable = [
        'bitmap',
    ];
	

}