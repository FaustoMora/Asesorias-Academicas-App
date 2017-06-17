<?php

namespace App\Http\Controllers;

use App\User;
use App\Tema;
use App\Materia;
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
		$user = User::find($user->id);
		$temas = [];
		if($user->materias()->get()->count() > 0){
            $temas = Tema::whereIn('fk_materia',$user->materias()->pluck('id')->toArray())->get();
        }
		return view('layouts.temas')->with('temas',$temas)->with('materias',$user->materias()->get());
	}

	public function update_detail(Request $request, $id_tema)
	{

		$name = $request->input('nombreTema');
		$description = $request->input('descripcionTema');

		if ( !empty ( $id_tema ) ) {
			$tema = Tema::find($id_tema);
			if(!is_null($tema)){
				$tema->nombre = $name;
				$tema->descripcion = $description;
				$tema->save();
			}
			if($request->hasFile('formulasDoc')){
				$file = $request->file('formulasDoc');
				$docName = ''.str_random(10).'_'.$name.'.'.$file->getClientOriginalExtension();
				$file->move('uploads/docs/',$docName);
				$tema->formulas = 'uploads/docs/'.$docName;
				$tema->save();
			}
		}

		return redirect('/MisTemas');
	}


	public function delete_detail(Request $request, $id_tema)
	{
		if ( !empty ( $id_tema ) ) {
			$tema = Tema::find($id_tema);
			if(!is_null($tema)){
				$tema->delete();
			}
		}

		return redirect('/MisTemas');
	}

	public function create_detail(Request $request)
	{
		$name = $request->input('nombreTema');
		$id_materia = $request->input('id_materia');
		$description = $request->input('descripcionTema');

		if(!is_null($id_materia)){
			$materia = Materia::find($id_materia);
			if(!is_null($materia)){

				$tema = new Tema;
				$tema->nombre = $name;
				$tema->descripcion = $description;
				$tema->materia()->associate($materia);
				$tema->save();

				if($request->hasFile('formulasDoc')){
					$file = $request->file('formulasDoc');
					$docName = ''.str_random(10).'.'.$file->getClientOriginalExtension();
					$file->move('uploads/docs/',$docName);
					$tema->formulas = 'uploads/docs/'.$docName;
					$tema->save();
				}
			}
		}


		return redirect('/MisTemas');
	}

}
