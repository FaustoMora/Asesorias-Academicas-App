@extends('prehome')

@section('content')
@parent

@section('overview')
@stop

@section('menu')
<div class="panel panel-info">
  <!-- Default panel contents -->
  <div class="panel-body">
  <hr>
<div class="col-md-8 col-sm-8">
  <h2 class="text-muted">Sumario</h2>
  <ul class="list-group">
    <li class="list-group-item" style="font-size: 150%;">Total Materias: <span class="badge" style="font-size: 80%;">{{ $count_materias }}</span></li>
    <li class="list-group-item" style="font-size: 150%;">Total Temas: <span class="badge" style="font-size: 80%;">{{ $count_temas }}</span></li>
	<li class="list-group-item" style="font-size: 150%;">Total Test: <span class="badge" style="font-size: 80%;">{{ $count_tests }}</span></li>
    <li class="list-group-item" style="font-size: 150%;">Total Preguntas: <span class="badge" style="font-size: 80%;">{{ $count_preguntas }}</span></li>
  </ul>
</div>
</div>
</div>

<div style="clear:both;"></div>


@stop

@stop
