@extends('prehome')

@section('content')
@parent

@section('menu')

<div class="panel panel-info">
  <!-- Default panel contents -->
  <div class="panel-heading">MENU MATERIAS</div>
  <div class="panel-body">
	<form method="post" role="form" action="{{ url('/crearMateria') }}" enctype="multipart/form-data">
	{{ csrf_field() }}
		<div class="col-md-12 col-sm-12 form-group row">
			<label class=" col-md-2 col-form-label">Nombre</label>
			<div class="col-md-10">
				<input type="text" name="nombre" placeholder="Nombre de la Materia" class="form-control" required>
				<br>
				<input type="file" name="icono" accept="image/png" class="form-control" required>
			</div>
		</div>
		<div style="clear:both;"></div>
		<div class="col-md-11 col-sm-11 form-group">
		<button type="submit" class="btn btn-success pull-right">Crear Nueva Materia</button>
		</div>
	</form>
  </div>
<br>
  <!-- Table -->
  <hr>

<div class="table-responsive" style="margin: 15px;">
<h3>Mis Materias</h3>
  <table class="table" id="table_id" style="text-align: center;">
	 <thead >
	  <tr>
		<th style="text-align: center;">Icono</th>
		<th style="text-align: center;">Nombre</th>
		<th style="text-align: center;">Editar</th>
		<th style="text-align: center;">Eliminar</th>
	  </tr>
	</thead>
	<tbody>
		@foreach ($materias as $materia)
		<tr>
		  <td>@if( $materia->icono != null )
				<?php
					$imagen = $materia->icono;
					$decoded = base64_decode($imagen);
					echo '<img style="width:50px;height:50px;" src="data:image/png;base64,'. $imagen.'"/>';
				?>
				@endif</td>
		  <td>{{ $materia->nombre }}</td>
		  <td><button type="button" class="btn btn-default" data-toggle="modal" data-target="#myModal{{ $materia->id }}"><span><i class="fa fa-pencil-square-o" aria-hidden="true"></i></span></button></td>
		  <td><button type="button" class="btn btn-danger" data-toggle="modal" data-target="#myModalDel{{ $materia->id }}"><span><i class="fa fa-pencil-square-o" aria-hidden="true"></i></span></button></td>
		</tr>
		<!-- Modal -->
		@endforeach
	</tbody>
  </table>

  @foreach($materias as $materia)
<div id="myModal{{ $materia->id }}" class="modal fade" role="dialog">
  <div class="modal-dialog">

	<!-- Modal content-->
	<div class="modal-content">
	<form method="post" role="form" action="{{ url('updateMateria',array('id_materia'=>$materia->id))  }}" enctype="multipart/form-data">
	{{csrf_field()}}
	  <div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">&times;</button>
		<h4 class="modal-title">Editar Materia</h4>
	  </div>
	  <div class="modal-body">

			<div class="col-md-12 col-sm-12 form-group row">
			  <label class=" col-md-2 col-form-label">Nombre</label>
			  <div class="col-md-10">
				<input type="text" name="nombre" placeholder="Nombre de la Materia" class="form-control" value="{{ $materia->nombre }}" required>
				<br>
				<input type="file" name="icono" accept="image/png" class="form-control">
			  </div>
			</div>
			<div style="clear:both;"></div>
	  </div>
	  <div class="modal-footer">
		<button type="submit" class="btn btn-success pull-right"  style="margin: 4px;">Guardar</button>
		<button type="button" class="btn btn-default pull-right" data-dismiss="modal" style="margin: 4px;">Cerrar</button>
		<div style="clear:both;"></div>
	  </div>
	</form>
	</div>
  </div>
</div>

<div id="myModalDel{{ $materia->id }}" class="modal fade" role="dialog">
  <div class="modal-dialog">

	<!-- Modal content-->
	<div class="modal-content">
	<form method="post" role="form" action="{{ url('deleteMateria',array('id_materia'=>$materia->id))  }}">
	{{csrf_field()}}
	  <div class="modal-header">
		<button type="button" class="close" data-dismiss="modal">&times;</button>
		<h4 class="modal-title">Eliminar Materia</h4>
	  </div>
	  <div class="modal-body">
			<p ><strong class="text-warning">Al eliminar la materia {{ $materia->nombre }} se eliminara todo contenido relacionado con ella. </strong> <br><strong class="text-danger">
			EST&Aacute; SEGURO DE PROCEDER?</strong></p>
			<div style="clear:both;"></div>
	  </div>
	  <div class="modal-footer">
		<button type="submit" class="btn btn-danger pull-right"  style="margin: 4px;">Aceptar</button>
		<button type="button" class="btn btn-default pull-right" data-dismiss="modal" style="margin: 4px;">Cancelar</button>
		<div style="clear:both;"></div>
	  </div>
	</form>
	</div>

  </div>
</div>
  @endforeach
</div>
</div>


<script type="text/javascript">

  $(document).ready( function () {

	$('#table_id').DataTable({
		"language": {
				"sProcessing":     "Procesando...",
				"sLengthMenu":     "Mostrar _MENU_ registros",
				"sZeroRecords":    "No se encontraron resultados",
				"sEmptyTable":     "Ningún dato disponible en esta tabla",
				"sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
				"sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0 registros",
				"sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
				"sInfoPostFix":    "",
				"sSearch":         "Buscar:",
				"sUrl":            "",
				"sInfoThousands":  ",",
				"sLoadingRecords": "Cargando...",
				"oPaginate": {
					"sFirst":    "Primero",
					"sLast":     "Último",
					"sNext":     "Siguiente",
					"sPrevious": "Anterior"
				}
		},
		"order": [[ 1, "asc" ]]
	});

} );

</script>

@stop

@stop
