<?php

namespace App\Http\Controllers;

use App\User;
use App\Materia;
use App\Tema;
use App\Test;
use App\Pregunta;
use App\Respuesta;
use App\Imagen;
use Illuminate\Http\Request;
use App\Http\Controllers\View;
use Illuminate\Support\Facades\Auth;

/**
*
*/
class PreguntaController extends Controller
{

	public function home_pregunta(Request $request){

		$user = $request->user();
        $user = User::find($user->id);
		$temas = [];
		$tests = [];
		$preguntas = [];

		if($user->materias()->get()->count() > 0){
			$temas = Tema::whereIn('fk_materia',$user->materias()->pluck('id')->toArray())->get();
		}
		if(!is_null($temas) && count($temas) > 0){
			$tests = Test::whereIn('fk_tema',$temas->pluck('id'))->get();
		}
		if(!is_null($tests) && count($tests) > 0){
			$preguntas = Pregunta::whereIn('fk_test',$tests->pluck('id'))->get();
		}

		return view('layouts.preguntas')->with('tests',$tests)->with('preguntas',$preguntas);

	}

	public function crear_pregunta(Request $request){
		$seleccion = $request->input('lstTests');
		$test = null;
		if($seleccion != '0'){
			try{
				$test = Test::where("id",$seleccion)->first();//Obtengo el tema mediante el id de la seleccion
			}catch(\Exception $e){
				$test = "";
			}//Fin del try catch
		}//Fin del if de seleccion
		if($test){
			$desc = $request->input('descPreg');

			//Subida de imagen de pregunta
			$encoded_preg = null;
			if ($request->hasFile('subirImgPreg')) {
				$solucionpreg = $request->file('subirImgPreg');
				$encoded_preg = base64_encode(file_get_contents("/".$solucionpreg, FILE_USE_INCLUDE_PATH,NULL));
			}

	/*		//Subida de imagen de pregunta
			$solucionpreg = $request->file('subirImgPreg');
			$encoded_preg = base64_encode(file_get_contents("/".$solucionpreg, FILE_USE_INCLUDE_PATH,NULL));
	*/
			//Subida de imagen de solución
			$solucion = $request->file('subirSolución');
			$encoded_sol = base64_encode(file_get_contents("/".$solucion, FILE_USE_INCLUDE_PATH,NULL));

			$youtube = $request->input('youtube');
			//Parte de respuestas
			$resp1 = $request->input('opc1');
			$resp2 = $request->input('opc2');
			$resp3 = $request->input('opc3');
			$resp4 = $request->input('opc4');

			//Si son correctas
			$corr1 = ($request->input('correctOption') == 'opc1') ? true : false;
			$corr2 = ($request->input('correctOption') == 'opc2') ? true : false;
			$corr3 = ($request->input('correctOption') == 'opc3') ? true : false;
			$corr4 = ($request->input('correctOption') == 'opc4') ? true : false;

			//Imagen de pregunta
			$imgprg = new Imagen;
			$imgprg->bitmap = $encoded_preg;
			$imgprg->save();

			//Imagen de la solución
			$imgsol = new Imagen;
			$imgsol->bitmap = $encoded_sol;
			$imgsol->save();

			//Creo la pregunta en base
			$preg = new Pregunta;
			$preg->detalle = $desc;
			$preg->link_youtube = $youtube;
			$preg->Test()->associate($test);
			$preg->fk_pregunta_imagen = $imgprg->id;
			$preg->fk_solucion_imagen = $imgsol->id;
			$preg->save();


			//Se crean las respuestas asociadas a la pregunta
			$r1 = new Respuesta;
			$r1->detalle = $resp1;
			$r1->es_correcta = $corr1;
			$r1->pregunta()->associate($preg);

			$r2 = new Respuesta;
			$r2->detalle = $resp2;
			$r2->es_correcta = $corr2;
			$r2->pregunta()->associate($preg);

			$r3 = new Respuesta;
			$r3->detalle = $resp3;
			$r3->es_correcta = $corr3;
			$r3->pregunta()->associate($preg);

			$r4 = new Respuesta;
			$r4->detalle = $resp4;
			$r4->es_correcta = $corr4;
			$r4->pregunta()->associate($preg);


			$r1->save();
			$r2->save();
			$r3->save();
			$r4->save();
		}

		return redirect('/Preguntas');


	}

	public function editar_pregunta(Request $request, $id_pregunta){

		$preg = Pregunta::where("id",$id_pregunta)->first();

		$desc = $request->input('descEdit');

		//Subida de imagen de pregunta
		if ($request->hasFile('imgEnunciadoEdit')) {
			$solucionpreg = $request->file('imgEnunciadoEdit');
			$encoded_preg = base64_encode(file_get_contents("/".$solucionpreg, FILE_USE_INCLUDE_PATH,NULL));
		}else{
			$encoded_preg = null;
		}
		//Subida de imagen de solución
		if($request->hasFile('imgSolucionEdit')){
			$solucion = $request->file('imgSolucionEdit');
			$encoded_sol = base64_encode(file_get_contents("/".$solucion, FILE_USE_INCLUDE_PATH,NULL));
		}else{
			$encoded_sol = null;
		}
		$youtube = $request->input('ytbEditar');

		$user = $request->user();

		//Imagen de pregunta
		$imgprg = Imagen::where("id",$preg->fk_pregunta_imagen)->first();
		if($encoded_preg != null){
			$imgprg->bitmap = $encoded_preg;
			$imgprg->save();
		}

		//Imagen de la solución
		$imgsol = Imagen::where("id",$preg->fk_solucion_imagen)->first();
		if($encoded_sol != null){
			$imgsol->bitmap = $encoded_sol;
			$imgsol->save();
		}

		$preg->detalle = $desc;
		$preg->link_youtube = $youtube;
		$preg->fk_pregunta_imagen = $imgprg->id;
		$preg->fk_solucion_imagen = $imgsol->id;
		$preg->save();

		//Parte de respuestas
		foreach($preg->respuestas()->get() as $respuesta){
			$resp = $request->input('OpcEdit'.$respuesta->id);
			//Se crean las respuestas asociadas a la pregunta
			$respuesta->detalle = $resp;
			$respuesta->es_correcta = ($request->input('updateCorrectOption') == $respuesta->id) ? true : false;
			$respuesta->save();
		}

		return redirect('/Preguntas');
	}



	public function eliminar_pregunta(Request $request, $id_pregunta)
	{

		if ( !empty ( $id_pregunta ) ) {
    		$preg = Pregunta::where("id",$id_pregunta)->first();
			$preg->delete();

		}

		return redirect('/Preguntas');
	}
}
