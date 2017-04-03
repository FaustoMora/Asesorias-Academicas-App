<?php

namespace App\Http\Controllers;

use App\User;
use App\Tema;
use Illuminate\Http\Request;
use App\Http\Controllers\View;
use Illuminate\Support\Facades\Auth;

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
		$temas = Tema::where("user_id",$user->id)->get();
		//$user = User::find($user->id);
		return view('layouts.temas')->with('temas',$temas);
	}

	public function update_detail(Request $request, $id_tema)
	{

		$name = $request->input('nombreTema');
		$description = $request->input('descripcionTema');

		if ( !empty ( $id_tema ) ) {
    		$user = $request->user();
    		$user = User::find($user->id);
			$tema = Tema::find($id_tema);
			if($user->id == $tema->user()->first()->id){
				$tema->nombre = $name;
				$tema->descripcion = $description;
				$tema->save();
			}
		}

		return redirect('/MisTemas');	
	}

	public function create_detail(Request $request)
	{
		$name = $request->input('nombreTema');
		$description = $request->input('descripcionTema');
		
		$user = $request->user();
		$user = User::find($user->id);
		$tema = new Tema;
		$tema->nombre = $name;
		$tema->descripcion = $description;
		$tema->user()->associate($user);
		$tema->save();

		return redirect('/MisTemas');
	}

}