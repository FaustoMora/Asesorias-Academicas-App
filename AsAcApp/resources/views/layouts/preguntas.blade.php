@extends('home')

@section('content')
@parent

  @section('menu')

  <style>
  tr{
    padding-bottom: 1em;
  }
  </style>

  <div class="panel panel-info">
    <!-- Default panel contents -->
    <div class="panel-heading">PREGUNTAS</div>
      <div class="panel-body">
        <form method="post" role="form" action="{{ url('/crearPregunta') }}">
          {!! csrf_field() !!}
          <!--Seleccion de Tema-->
        	<div class="col-md-12 col-sm-12 form-group row">
            <label class=" col-md-2 col-form-label">Tema:</label>
            <div class="col-md-10">
              <select class="form-control" required name="lista_temas">
                <option value="0">--Seleccione un tema--</option>
                @foreach($temas as $tema)
                  <option value="{{$tema->id}}">{{$tema->nombre}}</option>
                @endforeach
              </select>
            </div>
          </div><!--Fin de seleccion de tema-->
          <!--Descripción de la pregunta-->
          <div class="col-md-12 col-sm-12 form-group row">
        		<label class=" col-md-2 col-form-label">Descripci&oacute;n</label>
        		<div class="col-md-10">
        			<input type="text" name="descPreg" placeholder="Descripción/Enunciado de la Pregunta" class="form-control" required>
        		</div>
        	</div>

        	<div class="col-md-12 col-sm-12 form-group row">
        		<label class=" col-md-2 col-form-label">Opciones</label>
        		<div class="col-md-10 col-sm-10 form-group row"><!--Div interno para las opciones-->
              
              <div class="col-md-10" style="width: 100%;"><!--Div para una respuesta-->
                <div class="input-group">
                  <label style="float: left" class=" col-md-2">A)</label>
                  <span style="display:table-cell;">
                    <input style="width: 100%;"type="text" name="opc1" placeholder="Opciones de respuestas" class="form-control" required>
                  </span>
                  <span style="display:table-cell;" class="input-group-addon">
                    <input style="float:right" type="checkbox" name="chkbox1">
                  </span>
                </div>
              </div>

              <div class="col-md-10" style="width: 100%; margin-top:10px"><!--Div para una respuesta-->
                <div class="input-group">
                  <label style="float: left" class=" col-md-2">B)</label>
                  <span style="display:table-cell;">
                    <input style="width: 100%;"type="text" name="opc2" placeholder="Opciones de respuestas" class="form-control" required>
                  </span>
                  <span style="display:table-cell;" class="input-group-addon">
                    <input style="float:right" type="checkbox" name="chkbox2">
                  </span>
                </div>
              </div>

              <div class="col-md-10" style="width: 100%; margin-top:10px"><!--Div para una respuesta-->
                <div class="input-group">
                  <label style="float: left" class=" col-md-2">C)</label>
                  <span style="display:table-cell;">
                    <input style="width: 100%;"type="text" name="opc3" placeholder="Opciones de respuestas" class="form-control" required>
                  </span>
                  <span style="display:table-cell;" class="input-group-addon">
                    <input style="float:right" type="checkbox" name="chkbox3">
                  </span>
                </div>
              </div>

              <div class="col-md-10" style="width: 100%; margin-top:10px"><!--Div para una respuesta-->
                <div class="input-group">
                  <label style="float: left" class=" col-md-2">D)</label>
                  <span style="display:table-cell;">
                    <input style="width: 100%;"type="text" name="opc4" placeholder="Opciones de respuestas" class="form-control" required>
                  </span>
                  <span style="display:table-cell;" class="input-group-addon">
                    <input style="float:right" type="checkbox" name="chkbox4">
                  </span>
                </div>
              </div>
            </div>
            <div class="col-md-12 col-sm-12 form-group row">
              <label style="float: left" class=" col-md-2">Solución</label>
              <div class="col-md-10">
                <input onchange="readURL(this);" class="form-control" id="subirSolución" name="subirSolución"  type="file" data-show-preview="true" data-show-caption="true">
                <img id="solc" hidden="true" src="#" alt="Solución de la Pregunta">
              </div>
            </div>
            
        	</div><!--Fin del div de todas las opciones-->
        	<div style="margin-bottom: 40px; clear:both;"></div>
        	<div style="text-align: center" class="col-md-11 col-sm-11 form-group">
        	<button style="display: inline-block" name="crearPreg" type="submit" class="btn btn-success">Crear Nueva Pregunta</button>
        	</div>
        </form>
      </div>
    <br>

      <!-- Table -->

    <div class="table-responsive" style="margin: 15px;">
      <h4>Lista de Preguntas por Tema</h4>
      <div class="panel panel-info" style="width: 95%; border-color: #ccc;">
        <div class="panel-heading" style="position: relative; background-color: #eee; border-color: #ccc;">
          <a style="text-decoration: none; position: relative; height: 100%; background-color: #E8E8E8;" data-toggle="collapse" data-parent="#accordion" href="#pregunta1">
            <h4 class="panel-title">
               Pregunta: Pregunta1 
            </h4>
          </a>
          
        </div>

        <div id="pregunta1" class="panel-collapse collapse" 
        name="ciclo_panel_body" style = "background: white;">
        <div class="panel-body">
          <div id="noneditModulo1" class="nonEditModulo">

          </div>
          <div id="editable_body_1" class="editableModulo">
            <table class="table table-striped table-bordered table-hover" id="opciones-respuestas">
              <thead>
                <tr>
                  <td style="width: 300px;"><b style="font-size:12px;">Opci&oacute;n A<div style="color:green; float:right;"align="right">Correcta!</div></td>
                </tr>
                <tr style="margin: 10px;">
                  <td style="width: 300px;"><b style="font-size:12px;">Opci&oacute;n B</td>
                </tr>
                <tr style="margin: 10px;">
                  <td>
                    <button style="font-size: 12px;"name="verSolucion" type="" class="btn btn-primary pull-right">Ver Solución</button>
                  </td>
                </tr>
              </thead>
            </table> 
          </div><!--Cierro el editable-body-->
        </div><!--Cierro el panel-body-->
        </div>
      </div>
    </div>
  </div>

<script type="text/javascript">
  function readURL(input) {
    if (input.files && input.files[0]) {
      var reader = new FileReader();
      reader.onload = function (e) {
        $('#solc')
          .attr('src', e.target.result)
          .width(450)
          .height(300)
          .removeAttr("hidden");
      };
      reader.readAsDataURL(input.files[0]);
    }
  }
</script>
  @endsection<!--Fin de la sección de Menú-->
@endsection

