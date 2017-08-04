<?php

namespace App\Http\Controllers;

use App\User;
use App\Tema;
use Illuminate\Http\Request;
use Auth;
use Hash;
use Pusher;
use Log;

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
			$user = User::find($user->id);
			$pass = $request->input('actualPass');
			$newPass = $request->input('newPass');

			if ( Hash::check($pass, Auth::user()->password) )
			{
    			$user->fill([
            		'password' =>  Hash::make($newPass)
        		])->save();
    			Auth::logout();
			}

		}
		return redirect('/Config');
	}

	public function update_email(Request $request)
	{

		if (Auth::check())
		{
			$user = Auth::user();
			$user = User::find($user->id);
			$pass = $request->input('actualPass');
			$newEmail = $request->input('newEmail');

			if ( Hash::check($pass, Auth::user()->password) )
			{
    			$user->fill([
            		'email' =>  $newEmail
        		])->save();
    			Auth::logout();
			}

		}
		return redirect('/Config');
	}

	public function update_message(Request $request)
	{

		if (Auth::check())
		{
			$user = Auth::user();
			$user = User::find($user->id);
			$message = $request->input('newMessage');

			if (!is_null($message) )
			{
				$user->message =  $message;
				$user->save();
			}

		}
		return redirect('/Config');
	}

	public function send_personal_message(Request $request)
	{
		if (Auth::check())
		{
			$message = $request->input('personalMessage');

			if (!is_null($message) )
			{
				$pusher = new Pusher(env('PUSHER_APP_KEY'),env('PUSHER_APP_SECRET'), env('PUSHER_APP_ID'), array('cluster' => env('PUSHER_APP_CLUSTER'),'encrypted'=>true));
				$pusher->notify(
				  array("asacapp"),
				  array(
					'gcm' => array(
					  'notification' => array(
						'title' => $message,
						'icon' => 'logoasac_noti'
					  ),
					),
				  )
				);
				Log::info('sending personal push notification');
			}

		}
		return redirect('/Config');

	}

}
