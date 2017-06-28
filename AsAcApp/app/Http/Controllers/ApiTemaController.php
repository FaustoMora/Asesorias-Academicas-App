<?php

namespace App\Http\Controllers;

use App\Tema;
use App\Pregunta;
use Illuminate\Http\Request;
use Illuminate\Http\JsonResponse;

class ApiTemaController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $temas = Tema::has('tests','>=', 1)->get();
        return response()->json($temas, 200, [], JSON_UNESCAPED_UNICODE);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\tema  $tema
     * @return \Illuminate\Http\Response
     */
    public function show(tema $tema)
    {
		$pk = $tema->id;
        return response()->json($tema->with('tests')->whereHas('tests',function($q){
			$q->has('preguntas','>=',5)->where('active',true);
		})->where('id',$pk)->first(), 200, [], JSON_UNESCAPED_UNICODE);
    }


    /**
     *
     * @param  \App\tema  $tema
     * @return \Illuminate\Http\Response
     */
    public function getInitialPregunta($id_tema)
    {
        if (!is_null($id_tema)) {
            $tema = Tema::find($id_tema);
            if (!is_null($tema)) {
                $preguntas = $tema->preguntas()->pluck('id')->toArray();
                $pk = $preguntas[array_rand($preguntas)];
                $pregunta = $tema->preguntas()->where('id',$pk)->with('PreguntaImagen','SolucionImagen','Respuestas')->first();

                return response()->json($pregunta, 200, [], JSON_UNESCAPED_UNICODE);
            }else{
                return response()->json(NULL, 404, [], JSON_UNESCAPED_UNICODE);
            }
        }
        return response()->json(NULL, 400, [], JSON_UNESCAPED_UNICODE);
    }

    /**
     *
     * @param  \App\tema  $tema
     * @return \Illuminate\Http\Response
     */
    public function getExcludedPregunta($id_tema, Request $request)
    {
        if (!is_null($id_tema)) {
            $tema = Tema::find($id_tema);
            if (!is_null($tema)) {
                $p = explode(',',$request->p);

                if($tema->preguntas()->count()>0){
                    $preguntas = $tema->preguntas()->pluck('id')->toArray();
                    $pk = NULL;
                    while( in_array( ($pk = $preguntas[array_rand($preguntas)]),$p));

                    $pregunta = $tema->preguntas()->where('id',$pk)->with('PreguntaImagen','SolucionImagen','Respuestas')->first();

                    return response()->json($pregunta, 200, [], JSON_UNESCAPED_UNICODE);
                }
                return response()->json(NULL, 200, [], JSON_UNESCAPED_UNICODE);
            }else{
                return response()->json(NULL, 404, [], JSON_UNESCAPED_UNICODE);
            }
        }
        return response()->json(NULL, 400, [], JSON_UNESCAPED_UNICODE);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\tema  $tema
     * @return \Illuminate\Http\Response
     */
    public function edit(tema $tema)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\tema  $tema
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, tema $tema)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\tema  $tema
     * @return \Illuminate\Http\Response
     */
    public function destroy(tema $tema)
    {
        //
    }

}
