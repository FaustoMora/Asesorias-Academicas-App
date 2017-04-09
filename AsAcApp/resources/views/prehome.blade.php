@extends('layouts.app')

@section('content')
<div class="container">
    <div class="row">
        <div class="col-md-8 col-sm-8 col-md-offset-2 col-sm-offset-2">
        <div class="alert alert-success alert-dismissable fade in">
                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                <strong>Bienvenido! {{ Auth::user()->first_name }} {{ Auth::user()->last_name }}.</strong> 
            </div>
        </div>
        <br>
        <br>
        <br>
        <br>
        <div style="clear:both;"></div>
        <div class="col-md-12 col-sm-12">
            <div class="panel panel-default">
                <div class="panel-heading">Dashboard</div>
                <div class="panel-body">
                    <ul class="nav nav-pills nav-justified">
                      <li><a href="{{ url('/home') }}">Overview</a></li>
                      <li><a href="{{ url('/MisMaterias') }}">Materias</a></li>
                      <li><a href="{{ url('/MisTemas') }}">Temas</a></li>
                      <li><a href="{{ url('/Preguntas') }}">Preguntas</a></li>
                      <li><a href="{{ url('/Config') }}">Configuraci&oacute;n</a></li>
                    </ul>
                    <br>
                    <div class="col-md-12 col-sm-12">

                
                    @yield('menu')


                    </div>
                </div>
            </div>
        </div>
        <div style="clear:both;"></div>
    </div>
</div>
@endsection
