<?php

namespace App\Http\Controllers;

use App\Tema;
use App\Pregunta;
use Illuminate\Http\Request;

class ApiTemaController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        //
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\tema  $tema
     * @return \Illuminate\Http\Response
     */
    public function show(tema $tema)
    {
        //
        error_log('holi show');
    }

    
    /**
     *
     * @param  \App\tema  $tema
     * @return \Illuminate\Http\Response
     */
    public function getInitialPregunta(tema $tema)
    {
        //
        error_log('getInitialPregunta');
    }

    /**
     *
     * @param  \App\tema  $tema
     * @return \Illuminate\Http\Response
     */
    public function getExcludedPregunta(tema $tema)
    {
        //
        error_log('getExcludedPregunta');
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
