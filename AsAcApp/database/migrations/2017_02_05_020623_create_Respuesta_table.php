<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateRespuestaTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('respuesta', function (Blueprint $table) {
            $table->increments('id');
            $table->string('detalle');
            $table->boolean('es_correcta');
            $table->integer('pregunta_id')->unsigned();
            $table->integer('imagen_id')->unsigned();
            $table->timestamps();
        });
        Schema::table('respuesta', function(Blueprint $table){
            $table->foreign('pregunta_id')->references('id')->on('pregunta');
        });
        Schema::table('respuesta', function(Blueprint $table){
            $table->foreign('imagen_id')->references('id')->on('imagen');
            
        });
    }
    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('respuesta');
    }
}
