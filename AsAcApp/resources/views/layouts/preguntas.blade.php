@extends('prehome')

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
                @foreach($materias as $materia)
                  <option>{{$materia->nombre_materia}}</option>
                  @foreach($temas as $tema)
                    @if($tema->materia_id == $materia->id)
                      <option value="{{$tema->id}}">{{$tema->nombre}}</option>
                    @endif  
                  @endforeach
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
              <label style="float: left" class=" col-md-2">Imagen Enunciado</label>
              <div class="col-md-10">
                <input type="file" onchange="read2URL(this);" accept="image/png" class="form-control" id="subirImgPreg" name="subirImgPreg" data-show-preview="true" data-show-caption="true">
                <img id="preg" name="preg" hidden="true" src="#" alt="Enunciado de la Pregunta">
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
                <input type="file" onchange="readURL(this);" accept="image/png" class="form-control" id="subirSolución" name="subirSolución" data-show-preview="true" data-show-caption="true">
                <img id="solc" name="solc"hidden="true" src="#" alt="Solución de la Pregunta">
              </div>
            </div>
            
            <div class="col-md-12 col-sm-12 form-group row">
              <label style="float: left" class=" col-md-2">Video Solución</label>
              <div class="col-md-10">
                <input type="text" name="youtube" placeholder="Link del video de Youtube" class="form-control" required>
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
      <h3>Lista de Preguntas por Materia</h3>
        <table class="table" id="table_id" style="text-align: center;">
           <thead >
            <tr>
              <th style="text-align: center;">Nombre</th>
              <th style="text-align: center;">Descripcion</th>
              <th style="text-align: center;">Editar</th>
           </tr>
          </thead>
          <tbody>
              @foreach ($temas as $tema)
              <tr>
                <td>
                  <div class="panel panel-info" style="width: 95%; border-color: #ccc;">
                    <div class="panel-heading" style="position: relative; background-color: #eee; border-color: #ccc;">
                      <a style="text-decoration: none; position: relative; height: 100%; background-color: #E8E8E8;" data-toggle="collapse" data-parent="#accordion" href="#tema{{$tema->id}}">
                        <h3 class="panel-title">
                          {{ $tema->nombre }}
                        </h3>
                      </a>
                    </div><!--Fin del panel-heading-->

                    <div id="tema{{$tema->id}}" class="panel-collapse collapse" 
                    name="ciclo_panel_body" style = "background: white;">
                      <div class="panel-body">
                        <div id="noneditModulo1" class="nonEditModulo">

                        </div>
                        <div id="editable_body_1" class="editableModulo">
                            @foreach($preguntas as $pregunta)
                              @if($pregunta->tema_id == $tema->id)
                                <div class="panel panel-info" style="width: 95%; border-color: #ccc;">
                                  <div class="panel-heading" style="position: relative; background-color: #eee; border-color: #ccc;">
                                    <a style="text-decoration: none; position: relative; height: 100%; background-color: #E8E8E8;" data-toggle="collapse" data-parent="#accordion" href="#pregunta{{$pregunta->id}}">
                                      <h4 class="panel-title">
                                         <b>Pregunta: {{ $pregunta->detalle }}</b>
                                      </h4>
                                    </a>
                                  </div><!--Fin del panel-heading-->
                                  <div id="pregunta{{$pregunta->id}}" class="panel-collapse collapse" 
                                    name="ciclo_panel_body" style = "background: white;">
                                    <div class="panel-body">
                                      <div id="noneditModulo1" class="nonEditModulo">
                                        <!--NO EDITABLE-->
                                      </div>
                                      <div id="editable_body_1" class="editableModulo">
                                        <div style="text-align:center;">
                                          <?php
                                            $imagenpreg = $pregunta->preguntaimagen()->first();
                                            $decoded = base64_decode($imagenpreg->bitmap);
                                            echo "<img style=width:400px; alt=Solución de la Pregunta src=\"data:image/png;base64,$imagenpreg->bitmap\" />";
                                          ?>
                                        </div>
                                        <table class="table table-striped table-bordered table-hover" id="opciones-respuestas">
                                          <thead>
                                            @foreach($respuestas as $respuesta)
                                              @if($respuesta->pregunta->id == $pregunta->id)
                                                <tr style="margin: 10px;">
                                                  <td style="text-align:left; width: 300px;"><b style="font-size:12px;">{{ $respuesta->detalle }}
                                                    @if($respuesta->es_correcta == 1)
                                                        <div style="color:green; float:right;"align="right">Correcta!</div></td>
                                                    @endif
                                                  </td>
                                                </tr>
                                                                        
                                              @endif
                                            @endforeach
                                            <tr style="margin: 10px;">
                                              <td>
                                                <button style="font-size: 12px;"name="verSolucion" type="" 
                                                data-toggle="modal" data-target="#solucion{{$pregunta->id}}" 
                                                class="btn btn-primary pull-right">Ver Solución</button>
                                              </td>
                                            </tr>
                                          </thead>
                                        </table> 
                                        <!-- Modal -->
                                        <div id="solucion{{$pregunta->id}}" class="modal fade" role="dialog">
                                          <div class="modal-dialog">

                                            <!-- Modal content-->
                                            <div class="modal-content">
                                              <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                <h4 class="modal-title">Soluci&oacute;n</h4>
                                              </div>
                                              <div style="text-align:center;"class="modal-body">
                                                <?php
                                                  $imagensol = $pregunta->solucionimagen()->first();
                                                  $decoded = base64_decode($imagensol->bitmap);
                                                  echo "<img style=width:300px; alt=Solución de la Pregunta src=\"data:image/png;base64,$imagensol->bitmap\" />";
                                                ?>

                                                <!--
                                                  <img id="{{$imagensol->id}}" src="data:image/gif;base64,' . $decoded . '" alt="Solución de la Pregunta">
                                                -->
                                              </div>
                                              <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                                              </div>
                                            </div>

                                          </div>
                                        </div>
                                      </div>
                                    </div><!--Cierro el panel-body-->
                                  </div><!--Cierro el panel-collapse-->
                                </div><!--Fin del panel-info-->
                                
                              @endif
                            @endforeach
                        </div><!--Cierro el editable-body-->
                      </div><!--Cierro el panel-body-->
                    </div><!--Cierro el panel-collapse-->
                  </div><!--Cierro el panel-info de la ventana externa de lista de temas-->
                </td>
                <td>{{ $tema->descripcion }}</td>
                <td>Editar</td>
              </tr>
              <!-- Modal -->
              @endforeach
          </tbody>
        </table>
      </div>
  </div>

<script type="text/javascript">
  function readURL(input) {
    if (input.files && input.files[0]) {
      var reader = new FileReader();
      reader.onload = function (e) {
        $('#solc')
          .attr('src', e.target.result)
          .width(300)
          .height(200)
          .removeAttr("hidden");
      };
      reader.readAsDataURL(input.files[0]);
    }
  }

  function read2URL(input) {
    if (input.files && input.files[0]) {
      var reader = new FileReader();
      reader.onload = function (e) {
        $('#preg')
          .attr('src', e.target.result)
          .width(300)
          .height(200)
          .removeAttr("hidden");
      };
      reader.readAsDataURL(input.files[0]);
    }
  }



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

  });
</script>
  @endsection<!--Fin de la sección de Menú-->
@endsection

