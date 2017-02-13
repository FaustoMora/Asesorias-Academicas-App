<?php

namespace App\Http\Controllers;

use App\User;
use App\Tema;
use Illuminate\Http\Request;

/**
* 
*/
class TemaController extends Controller
{
	
	public function __construct()
	{
		$this->middleware('auth');
	}

	public function get_list(Request $request)
	{
		error_log('holi get list');
		return redirect('/home');
	}

	public function get_detail(Request $request, $id_tema)
	{
		error_log('holi get detail' + $id_tema);
		return redirect('/home');	
	}

	public function create_detail(Request $request)
	{
		
		return redirect('/home');
	}

}