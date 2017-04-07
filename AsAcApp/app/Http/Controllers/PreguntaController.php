<?php

namespace App\Http\Controllers;

use App\User;
use App\Tema;
use App\Pregunta;
use App\Respuesta;
use App\Imagen;
use Illuminate\Http\Request;
use App\Http\Controllers\View;

/**
* 
*/
class PreguntaController extends Controller
{

	public function home_pregunta(Request $request){

		$user = $request->user();
		$temas = Tema::where("user_id",$user->id)->get();//Obtengo los temas
		$preguntas = Pregunta::all();
		$respuestas = Respuesta::all();
		return view('layouts.preguntas')->with('temas',$temas)
		->with('preguntas',$preguntas)->with('respuestas',$respuestas);

	}

	public function crear_pregunta(Request $request){
		set_include_path(get_include_path() . PATH_SEPARATOR . '/home/ray/');
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
		$solucion = $request->input('subirImgPreg');
		$encoded_preg = base64_encode(file_get_contents("/".$solucion, FILE_USE_INCLUDE_PATH,NULL));

		//Subida de imagen de solución
		$solucion = $request->input('subirSolución');
		$encoded_sol = base64_encode(file_get_contents("/".$solucion, FILE_USE_INCLUDE_PATH,NULL));


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
		$preg->tema_id = $tema->id;
		$preg->fk_pregunta_imagen = $imagen_preg->id;
		$preg->fk_solucion_imagen = $imagen_solucion->id;
		$preg->save();
		$pregunta = Pregunta::where("id",$preg->id)->first();


		//Se crean las respuestas asociadas a la pregunta
		$r1 = new Respuesta;
		$r1->detalle = $resp1;
		$r1->es_correcta = $corr1;
		$r1->pregunta_id = $pregunta->id;
		$r1->imagen_id = $imagen_solucion->id;

		$r2 = new Respuesta;
		$r2->detalle = $resp2;
		$r2->es_correcta = $corr2;
		$r2->pregunta_id = $pregunta->id;
		$r2->imagen_id = $imagen_solucion->id;
		
		$r3 = new Respuesta;
		$r3->detalle = $resp3;
		$r3->es_correcta = $corr3;
		$r3->pregunta_id = $pregunta->id;
		$r3->imagen_id = $imagen_solucion->id;
		
		$r4 = new Respuesta;
		$r4->detalle = $resp4;
		$r4->es_correcta = $corr4;
		$r4->pregunta_id = $pregunta->id;
		$r4->imagen_id = $imagen_solucion->id;

		
		$r1->save();
		$r2->save();
		$r3->save();
		$r4->save();

		return redirect('/Preguntas');

		
	}
}