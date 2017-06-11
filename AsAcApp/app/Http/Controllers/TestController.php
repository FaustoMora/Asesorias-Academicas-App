<?php

namespace App\Http\Controllers;

use App\User;
use App\Tema;
use App\Test;
use Illuminate\Http\Request;
use App\Http\Controllers\View;
use Illuminate\Support\Facades\Auth;

/**
*
*/
class TestController extends Controller
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
		$tests = [];
		if($user->materias()->get()->count() > 0){
            $temas = Tema::whereIn('fk_materia',$user->materias()->pluck('id')->toArray())->get();
        }
		if(!is_null($temas) && count($temas) > 0){
			$tests = Test::whereIn('fk_tema',$temas->pluck('id'))->get();
		}
		return view('layouts.tests')->with('tests',$tests)->with('temas',$temas);
	}

	public function update_detail(Request $request, $id_test)
	{

		$name = $request->input('nombre');

		if ( !empty ( $id_test ) ) {
			$test = Test::find($id_test);
			if(!is_null($test)){
				$test->nombre = $name;
				$test->save();
			}
		}

		return redirect('/MisTests');
	}

	public function update_status(Request $request, $id_test)
	{

		if ( !empty ( $id_test ) ) {
			$test = Test::find($id_test);
			if(!is_null($test)){
				if($test->active){
					$test->active = false;
					$test->save();
				}else{
					$test->active = true;
					$test->save();
				}
				return response()->json(NULL, 200, [], JSON_UNESCAPED_UNICODE);
			}
		}
		return response()->json(NULL, 400, [], JSON_UNESCAPED_UNICODE);
	}


	public function delete_detail(Request $request, $id_test)
	{
		if ( !empty ( $id_test ) ) {
			$test = Test::find($id_test);
			if(!is_null($test)){
				$test->delete();
			}
		}

		return redirect('/MisTests');
	}

	public function create_detail(Request $request)
	{
		$name = $request->input('nombre');
		$id_tema = $request->input('id_tema');

		if(!is_null($id_tema)){
			$tema = Tema::find($id_tema);
			if(!is_null($tema)){

				$test = new Test;
				$test->nombre = $name;
				$test->tema()->associate($tema);
				$test->save();
			}
		}


		return redirect('/MisTests');
	}

}
