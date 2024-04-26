package userstories;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mascotapp.core.MascotApp;
import com.mascotapp.core.MascotAppCore;
import com.mascotapp.core.MascotAppFactory;
import com.mascotapp.core.entities.Match;
import com.mascotapp.core.entities.Post;
import com.mascotapp.core.service.dataprovider.MockPetDataProvider;
import com.mascotapp.core.service.dataprovider.PetDataProvider;

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
	
	private void setUpMascotApp(Set<Post> foundPets, Set<Post> lostPets) {
		PetDataProvider mockDataProvider = new MockPetDataProvider(foundPets, lostPets);
        Set<PetDataProvider> providers = new HashSet<>();
        providers.add(mockDataProvider);
        mascotApp = new MascotApp(new MascotAppCore(providers));
	}
	
	private void setUpMascotAppWithFactory(String path) throws FileNotFoundException {
		mascotApp =  MascotAppFactory.create(path);
	}

	@Test
	public void CA1_noCoincidencesLostAndFoundPosts() {
        Set<Post> founds = new HashSet<>();
		Post foundPost = new Post("encontré un pastor alemán", "https://www.facebook.com/posts/121247009");
		founds.add(foundPost);        
		
		setUpMascotApp(founds, lostsPosts);
		assertTrue(mascotApp.getMatches().isEmpty());
	}
	
	@Test
	public void CA2_singleCoincidenceLostAndFoundPosts() {
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
	public void CA3_multipleCoincidencesLostAndFoundPosts() {   			
		assertEquals(4, mascotApp.getMatches().size());
	}
	
	@Test
	public void CA4_noFoundPosts() {   
		setUpMascotApp(noFoundsPosts, lostsPosts);
		Set<Match> noFoundPosts = mascotApp.getMatches();
		assertTrue(noFoundPosts.isEmpty());
	}
	
	@Test
	public void CA5_noLostAndFoundPosts() {
		setUpMascotApp(noFoundsPosts, noLostsPosts);
		Set<Match> noPosts = mascotApp.getMatches();
		assertTrue(noPosts.isEmpty());
	}
	
	@Test
	public void CA6_nullLostAndnullFoundPosts() {
		setUpMascotApp(null, null);
		assertThrows(NullPointerException.class, () -> mascotApp.getMatches());
	}
	
	@Test
	public void CA7_multipleCoincidencesLostAndFoundPostsFromTwoSocialNetworks() throws FileNotFoundException {
		setUpMascotAppWithFactory("src/test/resources/US1");
		assertEquals(6, mascotApp.getMatches().size());
	}
}
