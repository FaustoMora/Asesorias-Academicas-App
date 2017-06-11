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
            $table->integer('fk_pregunta')->unsigned();
            $table->integer('fk_imagen')->unsigned()->nullable();
            $table->timestamps();
        });
        Schema::table('respuesta', function(Blueprint $table){
            $table->foreign('fk_pregunta')->references('id')->on('pregunta')->onDelete('cascade');
        });
        Schema::table('respuesta', function(Blueprint $table){
            $table->foreign('fk_imagen')->references('id')->on('imagen')->onDelete('cascade');

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
