package userstories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mascotapp.core.MascotApp;
import com.mascotapp.core.MascotAppCore;
import com.mascotapp.core.entities.Match;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.service.matcher.SimpleMatcher;
import com.mascotapp.core.service.socialNetwork.MockSocialNetwork;
import com.mascotapp.core.service.socialNetwork.SocialNetwork;

import java.util.Observer;

public class UserStory4 {
	
	private MascotApp mascotApp;
	private Set<Post> foundPosts;
	private Set<Post> lostsPosts;
	private DummyObserver dummyObserver;
	
	// Implementación dummy de Observer
    private class DummyObserver implements Observer {
    	
    	private Set<Match> results;
    	
    	public DummyObserver() {
    		results = new HashSet<>();
    	}
    	
		@Override
		public void update(Observable o, Object arg) {
			results = (Set<Match>) arg;
		}
		
		public Set<Match> getMatches() {
			return results;
		}
    }
	
	@BeforeEach
	void setUp() {
		this.setUpMascotApp();
	}
	
	private void setUpMascotApp() {
		foundPosts = new HashSet<>();
		lostsPosts = new HashSet<>();
		SocialNetwork mockSocialNetworkFacebook = new MockSocialNetwork(foundPosts, lostsPosts, "Facebook");
        Set<SocialNetwork> socialNetworks = new HashSet<>();
        socialNetworks.add(mockSocialNetworkFacebook);
        mascotApp = new MascotApp(new MascotAppCore(socialNetworks, new SimpleMatcher()));
        dummyObserver = new DummyObserver();
        mascotApp.addObserver(dummyObserver);
	}
	
	@Test
	public void CA_1_Nuevo_Match() {
		assertTrue(dummyObserver.getMatches().isEmpty());
		Post lostPost = new Post("perdi mi perro labrador", "https://www.facebook.com/posts/1654397464");
		Post foundPost = new Post("se encontró un perro labrador", "https://www.facebook.com/posts/161893467");
		lostsPosts.add(lostPost);
		foundPosts.add(foundPost);
		mascotApp.getMatches();
		assertFalse(dummyObserver.getMatches().isEmpty());
	}
	
	@Test
	public void CA_2_No_Hay_Nuevos_Matchs() {
		assertTrue(dummyObserver.getMatches().isEmpty());
		mascotApp.getMatches();
		assertTrue(dummyObserver.getMatches().isEmpty());
	}
}
