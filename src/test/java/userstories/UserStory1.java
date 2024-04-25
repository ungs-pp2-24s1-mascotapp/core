package userstories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mascotapp.core.entities.Match;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.service.matcher.Matcher;

public class UserStory1 {

	private Set<Post> founds;
    private Set<Post> losts;
    private Set<Post> noFounds;
    private Set<Post> noLosts;
    
	@BeforeEach
	void setUp() throws Exception { 
		founds = new HashSet<>();
		losts = new HashSet<>();
		noFounds = new HashSet<>();
		noLosts = new HashSet<>();
		
		Post lostPost1 = new Post("perdí a mi caniche", "https://www.facebook.com/posts/164026166");
		Post lostPost2 = new Post("perdi mi perro labrador", "https://www.facebook.com/posts/1654397464");
		Post lostPost3 = new Post("perdí un gatito negro", "https://www.facebook.com/posts/1394259383");
		Post lostPost4 = new Post("Ayer a las 22hs se escapó mi perro Rocky", "https://www.facebook.com/posts/172024136");
		Post lostPost5 = new Post("estoy buscando a mi caniche blanco perdido", "https://www.facebook.com/posts/16272773");
		losts.add(lostPost1);
		losts.add(lostPost2);
		losts.add(lostPost3);
		losts.add(lostPost4);
		losts.add(lostPost5);
		
		Post foundPost1 = new Post("vi un gato siames", "https://www.facebook.com/posts/191267009");
		Post foundPost2 = new Post("se encontró un perro labrador", "https://www.facebook.com/posts/161893467");
		Post foundPost3 = new Post("encontre un perro mestizo", "https://www.facebook.com/posts/165097290");
		Post foundPost4 = new Post("encontre un perro caniche color blanco", "https://www.facebook.com/posts/13280141");
		Post foundPost5 = new Post("encontré un perro con chapita de nombre Rocky", "https://www.facebook.com/posts/150314080");
		Post foundPost6 = new Post("vi un perro de raza labrador en Perón y Paunero", "https://www.facebook.com/posts/142402269");
		founds.add(foundPost1);
        founds.add(foundPost2);
        founds.add(foundPost3);
        founds.add(foundPost4);
        founds.add(foundPost5);
        founds.add(foundPost6);
	}

	@Test
	public void CA1_noCoincidencesLostAndFoundPosts() {
        Set<Post> found = new HashSet<>();
		Post foundPost = new Post("encontré un pastor alemán", "https://www.facebook.com/posts/121247009");
		found.add(foundPost);        
		
		Set<Match> noMatches = Matcher.getMatchs(found, losts);
		assertEquals(0, noMatches.size());
	}
	
	/*	
	@Test
	public void CA2_singleCoincidenceLostAndFoundPosts() {
		Set<Match> singleMatch = Matcher.getMatchs(founds, losts);
		
		Set<Match> expectedMatch = new HashSet<>();
		Post lostPost = new Post("Ayer a las 22hs se escapó mi perro Rocky", "https://www.facebook.com/posts/172024136");
		Post foundPost = new Post("encontré un perro con chapita de nombre Rocky", "https://www.facebook.com/posts/150314080");
		expectedMatch.add(new Match(foundPost, lostPost));
		
		assertEquals(expectedMatch, singleMatch);
	}
		
	@Test
	public void CA3_multipleCoincidencesLostAndFoundPosts() {   			
	
	}
	*/
	
	@Test
	public void CA4_noFoundPosts() {   	
		Set<Match> noFoundPosts = Matcher.getMatchs(noFounds, losts);		
		
		assertEquals(0, noFoundPosts.size());
	}
	
	@Test
	public void CA5_noLostAndFoundPosts() {   	
		Set<Match> noPosts = Matcher.getMatchs(noFounds, noLosts);		
		
		assertTrue(noPosts.isEmpty());
	}
}
