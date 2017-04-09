<?php

namespace App\Http\Controllers;

use App\User;
use App\Materia;
use Illuminate\Http\Request;
use App\Http\Controllers\View;
use Illuminate\Support\Facades\Auth;

/**
* 
*/
class MateriaController extends Controller
{
	
	public function __construct()
	{
		$this->middleware('auth');
	}

	public function get_list(Request $request)
	{	
		//$user = Auth::user();
		$user = $request->user();
		$user = User::find($user->id);
		return view('layouts.materias')->with('materias',$user->materias()->get());
	}

	public function update_detail(Request $request, $id_materia)
	{

		$name = $request->input('nombre');

		if ( !empty ( $id_materia ) ) {
    		$user = $request->user();
    		$user = User::find($user->id);
			$materia = Materia::find($id_materia);
			if($user->id == $materia->user()->first()->id){
				$materia->nombre_materia = $name;
				$materia->save();
			}
		}

		return redirect('/MisMaterias');	
	}


	public function delete_detail(Request $request, $id_materia)
	{

		if ( !empty ( $id_materia ) ) {
    		$user = $request->user();
    		$user = User::find($user->id);
			$materia = Materia::find($id_materia);
			if($user->id == $materia->user()->first()->id){
				$materia->delete();
			}
		}

		return redirect('/MisMaterias');	
	}


	public function create_detail(Request $request)
	{
		$name = $request->input('nombre');
		
		$user = $request->user();
		$user = User::find($user->id);
		$materia = new Materia;
		$materia->nombre_materia = $name;
		$materia->user()->associate($user);
		$materia->save();

		return redirect('/MisMaterias');
	}

}