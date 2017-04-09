<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateMateriaTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('materia', function (Blueprint $table) {
            $table->increments('id');
            $table->string('nombre_materia');
<<<<<<< HEAD:AsAcApp/database/migrations/2017_04_07_031126_create_materia_table.php
            $table->string('icono_materia')->nullable;
            $table->integer('user_id')->unsigned();
=======
            $table->mediumText('icono_materia')->nullable();
            $table->integer('user_id')->unsigned()->nullable();
>>>>>>> 2caeb74856732df604ebc4c2b87049633a79c563:AsAcApp/database/migrations/2017_02_05_020600_create_materia_table.php
            $table->timestamps();
        });
         Schema::table('materia', function(Blueprint $table){
            $table->foreign('user_id')->references('id')->on('users')->onDelete('cascade');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('materia');
    }
}
