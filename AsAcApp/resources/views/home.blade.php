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
                    <ul class="nav nav-tabs">
                      <li class="active"><a data-toggle="tab" href="#overview">Overview</a></li>
                      <li><a data-toggle="tab" href="#temas">Temas</a></li>
                      <li><a data-toggle="tab" href="#preguntas">Preguntas</a></li>
                      <!--<li><a data-toggle="tab" href="#soluciones">Soluciones</a></li>-->
                      <li><a data-toggle="tab" href="#config">Configuraci&oacute;n</a></li>
                    </ul>

                    <div class="tab-content">
                      <div id="overview" class="tab-pane fade in active">
                        <h3>Overview</h3>
                            
                      </div>
                      <div id="temas" class="tab-pane fade">
                      <br>
                            @include('layouts.temas')
                      </div>
                      <div id="preguntas" class="tab-pane fade">
                      <br>
                        <h3>Preguntas</h3>
                            @include('layouts.preguntas')
                      </div>
<!--
                      <div id="soluciones" class="tab-pane fade">
                      <br>
                        <h3>Soluciones</h3>
                            @include('layouts.soluciones')                
                      </div>
-->
                      <div id="config" class="tab-pane fade">
                      <br>
                            @include('layouts.config')    
                      </div>
                    </div>
                </div>
            </div>
        </div>
        <div style="clear:both;"></div>
    </div>
</div>
@endsection
