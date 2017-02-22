<?php

namespace App\Http\Controllers;

use App\User;
use App\Tema;
use Illuminate\Http\Request;
use Auth;
use Hash;

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
		return view('layouts.config');
	}

	public function update_password(Request $request)
	{
		if (Auth::check())
		{
			$user = Auth::user();
			$pass = $request->input('actualPass');
			$newpass = $request->input('newPass');

			if ( Hash::check($pass, Auth::user()->password) ) 
			{
    			$user->fill([
            		'password' =>  Hash::make($newpass)
        		])->save();
    			Auth::logout();
			}

		}
		return redirect('/Config');	
	}

	public function update_email(Request $request)
	{
		
		return redirect('/Config');
	}

}