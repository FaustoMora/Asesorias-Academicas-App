<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreatePreguntaTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('pregunta', function (Blueprint $table) {
            $table->increments('id');
            $table->string('detalle');
            $table->integer('tema_id')->unsigned();
            $table->integer('fk_pregunta_imagen')->unsigned();
            $table->integer('fk_solucion_imagen')->unsigned();
        });
        Schema::table('pregunta', function(Blueprint $table){
            $table->foreign('fk_pregunta_imagen')->references('id')->on('imagen');
        });
        Schema::table('pregunta', function(Blueprint $table){
            $table->foreign('fk_solucion_imagen')->references('id')->on('imagen');
            
        });
    }
    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('pregunta');
    }
}
