<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

// Route::get('/', function () {
//     return view('welcome');
// });

Route::get('/', function () {
    return redirect('/home');
});

Auth::routes();

Route::get('/home', 'HomeController@index');

/// ROUTE para TEMAS

Route::get('/MisTemas', 'TemaController@get_list');

Route::post('/updateTema/{id_tema}', ['uses' =>'TemaController@update_detail']);

Route::post('/crearTema', 'TemaController@create_detail');

/// ROUTE para CONFIG

Route::get('/Config', 'ConfigController@get_page');

Route::post('/UpdateEmail', 'ConfigController@update_email');

Route::post('/UpdatePassword', 'ConfigController@update_password');
