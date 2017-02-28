<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class AddColumnPregunta2 extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        //
        Schema::table('pregunta', function(Blueprint $table){
            $table->integer('imagen_titulo_id')->unsigned();
        });

        Schema::table('pregunta', function(Blueprint $table){
            $table->foreign('imagen_titulo_id')->references('id')->on('imagen')->onDelete('cascade');
            
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        //
        Schema::table('pregunta', function($table) {
            $table->dropColumn('imagen_titulo_id');
        });
    }
}
