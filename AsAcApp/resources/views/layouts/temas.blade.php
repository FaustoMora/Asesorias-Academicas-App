@extends('home')

@section('content')
@parent

@section('menu')

<div class="panel panel-info">
  <!-- Default panel contents -->
  <div class="panel-heading">MENU TEMAS</div>
  <div class="panel-body">
    <form method="post" role="form" >
    	<div class="col-md-12 col-sm-12 form-group row">
    		<label class=" col-md-2 col-form-label">Nombre</label>
    		<div class="col-md-10">
    			<input type="text" name="nombreTema" placeholder="Nombre del Tema" class="form-control" required>
    		</div>
    	</div>
    	<div class="col-md-12 col-sm-12 form-group row">
    		<label class=" col-md-2 col-form-label">Descripci&oacute;n</label>
    		<div class="col-md-10">
    			<input type="text" name="descripcionTema" placeholder="Descripci&oacute;n del Tema" class="form-control" required>
    		</div>
    	</div>
    	<div style="clear:both;"></div>
    	<div class="col-md-11 col-sm-11 form-group">
    	<button type="submit" class="btn btn-success pull-right">Crear Nuevo Tema</button>
    	</div>
    </form>
  </div>
<br>
  <!-- Table -->
<div class="table-responsive" style="margin: 15px;">
  <table class="table" id="table_id">
     <thead>
      <tr>
        <th style="text-align: center;">Nombre</th>
        <th style="text-align: center;">Descripcion</th>
        <th style="text-align: center;">Editar</th>
      </tr>
    </thead>
    <tbody>
        @foreach ($temas as $tema)
        <tr>
          <td>{{ $tema->nombre }}</td>
          <td>{{ $tema->descripcion }}</td>
          <td><button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal{{ $tema->id }}">Editar</button></td>
        </tr>
        <!-- Modal -->
<div id="myModal{{ $tema->id }}" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
    <form method="post" role="form" >
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Editar Tema</h4>
      </div>
      <div class="modal-body">
          
            <div class="col-md-12 col-sm-12 form-group row">
              <label class=" col-md-2 col-form-label">Nombre</label>
              <div class="col-md-10">
                <input type="text" name="nombreTema" placeholder="Nombre del Tema" class="form-control" required>
              </div>
            </div>
            <div class="col-md-12 col-sm-12 form-group row">
              <label class=" col-md-2 col-form-label">Descripci&oacute;n</label>
              <div class="col-md-10">
                <input type="text" name="descripcionTema" placeholder="Descripci&oacute;n del Tema" class="form-control" required>
              </div>
            </div>
            <div style="clear:both;"></div>
      </div>
      <div class="modal-footer">
        <button type="submit" class="btn btn-success pull-right">Guardar</button>
        <button type="button" class="btn btn-default pull-right" data-dismiss="modal">Close</button>
        <div style="clear:both;"></div>
      </div>
    </form>
    </div>

  </div>
</div>
        @endforeach
    </tbody>
  </table>
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
        }
    });

} );

</script>

@stop

@stop