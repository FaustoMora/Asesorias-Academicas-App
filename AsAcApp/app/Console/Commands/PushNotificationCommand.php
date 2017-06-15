<?php

namespace App\Console\Commands;

use Illuminate\Console\Command;
use App\User;
use Pusher;
use Log;

class PushNotificationCommand extends Command
{

    /**
     * The name and signature of the console command.
     *
     * @var string
     */
    protected $signature = 'push:notification';
	protected $pusher;
    /**
     * The console command description.
     *
     * @var string
     */
    protected $description = 'Comando para enviar notifcaciones al cliente app android';

    /**
     * Create a new command instance.
     *
     * @return void
     */
    public function __construct()
    {
        parent::__construct();
		$this->pusher = new Pusher(env('PUSHER_APP_KEY'),env('PUSHER_APP_SECRET'), env('PUSHER_APP_ID'), array('cluster' => env('PUSHER_APP_CLUSTER'),'encrypted'=>true) );
    }

    /**
     * Execute the console command.
     *
     * @return mixed
     */
    public function handle()
    {
		$user = User::find(1);
		error_log($user->message);
		$this->pusher->notify(
		  array("asacapp"),
		  array(
			'gcm' => array(
			  'notification' => array(
				'title' => $user->message,
				'icon' => 'logoasac'
			  ),
			),
		  )
		);
		Log::info('sending push notification');
    }
}
