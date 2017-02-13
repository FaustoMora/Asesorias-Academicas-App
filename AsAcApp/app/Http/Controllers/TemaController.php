<?php

namespace App\Http\Controllers;

use App\User;
use App\Tema;
use Illuminate\Http\Request;
use App\Http\Controllers\View;

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
		return view('layouts.temas');
	}

	public function get_detail(Request $request, $id_tema)
	{
		return redirect('/home');	
	}

	public function create_detail(Request $request)
	{
		
		return redirect('/home');
	}

}