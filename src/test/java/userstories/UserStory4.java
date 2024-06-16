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
import com.mascotapp.core.filter.ContentFoundPostFilter;
import com.mascotapp.core.filter.ContentLostPostFilter;
import com.mascotapp.core.filter.ContentPetPostFilter;
import com.mascotapp.core.service.matcher.ContentPostMatcher;
import com.mascotapp.core.service.socialNetwork.MockSocialNetwork;
import com.mascotapp.core.service.socialNetwork.SocialNetwork;

import java.util.Observer;

@SuppressWarnings("deprecation")
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
    	
		@SuppressWarnings("unchecked")
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
		posts.add(new Post("se encontró un perro labrador", "https://www.facebook.com/posts/161893467"));
		posts.add(new Post("perdí a mi perro , es un caniche", "https://www.facebook.com/posts/164026166"));
		mockSocialNetworkFacebook = new MockSocialNetwork(posts, "Facebook");
        Set<SocialNetwork> socialNetworks = new HashSet<>();
        socialNetworks.add(mockSocialNetworkFacebook);
        MascotAppCore core = new MascotAppCore(
            	socialNetworks, 
            	new ContentPostMatcher(), 
            	new ContentPetPostFilter(), 
            	new ContentFoundPostFilter(),
            	new ContentLostPostFilter()
            );
        mascotApp = new MascotApp(core);
        dummyObserver = new DummyObserver();
        mascotApp.addObserver(dummyObserver);
	}
	
	@Test
	public void CA_1_Nueva_Publicacion_Mascota_Encontrada_Nuevo_Match() {
		assertTrue(dummyObserver.getMatches().isEmpty());
		Post newPost = new Post("encontre un perro caniche color blanco", "https://www.facebook.com/posts/13280141");
		mockSocialNetworkFacebook.notifyNewPost(newPost);
		assertFalse(dummyObserver.getMatches().isEmpty());
	}
	
	@Test
	public void CA_2_Nueva_Publicacion_Mascota_Perdida_Nuevo_Match() {
		assertTrue(dummyObserver.getMatches().isEmpty());
		Post newPost = new Post("perdi mi perro labrador", "https://www.facebook.com/posts/1654397464");
		mockSocialNetworkFacebook.notifyNewPost(newPost);
		assertFalse(dummyObserver.getMatches().isEmpty());
	}
	
	@Test
	public void CA_3_Nueva_Publicacion_Mascota_Perdida_No_Hay_Nuevo_Match() {
		assertTrue(dummyObserver.getMatches().isEmpty());
		Post newPost = new Post("perdí un gatito negro", "https://www.facebook.com/posts/1394259383");
		mockSocialNetworkFacebook.notifyNewPost(newPost);
		assertTrue(dummyObserver.getMatches().isEmpty());
	}
	
	@Test
	public void CA_4_Nueva_Publicacion_Mascota_Encontrada_No_Hay_Nuevo_Match() {
		assertTrue(dummyObserver.getMatches().isEmpty());
		Post newPost = new Post("vi un gato siames", "https://www.facebook.com/posts/191267009");
		mockSocialNetworkFacebook.notifyNewPost(newPost);
		assertTrue(dummyObserver.getMatches().isEmpty());
	}
	
	@Test
	public void CA_5_No_Hay_Nueva_Publicacion_No_Hay_Nuevos_Matchs() {
		assertTrue(dummyObserver.getMatches().isEmpty());
		Set<Post> newPosts = new HashSet<>();
		mockSocialNetworkFacebook.notifyNewPosts(newPosts);
		assertTrue(dummyObserver.getMatches().isEmpty());
	}
	
	@Test
	public void CA_6_Nueva_Publicacion_Sin_Relacion_Mascotas_No_Hay_Nuevos_Matchs() {
		assertTrue(dummyObserver.getMatches().isEmpty());
		Post newPost = new Post("hola buen dia", "https://www.facebook.com/posts/161893467");
		mockSocialNetworkFacebook.notifyNewPost(newPost);
		assertTrue(dummyObserver.getMatches().isEmpty());
	}
}
