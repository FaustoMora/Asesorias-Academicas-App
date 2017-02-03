<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreatePregunta2Table extends Migration
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
            $table->integer('imagen_id')->unsigned();
            $table->timestamps();
        });

        Schema::table('pregunta', function(Blueprint $table){
            $table->foreign('tema_id')->references('id')->on('tema');

        });

        Schema::table('pregunta', function(Blueprint $table){
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
        Schema::dropIfExists('pregunta');
    }
}
