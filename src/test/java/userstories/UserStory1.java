package userstories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
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

public class UserStory1 {

	private Set<Post> foundPosts;
    private Set<Post> lostsPosts;
    private Set<Post> noFoundsPosts;
    private Set<Post> noLostsPosts;
    
    private MascotApp mascotApp;
    
    
	@BeforeEach
	void setUp() throws Exception {
		foundPosts = new HashSet<>();
		lostsPosts = new HashSet<>();
		noFoundsPosts = new HashSet<>();
		noLostsPosts = new HashSet<>();
		
		Post lostPost1 = new Post("perdí a mi caniche", "https://www.facebook.com/posts/164026166");
		Post lostPost2 = new Post("perdi mi perro labrador", "https://www.facebook.com/posts/1654397464");
		Post lostPost3 = new Post("perdí un gatito negro", "https://www.facebook.com/posts/1394259383");
		Post lostPost4 = new Post("Ayer a las 22hs se escapó mi perro Rocky", "https://www.facebook.com/posts/172024136");
		Post lostPost5 = new Post("estoy buscando a mi caniche blanco perdido", "https://www.facebook.com/posts/16272773");
		lostsPosts.add(lostPost1);
		lostsPosts.add(lostPost2);
		lostsPosts.add(lostPost3);
		lostsPosts.add(lostPost4);
		lostsPosts.add(lostPost5);
		
		Post foundPost1 = new Post("vi un gato siames", "https://www.facebook.com/posts/191267009");
		Post foundPost2 = new Post("se encontró un perro labrador", "https://www.facebook.com/posts/161893467");
		Post foundPost3 = new Post("encontre un perro mestizo", "https://www.facebook.com/posts/165097290");
		Post foundPost4 = new Post("encontre un perro caniche color blanco", "https://www.facebook.com/posts/13280141");
		Post foundPost5 = new Post("encontré un perro con chapita de nombre Rocky", "https://www.facebook.com/posts/150314080");
		Post foundPost6 = new Post("vi un perro de raza labrador en Perón y Paunero", "https://www.facebook.com/posts/142402269");
		foundPosts.add(foundPost1);
		foundPosts.add(foundPost2);
		foundPosts.add(foundPost3);
		foundPosts.add(foundPost4);
		foundPosts.add(foundPost5);
		foundPosts.add(foundPost6);
        
        setUpMascotApp(foundPosts, lostsPosts);
        
	}
	
	private void setUpMascotApp(Set<Post> founds, Set<Post> losts) {
		Set<Post> allPosts = new HashSet<>();
		allPosts.addAll(founds);
		allPosts.addAll(losts);
		
		SocialNetwork mockSocialNetwork = new MockSocialNetwork(allPosts, "Mock");
        Set<SocialNetwork> socialNetworks = new HashSet<>();
        socialNetworks.add(mockSocialNetwork);
        
        MascotAppCore core = new MascotAppCore(
        	socialNetworks, 
        	new ContentPostMatcher(), 
        	new ContentPetPostFilter(), 
        	new ContentFoundPostFilter(),
        	new ContentLostPostFilter()
        );
        mascotApp = new MascotApp(core);
	}

	@Test
	public void CA1_Sin_coincidencias() {
        Set<Post> founds = new HashSet<>();
		Post foundPost = new Post("encontré un pastor alemán", "https://www.facebook.com/posts/121247009");
		founds.add(foundPost);        
		
		setUpMascotApp(founds, lostsPosts);
		assertTrue(mascotApp.getMatches().isEmpty());
	}
	
	@Test
	public void CA2_Una_coincidencia() {
		Post lostPost = new Post("perdi mi perro labrador", "https://www.facebook.com/posts/1654397464");
		Post foundPost = new Post("vi un perro de raza labrador en Perón y Paunero", "https://www.facebook.com/posts/142402269");
		Set<Post> founds = new HashSet<>();
		founds.add(foundPost);
		setUpMascotApp(founds, lostsPosts);
		
		Set<Match> setWithSingleMatch = mascotApp.getMatches();
		Match singleMatch =  setWithSingleMatch.iterator().next();
		Match expectedMatch = new Match(foundPost, lostPost);
		
		assertEquals(expectedMatch, singleMatch);
		assertEquals(1, setWithSingleMatch.size());
	}
		
	@Test
	public void CA3_Multiples_coincidencias() {
		assertEquals(3, mascotApp.getMatches().size());
	}
	
	@Test
	public void CA4_Sin_publicaciones_de_encontrados() {   
		setUpMascotApp(noFoundsPosts, lostsPosts);
		assertTrue(mascotApp.getMatches().isEmpty());
	}
	
	@Test
	public void CA5_Sin_publicaciones_de_encontrados_y_perdidos() {
		setUpMascotApp(noFoundsPosts, noLostsPosts);
		assertTrue(mascotApp.getMatches().isEmpty());
	}
}
