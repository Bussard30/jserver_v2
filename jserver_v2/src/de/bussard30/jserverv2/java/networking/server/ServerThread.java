<<<<<<< HEAD
package networking.server;
=======
package de.bussard30.jserverv2.java.networking.server;
>>>>>>> develop

import java.util.ConcurrentModificationException;

/**
 * Workaround for Thread not being able to access itself
<<<<<<< HEAD
 *
 * @author Jonas
 */
public class ServerThread {
    private final String name;
    private final ServerThread st;
    private Thread t;

    public ServerThread() {
        name = "SERVERTHREAD";
        st = this;
        run();
    }

    public ServerThread(String name) {
        this.name = name;
        st = this;
        run();
    }

    public void run() {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (Server.getInstance().isOnline()) {
                    try {
                        for (ServerHandler h : Server.getInstance().getAssigments(st)) {
                            try {
                                Diagnostics.getInstance().process(h, true);
                                h.run();
                                try {
                                    Thread.sleep(0, 500000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Diagnostics.getInstance().process(h, false);
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    } catch (ConcurrentModificationException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(0, 500000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        t.start();
    }
=======
 * 
 * @author Jonas
 *
 */
public class ServerThread
{
	private final String name;
	private Thread t;
	private final ServerThread st;

	public ServerThread()
	{
		name = "SERVERTHREAD";
		st = this;
		run();
	}

	public ServerThread(String name)
	{
		this.name = name;
		st = this;
		run();
	}
	public void run()
	{
		t = new Thread(() -> {
			while(Server.getInstance().isOnline())
			{
				try
				{
					for(ServerHandler h : Server.getInstance().getAssignments(st))
					{
						try
						{
							Diagnostics.getInstance().process(h, true);
							h.run();
							try
							{
								Thread.sleep(0, 500000);
							} catch (InterruptedException e)
							{
								e.printStackTrace();
							}
							Diagnostics.getInstance().process(h, false);
						} catch (Exception e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} catch (ConcurrentModificationException e)
				{
					e.printStackTrace();
				}
				try
				{
					Thread.sleep(0, 500000);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		t.start();
	}
>>>>>>> develop
}
