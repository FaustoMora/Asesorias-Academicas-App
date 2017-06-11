<?php

namespace App\Http\Controllers;

use App\User;
use App\Materia;
use App\Tema;
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
		$materias = Materia::where("fk_user",$user->id)->get();
		$temas = Tema::all();//Obtengo los temas
		$preguntas = Pregunta::all();
		$respuestas = Respuesta::all();
		return view('layouts.preguntas')->with('materias',$materias)->with('temas',$temas)
		->with('preguntas',$preguntas)->with('respuestas',$respuestas);

	}

	public function crear_pregunta(Request $request){
		$seleccion = $_POST['lista_temas'];
		if($seleccion != '0'){
			try{
				$tema = Tema::where("id",$seleccion)->first();//Obtengo el tema mediante el id de la seleccion
			}catch(\Exception $e){
				$tema = "";
			}//Fin del try catch
		}//Fin del if de seleccion
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
		$corr1 = isset($_POST['chkbox1']) && $_POST['chkbox1']  ? "1" : "0";
		$corr2 = isset($_POST['chkbox2']) && $_POST['chkbox2']  ? "1" : "0";
		$corr3 = isset($_POST['chkbox3']) && $_POST['chkbox3']  ? "1" : "0";
		$corr4 = isset($_POST['chkbox4']) && $_POST['chkbox4']  ? "1" : "0";


		$user = $request->user();

		//Imagen de pregunta
		$imgprg = new Imagen;
		$imgprg->bitmap = $encoded_preg;
		$imgprg->save();
		$imagen_preg = Imagen::where("id",$imgprg->id)->first();

		//Imagen de la solución
		$imgsol = new Imagen;
		$imgsol->bitmap = $encoded_sol;
		$imgsol->save();
		$imagen_solucion = Imagen::where("id", $imgsol->id)->first();

		//Creo la pregunta en base
		$preg = new Pregunta;
		$preg->detalle = $desc;
		$preg->link_youtube = $youtube;
		$preg->fk_tema = $tema->id;
		$preg->fk_pregunta_imagen = $imagen_preg->id;
		$preg->fk_solucion_imagen = $imagen_solucion->id;
		$preg->save();
		$pregunta = Pregunta::where("id",$preg->id)->first();


		//Se crean las respuestas asociadas a la pregunta
		$r1 = new Respuesta;
		$r1->detalle = $resp1;
		$r1->es_correcta = $corr1;
		$r1->fk_pregunta = $pregunta->id;
		$r1->fk_imagen = $imagen_solucion->id;

		$r2 = new Respuesta;
		$r2->detalle = $resp2;
		$r2->es_correcta = $corr2;
		$r2->fk_pregunta = $pregunta->id;
		$r2->fk_imagen = $imagen_solucion->id;

		$r3 = new Respuesta;
		$r3->detalle = $resp3;
		$r3->es_correcta = $corr3;
		$r3->fk_pregunta = $pregunta->id;
		$r3->fk_imagen = $imagen_solucion->id;

		$r4 = new Respuesta;
		$r4->detalle = $resp4;
		$r4->es_correcta = $corr4;
		$r4->fk_pregunta = $pregunta->id;
		$r4->fk_imagen = $imagen_solucion->id;


		$r1->save();
		$r2->save();
		$r3->save();
		$r4->save();

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
		$respuestas = Respuesta::where("fk_pregunta",$id_pregunta);
		foreach($respuestas as $respuesta){
			$resp = $request->input('OpcEdit{{$respuesta->id}}');
			//Si son correctas
			$corr = isset($_POST['chkbox{{$respuesta->id}}']) && $_POST['chkbox1']  ? "1" : "0";
			//Se crean las respuestas asociadas a la pregunta
			$r = Respuesta::where("id",$respuesta->id);
			$r->detalle = $resp;
			$r->es_correcta = $corr;
			$r->fk_pregunta = $preg->id;
			$r->fk_imagen = $imgsol->id;
			$r->save();
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
