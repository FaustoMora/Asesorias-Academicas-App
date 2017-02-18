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
		//$user = Auth::user();
		$user = $request->user();
		error_log($user);
		error_log($user->temas()->count());
		return view('layouts.temas',['temas' => $user->temas()]);
	}

	public function get_detail($id_tema)
	{
		return redirect('/home');	
	}

	public function create_detail()
	{
		
		return redirect('/home');
	}

}