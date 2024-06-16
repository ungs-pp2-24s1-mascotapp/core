package com.mascotapp.core;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.mascotapp.core.entities.Match;
import com.mascotapp.core.service.socialNetwork.SocialNetworkInfo;

@SuppressWarnings("deprecation")
public class MascotApp extends Observable {
	
	private final MascotAppCore core;
	private final Runnable fetchMatches;
	private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> scheduledFuture;
    private final long defaultInitialDelay = 0;
    private final long defaultPeriod = 5;
    private final TimeUnit defaultTimeUnit = TimeUnit.SECONDS;
    
    private long period = defaultPeriod;
    private TimeUnit timeUnit = defaultTimeUnit;

    public MascotApp(MascotAppCore core) {
    	this.core = core;
    	this.core.setMascotApp(this);
    	this.scheduler = Executors.newScheduledThreadPool(1);
        this.fetchMatches = () -> {
            try {
                getMatches();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        
        // Registra el shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
	}

	public Set<Match> getMatches() {
		Set<Match> matches = new HashSet<>();
    	matches.addAll(this.core.getMatches());
        
    	notifyMatches(matches);
        
        return matches;
    }
	
	void notifyMatches(Set<Match> matches) {
		setChanged();
        notifyObservers(matches);
	}
	
	public Set<SocialNetworkInfo> getSocialNetworks() {
    	return this.core.getSocialNetworks();
    }
	
	public void activateSocialNetwork(String name) {
		this.core.activateSocialNetwork(name);
	}
	
	public void deactivateSocialNetwork(String name) {
		this.core.deactivateSocialNetwork(name);
	}
	
	public synchronized void startService() {
        if (canStart()) {
        	this.period = defaultPeriod;
        	this.timeUnit = defaultTimeUnit;
        	schedule(defaultInitialDelay, period, timeUnit);
        }
    }
	
	public synchronized void startService(long initialDelay, long period, TimeUnit timeUnit) {
		if (canStart()) {
			this.period = period;
			this.timeUnit = timeUnit;
			schedule(initialDelay, period, timeUnit);
		}
	}

    public synchronized void stopService() {
        if (canStop()) {
            scheduledFuture.cancel(true);
        }
    }
    
    private void schedule(long initialDelay, long period, TimeUnit timeUnit) {
    	scheduledFuture = scheduler.scheduleAtFixedRate(fetchMatches, initialDelay, period, timeUnit);
    }
    
    private boolean canStart() {
    	return scheduledFuture == null || scheduledFuture.isCancelled();
    }
    
    private boolean canStop() {
    	return scheduledFuture != null && !scheduledFuture.isCancelled();
    }
    
    public void shutdown() {
        stopService();
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(period, timeUnit)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
}
