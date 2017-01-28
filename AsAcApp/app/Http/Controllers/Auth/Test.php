<?php

	use App\Http\Controllers\Controller;


class Test extends Controller {
	
	public function hola_mundo(){
		return view('hola_mundo');
	}
}