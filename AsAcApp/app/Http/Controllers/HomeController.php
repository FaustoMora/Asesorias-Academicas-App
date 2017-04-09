<?php

namespace App\Http\Controllers;

use App\User;
use App\Materia;
use App\Tema;
use App\Pregunta;
use Illuminate\Support\Facades\Auth;

class HomeController extends Controller
{
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('auth');
    }

    /**
     * Show the application dashboard.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        $user = Auth::user();
        $user = User::find($user->id);
        $temas = null;
        $preguntas = null;
        if($user->materias()->get()->count() > 0){
            $temas = Tema::where('materia_id',$user->materias()->pluck('id')->toArray())->get();
        }
        if(!is_null($temas) && count($temas) > 0){
            $preguntas = Pregunta::where('tema_id',$temas->pluck('id'))->get();
        }

        $data = array(
            'count_materias'  => $user->materias()->get()->count(),
            'count_temas'   => (!$temas) ? 0 : $temas->count(),
            'count_preguntas' => (!$preguntas) ? 0 :$preguntas->count()
        );
        return view('home')->with($data);
    }
}

