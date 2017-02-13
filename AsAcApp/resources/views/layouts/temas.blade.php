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
  <table class="table">
     <thead>
      <tr>
        <th>Nombre</th>
        <th>Descripcion</th>
        <th>Editar</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>Tema 1</td>
        <td>Desc Tema 1</td>
        <td><button class="btn btn-default" type="button" aria-label="Left Align"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></button></td>
      </tr>
      <tr>
        <td>Tema 1</td>
        <td>Desc Tema 1</td>
        <td><button class="btn btn-default" type="button" aria-label="Left Align"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span></button></td>
      </tr>
    </tbody>
  </table>
</div>
</div>

@stop

@stop