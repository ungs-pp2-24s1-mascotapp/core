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

    public MascotApp(MascotAppCore core) {
    	this.core = core;
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
		Set<Match> results = new HashSet<>();
    	results.addAll(this.core.getMatches());
        
        setChanged();
        notifyObservers(results);
        
        return results;
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
	
	public synchronized void startScheduledTask() {
        if (scheduledFuture == null || scheduledFuture.isCancelled()) {
            scheduledFuture = scheduler.scheduleAtFixedRate(fetchMatches, 0, 5, TimeUnit.SECONDS);
        }
    }

    public synchronized void stopScheduledTask() {
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(true);
        }
    }
    
    public void shutdown() {
        stopScheduledTask();
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduler.shutdownNow();
        }
    }
}
