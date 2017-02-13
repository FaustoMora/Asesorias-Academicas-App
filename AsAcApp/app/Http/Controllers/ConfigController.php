<?php

namespace App\Http\Controllers;

use App\User;
use App\Tema;
use Illuminate\Http\Request;

/**
* 
*/
class ConfigController extends Controller
{
	
	public function __construct()
	{
		$this->middleware('auth');
	}

	public function get_page(Request $request)
	{
		return redirect('/home');
	}

	public function update_password(Request $request)
	{
		return redirect('/home');	
	}

	public function update_email(Request $request)
	{
		
		return redirect('/home');
	}

}