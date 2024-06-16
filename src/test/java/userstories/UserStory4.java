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
import com.mascotapp.core.filter.ContentPostFilter;
import com.mascotapp.core.service.matcher.ContentPostMatcher;
import com.mascotapp.core.service.socialNetwork.MockSocialNetwork;
import com.mascotapp.core.service.socialNetwork.SocialNetwork;

import java.util.Observer;

public class UserStory4 {
	
	private MascotApp mascotApp;
	private Set<Post> posts;
	private DummyObserver dummyObserver;
	private SocialNetwork mockSocialNetworkFacebook;
	
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
		posts = new HashSet<>();
		mockSocialNetworkFacebook = new MockSocialNetwork(posts, "Facebook");
        Set<SocialNetwork> socialNetworks = new HashSet<>();
        socialNetworks.add(mockSocialNetworkFacebook);
        MascotAppCore core = new MascotAppCore(
            	socialNetworks, 
            	new ContentPostMatcher(), 
            	new ContentPostFilter(), 
            	new ContentPostFilter(),
            	new ContentPostFilter()
            );
        mascotApp = new MascotApp(core);
        dummyObserver = new DummyObserver();
        mascotApp.addObserver(dummyObserver);
	}
	
	@Test
	public void CA_1_Nuevo_Match() {
		assertTrue(dummyObserver.getMatches().isEmpty());
		Post lostPost = new Post("perdi mi perro labrador", "https://www.facebook.com/posts/1654397464");
		Post foundPost = new Post("se encontró un perro labrador", "https://www.facebook.com/posts/161893467");
		Set<Post> newPosts = new HashSet<>();
		newPosts.add(lostPost);
		newPosts.add(foundPost);
		mockSocialNetworkFacebook.notifyNewPosts(newPosts);
		assertFalse(dummyObserver.getMatches().isEmpty());
	}
	
	@Test
	public void CA_2_No_Hay_Nuevos_Matchs() {
		assertTrue(dummyObserver.getMatches().isEmpty());
		Post lostPost = new Post("perdi mi collar", "https://www.facebook.com/posts/1654397464");
		Post foundPost = new Post("encontre mi collar", "https://www.facebook.com/posts/161893467");
		Set<Post> newPosts = new HashSet<>();
		newPosts.add(lostPost);
		newPosts.add(foundPost);
		mockSocialNetworkFacebook.notifyNewPosts(newPosts);
		assertTrue(dummyObserver.getMatches().isEmpty());
	}
}
