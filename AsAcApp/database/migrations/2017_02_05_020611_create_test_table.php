<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateTestTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('test', function (Blueprint $table) {
            $table->increments('id');
			$table->string('nombre');
			$table->boolean('active')->default(false);
			$table->integer('fk_tema')->unsigned();
            $table->timestamps();
        });
		Schema::table('test', function(Blueprint $table){
			$table->foreign('fk_tema')->references('id')->on('tema')->onDelete('cascade');
		});
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('test');
    }
}
